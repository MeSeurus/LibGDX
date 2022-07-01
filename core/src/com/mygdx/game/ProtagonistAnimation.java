package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ProtagonistAnimation {

    Texture texture;
    Animation<TextureRegion> animation;
    private float time;
    private boolean loop;

    public ProtagonistAnimation(String name, int width, int height, float fps, Animation.PlayMode mode) {
        this.loop = loop;
        texture = new Texture(name);
        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] regions = region.split(region.getRegionWidth()/width, region.getRegionHeight()/height);
        TextureRegion[] subRegions = new TextureRegion[width * height];

        int k = 0;
        for (TextureRegion[] textureRegion : regions) {
            for (TextureRegion innerRegion : textureRegion) {
                subRegions[k++] = innerRegion;
            }
        }

        animation = new Animation<>(1.0f/fps, subRegions);
        animation.setPlayMode(mode);
    }


    public void setTime(float time) {
        this.time += time;
    }

    public TextureRegion getTexture() {
        return animation.getKeyFrame(time);
    }

    public void dispose() {
        texture.dispose();
    }

}
