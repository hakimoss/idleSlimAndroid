package com.mygdx.game.gamplay;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.personnages.Hero;
import com.mygdx.game.personnages.Personnages;

public class Combat {

    public void combatEntrePerso(Hero hero, Personnages personnage) {

        if (hero.isEnCombat() == true) {
            System.out.println("compteur hero : " + hero.compteurCombat);

            if(hero.hitDone == true) {
                hero.setHealth(hero.getHealth() - personnage.getDmg());
                personnage.setHealth(personnage.getHealth() - hero.getDmg());
                //System.out.println("vie du hero : "+ hero.getHealth());
                //System.out.println("vie du monstre : "+ personnage.getHealth());
                hero.hitDone = false;
            }


            if(hero.getHealth() <= 0) {
                hero.setVivant(false);

            } else if (personnage.getHealth() <= 0) {
                System.out.println("personnage mort");
                hero.setGold(hero.getGold() + personnage.getGoldValue());
                personnage.setVivant(false);
                hero.setEnCombat(false);

            }

        }
    }


}
