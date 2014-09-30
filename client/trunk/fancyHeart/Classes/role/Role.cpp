//
//  Role.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#include "Role.h"

Role* Role::create(int spriteId)
{
    Role* role=new Role();
    if (role && role->init("publish/roleInfo/roleInfo.ExportJson",spriteId)) {
        role->autorelease();
        return role;
    }
    CC_SAFE_DELETE(role);
    return nullptr;
}

bool Role::init(std::string fileName,int spriteId)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
    LoginResp* loginResp = Manager::getInstance()->getRoleData();
    for (int i = 0; i<loginResp->npclist_size(); i++) {
        if (loginResp->npclist(i).spriteid() == spriteId) {
            this->itemData = loginResp->npclist(i);
            break;
        }
    }

	//如果需要对cocostudio 设计的ui进行调整
    this->imgBg = static_cast<Widget*>(layout->getChildByName("img_bg"));
    this->propertyPanel = static_cast<Widget*>(imgBg->getChildByName("propertyPanel"));
    this->skillPanel = static_cast<Widget*>(imgBg->getChildByName("skillPanel"));
    this->rolePic = static_cast<Widget*>(imgBg->getChildByName("rolePic"));
    this->equipPanel = static_cast<Widget*>(imgBg->getChildByName("equipPanel"));
    Button* btnReturn = static_cast<Button*>(imgBg->getChildByName("btnReturn"));
    this->progress = static_cast<Widget*>(imgBg->getChildByName("progress"));
    Button* propInfoBtn = static_cast<Button*>(this->progress->getChildByName("propInfoBtn"));
    this->changeBtn = static_cast<Button*>(imgBg->getChildByName("changeBtn"));
    this->changeBtn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    this->desc = static_cast<Text*>(imgBg->getChildByName("desc"));
    btnReturn->setTouchEnabled(true);
    propInfoBtn->setTouchEnabled(true);
    btnReturn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    propInfoBtn->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    static_cast<Button*>(this->equipPanel->getChildByName("btnAscend"))->addTouchEventListener(CC_CALLBACK_2(Role::touchBtnEvent, this));
    //设置左边栏属性的显示
    this->setBtnVisible();
    this->rolePic->setVisible(true);
    this->desc->setVisible(false);
    
    //按钮分别为图鉴，属性，技能，派遣
    std::vector<Button*> buttons;
    std::vector<std::string> btnName={"handbookBtn","qualityBtn","skillBtn","sendBtn"};
    for (std::string name : btnName)
    {
        Button* btn=static_cast<Button*>(imgBg->getChildByName(name));
        btn->setTouchEnabled(true);
        btn->addTouchEventListener(CC_CALLBACK_2(Role::touchEvent,this));
        buttons.push_back(static_cast<Button*>(btn));
    }
    tabBar=TabBar::create(buttons);
    tabBar->retain();
    
    std::string str = Value(this->itemData.spriteid()).asString() +Value(this->itemData.quality()).asString();
    
    this->xRoleData = XRoleData::record(Value(Value(this->itemData.spriteid()).asString() +Value(this->itemData.quality()).asString()));
    if(this->xRoleData->doc[Value(Value(this->itemData.spriteid()).asString() +Value(this->itemData.quality()).asString()).asString().c_str()].IsNull()){
        return true;
    }
    //将属性存储在列表中
    this->propertyList.push_back(PropertyMess{"生命 "+Value(this->xRoleData->getHp()).asString(),"成长 "+Value(this->xRoleData->getHpRate()).asString()});
    this->propertyList.push_back(PropertyMess{"攻击 "+Value(this->xRoleData->getAtk()).asString(),"成长 "+Value(this->xRoleData->getAtkRate()).asString()});
    this->propertyList.push_back(PropertyMess{"物防 "+Value(this->xRoleData->getDf()).asString(),"成长 "+Value(this->xRoleData->getDfRate()).asString()});
    this->propertyList.push_back(PropertyMess{"法防 "+Value(this->xRoleData->getMDf()).asString(),"成长 "+Value(this->xRoleData->getMDfRate()).asString()});
    this->propertyList.push_back(PropertyMess{"闪避 "+Value(this->xRoleData->getMiss()).asString(),"0"});
    this->propertyList.push_back(PropertyMess{"爆击 "+Value(this->xRoleData->getCrh()).asString(),"0"});
    this->propertyList.push_back(PropertyMess{"生命恢复速度 "+Value(this->xRoleData->getHeal()).asString(),"0"});
    
    //存储需要装备位上的准备id
    this->equipsData = {this->xRoleData->getEquipPos1(),this->xRoleData->getEquipPos2(),this->xRoleData->getEquipPos3(),
        this->xRoleData->getEquipPos4(),this->xRoleData->getEquipPos5(),this->xRoleData->getEquipPos6()};
    for (int i = 0; i<6; i++) {
        Widget* equipPos = static_cast<Widget*>(this->equipPanel->getChildByName("equipPos"+Value(i+1).asString()));
        equipPos->addTouchEventListener(CC_CALLBACK_2(Role::touchEvent, this));
    }
    this->setData();
    
	return true;
}

void Role::onEnter()
{
    BaseUI::onEnter();
}

void Role::setData()
{
    Widget* info = static_cast<Text*>(this->imgBg->getChildByName("info"));
    //战力的显示(根据策划需求先写null，以后会有公式来算这个值)
//    static_cast<Text*>(panel->getChildByName("powerRight"))->setString("Null");
//    static_cast<Text*>(this->rolePic->getChildByName("powerLabel"))->setString("Null");
    
    static_cast<Text*>(rolePic->getChildByName("nameLabel"))->setString(Value(itemData.npcname()).asString());
    static_cast<Text*>(info->getChildByName("nameRight"))->setString(Value(itemData.npcname()).asString());
    static_cast<Text*>(rolePic->getChildByName("levelLabel"))->setString(Value(itemData.level()).asString());
    static_cast<Text*>(info->getChildByName("levelRight"))->setString(Value(itemData.level()).asString());
    //设置星级
    this->setStar();
    
    ImageView*posPic = static_cast<ImageView*>(this->rolePic->getChildByName("posPic"));
    ImageView*posPicRight = static_cast<ImageView*>(info->getChildByName("posPicRight"));
    //站位
    XRole*xRole = XRole::record(Value(this->itemData.spriteid()));
    posPic->loadTexture("grade_icon_"+Value(xRole->getPos()).asString()+".png");
    posPicRight->loadTexture("grade_icon_"+Value(xRole->getPos()).asString()+".png");
    
    //人物颜色框
    ImageView* roleFrame=static_cast<ImageView*>(this->rolePic->getChildByName("roleFrame"));
    roleFrame->loadTexture("card_"+Value(Manager::getInstance()->Qualitys[this->itemData.quality()].color).asString()+".png",TextureResType::LOCAL);
    //装备
    for (int m=0; m<6; m++) {
        Sprite*sp = Sprite::create();
        this->equips.push_back(sp);
    }
    //设置装备位装备的排列
    this->setEquips();
    //设置进度条进度以及按钮的显示
    //召唤石数量
    Text* rateLabel = static_cast<Text*>(progress->getChildByName("rateLabel"));
    LoadingBar* progressBar = static_cast<LoadingBar*>(this->progress->getChildByName("progressBar"));
    XRoleStar* xRoleStar = XRoleStar::record(Value(Value(this->itemData.spriteid()).asString()+Value(this->itemData.star()).asString()));
    
    //已有召唤石的数量，到背包中查看,如果为null，说明背包中没有此道具
    PItem* haveProp = Manager::getInstance()->getPropItem(xRole->getPropId());
    int haveNum = haveProp == NULL?0:haveProp->itemnum();
    //需要召唤石的数量
    int needNum;
    if (this->itemData.star() < 6){//进化需要召唤石数量
        needNum =xRoleStar->getItemNum();
    }else if(this->itemData.quality() >= 8 && this->itemData.star() == 6){//变异所需召唤石
        needNum =xRole->getMutationNum();
    }
    //进化需要花费
    int cost =xRoleStar->getCost();
    rateLabel->setString(Value(haveNum).asString()+"/"+Value(needNum).asString());
    //如果已有召唤石数量等于需要召唤石数量，那么进度条消失，召唤石按钮出现
    this->progress->setVisible(haveNum < needNum?true:false);
    this->changeBtn->setVisible(haveNum < needNum?false:true);
    
    if (haveNum >= needNum) {
        //当召唤石足够的时候，显示的是进化按钮，品阶为【紫+2】及星级为6星的角色才能进行变异并且召唤石是否足够，不足够则显示召唤石进度
        if (this->itemData.quality() >= 8 && this->itemData.star() == 6) {
            this->changeBtn->setTitleText("变异");
            this->equipPanel->setVisible(false);
            this->status = 1;
        }else if (this->itemData.star() < 6){
            this->changeBtn->setTitleText("进化");
            this->status = 0;
        }else if(this->itemData.quality() < 8 &&this->itemData.star()== 6){
            this->desc->setVisible(true);
            //让按钮置灰并且不可点击
            this->changeBtn->setTouchEnabled(false);
        }
    }
    if (needNum != 0) {
        progressBar->setPercent(float(haveNum*100/needNum));
    }
    
    //经验
    LoadingBar*expProgressBar = static_cast<LoadingBar*>(info->getChildByName("expProgressBar"));
    int exp = this->itemData.exp();
    int level = this->itemData.level();
    for(int i = 1;i<this->itemData.level();i++){
        exp -= XExp::record(Value(level-i))->getExp();
    }
    XExp*xExp = XExp::record(Value(this->itemData.level()));
    static_cast<Text*>(info->getChildByName("rateRight"))->setString(Value(exp).asString() +"/"+ Value(xExp->getExp()).asString());
    //进度条进度
    expProgressBar->setPercent(float(exp*100/xExp->getExp()));
}

void Role::setStar()
{
    for (int i=0; i<6; i++) {
        ImageView* star=static_cast<ImageView*>(this->rolePic->getChildByName("star"+Value(i+1).asString()));
        //右边显示的星级
        ImageView* star1=static_cast<ImageView*>(this->imgBg->getChildByName("starRight"+Value(i+1).asString()));
        star->loadTexture(i <this->itemData.star()?"star_2.png":"star_1.png");
        star1->loadTexture(i <this->itemData.star()?"star_2.png":"star_1.png");
    }
}

void Role::setEquips()
{
    if (this->xRoleData == nullptr) {
        return;
    }
    this->equipStatus.clear();
    
    Sprite*newEquip;
    //判断装备位的状态以及显示已经穿上的装备－－共五种状态：无装备，未装备，可合成，可装备，已装备
    for (int i = 0; i<6; i++) {
        //如果装备位存在道具图标，先移除道具图标
        Widget* equipPosItem = static_cast<Widget*>(this->equipPanel->getChildByName("equipPos"+Value(i+1).asString()));
        Widget* iconBox=static_cast<Widget*>(equipPosItem->getChildByName("iconBox"));
        if(iconBox)equipPosItem->removeChild(iconBox);
        
        XItem* xItem = XItem::record(Value(this->equipsData.at(i)));
        //合成表
        XCraft*xCraft = XCraft::record(Value(this->equipsData.at(i)));
        bool isHaveEquip = false;
        for (int j = 0; j<this->itemData.equiplist_size(); j++) {
            PItem item = this->itemData.equiplist(j);
            //根据服务器给的装备位置，显示道具
            if (this->itemData.equiplist(j).posid() == i) {
                isHaveEquip = true;
                this->setEquipToPos(2,i+1);
                this->equipStatus.push_back(2);
                //显示装备
                createIconBox(this->equipsData.at(i),"equipPos"+Value(i+1).asString(),i+1);
                break;
            }
        }
        //当装备位上没有穿装备
        if (!isHaveEquip) {
            PItem* haveProp = Manager::getInstance()->getPropItem(this->equipsData.at(i));
            //当背包中没有此装备的时候分为两种情况，一种是有足够的材料可合成一种是材料不够不足以合成
            if (haveProp == nullptr) {
                std::vector<int> materialsId;
                //可合成－－当角色未穿上装备及玩家的背包内没有所需的装备道具，但背包中有足以合成装备道具的材料的时候
                if (xCraft->doc[Value(this->equipsData.at(i)).asString().c_str()].IsNull() == false) {
                    bool isMayCompose = true;
                    for (int t =1; t<6; t++) {
                        if (xCraft->doc[Value(this->equipsData.at(i)).asString().c_str()][("getItem"+Value(t).asString()).c_str()].GetInt() != 0 ) {
                            
                            isMayCompose = propIsEnough(xCraft->getItem1(),xCraft->doc[Value(this->equipsData.at(i)).asString().c_str()][("getNum"+Value(t).asString()).c_str()].GetInt());
                            if (isMayCompose == false) {
                                this->setEquipToPos(1,i+1);
                                this->equipStatus.push_back(1);
                                continue;
                            }
                        }
                    }
                    this->setEquipToPos(3,i+1);
                    this->equipStatus.push_back(3);
                }//无装备--当角色未穿上装备，且玩家的背包内没有所需的装备道具时
                else{
                    this->setEquipToPos(1,i+1);
                    this->equipStatus.push_back(1);
                }
            }//未装备－－当玩家的背包内有所需的装备道具，但角色因等级小于装备等级限制，而尚未装备该道具时
            else if (haveProp!= nullptr && xItem->getMaxLv()>=itemData.level()){
                this->setEquipToPos(5,i+1);
                this->equipStatus.push_back(5);
            }//可装备－－当玩家的背包内有所需的装备道具，且角色等级与装备等级限制限制相当，且尚未装备该道具时
            else if (haveProp!= nullptr && xItem->getMaxLv()<itemData.level()){
                this->setEquipToPos(4,i+1);
                this->equipStatus.push_back(4);
            }
        }
    }
    //判断各装备位状态，如果都有装备，则升阶按钮可点击
    this->isMayPress = true;
    Button* btnAscend = static_cast<Button*>(this->equipPanel->getChildByName("btnAscend"));
    for (int m = 0; m<this->equipStatus.size(); m++) {
        if(this->equipStatus.at(m)!= 2){
            this->isMayPress = false;
            break;
        }
    }
}
bool Role::propIsEnough(int itemId,int num)
{
    PItem*item = Manager::getInstance()->getPropItem(itemId);
    if (item != nullptr) {
        if (item->itemnum() >= num) {
            return true;
        }
    }
    return false;
    
}

void Role::setEquipToPos(int index,int posIndex)
{
    //index——1:无装备,2:已装备,3:可合成，4:可装备，5:未装备
    Widget* equipPos = static_cast<Widget*>(this->equipPanel->getChildByName("equipPos"+Value(posIndex).asString()));
    ImageView* addPic = static_cast<ImageView*>(equipPos->getChildByName("addPic"));
    ImageView* statuesPic = static_cast<ImageView*>(equipPos->getChildByName("statuesPic"));

    statuesPic->loadTexture("equipStatus_"+ Value(index).asString() +".png");
    statuesPic->setVisible(index == 2?false:true);
    addPic->loadTexture(index == 1 || index == 5?"addition_1.png":"addition_2.png");
}

void Role::setBtnVisible()
{
    this->propertyPanel->setVisible(false);
    this->skillPanel->setVisible(false);
    this->rolePic->setVisible(false);
}

void Role::touchEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    switch (btn->getTag()) {
        case 10000://图鉴按钮
        {
            //设置按钮选中状态
            tabBar->setIndex(0);
            setBtnVisible();
            this->rolePic->setVisible(true);
            break;
        }
        case 10001://属性按钮
        {
            tabBar->setIndex(1);
            setBtnVisible();
            this->propertyPanel->setVisible(true);
            //数据表里如果没信息，则返回
            if (this->xRoleData == nullptr) {
                return;
            }
            for (int i = 0; i<7; i++) {
                Widget* widget = static_cast<Widget*>(this->propertyPanel->getChildByName("qualityItem_"+Value(i).asString()));
                Text* rateLabel = static_cast<Text*>(widget->getChildByName("rateLabel"));
                Widget* smallBottom = static_cast<Widget*>(widget->getChildByName("smallBottom"));
                Text* qualityLabel = static_cast<Text*>(widget->getChildByName("qualityLabel"));
                if(this->propertyList.size() !=0) qualityLabel->setString(this->propertyList.at(i).propertyNum);
                if (i!=6) {
                    if(this->propertyList.size() !=0) rateLabel->setString(this->propertyList.at(i).growUpNum);
                    smallBottom->setVisible(i<4?true:false);
                    rateLabel->setVisible(i<4?true:false);
                }
            }
            break;
        }
        case 10002://技能按钮
        {
            this->tabBar->setIndex(2);
            setBtnVisible();
            for (int i=0; i<7; i++) {
                Widget* skill=static_cast<Widget*>(skillPanel->getChildByName("skill"+Value(i+1).asString()));
                Text* levelLabel =static_cast<Text*>(skill->getChildByName("levelLabel"));
                bool boo = this->itemData.skillidlist_size()>i;
                levelLabel->setVisible(boo);
                if (boo) {
                    int skillId = this->itemData.skillidlist(i);
                    //还需要显示技能图标
                    levelLabel->setString(Value(skillId%100).asString());
                }
            }
            this->skillPanel->setVisible(true);
            break;
        }
        case 10003://派遣按钮
        {
            this->tabBar->setIndex(3);
            break;
        }
        case 20001://6个装备位
        case 20002:
        case 20003:
        case 20004:
        case 20005:
        case 20006:
        {
            this->openInfoPanel(btn->getTag()%20000 - 1);
            break;
        }
        default:
            break;
    }
}
//更新添加技能及替换技能（替换技能是因为技能升级了）
void Role::updateSkills(int skillId)
{
    XRole*xRole = XRole::record(Value(this->itemData.spriteid()));
    int skill = skillId;
    //技能未开5个等级以上
    if(skillId == 0){
        std::vector<int> skillOpen = {0,1,3,6,9};
        for (int j = 0; j<skillOpen.size(); j++) {
            if (this->itemData.quality() == skillOpen[j] && this->itemData.skillidlist_size() < j+1) {
                skill = xRole->doc[Value(this->itemData.spriteid()).asString().c_str()][("skill"+Value(j+1).asString()).c_str()].GetInt();
            }
        }
    }
    if(skill!=0){
        Manager::getInstance()->updateNpcSkills(this->itemData.npcid(),skill);
        this->itemData = *Manager::getInstance()->getNpc(this->itemData.npcid());
    }
}

void Role::openInfoPanel(int index)
{
    XItem* xItem;
    int equipIndex;
    equipIndex = this->equipStatus.at(index);
    xItem = XItem::record(Value(this->equipsData.at(index)));
    this->messageId = 2;
    EquipInfo* equipInfo = EquipInfo::create(equipIndex,xItem,this->itemData.npcid(),index,this);
    equipInfo->show();
}

void Role::touchBtnEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    switch (btn->getTag()) {
        case 12088://返回按钮
        {
            this->clear(true);
            break;
        }
        case 12305://点击后弹出召唤石获得途径窗口按钮
        {
            
            break;
        }
        case 12548://角色的升阶按钮
        {
            if (this->isMayPress) {//可以升阶
                this->messageId = 1;
                //向服务器发送请求——升阶
                PAscend pAscend;
                pAscend.set_heroid(this->itemData.npcid());
                Manager::getInstance()->socket->send(C_ASCEND, &pAscend);
            }
            break;
        }
        case 14423://判断是进化还是变异按钮
        {
            //向服务器发送请求——进化
            if (this->status == 0) {//进化
                this->messageId = 0;
                PEvolve pEvolve;
                pEvolve.set_heroid(this->itemData.npcid());
                Manager::getInstance()->socket->send(C_EVOLVE, &pEvolve);
            }else if(this->status == 1){//变异
                this->messageId = 3;
                PMutation pMutation;
                pMutation.set_heroid(this->itemData.npcid());
                Manager::getInstance()->socket->send(C_MUTATION, &pMutation);
            }
            break;
        }
            
        default:
            break;
    }
}

void Role::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_COMMONMSG:
            {
                PCommonResp pCommonResp;
                pCommonResp.ParseFromArray(msg->bytes, msg->len);
                if(pCommonResp.resulttype()==C_ASCEND){//升阶
                    //status:0成功
                    if (pCommonResp.status()!=0) {
//                        Manager::getInstance()->showMsg("");
                    }
                }else if (pCommonResp.resulttype()==C_EVOLVE){//进化
                    if (pCommonResp.status()==0) {
                    }
                }else if (pCommonResp.resulttype()==C_MUTATION){//变异
                    if (pCommonResp.status()!=0) {
                    }
                }
            }
                break;
            case C_UPROLE://更新角色
            {
                this->sucessReturn();
            }
                break;
            case C_UPITEM://更新物品
            {
                this->sucessReturn();
            }
                break;
            case C_MUTATION://变异
            {
                this->pGoldQualityUpgrade.ParseFromArray(msg->bytes, msg->len);
                if (this->pGoldQualityUpgrade.result()==0) {
                    //变异结果标示 0升到金色品质 1获得随机技能 2随机技能升级
                    if (this->pGoldQualityUpgrade.changeflag() == 0) {
                        this->setData();
                        
                    }else if (this->pGoldQualityUpgrade.changeflag() == 1||this->pGoldQualityUpgrade.changeflag() == 2) {
                        int index = this->pGoldQualityUpgrade.changeflag() == 1?3:4;
                        this->updateSkills(this->pGoldQualityUpgrade.skillid());
                        InfoPanel*infoPanel = InfoPanel::create(index,this->itemData);
                        infoPanel->show(this);
                    }
                }
            }
                break;
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void Role::createIconBox(int itemId,string parentName,int index)
{
    Widget* img=(Widget*)this->equipPanel->getChildByName(parentName);
    Widget* iconBox=Widget::create();
    Sprite* sprite=Sprite::create("prop.png");
    Sprite* spriteFrame=Sprite::create("Damascene.png");
    Sprite* maskSprite=Utils::maskedSpriteWithSprite(sprite, spriteFrame);
    maskSprite->setAnchorPoint(Vec2(0, 0));
    iconBox->addChild(maskSprite);
    maskSprite->setPosition(Vec2(3, 3));
    ImageView* iconFrame=ImageView::create();
    iconBox->addChild(iconFrame);
    XItem* xItem=XItem::record(Value(itemId));
    iconFrame->loadTexture("hero_circle_"+Value(xItem->getRate()+1).asString()+".png");
    iconFrame->setAnchorPoint(Vec2(0, 0));
    iconFrame->setTag(img->getTag());//点击此物品和点击此装备位一样弹出信息弹窗
    iconFrame->addTouchEventListener(CC_CALLBACK_2(Role::touchEvent, this));
    iconBox->setName("iconBox");

    img->addChild(iconBox);
    img->setVisible(true);
}

void Role::sucessReturn()
{
    this->itemData = *Manager::getInstance()->getNpc(this->itemData.npcid());
    if(this->messageId == 0){//进化
        this->setStar();
        //弹出进化成功弹窗
        InfoPanel*infoPanel = InfoPanel::create(2,this->itemData);
        infoPanel->show(this);
    }else if(this->messageId == 1){//升阶
        updateSkills(0);
        this->setData();
        InfoPanel*infoPanel = InfoPanel::create(0,this->itemData);
        infoPanel->show(this);
    }else if(this->messageId == 2){//穿装备
        this->setEquips();
    }else if(this->messageId == 3){//变异
        this->setData();
    }
}

void Role::onExit()
{
    this->tabBar->release();
    BaseUI::onExit();
}