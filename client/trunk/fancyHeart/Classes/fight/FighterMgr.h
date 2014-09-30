//
//  FighterMgr.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-8-14.
//
//

#ifndef __fancyHeart__FighterMgr__
#define __fancyHeart__FighterMgr__

#include <iostream>
#include "cocos2d.h"
#include "VFighter.h"
#include "Skill.h"
#include "MFighter.h"
#include "FData.h"
#include "fconfig.h"

using namespace cocos2d;

class Skill;
class FData;

//管理view 和 model 处理战斗逻辑
class FighterMgr:public Ref
{
public:
    static FighterMgr* create(FData* data);
    ~FighterMgr();
    bool init(FData* data);
    void start();
    void pause();
    void attack();
    void cast();
    void hitOne(FighterMgr* npc);
    void hitAll();
    void bounce();
    int getGrid();
    
    std::vector<int> selectTarget();
private:
    void checkRun(float dt);
    void startAttack();
    void autoHeal();
public:
    int pos;
    VFighter* view;
    MFighter* mf;
    Skill* skill;
    std::vector<int> targets;
};


#endif /* defined(__fancyHeart__FighterMgr__) */
