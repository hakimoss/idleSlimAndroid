package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gamplay.Combat;
import com.mygdx.game.personnages.Hero;
import com.mygdx.game.personnages.Oeil;
import com.mygdx.game.personnages.Personnages;

import java.util.ArrayList;

import javax.swing.JProgressBar;

public class Scene implements Screen {


    //scene
    private Camera camera;
    private Viewport viewport;
    //graphics
    private SpriteBatch batch;
    public TextureAtlas textureAtlas;

    public TextureRegion background;
    private TextureRegion backgrounMenu1;
    private TextureRegion backgrounMenu2;
    private TextureRegion backgrounMenu3;
    private TextureRegion backgrounMenu4;
    private TextureRegion backgrounMenu5;
    //timing
    private float backgroundsOffset = 0;
    private float backgroundsMaxScrollingSpeed;

    //world parameters
    private final float WORLD_WIDTH = 72;
    private final float WORLD_HEIGHT = 128;
    private final float TOUCH_MOVEMENT_THRESHOLD = 0.5f;

    private float dx;
    private float xPos;
    private int yDmgString;

    public int stage;

    public ArrayList<Personnages> tabEnemi;
    public Hero hero;
    public Oeil oeil1, oeil2, oeil3, oeil4, oeil5, oeil6, oeil7, oeil8, oeil9,oeil10;

    public float xFond1;
    public float xFond2;

    public LoadingScreen ld;


    BitmapFont font;

    Scene() {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


        ld = new LoadingScreen();
        this.dx = 0;
        this.xPos = -1;
        this.stage = 1;
        this.xFond1 = 0;
        this.xFond2 = 0 + (WORLD_WIDTH);

        //set up the texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        background = new TextureRegion();

        background = textureAtlas.findRegion("stageForest");

        backgrounMenu1 = textureAtlas.findRegion("menu1");
        backgrounMenu2 = textureAtlas.findRegion("menu2");
        backgrounMenu3 = textureAtlas.findRegion("menu3");
        backgrounMenu4 = textureAtlas.findRegion("menu4");
        backgrounMenu5 = textureAtlas.findRegion("menu5");


        backgroundsMaxScrollingSpeed = (float) (WORLD_WIDTH) / 8;

        hero=new Hero(0, 95);


        if(this.hero.getStageMax() == 10) {
            //setGolem();
            tabEnemi.clear();
        } else {
            setMonster();
        }

        font = new BitmapFont();
        prepareHUD();
        batch = new SpriteBatch();

    }

    //   GETTERS   //
    public float getDx() {return dx;}

    public float getxPos() {return xPos;}

    public int getyDmgString() {return yDmgString;}

    public int getStage() {return stage;}



    //   SETTERS   //

    public void setDx(float dx) {this.dx = dx;}

    public void setxPos(float xPos) {this.xPos = xPos;}

    public void setyDmgString(int yDmgString) {this.yDmgString = yDmgString;}

    public void setStage(int stage) {this.stage = stage;}


    public void render(float deltaTime) {
        batch.begin();

        //scrolling background
        deplacementFond();

        //render Oeil
        renderEnemi(deltaTime);

        //render hero
        renderHero(deltaTime);

        //loading
        if(this.getxPos() >= (WORLD_WIDTH*2)) {
            batch.draw(ld.gainPercent(), 0, WORLD_HEIGHT / 1.5f, WORLD_WIDTH, WORLD_HEIGHT / 3);
        }

        //contact entre le hero et les enemi
        contactEtFight();

        //HUD
        menuScene();

        batch.end();
    }

    public void menuScene() {
        batch.draw(backgrounMenu1, 0, 0, WORLD_WIDTH, ((WORLD_HEIGHT / 3) * 2));

    }

    private void prepareHUD() {
        //create a BitmapFont from font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("prstart.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 82;
        //fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1,1,1,0.8f);
        //fontParameter.borderColor = new Color(0,0,0,1);


        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit world
        font.getData().setScale(0.03f);

        //calculate hud marins, etc.
//        hudVerticalMargin = font.getCapHeight() / 2;
//        hudLeftX = hudVerticalMargin;
//        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
//        hudCenterX = WORLD_WIDTH / 3;
//        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
//        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
//        hudSectionWidth = WORLD_WIDTH / 3;



    }



    public void contactEtFight() {
        for(int i = 0; i < tabEnemi.size(); i++) {
            if(this.hero.proche(this.tabEnemi.get(i))) {
                this.hero.contact(this.tabEnemi.get(i));
                if(this.tabEnemi.get(i).isVivant() == false) {
                    this.hero.setLibre(true);
                }

            }
        }
    }



    public void deplacementFond() {

        if(this.xFond1 <= -WORLD_WIDTH) {this.xFond1 = WORLD_WIDTH;}
        else if(this.xFond2 <= -WORLD_WIDTH) {this.xFond2 = WORLD_WIDTH;}

        if(this.xPos >= 0 && this.xPos <= (WORLD_WIDTH*2)) {
            this.xPos = this.xPos + this.dx;
            this.xFond1 = this.xFond1 - this.dx;
            this.xFond2 = this.xFond2 - this.dx;
        }

        if(this.getxPos() >= (WORLD_WIDTH*2) /* || this.golem.getHealth() <=0 */) {
            System.out.println("changement de menu");
            //Main.changeScreentoLoading();



            System.out.println("percent Scene : " + ld.percent );




            if(ld.percent >= 5) {
                this.xPos = -1;
                stage++;
                hero.setStageMax(hero.getStageMax() + 1);
                ld.percent = 0;
            }


            if(this.stage != 10) {
                setMonster();
            } else {
               // setGolem();
            }
            if(this.hero.getStageMax() > 10) {
//                icoFond1 = new ImageIcon(getClass().getResource("/images/stageLava.png"));
//                imgFond1 = icoFond1.getImage();
//                imgFond2 = icoFond1.getImage();
            }

            //this.golem.setHealth(100);
        } else {
            batch.draw(background, this.xFond1, WORLD_HEIGHT / 1.5f, WORLD_WIDTH, WORLD_HEIGHT / 3);
            batch.draw(background, this.xFond2, WORLD_HEIGHT / 1.5f, WORLD_WIDTH, WORLD_HEIGHT / 3);
        }

        if(this.xPos == -1) {
            this.xPos = 0;
        }
        if(this.xPos >= 0 && this.xPos <= (WORLD_WIDTH*2)) {
            for(int i = 0; i < tabEnemi.size(); i++) {
                this.tabEnemi.get(i).deplacement();
            }
            //this.golem.deplacement();
        }

        if(this.hero.isLibre() == true) {
            this.hero.setMarche(true);
            this.setDx(0.1f);
        } else {
            this.hero.setMarche(false);
            this.setDx(0);
        }



    }

    private void renderHero(float deltaTime) {

        if(this.hero.isVivant() == true) {
            if(this.hero.isEnCombat() == true) {
                batch.draw(hero.combatImg("hero", deltaTime), 0, 95, 10, 7);
                for (int i = 0; i < tabEnemi.size(); i++) {
                    if(this.hero.proche(this.tabEnemi.get(i)) == true && this.tabEnemi.get(i).isVivant() == true) {
                        //montrer les pv de l'enemi
                        font.draw(batch, ""+this.tabEnemi.get(i).getHealth(), 15, 105, 0.5f, 1, false);
                    }
                }
            } else {
                batch.draw(hero.marche("hero", deltaTime), 0, 95, 10, 7);
            }
        } else {
//            g2.drawImage(this.hero.mortImg("hero", 200), 50, 183, null);
//            Main.changeScreentoLoading();
        }
    }

    public void renderEnemi(float deltaTime) {
        for (int i = 0; i < tabEnemi.size(); i++) {
            if(this.tabEnemi.get(i).isVivant() == true) {
                batch.draw(tabEnemi.get(i).marche("oeil", deltaTime), tabEnemi.get(i).getX(), tabEnemi.get(i).getY(), 10, 7);
            } else {
                //mort
                batch.draw(tabEnemi.get(i).mortImg("oeil", deltaTime), tabEnemi.get(i).getX(), tabEnemi.get(i).getY(), 10, 7);
            }
        }
    }

    public void setMonster() {
        if(this.stage <= 10) {
            oeil1=new Oeil(20, 95);
            oeil1.setHealth(oeil1.getHealth() * this.stage);
            oeil1.setGoldValue(oeil1.getGoldValue() + this.stage);
            oeil1.setDmg(oeil1.getDmg() * this.stage);

            oeil2=new Oeil(35, 95);
            oeil2.setHealth(oeil2.getHealth() * this.stage);
            oeil2.setGoldValue(oeil2.getGoldValue() + this.stage);
            oeil2.setDmg(oeil2.getDmg() * this.stage);
            oeil2.compteurMarche = 2;

            oeil3=new Oeil(50, 95);
            oeil3.setHealth(oeil3.getHealth() * this.stage);
            oeil3.setGoldValue(oeil3.getGoldValue() + this.stage);
            oeil3.setDmg(oeil3.getDmg() * this.stage);
            oeil3.compteurMarche = 1;

            oeil4=new Oeil(62, 95);
            oeil4.setHealth(oeil4.getHealth() * this.stage);
            oeil4.setGoldValue(oeil4.getGoldValue() + this.stage);
            oeil4.setDmg(oeil4.getDmg() * this.stage);
            oeil4.compteurMarche = 3;

            oeil5=new Oeil(71, 95);
            oeil5.setHealth(oeil5.getHealth() * this.stage);
            oeil5.setGoldValue(oeil5.getGoldValue() + this.stage);
            oeil5.setDmg(oeil5.getDmg() * this.stage);
            oeil5.compteurMarche = 1;

            oeil6=new Oeil(82, 95);
            oeil6.setHealth(oeil6.getHealth() * this.stage);
            oeil6.setGoldValue(oeil6.getGoldValue() + this.stage);
            oeil6.setDmg(oeil6.getDmg() * this.stage);
            oeil6.compteurMarche = 0;

            oeil7=new Oeil(91, 95);
            oeil7.setHealth(oeil7.getHealth() * this.stage);
            oeil7.setGoldValue(oeil7.getGoldValue() + this.stage);
            oeil7.setDmg(oeil7.getDmg() * this.stage);
            oeil7.compteurMarche = 2;

            oeil8=new Oeil(104, 95);
            oeil8.setHealth(oeil8.getHealth() * this.stage);
            oeil8.setGoldValue(oeil8.getGoldValue() + this.stage);
            oeil8.setDmg(oeil8.getDmg() * this.stage);
            oeil8.compteurMarche = 0;

            oeil9=new Oeil(112, 95);
            oeil9.setHealth(oeil9.getHealth() * this.stage);
            oeil9.setGoldValue(oeil9.getGoldValue() + this.stage);
            oeil9.setDmg(oeil9.getDmg() * this.stage);
            oeil9.compteurMarche = 3;

            oeil10=new Oeil(120, 95);
            oeil10.setHealth(oeil10.getHealth() * this.stage);
            oeil10.setGoldValue(oeil10.getGoldValue() + this.stage);
            oeil10.setDmg(oeil10.getDmg() * this.stage);
            oeil10.compteurMarche = 1;

            tabEnemi=new ArrayList<Personnages>();

            tabEnemi.add(oeil1);
            tabEnemi.add(oeil2);
            tabEnemi.add(oeil3);
            tabEnemi.add(oeil4);
            tabEnemi.add(oeil5);
            tabEnemi.add(oeil6);
            tabEnemi.add(oeil7);
            tabEnemi.add(oeil8);
            tabEnemi.add(oeil9);
            tabEnemi.add(oeil10);

        } else if (this.stage > 10) {
//            flamme=new Flamme(150, 170);
//            flamme.setHealth(flamme.getHealth() * this.stage);
//            flamme.setGoldValue(flamme.getGoldValue() + this.stage);
//            flamme.setDmg(flamme.getDmg() * this.stage);
//
//            flamme2=new Flamme(200, 170);
//            flamme2.setHealth(flamme2.getHealth() * this.stage);
//            flamme2.setGoldValue(flamme2.getGoldValue() + this.stage);
//            flamme2.setDmg(flamme2.getDmg() * this.stage);
//            flamme2.compteurMarche = 2;
//
//            flamme3=new Flamme(300, 170);
//            flamme3.setHealth(flamme3.getHealth() * this.stage);
//            flamme3.setGoldValue(flamme3.getGoldValue() + this.stage);
//            flamme3.setDmg(flamme3.getDmg() * this.stage);
//            flamme3.compteurMarche = 1;
//
//            flamme4=new Flamme(400, 170);
//            flamme4.setHealth(flamme4.getHealth() * this.stage);
//            flamme4.setGoldValue(flamme4.getGoldValue() + this.stage);
//            flamme4.setDmg(flamme4.getDmg() * this.stage);
//            flamme4.compteurMarche = 3;
//
//            flamme5=new Flamme(500, 170);
//            flamme5.setHealth(flamme5.getHealth() * this.stage);
//            flamme5.setGoldValue(flamme5.getGoldValue() + this.stage);
//            flamme5.setDmg(flamme5.getDmg() * this.stage);
//            flamme5.compteurMarche = 1;
//
//            flamme6=new Flamme(550, 170);
//            flamme6.setHealth(flamme6.getHealth() * this.stage);
//            flamme6.setGoldValue(flamme6.getGoldValue() + this.stage);
//            flamme6.setDmg(flamme6.getDmg() * this.stage);
//            flamme6.compteurMarche = 0;
//
//            flamme7=new Flamme(590, 170);
//            flamme7.setHealth(flamme7.getHealth() * this.stage);
//            flamme7.setGoldValue(flamme7.getGoldValue() + this.stage);
//            flamme7.setDmg(flamme7.getDmg() * this.stage);
//            flamme7.compteurMarche = 2;
//
//            flamme8=new Flamme(620, 170);
//            flamme8.setHealth(flamme8.getHealth() * this.stage);
//            flamme8.setGoldValue(flamme8.getGoldValue() + this.stage);
//            flamme8.setDmg(flamme8.getDmg() * this.stage);
//            flamme8.compteurMarche = 0;
//
//            flamme9=new Flamme(690, 170);
//            flamme9.setHealth(flamme9.getHealth() * this.stage);
//            flamme9.setGoldValue(flamme9.getGoldValue() + this.stage);
//            flamme9.setDmg(flamme9.getDmg() * this.stage);
//            flamme9.compteurMarche = 3;
//
//            flamme10=new Flamme(720, 170);
//            flamme10.setHealth(flamme10.getHealth() * this.stage);
//            flamme10.setGoldValue(flamme10.getGoldValue() + this.stage);
//            flamme10.setDmg(flamme10.getDmg() * this.stage);
//            flamme10.compteurMarche = 1;
//
//            tabEnemi=new ArrayList<Personnages>();
//
//            tabEnemi.add(flamme);
//            tabEnemi.add(flamme2);
//            tabEnemi.add(flamme3);
//            tabEnemi.add(flamme4);
//            tabEnemi.add(flamme5);
//            tabEnemi.add(flamme6);
//            tabEnemi.add(flamme7);
//            tabEnemi.add(flamme8);
//            tabEnemi.add(flamme9);
//            tabEnemi.add(flamme10);
        }

    }





    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {

    }

}
