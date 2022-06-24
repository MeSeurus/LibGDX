package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private ProtagonistAnimation protAnim;
	private ProtagonistAnimation protAnimReverse;
	private ProtagonistAnimation idleRight;
	private ProtagonistAnimation idleLeft;
//	private Label label;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private Coin coin;
	private List<Coin> coinList;
	private Boolean lastKey;
	private int[] foreGround, backGround;
	private int x, y;

	@Override
	public void create () {
		map = new TmxMapLoader().load("maps/MyMap_1.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);

//		foreGround = new int[1];
//		foreGround[0] = map.getLayers().get()

		batch = new SpriteBatch();
		protAnim = new ProtagonistAnimation("samurai.png", 10, 1, 16.0f, Animation.PlayMode.LOOP);
		protAnimReverse = new ProtagonistAnimation("samuraiReversed.png", 10, 1, 16.0f, Animation.PlayMode.LOOP_REVERSED);
		idleLeft = new ProtagonistAnimation("idleLeft.png", 1, 1, 1f, Animation.PlayMode.NORMAL);
		idleRight = new ProtagonistAnimation("idleRight.png", 1, 1, 1f, Animation.PlayMode.NORMAL);
//		label = new Label();
		img = new Texture("back.png");

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		RectangleMapObject o = (RectangleMapObject) map.getLayers().get("Camera").getObjects().get("Camera");
		camera.position.x = o.getRectangle().x;
		camera.position.y = o.getRectangle().y;
		camera.zoom = 1;
		camera.update();
		lastKey = true;

		coinList = new ArrayList<>();
		MapLayer ml = map.getLayers().get("Coins");
		if (ml != null) {
			MapObjects mo = ml.getObjects();
			if (mo.getCount() > 0) {
				for (int i = 0; i< mo.getCount(); i++) {
					RectangleMapObject tmpMo = (RectangleMapObject) ml.getObjects().get(i);
					Rectangle rect = tmpMo.getRectangle();
					coinList.add(new Coin(new Vector2(rect.x,rect.y)));
				}
			}
		}
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x = camera.position.x - 3f;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x = camera.position.x + 3f;
		camera.update();

		mapRenderer.setView(camera);
		mapRenderer.render();

		if ((Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
			lastKey = false;
			protAnim.setTime(Gdx.graphics.getDeltaTime());
		} if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
			lastKey = true;
			protAnimReverse.setTime(Gdx.graphics.getDeltaTime());
		}

		batch.begin();
			if ((Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
				batch.draw(protAnim.getTexture(), Gdx.graphics.getWidth() / 2, 205);
			}
			if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
				batch.draw(protAnimReverse.getTexture(), Gdx.graphics.getWidth() / 2, 205);
			}

			if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (lastKey)) {
				batch.draw(idleRight.getTexture(), Gdx.graphics.getWidth() / 2, 205);
			}
			if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (!lastKey)) {
				batch.draw(idleLeft.getTexture(), Gdx.graphics.getWidth() / 2, 205);
			}




//		label.draw(batch);

		for (int i = 0; i < coinList.size(); i++) {
			coinList.get(i).draw(batch, camera);
		}

		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		protAnim.dispose();
		protAnimReverse.dispose();
		idleRight.dispose();
		idleLeft.dispose();
		coinList.get(0).dispose();
	}
}
