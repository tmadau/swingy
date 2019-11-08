package com.swingy.model.heroes;

import com.swingy.model.heroes.Hero;

public class Knights implements Character {

    @Override
    public void CreateHeroStats(Hero hero) {
        hero.setHeroclass("Knight");
        hero.setLevel(1);
        hero.setExperience(0);
        hero.setAttack(5);
        hero.setDefense(10);
        hero.setHP(110);
        hero.setMaxHp(110);
    }

}
