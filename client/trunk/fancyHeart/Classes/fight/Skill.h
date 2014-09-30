//
//  Skill.h
//  fancyHeart
//
//  Created by 秦亮亮 on 14-6-4.
//
//

#ifndef __fancyHeart__Skill__
#define __fancyHeart__Skill__

#include <iostream>
#include "cocos2d.h"
#include "XSkill.h"
#include "XBuff.h"
#include "XRole.h"
#include "XRoleData.h"
#include "XRoleStar.h"
#include "Buff.h"
#include "fight.pb.h"
#include "XSkillEffect.h"
using namespace cocos2d;

class Skill:public Ref
{
public:
    ~Skill();
    static Skill* create(int skillID);
    bool init(int skillID);
    std::vector<int> selectStrategy(std::vector<int> arr,int num=5);

    void setIsReady(bool isReady);
    bool getIsReady();
    int getType();
    bool isReady;
    int skillID;
    int getMp();
  
    void stop();
    void resume();
    
    typedef std::function<void(int,Skill*,int)> skillReady;

    
    static bool sortLessHp(int pos1,int pos2);
    static bool sortMoreHp(int pos1,int pos2);
    static bool sortNear(int pos1,int pos2);
    static bool sortFar(int pos1,int pos2);
private:
    void coldDown(float dt);
    std::vector<int> targets;
    int bounceIdx; //弹射索引
};

#endif /* defined(__fancyHeart__Skill__) */
