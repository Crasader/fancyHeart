//
//  BagSellProp.h
//  fancyHeart
//
//  Created by doteyplay on 14-8-11.
//
//

#ifndef __fancyHeart__BagSellProp__
#define __fancyHeart__BagSellProp__

#include <iostream>
#include "cocos2d.h"
#include "Manager.h"
#include "BaseUI.h"
#include "XItem.h"
#include "Bag.h"

using namespace ui;
using namespace cocos2d;
using namespace cocostudio;

class BagSellProp:public BaseUI
{
public:
	static BagSellProp* create(BaseUI* delebag,PItem itemInfo);
	virtual bool init(std::string fileName,PItem itemInfo,BaseUI* delebag);
	virtual void onEnter();
	virtual void onExit();

private:
	void touchEvent(cocos2d::Ref *pSender, TouchEventType type);

private: //私有属性
    int selectNumber;//当前选择数量
    PItem itemInfo;//存储当前被显示道具的道具信息
    BaseUI* bagPanel;
    void setData(XItem*xItem);
    Widget* infoPanel;

};
#endif /* defined(__fancyHeart__BagSellProp__) */
