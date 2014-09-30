//
//  Skill.cpp
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-4.
//
//

#include "Skill.h"
Skill* Skill::create(int skillID)
{
    Skill* pRet=new Skill();
    if(pRet && pRet->init(skillID))
    {
        pRet->autorelease();
        return pRet;
    }else{
        delete pRet;
        pRet = NULL;
        return NULL;
    }
}

bool Skill::init(int skillID)
{
    this->skillID=skillID;
    this->isReady=false;
    //被动技能走CD
    XSkill* xskill=XSkill::record(Value(skillID));
    if(xskill->getType()==1){
        Director::getInstance()->getScheduler()->schedule(SEL_SCHEDULE(&Skill::coldDown),this,xskill->getTriggerParam()/10000.0,false);
    }
    return true;
}

void Skill::coldDown(float dt)
{
    this->setIsReady(true);
}

void Skill::setIsReady(bool isReady)
{
    this->isReady=isReady;
}

bool Skill::getIsReady()
{
    return this->isReady;
}

//选择策略
std::vector<int> Skill::selectStrategy(std::vector<int> arr,int num)
{
    std::vector<int> targets;
    num=MIN(num, arr.size());

    int type=1;
    switch (type) {
        case 0: //普通-  只能选中一次
        {
            std::vector<int> vec=Utils::randSeveral(num,false);
            for(int i=0;i<num;i++)
            {
                targets.push_back(arr.at(vec[i]));
            }
            break;
        }
        case 1: //随机- 可以选中多次
        {
            std::vector<int> vec=Utils::randSeveral(num,true);
            for(int i=0;i<num;i++)
            {
                targets.push_back(arr.at(vec[i]));
            }
            break;
        }
        case 2://血最少-
        {
            sort(arr.begin(),arr.end(),this->sortLessHp);
            for(int i=0;i<num;i++){
                targets.push_back(arr.at(i%arr.size()));
            }
            break;
        }
        case 3: //血最多
        {
            sort(arr.begin(),arr.end(),this->sortMoreHp);
            for(int i=0;i<num;i++){
                targets.push_back(arr.at(i%arr.size()));
            }
            break;
        }
        case 4: //离得近-
        {
            sort(arr.begin(),arr.end(),this->sortNear);
            for(int i=0;i<num;i++){
                targets.push_back(arr.at(i%arr.size()));
            }
            break;
        }
        case 5: //离得远
        {
            sort(arr.begin(),arr.end(),this->sortFar);
            for(int i=0;i<num;i++){
                targets.push_back(arr.at(i%arr.size()));
            }
            break;
        }
        default:
            log("错误的选择策略");
            break;
    }
    return targets;
}

int Skill::getType()
{
    return XSkill::record(Value(skillID))->getType();
}

int Skill::getMp()
{
    return XSkill::record(Value(skillID))->getMp();
}


bool Skill::sortFar(int pos1,int pos2)
{
    FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
    FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
    return f1->getGrid()-f2->getGrid() > 0;
}

bool Skill::sortNear(int pos1,int pos2)
{
    FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
    FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
    return f1->getGrid()-f2->getGrid() < 0;
    return 0;
}

bool Skill::sortLessHp(int pos1,int pos2)
{
    FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
    FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
    return f1->mf->data->hp<f2->mf->data->hp;
}

bool Skill::sortMoreHp(int pos1,int pos2)
{
    FighterMgr* f1=BattleMgr::getInstance()->getHero(pos1);
    FighterMgr* f2=BattleMgr::getInstance()->getHero(pos2);
    return f1->mf->data->hp>f2->mf->data->hp;
}

void Skill::stop()
{
    Director::getInstance()->getScheduler()->pauseTarget(this);
}

void Skill::resume()
{
    Director::getInstance()->getScheduler()->resumeTarget(this);
}

Skill::~Skill()
{
    Director::getInstance()->getScheduler()->unschedule(SEL_SCHEDULE(&Skill::coldDown),this);
}