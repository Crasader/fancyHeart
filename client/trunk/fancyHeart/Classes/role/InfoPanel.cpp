//
//  InfoPanel.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#include "InfoPanel.h"

InfoPanel* InfoPanel::create(int status,PNpc pNpc,int skillId)
{
    InfoPanel* infoPanel=new InfoPanel();
    if (infoPanel && infoPanel->init("publish/infoPanel/infoPanel.ExportJson",status,pNpc,skillId)) {
        infoPanel->autorelease();
        return infoPanel;
    }
    CC_SAFE_DELETE(infoPanel);
    return nullptr;
}

bool InfoPanel::init(std::string fileName,int status,PNpc pNpc,int skillId)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    
    this->pNpc = pNpc;
    this->imgBg = static_cast<Widget*>(layout->getChildByName("imgBg"));
    this->panel1 = static_cast<Widget*>(imgBg->getChildByName("panel1"));
    this->panel2 = static_cast<Widget*>(imgBg->getChildByName("panel2"));
    this->skillFrame = static_cast<Widget*>(imgBg->getChildByName("skillFrame"));
//    Widget* iconFrame2 = static_cast<Widget*>(panel1->getChildByName("iconFrame2"));
    
    Text* nameLabel = static_cast<Text*>(imgBg->getChildByName("nameLabel"));
    Text* explainLabel = static_cast<Text*>(this->skillFrame->getChildByName("explainLabel"));
    Button*sureBtn = static_cast<Button*>(imgBg->getChildByName("sureBtn"));
    sureBtn->addTouchEventListener(CC_CALLBACK_2(InfoPanel::touchBtnEvent, this));
    this->panel1->setVisible(false);
    this->panel2->setVisible(false);
    this->skillFrame->setVisible(false);
    
    this->infoVector.push_back({"atk","atk","atkRate","attackRate"});
    this->infoVector.push_back({"hp","hp","hpRate","hpRate"});
    this->infoVector.push_back({"def","df","dfRate","dfRate"});
    this->infoVector.push_back({"mDef","mDf","mDfRate","mDfRate"});
    
    if (status == 0) {//升阶
        int quality = this->pNpc.quality();
        XRole*xRole = XRole::record(Value(this->pNpc.spriteid()));
        //升阶成功后若品阶等级等于1、3、6、9时,弹出的弹窗
        if (quality == 1||quality == 3||quality == 6||quality == 9) {
            if (quality == 1) this->xSkill =XSkill::record(Value(xRole->getSkill2()));
            else if (quality == 3) this->xSkill =XSkill::record(Value(xRole->getSkill3()));
            else if (quality == 6) this->xSkill =XSkill::record(Value(xRole->getSkill4()));
            else if (quality == 9) this->xSkill =XSkill::record(Value(xRole->getSkill5()));
        }
        this->panel1->setVisible(true);
        nameLabel->setString("升阶成功");
        this->setAscend(quality == 1||quality == 3||quality == 6||quality == 9?0:1);
    }else if(status == 2){//进化
        this->panel2->setVisible(true);
        nameLabel->setString("进化成功");
        this->setEvolve(status);
    }else if(status == 3||status == 4){//技能解锁和升级
        this->skillFrame->setVisible(true);
        nameLabel->setString(status == 3?"觉醒技解锁":"技能升级");
        setSkill(status,skillId);
    }
    
	return true;
}

void InfoPanel::onEnter()
{
    BaseUI::onEnter();
}

//设置升阶数据
void InfoPanel::setAscend(int status)
{
    Text* defAfterLabel = static_cast<Text*>(this->panel1->getChildByName("defAfterLabel"));
    Widget* skillFra= static_cast<Widget*>(this->panel1->getChildByName("skillFra"));
    skillFra->setVisible(status == 0?true:false);
    //0:品阶等级等于1、3、6、9;1:品阶等级等于2、4、5、7、8
    //技能属性的显示
    static_cast<Text*>(skillFra->getChildByName("explainLabel"))->setString(status == 0?Value(this->xSkill->getDesc()).asString():"");
    
    //人物颜色框
    ImageView* panel1=static_cast<ImageView*>(this->imgBg->getChildByName("panel1"));
    ImageView* iconFrame2 = static_cast<ImageView*>(this->panel1->getChildByName("iconFrame2"));
    panel1->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[this->pNpc.quality()-1].color).asString()+".png",TextureResType::PLIST);
    iconFrame2->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[this->pNpc.quality()].color).asString()+".png",TextureResType::PLIST);
    
    //升阶前数据
    XRoleData* xRoleDataBf = XRoleData::record(Value(Value(this->pNpc.spriteid()).asString()+Value(this->pNpc.quality()-1).asString()));
    //攻击
    static_cast<Text*>(defAfterLabel->getChildByName("atkLabel"))->setString(Value(getQualityData(this->pNpc,0,0,-1)).asString());//——升阶之前的数据
    //生命
    static_cast<Text*>(defAfterLabel->getChildByName("hpLabel"))->setString(Value(getQualityData(this->pNpc,1,0,-1)).asString());
    //物防
    static_cast<Text*>(defAfterLabel->getChildByName("defLabel"))->setString(Value(getQualityData(this->pNpc,2,0,-1)).asString());
    //法防
    static_cast<Text*>(defAfterLabel->getChildByName("mDefLabel"))->setString(Value(getQualityData(this->pNpc,3,0,-1)).asString());
    
    XRoleData* xRoleDataAf = XRoleData::record(Value(Value(this->pNpc.spriteid()).asString()+Value(this->pNpc.quality()).asString()));
    //参数分别为——pNpc:角色信息，index:取哪种数据（攻击，生命，物防，法防）,status:表示的是0:升阶还是1:进化
    static_cast<Text*>(defAfterLabel->getChildByName("atkAfterLabel"))->setString(Value(getQualityData(this->pNpc,0,0,0)).asString());//升阶之后的数据npc值有变化
    static_cast<Text*>(defAfterLabel->getChildByName("hpAfterLabel"))->setString(Value(getQualityData(this->pNpc,1,0,0)).asString());
    defAfterLabel->setString(Value(getQualityData(this->pNpc,2,0,0)).asString());
    static_cast<Text*>(defAfterLabel->getChildByName("mDefAfterLabel"))->setString(Value(getQualityData(this->pNpc,3,0,0)).asString());
    
}
//设置进化数据
void InfoPanel::setEvolve(int status)
{
    ImageView* frame1= static_cast<ImageView*>(this->panel2->getChildByName("frame1"));
    ImageView* frame2= static_cast<ImageView*>(this->panel2->getChildByName("frame2"));
    //人物颜色框
    frame1->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[this->pNpc.quality()-1].color).asString()+".png",TextureResType::PLIST);
    frame2->loadTexture("frame_"+Value(Manager::getInstance()->Qualitys[this->pNpc.quality()].color).asString()+".png",TextureResType::PLIST);
    
    //进化前的属性
    XRoleData*xRoleDataBf = XRoleData::record(Value(Value(this->pNpc.spriteid()).asString()+Value(this->pNpc.quality()-1).asString()));
    static_cast<Text*>(this->panel2->getChildByName("atk"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataBf->getAtkRate())/10000)).asString());
    static_cast<Text*>(this->panel2->getChildByName("hp"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataBf->getHpRate())/10000)).asString());
    static_cast<Text*>(this->panel2->getChildByName("def"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataBf->getDfRate())/10000)).asString());
    static_cast<Text*>(this->panel2->getChildByName("mDef"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataBf->getMDfRate())/10000)).asString());
    //进化后的属性
    XRoleData*xRoleDataAf = XRoleData::record(Value(Value(this->pNpc.spriteid()).asString()+Value(this->pNpc.quality()).asString()));
    static_cast<Text*>(this->panel2->getChildByName("atkAfter"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataAf->getAtkRate())/10000)).asString());
    static_cast<Text*>(this->panel2->getChildByName("hpAfter"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataAf->getHpRate())/10000)).asString());
    static_cast<Text*>(this->panel2->getChildByName("defAfter"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataAf->getDfRate())/10000)).asString());
    static_cast<Text*>(this->panel2->getChildByName("mDefAfter"))->setString(Value(StringUtils::format("%.2f",floor(xRoleDataAf->getMDfRate())/10000)).asString());
    
    static_cast<Text*>(this->panel2->getChildByName("atkAdd"))->setString(Value(StringUtils::format("%.2f",getQualityData(this->pNpc,0,1,0) - getQualityData(this->pNpc,0,1,-1))).asString());
    static_cast<Text*>(this->panel2->getChildByName("hpAdd"))->setString(Value(StringUtils::format("%.2f",getQualityData(this->pNpc,1,1,0) - getQualityData(this->pNpc,1,1,-1))).asString());
    static_cast<Text*>(this->panel2->getChildByName("defAdd"))->setString(Value(StringUtils::format("%.2f",getQualityData(this->pNpc,2,1,0) - getQualityData(this->pNpc,2,1,-1))).asString());
    static_cast<Text*>(this->panel2->getChildByName("mDefAdd"))->setString(Value(StringUtils::format("%.2f",getQualityData(this->pNpc,3,1,0) - getQualityData(this->pNpc,3,1,-1))).asString());
    
}
//设置技能解锁和升阶界面
void InfoPanel::setSkill(int status,int skillId)
{
    Widget* skillIcon = static_cast<Widget*>(this->skillFrame->getChildByName("skillIcon"));
    Text* skillInfo= static_cast<Text*>(this->skillFrame->getChildByName("skillInfo"));
    Text* levelPercentLabel= static_cast<Text*>(this->skillFrame->getChildByName("levelPercentLabel"));
    XSkill* xSkills = XSkill::record(Value(skillId));
    skillInfo->setString(Value(xSkills->getDesc()).asString());
    //技能等级的显示
    levelPercentLabel->setString(Value(skillId%100).asString());
}

void InfoPanel::touchBtnEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    if (btn->getTag() == 12725) {//确定按钮
        this->clear(true);
    }
}
//参数分别为——pNpc:角色信息，index:取哪种数据（攻击，生命，物防，法防）,status:表示的是0:升阶还是1:进化,qualityOrStarAdd:增加数值
float InfoPanel::getQualityData(PNpc pNpc,int index,int status,int qualityOrStarAdd)
{
    int id = pNpc.spriteid();
    XItem* xItem;//
    XRoleData* xRoleData = XRoleData::record(Value(Value(id).asString()+Value(pNpc.quality()+qualityOrStarAdd).asString()));
    XRoleStar* xRoleStar =XRoleStar::record(Value(Value(id).asString()+Value(pNpc.star()+qualityOrStarAdd).asString()));
    //本品质基础数据 + 装备 + 等级 X（本品质成长率+星级成长率）
    int resuleNum=0;
    int equipNum = 0;

    if(status !=0){
        for (int i = 0; i<pNpc.equiplist_size(); i++) {
            xItem = XItem::record(Value(pNpc.equiplist(i).itemid()));
            equipNum += xItem->doc[Value(pNpc.equiplist(i).itemid()).asString().c_str()][Value(infoVector.at(index).equipProperty).asString().c_str()].GetInt();
        }
    }
    int atk =xRoleData->doc[(Value(Value(id).asString()+Value(pNpc.quality()+qualityOrStarAdd).asString())).asString().c_str()][Value(infoVector.at(index).baseNum).asString().c_str()].GetInt();
    string roleRate = StringUtils::format("%.2f",floor(xRoleData->doc[(Value(Value(id).asString()+Value(pNpc.quality()+qualityOrStarAdd).asString())).asString().c_str()][Value(infoVector.at(index).rate).asString().c_str()].GetInt())/10000);
    string starRate = StringUtils::format("%.2f",floor(xRoleStar->doc[(Value(Value(id).asString()+Value(pNpc.star()+qualityOrStarAdd).asString())).asString().c_str()][Value(infoVector.at(index).starRate).asString().c_str()].GetInt())/10000);

    resuleNum = floor(atk+equipNum+pNpc.level()*(std::atof(roleRate.c_str()) + std::atof(starRate.c_str())));
    return resuleNum;
}

void InfoPanel::onExit()
{
    BaseUI::onExit();
}