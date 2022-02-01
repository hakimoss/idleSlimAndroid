package com.mygdx.game.personnages;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Main;
import com.mygdx.game.gamplay.Combat;

public class Hero extends Personnages{

    TextureAtlas textureAtlas;
    TextureRegion texture;


    private String playerName;
    private long lastUpdate;

    private int health;
    private int healthLvl;
    private int healthRegen;
    private int dmg;
    private int dmgAvantEquip;
    private int gold;
    private int dmgPrice;
    private int stageMax;
    private String email;
    private String selectedHero;
    private boolean slimHerbe;
    private boolean slimFeu;
    private boolean slimEau;



    private boolean libre;
    private boolean enCombat;

    private int compteurHealthRegen;

    public Combat combat;

    //public Item[] itemInInventory = new Item[10];

    //public Item[] equipedItem = new Item[2];

    float accumulatorTime = 0;

    public Hero(int x, int y) {
        super(x, y, 10, 7);


        textureAtlas = new TextureAtlas("images.atlas");
        texture = textureAtlas.findRegion("hero1Droite");
        this.libre = true;
        //RuneDmg runeDamage1 = new RuneDmg("RDW", "white", 3);

        this.healthLvl = 1;
        this.health = 90;
        this.healthRegen = 1;
        this.dmg = 2;
        this.dmgAvantEquip = 2;
        this.enCombat = false;
        this.gold = 0;
        this.dmgPrice = this.dmg*10;
        this.stageMax = 0;
        this.email = null;
        this.playerName = null;
        this.selectedHero = null;
        this.slimHerbe = false;
        this.slimFeu = false;
        this.slimEau = false;

//        this.itemInInventory[0] = new Item();
//        this.itemInInventory[1] = new Item();
//        this.itemInInventory[2] = new Item();
//        this.itemInInventory[3] = new Item();
//        this.itemInInventory[4] = new Item();
//        this.itemInInventory[5] = new Item();
//        this.itemInInventory[6] = new Item();
//        this.itemInInventory[7] = new Item();
//        this.itemInInventory[8] = new Item();
//        this.itemInInventory[9] = new Item();
//
//
//        this.equipedItem[0] = new RuneDmg();
//        this.equipedItem[1] = new RuneDmg();
    }

    //   GETTERS   //
    public TextureRegion getImgHero() {return texture;}

    public boolean isLibre() {return libre;}

    public int getHealth() {return health;}

    public int getDmg() {return dmg;}

    public int getDmgAvantEquip() {return dmgAvantEquip;}

    public boolean isEnCombat() {return enCombat;}

    public int getGold() {return gold;}

    public int getDmgPrice() {return dmgPrice;}

    public int getHealthLvl() {return healthLvl;}

    public int getStageMax() {return stageMax;}

    public String getEmail() {return email;}

    public String getPlayerName() {return playerName;}

    public long getLastUpdate() {return lastUpdate;}

    public String getSelectedHero() {return selectedHero;}

    public boolean isSlimHerbe() {return slimHerbe;}

    public boolean isSlimFeu() {return slimFeu;}

    public boolean isSlimEau() {return slimEau;}

    public int getHealthRegen() {return healthRegen;}

    //   SETTERS   //
    public void setLibre(boolean libre) {this.libre = libre;}

    public void setHealth(int health) {this.health = health;}

    public void setDmg(int dmg) {this.dmg = dmg;}

    public void setDmgAvantEquip(int dmgAvantEquip) {this.dmgAvantEquip = dmgAvantEquip;}

    public void setEnCombat(boolean enCombat) {this.enCombat = enCombat;}

    public void setGold(int gold) {this.gold = gold;}

    public void setDmgPrice(int dmgPrice) {this.dmgPrice = dmgPrice;}

    public void setHealthLvl(int healthLvl) {this.healthLvl = healthLvl;}

    public void setStageMax(int stageMax) {this.stageMax = stageMax;}

    public void setEmail(String email) {this.email = email;}

    public void setPlayerName(String playerName) {this.playerName = playerName;}

    public void setLastUpdate(long lastUpdate) {this.lastUpdate = lastUpdate;}

    public void setSelectedHero(String selectedHero) {this.selectedHero = selectedHero;}

    public void setSlimHerbe(boolean slimHerbe) {this.slimHerbe = slimHerbe;}

    public void setSlimFeu(boolean slimFeu) {this.slimFeu = slimFeu;}

    public void setSlimEau(boolean slimEau) {this.slimEau = slimEau;}

    public void setHealthRegen(int healthRegen) {this.healthRegen = healthRegen;}

    public void contact(Personnages personnage) {

        if(personnage.isVivant() == true) {

            if(super.contactAvant(personnage) == true || super.contactArriere(personnage) ==  true ) {
                Main.scene.setDx(0);
                this.libre = false;
                this.setMarche(false);
                this.setEnCombat(true);
            }
            if(this.isEnCombat() == true) {
                Combat combat2 = new Combat();
                this.combat = combat2;
                this.combat.combatEntrePerso(this, personnage);
            }

        }

    }

    public TextureRegion marche(String nom, float deltaTime ) {

        String str;
        TextureRegion texture;

        this.accumulatorTime = this.accumulatorTime + deltaTime;


        if(this.marche == false) {
            if(this.isVersDroite() == true) {
                str = nom+"1Droite";
            } else {
                str = nom+"1Gauche";
            }
        } else {
            if(this.accumulatorTime >= 0.15) {
                this.compteurMarche++;
                this.accumulatorTime = 0;
            }


            if(this.compteurMarche >=4) {
                this.compteurMarche = 0;
            }
            if(this.health < 90 + (healthLvl * 10)) {
                this.compteurHealthRegen++;
                if(this.compteurHealthRegen >=20) {

                    this.health = this.health + this.healthRegen;
                    this.compteurHealthRegen = 0;
                    if(this.health > 90 + (healthLvl * 10)) {
                        this.health = 90 + (healthLvl * 10);
                    }
                }
            }

            if(this.isVersDroite() == true) {
                if(this.compteurMarche == 1) {
                    str = nom+"2Droite";
                } else if(this.compteurMarche == 2) {
                    str = nom+"3Droite";
                } else if(this.compteurMarche == 3) {
                    str = nom+"4Droite";
                } else {
                    str = nom+"1Droite";
                }
            } else {
                if(this.compteurMarche == 1) {
                    str = nom+"2Gauche";
                } else if(this.compteurMarche == 2) {
                    str = nom+"3Gauche";
                } else if(this.compteurMarche == 3) {
                    str = nom+"4Gauche";
                } else {
                    str = nom+"1Gauche";
                }
            }

        }
        texture = textureAtlas.findRegion(str);
        return texture;

    }

}
