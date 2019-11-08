package com.swingy.model.heroes;

import com.swingy.model.heroes.Hero;

public class Soldiers implements Character {

    @Override
    public void CreateHeroStats(Hero hero) {
        hero.setHeroclass("Soldier");
        hero.setLevel(1);
        hero.setExperience(0);
        hero.setAttack(3);
        hero.setDefense(8);
        hero.setHP(90);
        hero.setMaxHp(90);
    }

}
