package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.swing.JProgressBar;

public class LoadingScreen {
    public float percent = 0;
    private float accumulateTime;

    TextureAtlas textureAtlas;
    TextureRegion texture;


    public LoadingScreen() {
        this.accumulateTime = 0;
        this.percent = 0;

        textureAtlas = new TextureAtlas("images.atlas");
    }

    public TextureRegion gainPercent() {
        String str = null;

        this.accumulateTime = this.accumulateTime + Gdx.graphics.getDeltaTime();

        System.out.println(this.accumulateTime);
        if(this.accumulateTime >= 0.02f) {
            this.percent++;
            this.accumulateTime = 0;

        }
        if(this.percent == 0) {
            str = "loading1";
        } else if(this.percent == 1) {
            str = "loading2";
        } else if(this.percent == 2) {
            str = "loading3";
        } else if(this.percent == 3) {
            str = "loading4";
        } else if (this.percent == 4) {
            str = "loading5";
        } else  if(this.percent == 5){
            str = "loading6";
        }

        texture = textureAtlas.findRegion(str);
        return texture;


    }



}
