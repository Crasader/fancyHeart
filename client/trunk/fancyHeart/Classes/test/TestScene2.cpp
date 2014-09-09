//
//  TestScene2.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-25.
//
//

#include "TestScene2.h"
#include "Clip.h"
Scene* TestScene2::createScene(){
	auto scene = Scene::create();
    scene->addChild(TestScene2::create());
	return scene;
}

bool TestScene2::init(){
	if(!BaseUI::init("publish/gate/gate.ExportJson")){
		return false;
	}
	//init ui
    auto winSize = Director::getInstance()->getWinSize();
    
    /*
    Clip* clip=Clip::create("effect/battle_Flashing box.plist", "battle_Flashing box");
    this->addChild(clip);
    clip->setPosition(Vec2(520,320));
    clip->setScale(2, 2);
    
    return true;
    */
    
    this->rotateList = RotateList::create();
//    this->rotateList->setSize(Size(widgetSize.width, widgetSize.height));
//    this->rotateList->addEventListener(CC_CALLBACK_2(Gate::rotateListCallback,this));
    
    //模版
    auto item=layout->getChildByName("item");
    item->removeFromParent();
    this->rotateList->setItemModel(item,winSize.width,Size(winSize.width,item->getContentSize().height),winSize.width/7);
    this->addChild(this->rotateList);
    this->rotateList->setPosition(Vec2(0,200));
    
    //滚动条
    auto bottom=static_cast<Widget*>(layout->getChildByName("bottom"));
    Slider* slider=static_cast<Slider*>(bottom->getChildByName("slider"));
    this->rotateList->setSlider(slider);

    this->rotateList->setNum(140);


    
	return true;
}

void TestScene2::listEvent(Ref* pSender, ScrollView::EventType type)
{
    
}

void TestScene2::selectedItemEvent(Ref *pSender, ListView::EventType type)
{
    
}
