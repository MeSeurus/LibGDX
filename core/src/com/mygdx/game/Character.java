package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Character {
    private ProtagonistAnimation idleLeft, idleRight, runLeft, runRight, hitLeft, hitRight, idleStanceRight, idleStanceLeft, stanceStartL, stanceStartR;
    private boolean isIdleLeft, isIdleRight, isRunLeft, isRunRight, isHitLeft, isHitRight;
    private Vector2 pos;
    private Rectangle rect;

    public Character() {

        runRight = new ProtagonistAnimation("samurai.png", 10, 1, 16.0f, Animation.PlayMode.LOOP);
        runLeft = new ProtagonistAnimation("samuraiReversed.png", 10, 1, 16.0f, Animation.PlayMode.LOOP_REVERSED);
        idleLeft = new ProtagonistAnimation("idleLeft.png", 1, 1, 1f, Animation.PlayMode.NORMAL);
        idleRight = new ProtagonistAnimation("idleRight.png", 1, 1, 1f, Animation.PlayMode.NORMAL);
        idleStanceRight = new ProtagonistAnimation("idleStanceRight.png", 1, 1, 1f, Animation.PlayMode.NORMAL);
        idleStanceLeft = new ProtagonistAnimation("idleStanceleft.png", 1, 1, 1f, Animation.PlayMode.NORMAL);
        hitLeft = new ProtagonistAnimation("hitLeft.png", 5, 1, 16.0f, Animation.PlayMode.LOOP);
        hitRight = new ProtagonistAnimation("hitRight.png", 5, 1, 16.0f, Animation.PlayMode.LOOP);
        stanceStartL = new ProtagonistAnimation("stanceStartL.png", 5, 1, 16.0f, Animation.PlayMode.NORMAL);
        stanceStartR = new ProtagonistAnimation("stanceStartR.png", 5, 1, 16.0f, Animation.PlayMode.NORMAL);
        rect = new Rectangle(Gdx.graphics.getWidth() / 2 + runRight.getTexture().getRegionWidth() / 4 - 65, Gdx.graphics.getHeight() / 2 - runRight.getTexture().getRegionHeight() / 4,
                runRight.getTexture().getRegionWidth() / 2, runRight.getTexture().getRegionHeight());

        pos = new Vector2(0,0);
    }

    public TextureRegion getRun() {
        runRight.setTime(Gdx.graphics.getDeltaTime());
        return runRight.getTexture();
    }

    public TextureRegion getRunR() {
        runLeft.setTime(Gdx.graphics.getDeltaTime());
        return runLeft.getTexture();
    }

    public TextureRegion getIdle() {
        idleRight.setTime(Gdx.graphics.getDeltaTime());
        return idleRight.getTexture();
    }

    public TextureRegion getIdleR() {
        idleLeft.setTime(Gdx.graphics.getDeltaTime());
        return idleLeft.getTexture();
    }

    public TextureRegion getAttack() {
        hitRight.setTime(Gdx.graphics.getDeltaTime());
        return hitRight.getTexture();
    }

    public TextureRegion getAttackR() {
        hitLeft.setTime(Gdx.graphics.getDeltaTime());
        return hitLeft.getTexture();
    }

    public TextureRegion getIdleStance() {
        idleStanceRight.setTime(Gdx.graphics.getDeltaTime());
        return idleStanceRight.getTexture();
    }

    public TextureRegion getIdleStanceR() {
        idleStanceLeft.setTime(Gdx.graphics.getDeltaTime());
        return idleStanceLeft.getTexture();
    }

    public TextureRegion getStanceStartL() {
        stanceStartL.setTime(Gdx.graphics.getDeltaTime());
        return stanceStartL.getTexture();
    }

    public TextureRegion getStanceStartR() {
        stanceStartR.setTime(Gdx.graphics.getDeltaTime());
        return stanceStartR.getTexture();
    }

    public Vector2 getPos() {
        return pos;
    }

    public Rectangle getRect() {
        return rect;
    }
}
