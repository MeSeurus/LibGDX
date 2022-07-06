package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
	private Label label;
	private int score;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private List<Coin> coinList;
	private Boolean lastKey;
	private int[] foreGround, backGround;
	private Texture back;
	private Character hero;
	private Boolean stance;
	private Label instruction;

	@Override
	public void create() {
		hero = new Character();
		back = new Texture("back.jpg");
		map = new TmxMapLoader().load("maps/MyMap_1.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);

//		foreGround = new int[1];
//		foreGround[0] = map.getLayers().get()

		batch = new SpriteBatch();
		label = new Label();
		instruction = new Label();
		img = new Texture("back.png");

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		RectangleMapObject o = (RectangleMapObject) map.getLayers().get("Camera").getObjects().get("Camera");
		camera.position.x = o.getRectangle().x;
		camera.position.y = o.getRectangle().y;
		camera.zoom = 1;
		camera.update();
		lastKey = true;
		stance = false;

		coinList = new ArrayList<>();
		MapLayer ml = map.getLayers().get("Coins");
		if (ml != null) {
			MapObjects mo = ml.getObjects();
			if (mo.getCount() > 0) {
				for (int i = 0; i < mo.getCount(); i++) {
					RectangleMapObject tmpMo = (RectangleMapObject) ml.getObjects().get(i);
					Rectangle rect = tmpMo.getRectangle();
					coinList.add(new Coin(new Vector2(rect.x, rect.y)));
				}
			}
		}
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x = camera.position.x - 3f;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x = camera.position.x + 3f;
		camera.update();

		batch.begin();
		batch.draw(back, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

		mapRenderer.setView(camera);
		mapRenderer.render();

		if ((Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
			lastKey = false;
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
			lastKey = true;
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.F))) {
			stance = true;
		}
		if ((Gdx.input.isKeyPressed(Input.Keys.G))) {
			stance = false;
		}

		batch.begin();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
			if (lastKey) {
				batch.draw(hero.getIdleR(), Gdx.graphics.getWidth() / 2, 205);
			} if (!lastKey) {
				batch.draw(hero.getIdle(), Gdx.graphics.getWidth() / 2, 205);
			}
		} if ((Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (!lastKey)) {
			batch.draw(hero.getAttackR(), Gdx.graphics.getWidth() / 2, 205);
		} if ((Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (lastKey)) {
			batch.draw(hero.getAttack(), Gdx.graphics.getWidth() / 2, 205);
		} if ((Gdx.input.isKeyPressed(Input.Keys.LEFT)) && (!Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
			batch.draw(hero.getRun(), Gdx.graphics.getWidth() / 2, 205);
		} if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && (!Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
			batch.draw(hero.getRunR(), Gdx.graphics.getWidth() / 2, 205);
		}

		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (lastKey) && (!stance)){
			batch.draw(hero.getIdle(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (!lastKey) && (!stance)) {
			batch.draw(hero.getIdleR(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (lastKey) && (stance)){
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (!lastKey) && (stance)) {
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (lastKey) && (!stance)){
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (!lastKey) && (stance)) {
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (!lastKey) && (!stance)){
			batch.draw(hero.getStanceStartL(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (lastKey) && (stance)) {
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (lastKey) && (!stance)){
			batch.draw(hero.getStanceStartR(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (!lastKey) && (stance)) {
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (!lastKey) && (!stance)){
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2, 205);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (lastKey) && (stance)) {
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2, 205);
		}


		label.draw(batch, "Coins: " + String.valueOf(score));
		label.drawInstruction(batch, "'Space' to attack \n" + "'F' to draw the weapon \n" + "'G' to hide the weapon");

		for (int i = 0; i < coinList.size(); i++) {
			coinList.get(i).draw(batch, camera);
			if (coinList.get(i).isOverlaps(hero.getRect(), camera)) {
				coinList.remove(i);
				score++;
			}
		}

		batch.end();

//		Color heroClr = new Color(Color.WHITE);
//		renderer.begin(ShapeRenderer.ShapeType.Line);
//		renderer.setColor(heroClr);
		for (int i = 0; i < coinList.size(); i++) {
//			coinList.get(i).shapeDraw(renderer, camera);
			if (coinList.get(i).isOverlaps(hero.getRect(), camera)) {
				coinList.remove(i);
//				heroClr = Color.BLUE;
			}
		}
//		renderer.setColor(heroClr);
//		renderer.rect(heroRect.x, heroRect.y, heroRect.width, heroRect.height);
//		renderer.end();

	}

		@Override
		public void dispose() {
			batch.dispose();
			coinList.get(0).dispose();
		}
	}

