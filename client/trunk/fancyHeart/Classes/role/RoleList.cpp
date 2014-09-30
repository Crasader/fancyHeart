//
//  RoleList.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-12.
//
//

#include "RoleList.h"

RoleList* RoleList::create()
{
    RoleList* roleList=new RoleList();
    if (roleList && roleList->init("publish/roleList/roleList.ExportJson")) {
        roleList->autorelease();
        return roleList;
    }
    CC_SAFE_DELETE(roleList);
    return nullptr;
}

bool RoleList::init(std::string fileName)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
    
	//如果需要对cocostudio 设计的ui进行调整
    this->btnCall = static_cast<Button*>(layout->getChildByName("btnCall"));
    this->progress = static_cast<Widget*>(layout->getChildByName("progress"));
    Button* searchBtn = static_cast<Button*>(layout->getChildByName("searchBtn"));
    
    //模版
    Widget* item=static_cast<Widget*>(layout->getChildByName("item"));
    item->removeFromParent();
    Size widgetSize = Director::getInstance()->getWinSize();
    this->rotateList = RotateList::create();
    this->rotateList->setSize(Size(widgetSize.width, widgetSize.height));
    this->rotateList->addEventListener(CC_CALLBACK_3(RoleList::rotateListCallback,this));
    //传进去单个模版
    this->rotateList->setItemModel(item,widgetSize.width,widgetSize,113.6);//widgetSize.width/9);
    this->addChild(this->rotateList,0);
    float y =widgetSize.height-item->getContentSize().height;
    this->rotateList->setPosition(Vec2(0,y));
    
    //滚动条
    Slider* slider=static_cast<Slider*>(layout->getChildByName("slider"));
    this->rotateList->setSlider(slider);
    
    Button* returnBtn = static_cast<Button*>(layout->getChildByName("returnBtn"));
    Button* propInfoBtn = static_cast<Button*>(this->progress->getChildByName("propInfoBtn"));
    returnBtn->addTouchEventListener(CC_CALLBACK_2(RoleList::touchEvent, this));
    this->btnCall->addTouchEventListener(CC_CALLBACK_2(RoleList::touchEvent, this));
    
    searchBtn->removeFromParent();
    this->addChild(searchBtn,1);
    searchBtn->addTouchEventListener(CC_CALLBACK_2(RoleList::touchEvent, this));
    propInfoBtn->addTouchEventListener(CC_CALLBACK_2(RoleList::touchEvent, this));
    
    //表中所有可召唤的武将
    LoginResp* loginResp = Manager::getInstance()->getRoleData();
    //对已被召唤的角色进行排序
    
    
    //对未被召唤的角色进行排序
    XRole* xRole = XRole::record(Value(0));
    for (rapidjson::GenericValue<rapidjson::UTF8<> >::MemberIterator it=xRole->doc.MemberonBegin(); it != xRole->doc.MemberonEnd(); it++ )
    {
        //item：得到表中一条条数据
        rapidjson::Value& item=it->value;
        bool isHave = false;
        for (int i=0; i<loginResp->npclist_size(); i++) {
            PNpc pnpc=loginResp->npclist(i);
            if (pnpc.spriteid()==item["id"].GetInt()) {
                this->listItems.push_back(RoleItemData{pnpc.spriteid(),3,pnpc.level(),pnpc.quality(),pnpc.star()});
                isHave=true;
                break;
            }
            
        }
        if (isHave||item["called"].GetInt() != 1) {
            continue;
        }
        PItem* haveProp = Manager::getInstance()->getPropItem(item["propId"].GetInt());
        int haveNum = haveProp == NULL?0:haveProp->itemnum();
        //需要召唤石的数量
        int needNum =item["calledNum"].GetInt();
        this->listItems.push_back(RoleItemData{item["id"].GetInt(),haveNum>=needNum?2:1,1,0,item["star"].GetInt()});
    }
    sort(this->listItems.begin(), this->listItems.end(), this->sortHandler);
    //此处传进去的应该是表中可招募武将的长度
    this->rotateList->setNum(int(this->listItems.size()));
	return true;
}

void RoleList::onEnter()
{
    BaseUI::onEnter();
}

bool RoleList::sortHandler(RoleItemData data1,RoleItemData data2)
{
    if (data1.type!=data2.type) {
        return data1.type>data2.type;
    }else if(data1.level!=data2.level) {
        return data1.level>data2.level;
    }else if (data1.quality!=data2.quality){
        return data1.quality>data2.quality;
    }else if (data1.star!=data2.star){
        return data1.star>data2.star;
    }else{
        return data1.spriteId>data2.spriteId;
    }
}

//设置单个模版的数据显示
void RoleList::setItemData(Widget* item,RoleItemData itemData,int index)
{
    Text* levelLabel=static_cast<Text*>(item->getChildByName("levelLabel"));
    Text* nameLabel=static_cast<Text*>(item->getChildByName("nameLabel"));
    ImageView*icon = static_cast<ImageView*>(item->getChildByName("icon"));
    ImageView* roleFrame=static_cast<ImageView*>(item->getChildByName("roleFrame"));
    
    //遮罩
    Mask* mask=static_cast<Mask*>(item->getChildByName("mask"));
    if (!mask) {
        mask=Mask::create(item->getSize());
        mask->setName("mask");
        item->addChild(mask);
        mask->setTouchEnabled(true);
        mask->setEnabled(false);
    }
    //如果此数据不包含在服务器给的数据中，那么模版显示
    if (itemData.type !=3) {
        mask->show();
        levelLabel->setVisible(false);
        
    }//如果此数据包含在服务器给的数据中，那么模版不显示
    else{
        mask->hide();
        levelLabel->setVisible(true);
        levelLabel->setString(Value(itemData.level).asString());
    }
    
    roleFrame->loadTexture("card_"+Value(Manager::getInstance()->Qualitys[itemData.quality].color).asString()+".png",TextureResType::LOCAL);
    XRole* xRole = XRole::record(Value(itemData.spriteId));
    icon->loadTexture("grade_icon_"+Value(xRole->getPos()).asString()+".png");
    nameLabel->setString(Value(xRole->getName()).asString());
    //星级的显示
    for (int i = 1; i<7; i++) {
        ImageView*star = static_cast<ImageView*>(item->getChildByName("star"+Value(i).asString()));
        star->loadTexture(i<=itemData.star?"star_2.png":"star_1.png");
    }
}

void RoleList::touchEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if(type!=TouchEventType::ENDED){
        return;
    }
    switch (btn->getTag()) {
        case 12161://返回按钮
        {
            this->clear(true);
            break;
        }
        case 12183://召唤按钮
        {
            //记录当前点击的召唤的卡牌，成功后将其卡牌上的遮罩去掉
            this->currentCalledItem = this->middleItem;
            
            //向服务器发送请求
            PCallHero pCallHero;
            pCallHero.set_heroid(middleItemData.spriteId);//->getId());
            Manager::getInstance()->socket->send(C_CALLHERO, &pCallHero);
            break;
        }
        case 13223://搜索按钮
        {
            if (this->middleItemData.type==3) {
                Role*role = Role::create(this->middleItemData.spriteId);
                role->show(this);
            }else{
                this->currentCalledItem = this->middleItem;
                CheckRole*checkRole = CheckRole::create(this->middleItemData.spriteId);
                checkRole->show(this);
            }
            break;
        }
        case 12547://召唤石获得途径按钮
        {
            
            break;
        }
            
        default:
            break;
    }
}

void RoleList::rotateListCallback(RotateList::EventType type,Widget*item,int index)
{
    RoleItemData data= this->listItems.at(index);
    XRole* xRole;
    switch (type)
    {
        case RotateList::EventType::SCROLL_MIDDLE:
        {
            xRole = XRole::record(Value(data.spriteId));
            this->middleItem = item;
            this->middleItemData = data;//xRole;
            //如果此时的角色未召唤,并且召唤石足够，那么召唤石进度条变没，出现召唤按钮
            Text* progressLabel = static_cast<Text*>(this->progress->getChildByName("progressLabel"));
            //进度条
            LoadingBar* progressBar = static_cast<LoadingBar*>(this->progress->getChildByName("progressBar"));
            //已有召唤石的数量，到背包中查看,如果为null，说明背包中没有此道具
            PItem* haveProp = Manager::getInstance()->getPropItem(xRole->getPropId());
            int haveNum = haveProp == NULL?0:haveProp->itemnum();
            //需要召唤石的数量
            int needNum =xRole->getCalledNum();
            progressLabel->setString(Value(haveNum).asString()+"/"+Value(needNum).asString());
            //如果已有召唤石数量等于需要召唤石数量，那么进度条消失，召唤石按钮出现
            this->btnCall->setVisible((haveNum >= needNum && data.type!=3)?true:false);
            this->progress->setVisible((haveNum >= needNum || data.type==3)?false:true);
            if (needNum != 0) {
                progressBar->setPercent(float(haveNum*100/needNum));
            }
            break;
        }
        case RotateList::EventType::TOUCH_ITEM:
        {
            if (data.type==3) {
                Role*role = Role::create(data.spriteId);
                role->show(this);
            }else{
                this->currentCalledItem = this->middleItem;
                CheckRole*checkRole = CheckRole::create(data.spriteId);
                checkRole->show(this);
            }
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

void RoleList::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_COMMONMSG:
            {
                PCommonResp pCommonResp;
                pCommonResp.ParseFromArray(msg->bytes, msg->len);
                if(pCommonResp.resulttype()==C_CALLHERO){
                    //status:0成功
                    if (pCommonResp.status()!=0) {
//                        Manager::getInstance()->showMsg("");
                    }
                }
            }
                break;
            case C_ADDORREMOVENPC:
            {
                Mask* mask=static_cast<Mask*>(this->currentCalledItem->getChildByName("mask"));
                if(mask->isVisible()){
                    mask->hide();
                }
            }
                break;
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void RoleList::onExit()
{
    BaseUI::onExit();
}