//
//  Compose.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-20.
//
//

#include "Compose.h"

Compose* Compose::create(int composeId)
{
    Compose* compose=new Compose();
    if (compose && compose->init("publish/compose/compose.ExportJson",composeId)) {
        compose->autorelease();
        return compose;
    }
    CC_SAFE_DELETE(compose);
    return nullptr;
}

bool Compose::init(std::string fileName,int composeId)
{
	if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    layout->addTouchEventListener(CC_CALLBACK_2(Compose::touchEvent, this));
    this->img_bg=static_cast<Widget*>(layout->getChildByName("img_bg"));
    this->currentIndex=1;
    //this->indexs.push_back(composeId);
    for (int i=1; i<6; i++) {
         Widget* item=(Widget*)this->img_bg->getChildByName("item_"+Value(i).asString());
        item->setVisible(false);
    }
    this->createIconBox(composeId,"item_1",1);
    Widget* btn=static_cast<Widget*>(this->img_bg->getChildByName("btnCompose"));
    btn->addTouchEventListener(CC_CALLBACK_2(Compose::touchButtonEvent, this));
    this->initUi(composeId);
	return true;
}

void Compose::initUi(int composeId)
{
    XCraft* xCraft=XCraft::record(Value(composeId));
    if(xCraft->doc[Value(composeId).asString().c_str()].IsNull()){
        return;
    }
    if (this->composeId==composeId) {
        return;
    }
    this->composeId=composeId;
    this->createIconBox(composeId,"img_6",6);
    static_cast<Text*>(this->img_bg->getChildByName("txt_coin"))->setString(Value(xCraft->getCost()).asString());
    XItem* xItem=XItem::record(Value(composeId));
    static_cast<Text*>(this->img_bg->getChildByName("nameLabel"))->setString(Value(xItem->getNameID()).asString());
    for (int i=1; i<6; i++) {
        int id =xCraft->doc[Value(composeId).asString().c_str()][("item"+Value(i).asString()).c_str()].GetInt();
        Widget* img=(Widget*)this->img_bg->getChildByName("img_"+Value(i).asString());
        Text* txt= static_cast<Text*>(img->getChildByName("txt_num"));
        txt->setVisible(false);
        if (id==0) {
            continue;
        }
        int num =xCraft->doc[Value(composeId).asString().c_str()][("num"+Value(i).asString()).c_str()].GetInt();
        PItem* item=Manager::getInstance()->getPropItem(id);
        txt->setVisible(true);
        Widget* iconBox=static_cast<Widget*>(img->getChildByName("iconBox"));
        if (iconBox) {
            iconBox->removeFromParent();
        }
        this->createIconBox(id,"img_"+Value(i).asString(),i);
        //设置数量显示
        int hasNum=item?item->itemnum():0;
        this->isEnough=hasNum>=num;
        txt->setString(StringUtils::format("%d/%d",hasNum,num));
    }
}

void Compose::onEnter()
{
    BaseUI::onEnter();
}

Widget* Compose::createIconBox(int itemId,string parentName,int index)
{
    Widget* img=(Widget*)this->img_bg->getChildByName(parentName);
    Widget* iconBox=Widget::create();
    Sprite* sprite=Sprite::create("prop.png");
    Sprite* spriteFrame=Sprite::create("Damascene.png");
    Sprite* maskSprite=Utils::maskedSpriteWithSprite(sprite, spriteFrame);
    maskSprite->setAnchorPoint(Vec2(0, 0));
    iconBox->addChild(maskSprite);
    maskSprite->setPosition(Vec2(3, 3));
    ImageView* iconFrame=ImageView::create();
    iconFrame->setName("iconFrame");
    iconBox->addChild(iconFrame);
    XItem* xItem=XItem::record(Value(itemId));
    iconFrame->loadTexture("hero_circle_"+Value(xItem->getRate()+1).asString()+".png");
    iconFrame->setAnchorPoint(Vec2(0, 0));
    iconFrame->setTag(itemId*1000+index);//为了获取当前点中的物品id和index 所以做个组合
    if(parentName.find("item")==-1){
        iconFrame->addTouchEventListener(CC_CALLBACK_2(Compose::touchIconEvent, this));
    }else{
        iconFrame->addTouchEventListener(CC_CALLBACK_2(Compose::touchItemEvent, this));
    }
    
    iconBox->setName("iconBox");
    iconBox->setTag(itemId*1000+index);
    img->addChild(iconBox);
    img->setVisible(true);
    return iconBox;
}

void Compose::removeItemToEnd(int index)
{
    for (int i=index+1; i<6; i++) {
        Widget* item=(Widget*)this->img_bg->getChildByName("item_"+Value(i).asString());
        item->setVisible(false);
        Widget* iconBox=static_cast<Widget*>(item->getChildByName("iconBox"));
        if(iconBox)item->removeChild(iconBox);
    }
}

void Compose::touchEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    if (this->img_bg->hitTest(static_cast<Layout*>(pSender)->getTouchStartPos())) {
        return;
    }
    this->clear(true);
    
}

void Compose::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if(type!=TouchEventType::ENDED){
        return;
    }
    if (btn->getTag()==12647) {
        if (!this->isEnough) {
            Manager::getInstance()->showMsg("物品数量不足!");
            return;
        }
        XCraft* xCraft=XCraft::record(Value(this->composeId));
        if (Manager::getInstance()->getRoleData()->role().coin()<xCraft->getCost()) {
            Manager::getInstance()->showMsg("钱不够");
            return;
        }
        PComposeItem pComposeItem;
        pComposeItem.set_itemid(this->composeId);
        pComposeItem.set_itemnum(1);
        Manager::getInstance()->socket->send(C_COMPOSEITEM, &pComposeItem);
        //合成
    }
}

void Compose::touchIconEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    Widget* widget=static_cast<Widget*>(pSender);
    int itemId=widget->getTag()/1000;
    int index=widget->getTag()%1000;
    if (index>5) {
        return;
    }
    XCraft* xCraft=XCraft::record(Value(itemId));
    if(xCraft->doc[Value(itemId).asString().c_str()].IsNull()){
        return;
    }
    this->currentIndex=index+1;
    this->createIconBox(itemId,"item_"+Value(this->currentIndex).asString(),this->currentIndex);
    this->initUi(static_cast<Widget*>(pSender)->getTag()/1000);
    
}

void Compose::touchItemEvent(Ref *pSender, TouchEventType type)
{
    if (type!=TouchEventType::ENDED) {
        return;
    }
    Widget* widget=static_cast<Widget*>(pSender);
    int index=widget->getTag()%1000;
    this->removeItemToEnd(index);
    this->initUi(static_cast<Widget*>(pSender)->getTag()/1000);
    
}

void Compose::initNetEvent(){
    auto listener = EventListenerCustom::create(NET_MESSAGE, [=](EventCustom* event){
        NetMsg* msg = static_cast<NetMsg*>(event->getUserData());
        switch (msg->msgId)
        {
            case C_UPITEM:
            {
                this->currentIndex=max(1, this->currentIndex-1);
                this->composeId=0;
                this->removeItemToEnd(this->currentIndex);
                Widget* item=(Widget*)this->img_bg->getChildByName("item_"+Value(this->currentIndex).asString());
                Widget* iconBox=static_cast<Widget*>(item->getChildByName("iconBox"));
                this->initUi(iconBox->getTag()/1000);
                break;
                
            }
            case C_COMMONMSG:
            {
                PCommonResp pCommonResp;
                pCommonResp.ParseFromArray(msg->bytes, msg->len);
                if(pCommonResp.resulttype()==C_COMPOSEITEM){
                    if (pCommonResp.status()!=0) {
                        Manager::getInstance()->showMsg("合成失败,物品数量不足或者钱不够");
                    }                }
                break;
            }
            default:
                break;
        }
    });
    Director::getInstance()->getEventDispatcher()->addEventListenerWithSceneGraphPriority(listener, this);
}

void Compose::onExit()
{
    BaseUI::onExit();
}