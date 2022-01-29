package com.mygdx.game.gamplay;

import com.mygdx.game.personnages.Hero;
import com.mygdx.game.personnages.Personnages;

import java.util.Random;

public class Combat {

    private Hero hero;
    public Personnages personnage;
    private int personnageHealth;

    float accumulatorTime = 0;

    public void combatEntrePersonnages(Hero hero, Personnages enemi, float deltaTime) {
        this.personnage = enemi;
        this.hero = hero;
        this.hero.compteurCombat = 0;

        //Thread chronoCombat= new Thread(this);

        while(this.hero.isVivant() == true && this.personnage.isVivant() == true) {
            //chronoCombat.start();
            combatEntrePerso(deltaTime);
        }
    }

    //   GETTERS   //
    public int getPersonnageHealth() {return personnageHealth;}

    //   SETTERS   //
    public void setPersonnageHealth(int personnageHealth) {this.personnageHealth = personnageHealth;}

    public void combatEntrePerso(float deltaTime) {
        //// besoin de faire comme un thread (timer)
        System.out.println("dans la function combat");
        while(this.hero.isEnCombat() == true) {

            System.out.println("en combat");
            this.hero.setHealth(this.hero.getHealth() - this.personnage.getDmg());
            this.personnage.setHealth(this.personnage.getHealth() - this.hero.getDmg());

            if(this.hero.getHealth() <= 0) {
                this.hero.setVivant(false);
            } else if (this.personnage.getHealth() <= 0) {
                this.hero.setGold(this.hero.getGold() + personnage.getGoldValue());
                this.personnage.setVivant(false);
                this.hero.setEnCombat(false);

            }


        }
    }

}
