//
//  RoleAllList.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-28.
//
//

#include "RoleAllList.h"

RoleAllList* RoleAllList::create()
{
    RoleAllList* roleAllList=new RoleAllList();
    if (roleAllList && roleAllList->init("publish/roleAllList/roleAllList.ExportJson")) {
        roleAllList->autorelease();
        return roleAllList;
    }
    CC_SAFE_DELETE(roleAllList);
    return nullptr;
}

bool RoleAllList::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
    
	//如果需要对cocostudio 设计的ui进行调整
    //模版
    Widget* item=static_cast<Widget*>(layout->getChildByName("item"));
    item->removeFromParent();
    Size widgetSize = Director::getInstance()->getWinSize();
    this->rotateList = RotateList::create();
    this->rotateList->setSize(Size(widgetSize.width, widgetSize.height));
    this->rotateList->addEventListener(CC_CALLBACK_3(RoleAllList::rotateListCallback,this));
    //传进去单个模版
    this->rotateList->setItemModel(item,widgetSize.width,widgetSize,widgetSize.width/7);
    this->addChild(this->rotateList);
    float y =widgetSize.height-item->getContentSize().height;
    this->rotateList->setPosition(Vec2(0,y));
    
    Widget* btnPanel=static_cast<Widget*>(layout->getChildByName("btnPanel"));
//    btnPanel->removeFromParent();
//    this->addChild(btnPanel,1);
    static_cast<Button*>(btnPanel->getChildByName("allBtn"))->addTouchEventListener(CC_CALLBACK_2(RoleAllList::touchButtonEvent, this));
    static_cast<Button*>(btnPanel->getChildByName("frontBtn"))->addTouchEventListener(CC_CALLBACK_2(RoleAllList::touchButtonEvent, this));
    static_cast<Button*>(btnPanel->getChildByName("middleBtn"))->addTouchEventListener(CC_CALLBACK_2(RoleAllList::touchButtonEvent, this));
    static_cast<Button*>(btnPanel->getChildByName("backBtn"))->addTouchEventListener(CC_CALLBACK_2(RoleAllList::touchButtonEvent, this));

    Button* returnBtn = static_cast<Button*>(layout->getChildByName("returnBtn"));
    returnBtn->addTouchEventListener(CC_CALLBACK_2(RoleAllList::touchButtonEvent, this));
    
    LoginResp* loginResp = Manager::getInstance()->getRoleData();
    for (int i=0; i<loginResp->npclist_size(); i++) {
        PNpc pnpc=loginResp->npclist(i);
        pNpcs.push_back(pnpc);
    }
    //角色排序
    sort(this->pNpcs.begin(), this->pNpcs.end(), this->sortHandler);

    //此处传进去的应该是表中可招募武将的长度
    this->rotateList->setNum(int(this->pNpcs.size()));
    
    //当前拥有多少角色/最多可拥有多少角色
//    static_cast<Text*>(layout->getChildByName("progress"))->setString( /npclist_size());
	return true;
}

void RoleAllList::onEnter()
{
    BaseUI::onEnter();
}

bool RoleAllList::sortHandler(PNpc data1,PNpc data2)
{
    if(data1.level()!=data2.level()) {
        return data1.level()>data2.level();
    }else if (data1.quality()!=data2.quality()){
        return data1.quality()>data2.quality();
    }else if (data1.star()!=data2.star()){
        return data1.star()>data2.star();
    }else{
        return data1.npcid()>data2.npcid();
    }
}

//设置单个模版的数据显示
void RoleAllList::setItemData(Widget* item,PNpc itemData,int index)
{
    Text* levelLabel=static_cast<Text*>(item->getChildByName("levelLabel"));
    Text* nameLabel=static_cast<Text*>(item->getChildByName("nameLabel"));
    ImageView*icon = static_cast<ImageView*>(item->getChildByName("icon"));
    ImageView* roleFrame=static_cast<ImageView*>(item->getChildByName("roleFrame"));
    
    roleFrame->loadTexture("card_"+Value(Manager::getInstance()->Qualitys[itemData.quality()].color).asString()+".png",TextureResType::LOCAL);
    XRole* xRole = XRole::record(Value(itemData.spriteid()));
    icon->loadTexture("grade_icon_"+Value(xRole->getPos()).asString()+".png");
    nameLabel->setString(Value(xRole->getName()).asString());
    //星级的显示
    for (int i = 1; i<7; i++) {
        ImageView*star = static_cast<ImageView*>(item->getChildByName("star"+Value(i).asString()));
        star->loadTexture(i<=itemData.star()?"star_2.png":"star_1.png");
    }
}

void RoleAllList::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if (!btn||type!=TouchEventType::ENDED) {
        return;
    }
    switch (btn->getTag()) {
        case 20496://全部
        {
            this->currentPos=-1;
            this->initList();
            break;
        }
        case 20497://前排
        {
            this->currentPos=0;
            this->initList();
            break;
        }
        case 20498://中排
        {
            this->currentPos=1;
            this->initList();
            break;
        }
        case 20499://后排
        {
            this->currentPos=2;
            this->initList();
            break;
        }
        case 20495://返回按钮
            this->clear(true);
            break;
        default:
            break;
    }
}

void RoleAllList::initList()
{
    this->pNpcs.clear();
    for(int i=0;i<Manager::getInstance()->getRoleData()->npclist_size();i++){
        PNpc pNpc=Manager::getInstance()->getRoleData()->npclist(i);
        if (this->currentPos==-1) {
            this->pNpcs.push_back(pNpc);
        }else{
            XRole* xRole=XRole::record(Value(pNpc.spriteid()));
            if (xRole->getPos()==this->currentPos) {
                this->pNpcs.push_back(pNpc);
            }
        }
    }
    //角色排序
    sort(this->pNpcs.begin(), this->pNpcs.end(), this->sortHandler);
    this->rotateList->setNum(this->pNpcs.size());
}

void RoleAllList::rotateListCallback(RotateList::EventType type,Widget*item,int index)
{
    PNpc data= this->pNpcs.at(index);
    switch (type)
    {
        case RotateList::EventType::SCROLL_MIDDLE:
        {
//            this->middleItem = item;
//            this->middleItemData = data;
            break;
        }
        case RotateList::EventType::TOUCH_ITEM:
        {
            Role*role = Role::create(data.spriteid());
            role->show(this);
            break;
        }
        case RotateList::EventType::SET_ITEM_DATA:
        {
            this->setItemData(item,data,index);//传入数据
            break;
        }
        default:
            break;
    }
}

void RoleAllList::onExit()
{
    BaseUI::onExit();
}