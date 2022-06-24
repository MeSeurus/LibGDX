package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private ProtagonistAnimation protagonist;
    private Vector2 position;

    public Coin(Vector2 position) {
        protagonist = new ProtagonistAnimation("coin.png", 10, 1, 10, Animation.PlayMode.LOOP_PINGPONG);
        this.position = position;
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        protagonist.setTime(Gdx.graphics.getDeltaTime());
        float cx = (position.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
        float cy = (position.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
        batch.draw(protagonist.getTexture(), cx, cy);
    }

    public void dispose() {
        protagonist.dispose();
    }
}
