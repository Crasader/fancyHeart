//
//  BagSellProp.cpp
//  fancyHeart
//
//  Created by doteyplay on 14-8-11.
//
//

#include "BagSellProp.h"

BagSellProp* BagSellProp::create(BaseUI* delebag,PItem itemInfo)
{
    BagSellProp* bagSellProp=new BagSellProp();
    if (bagSellProp && bagSellProp->init("publish/bagSellProp/bagSellProp.ExportJson",itemInfo,delebag)) {
        bagSellProp->autorelease();
        return bagSellProp;
    }
    CC_SAFE_DELETE(bagSellProp);
    return nullptr;
}

bool BagSellProp::init(std::string fileName,PItem itemInfo,BaseUI* delebag)
{
    if(!BaseUI::init(fileName))
    {
        return false;
    }
	//如果需要对cocostudio 设计的ui进行调整
    selectNumber= 1;
    this->itemInfo = itemInfo;
    this->bagPanel = delebag;
    this->infoPanel=static_cast<Widget*>(layout->getChildByName("infoPanel"));
    Button* btnReduce =static_cast<ui::Button*>(this->infoPanel->getChildByName("btnReduce"));
    Button* btnAdd =static_cast<ui::Button*>(this->infoPanel->getChildByName("btnAdd"));
    Button* btnAll =static_cast<ui::Button*>(this->infoPanel->getChildByName("btnAll"));
    Button* sureSellBtn =static_cast<ui::Button*>(this->infoPanel->getChildByName("sureSellBtn"));
    
    btnReduce->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    btnAdd->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    btnAll->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    sureSellBtn->addTouchEventListener(CC_CALLBACK_2(BagSellProp::touchEvent,this));
    
    Text* nameLabel = static_cast<Text*>(this->infoPanel->getChildByName("nameLabel"));
    Text* propNum =static_cast<Text*>(this->infoPanel->getChildByName("propNum"));
    Text* itemPriceTxt =static_cast<Text*>(this->infoPanel->getChildByName("itemPriceTxt"));
    
    XItem*xItem = XItem::record(Value(itemInfo.itemid()));
    itemPriceTxt ->setString(Value(xItem->getPrice()).asString());
    nameLabel->setString(Value(xItem->getNameID()).asString());
    this->setData(xItem);

	return true;
}

void BagSellProp::onEnter()
{
    BaseUI::onEnter();
}

void BagSellProp::touchEvent(cocos2d::Ref *pSender, TouchEventType type)
{
    Button* btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if(type==TouchEventType::ENDED)
    {
        XItem*xItem = XItem::record(Value(itemInfo.itemid()));
        switch (btn->getTag()) {
            case 12042://减少
            {
                if(this->selectNumber == 1){
                    return;
                }
                -- this->selectNumber;
                break;
            }
            case 12043://增加
            {
                if(this->selectNumber == this->itemInfo.itemnum()){
                    return;
                }
                ++ this->selectNumber;
                break;
            }
            case 12044://全部
            {
                this->selectNumber = this->itemInfo.itemnum();
                break;
            }
            case 12063://确认出售
            {
                this->clear(true);
                static_cast<Bag*>(this->bagPanel)->sendInfo(this->selectNumber);
                return;
                break;
            }
            default:
                break;
        }
        this->setData(xItem);
        
    }
}

void BagSellProp::setData(XItem*xItem)
{
    int gainNum = this->selectNumber * (xItem->getPrice());
    static_cast<Text*>(infoPanel->getChildByName("selectNum"))->setString(Value(this->selectNumber).asString() +"/"+Value(itemInfo.itemnum()).asString());
    static_cast<Text*>(infoPanel->getChildByName("gainCoinTxt"))->setString(Value(gainNum).asString());
}

void BagSellProp::onExit()
{
    BaseUI::onExit();
}