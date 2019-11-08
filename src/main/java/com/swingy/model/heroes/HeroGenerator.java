package com.swingy.model.heroes;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;
import com.swingy.model.items.Artifact;
import com.swingy.model.heroes.Hero;
import javax.validation.Validation;
import java.util.logging.Level;
import java.sql.ResultSet;

public class HeroGenerator {

    private Character _character;

    public HeroGenerator() {
        _character = null;
    }

    public Hero GenerateHero(String HeroClass) {
        Hero hero = new Hero();

        switch (HeroClass) {
            case "Knight":
                this._character = new Knights();
                break;
            case "Saiyan":
                this._character = new Saiyans();
                break;
            case "Soldier":
                this._character = new Soldiers();
                break;
        }
        _character.CreateHeroStats(hero);
        return hero;
    }

    public String SetNameCheck(Hero hero, String Name) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        hero.setHeroName(Name);
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        for (ConstraintViolation<Hero> violation : factory.getValidator().validate(hero)) {
            return violation.getMessage();
        }
        return "";
    }

    public Hero HeroStats(ResultSet stats) throws Exception {
        Hero hero = new Hero();
        hero.setHeroName(stats.getString("name"));
        hero.setHeroclass(stats.getString("class"));
        hero.setExperience(stats.getInt("experience"));
        hero.setDefense(stats.getInt("defense"));
        hero.setAttack(stats.getInt("attack"));
        hero.setArtifact(new Artifact(stats.getInt("artifactValue"), stats.getString("artifactName")));
        hero.setLevel(stats.getInt("level"));
        hero.setHP(stats.getInt("HP"));
        hero.setMaxHp(stats.getInt("maxHP"));
        return hero;
    }

}
