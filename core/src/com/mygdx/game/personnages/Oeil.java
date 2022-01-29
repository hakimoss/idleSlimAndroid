package com.mygdx.game.personnages;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Oeil extends Personnages{

    TextureAtlas textureAtlas;
    TextureRegion texture;

    private int health;
    private int dmg;
    private int goldValue;

    public Oeil(int x, int y) {
        super(x, y, 10, 7);

        super.setVersDroite(false);
        super.setMarche(true);

        textureAtlas = new TextureAtlas("images.atlas");
        texture = textureAtlas.findRegion("oeil3Mort");

        this.health = 3;
        this.dmg = 1;
        this.goldValue = 3;
    }

    //   GETTERS   //
    public TextureRegion getImgEnemi() {return texture;}

    public int getHealth() {return health;}

    public int getDmg() {return dmg;}

    public int getGoldValue() {return goldValue;}


    //   SETTERS   //
    public void setHealth(int health) {this.health = health;}

    public void setDmg(int dmg) {this.dmg = dmg;}

    public void setGoldValue(int goldValue) {this.goldValue = goldValue;}
}
