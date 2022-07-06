package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private ProtagonistAnimation protagonist;
    private Vector2 position;
    private Rectangle rectangle;

    public Coin(Vector2 position) {
        protagonist = new ProtagonistAnimation("coin.png", 10, 1, 10, Animation.PlayMode.LOOP_PINGPONG);
        this.position = position;
        rectangle = new Rectangle(position.x, position.y, protagonist.getTexture().getRegionWidth(),
                protagonist.getTexture().getRegionWidth());
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        protagonist.setTime(Gdx.graphics.getDeltaTime());
        float cx = (position.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
        float cy = (position.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
        batch.draw(protagonist.getTexture(), cx, cy);
    }

//    public void shapeDraw(ShapeRenderer renderer, OrthographicCamera camera) {
//        float cx = (rectangle.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
//        float cy = (rectangle.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
//        renderer.rect(cx, cy, rectangle.getWidth(), rectangle.getHeight());
//    }

    public boolean isOverlaps(Rectangle heroRect, OrthographicCamera camera) {
        float cx = (rectangle.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
        float cy = (rectangle.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
        Rectangle rect = new Rectangle(cx, cy, rectangle.width, rectangle.height);
        return rect.overlaps(heroRect);
    }

//    public boolean isOverlap(Rectangle heroRect) {
//        return rectangle.overlaps(heroRect);
//    }

    public void dispose() {
        protagonist.dispose();
    }
}
