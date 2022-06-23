package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ProtagonistAnimation protAnim;
	Label label;

	private int x, y;

	@Override
	public void create () {
		batch = new SpriteBatch();
		protAnim = new ProtagonistAnimation("clone.png", 6, 1, 16.0f, Animation.PlayMode.NORMAL);
		label = new Label();
		img = new Texture("back.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;

		protAnim.setTime(Gdx.graphics.getDeltaTime());

		batch.begin();
		batch.draw(img,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(protAnim.getTexture(), x, 0);
		label.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		protAnim.dispose();
	}
}
