//
//  GateResult.cpp
//  fancyHeart
//
//  Created by zhai on 14-6-18.
//
//

#include "GateResult.h"

GateResult* GateResult::create(BaseUI* delegate,PResultResp pResultResp)
{
    GateResult* gateResult=new GateResult();
    if (gateResult && gateResult->init("publish/gateResult/gateResult.ExportJson",pResultResp)) {
        gateResult->autorelease();
        return gateResult;
    }
    CC_SAFE_DELETE(gateResult);
    return nullptr;
}

bool GateResult::init(std::string fileName,PResultResp pResultResp)
{
	if(!BaseUI::init(fileName))
	{
		return false;
	}
    this->pResultResp=pResultResp;
    //this->resetUI();
	return true;
}

void GateResult::onEnter()
{
    BaseUI::onEnter();
    this->resetUI();
}

void GateResult::resetUI()//初始化界面
{
    Widget* img_bg=(Widget*)layout->getChildByName("img_bg");
    Button* btn_return=(Button*)img_bg->getChildByName("btn_return");
    btn_return->addTouchEventListener(CC_CALLBACK_2(GateResult::touchButtonEvent, this));
    Text* tf_groupLvl=static_cast<Text*>(img_bg->getChildByName("tf_groupLvl"));
    Text* tf_groupExp=static_cast<Text*>(img_bg->getChildByName("tf_groupExp"));
    Text* tf_coin=static_cast<Text*>(img_bg->getChildByName("tf_coin"));
    tf_groupLvl->setString(Value(this->pResultResp.curgrouplvl()).asString());
    tf_groupExp->setString(StringUtils::format("+%d",this->pResultResp.groupexp()));
    tf_coin->setString(StringUtils::format("+%d",this->pResultResp.coin()));
    for (int i=0; i<5; i++) {
        Widget* npc=static_cast<Widget*>(img_bg->getChildByName("role_"+Value(i+1).asString()));
        Widget* item=static_cast<Widget*>(img_bg->getChildByName("item_"+Value(i+1).asString()));
        npc->setVisible(i<this->pResultResp.npcs_size());
        item->setVisible(i<this->pResultResp.items_size());
        if (i<this->pResultResp.npcs_size()) {
            PNpcRes pNpcRes=this->pResultResp.npcs(i);
            string addLevel=Value(pNpcRes.addlvl()).asString();
            string addExp=Value(pNpcRes.addexp()).asString();
            int totalLvl=pNpcRes.curlvl()+pNpcRes.addlvl();
            int oldExp=Manager::getInstance()->getCurrExp(pNpcRes.curexp(), pNpcRes.curlvl());
            int currExp=Manager::getInstance()->getCurrExp(pNpcRes.curexp()+pNpcRes.addexp(), totalLvl);
            int neddExp=XExp::record(Value(totalLvl))->getExp();
            float oldPrecent=float(oldExp*100/neddExp);
            float curPrecent=float(currExp*100/neddExp);
            static_cast<TextAtlas*>(npc->getChildByName("txt_lvl"))->setString(Value(totalLvl).asString());
            auto action = ProgressTo::create(abs(curPrecent-oldExp)/100, curPrecent);
            LoadingBar* loadingBar=static_cast<LoadingBar*>(npc->getChildByName("pro_exp"));
            loadingBar->setVisible(false);
            auto progressTimer = ProgressTimer::create(static_cast<Sprite*>(loadingBar->getVirtualRenderer()));
            progressTimer->setType(ProgressTimer::Type::BAR);
            progressTimer->setPosition(loadingBar->getPosition());
            npc->addChild(progressTimer);
            //Setup for a bar starting from the left since the midpoint is 0 for the x
            progressTimer->setMidpoint(Vec2(0,0));
            //Setup for a horizontal bar since the bar change rate is 0 for y meaning no vertical change
            progressTimer->setBarChangeRate(Vec2(1, 0));
            progressTimer->setPercentage(oldPrecent>curPrecent?0:oldExp);
            progressTimer->runAction(action);
            static_cast<Text*>(npc->getChildByName("txt_addExp"))->setString(StringUtils::format("EXP+%d",pNpcRes.addexp()));
            
            //loadingBar->setPercent(50);
            //static_cast<Text*>(npc->getChildByName("txt_exp"))->setString(Utils::getLang("EXP+{1}",params));
            //pNpcRes
        }
        if (i<this->pResultResp.items_size()) {
            PItemRes pItemRes=this->pResultResp.items(i);
            static_cast<Text*>(npc->getChildByName("txt_num"))->setString(Value(pItemRes.itemnum()).asString());
        }
    }
}

void GateResult::touchButtonEvent(Ref *pSender, TouchEventType type)
{
    auto btn=static_cast<Button*>(pSender);
    if (!btn) {
        return;
    }
    if(type==TouchEventType::ENDED){
        switch (btn->getTag()) {
            case 10611:
                Manager::getInstance()->switchScence(HomeScene::createScene());
                auto gate = Gate::create();
                gate->show();
                break;

        }
    }

}

void GateResult::onExit()
{
    BaseUI::onExit();

}

