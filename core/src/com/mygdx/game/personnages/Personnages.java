package com.mygdx.game.personnages;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Main;

public class Personnages {

    private int largeur, hauteur;
    private float x, y;
    protected boolean marche;

    public int compteurMarche;
    public int compteurCombat;
    public int compteurMort;

    protected boolean vivant;
    protected boolean disparait;
    protected boolean versDroite;

    protected int health;
    protected int dmg;
    protected int goldValue;

    TextureAtlas textureAtlas;

    float accumulatorTime = 0;

    public Personnages(int x, int y, int largeur, int hauteur) {

        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.marche = true;
        this.compteurMarche = 0;
        this.compteurCombat = 0;
        this.compteurMort = 0;
        this.vivant = true;
        this.versDroite = true;
        this.disparait = false;

        textureAtlas = new TextureAtlas("images.atlas");


    }




    //   GETTERS   //
    public int getLargeur() {return largeur;}

    public int getHauteur() {return hauteur;}

    public float getX() {return x;}

    public float getY() {return y;}

    public boolean isMarche() {return marche;}

    public int getCompteurMarche() {return compteurMarche;}

    public boolean isVivant() {return vivant;}

    public boolean isVersDroite() {return versDroite;}

    public int getHealth() {return health;}

    public int getDmg() {return dmg;}

    public int getGoldValue() {return goldValue;}



    public boolean isDisparait() {
        return disparait;
    }




    //   SETTERS   //
    public void setX(int x) {this.x = x;}

    public void setY(int y) {this.y = y;}

    public void setMarche(boolean marche) {this.marche = marche;}

    public void setCompteurMarche(int compteur) {this.compteurMarche = compteur;}

    public void setVivant(boolean vivant) {this.vivant = vivant;}

    public void setVersDroite(boolean versDroite) {this.versDroite = versDroite;}

    public void setHealth(int health) {this.health = health;}

    public void setDmg(int dmg) {this.dmg = dmg;}


    //   METHODS   //

    public TextureRegion marche(String nom, float deltaTime) {

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


    public TextureRegion combatImg(String nom, float deltaTime) {
        String str;
        TextureRegion texture;

        this.accumulatorTime = this.accumulatorTime + deltaTime;

        if(this.accumulatorTime >= 0.15) {
            this.compteurCombat++;
            this.accumulatorTime = 0;
        }

        if(this.compteurCombat >=5) {
            this.compteurCombat = 0;
        }

        if(this.compteurCombat == 4) {
            str = nom+"1Attaque";
        } else if(this.compteurCombat == 1) {
            str = nom+"4Attaque";
        } else {
            str = nom+"5Attaque";
        }

        texture = textureAtlas.findRegion(str);
        return texture;

    }

//    public Image mortImg(String nom, int frequence) {
//        String str;
//        Image img;
//        ImageIcon ico;
//        ActionListener listener = new TimeListener();
//        Timer timer = new Timer(1000, listener);
//        timer.start();
//        this.compteurMort++;
//
//
//
//        if(this.compteurMort == 1) {
//            str = "/images/"+nom+"1Mort.png";
//        } else if(this.compteurMort == 2) {
//            str = "/images/"+nom+"2Mort.png";
//        } else {
//            str = "/images/"+nom+"3Mort.png";
//        }
//
//        ico = new ImageIcon(getClass().getResource(str));
//        img = ico.getImage();
//        return img;
//
//    }
//
    public void deplacement() {
        if(Main.scene.getxPos() >= 0) {this.x = this.x - Main.scene.getDx();}
    }

    protected boolean contactAvant(Personnages personnage) {
        if(this.x + this.largeur < personnage.getX() || this.x + this.largeur > personnage.getX() + 5 || this.y + this.hauteur <= personnage.getY() || this.y >= personnage.getY() + personnage.getHauteur()) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean contactArriere(Personnages personnage) {
        if(this.x > personnage.getX() + personnage.getLargeur() || this.x + this.largeur < personnage.getX() + personnage.getLargeur() - 5 || this.y +this.hauteur <= personnage.getY() || this.y >= personnage.getY() + personnage.getHauteur()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean proche(Personnages personnage) {
        if((this.x > personnage.getX() - 10 && this.x < personnage.getX() + personnage.getHauteur() + 10) || (this.x + this.largeur > personnage.getX() - 10 && this.x +this.largeur < personnage.getX() + personnage.getLargeur() + 10)) {
            return true;
        } else {
            return false;
        }
    }
}
