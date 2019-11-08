package com.swingy.model.heroes;

import com.swingy.model.heroes.Hero;

public class Saiyans implements Character {

    @Override
    public void CreateHeroStats(Hero hero) {
        hero.setHeroclass("Saiyan");
        hero.setLevel(1);
        hero.setExperience(0);
        hero.setAttack(8);
        hero.setDefense(15);
        hero.setHP(150);
        hero.setMaxHp(150);
    }

}
