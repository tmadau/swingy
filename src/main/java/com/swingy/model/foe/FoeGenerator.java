package com.swingy.model.foe;

import com.swingy.model.heroes.Hero;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class FoeGenerator {

    public Foe GenerateFoe(int MapSize, ArrayList<Foe> Foes, Hero hero) {
        Random rand = new Random();
        Point position = new Point(-1, -1);
        Foe newFoe = new Foe();

        while (true) {
            position.setLocation(rand.nextInt(MapSize), rand.nextInt(MapSize));
            if (!position.equals(hero.getPosition())) {
                break;
            }
            for (Foe foe : Foes) {
                if (!foe.getPosition().equals(position)) {
                    break;
                }
            }
        }
        newFoe.setPosition(position);
        setAbility(newFoe, rand, hero.getLevel() << 1);
        return newFoe;
    }

    private void setAbility(Foe newFoe, Random rand, int factor) {
        factor = (factor == 2) ? 1 : factor;
        newFoe.setAttack((rand.nextInt(10) + 1) * factor);
        newFoe.setDefense(rand.nextInt(5) * factor);
        newFoe.setHP(rand.nextInt(90) + 10 * factor);
        newFoe.setIMGCount(rand.nextInt(7));        
    }

}
