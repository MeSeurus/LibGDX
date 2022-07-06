package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Body heroBody;
	private boolean isJump;
	private Music music;
	private Sound run;
	private int runState;

//	public void setState() {
//		run.play(0.75f, 1,0);
//		runState = 1;
//	}

	@Override
	public void create() {
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();

		BodyDef def = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape polygonShape = new PolygonShape();


		def.gravityScale = 1.2f;
		def.position.set(new Vector2(320,370));
		def.type = BodyDef.BodyType.StaticBody;

		fdef.density = 0;
		fdef.friction = 3f;
		fdef.restitution = 0.0f;

		polygonShape.setAsBox(320f, 10f);
		fdef.shape = polygonShape;
		world.createBody(def).createFixture(fdef);

		def.position.set(new Vector2(1020,370));
		def.type = BodyDef.BodyType.StaticBody;
		polygonShape.setAsBox(250f, 10f);
		fdef.shape = polygonShape;
		world.createBody(def).createFixture(fdef);

		def.type = BodyDef.BodyType.DynamicBody;
//		for (int i = 0; i < 10; i++) {
//			def.position.set(new Vector2(MathUtils.random(-130f, 130f), 450f));
//			def.type = BodyDef.BodyType.DynamicBody;
//
//			float size = MathUtils.random(3f, 15f);
//			polygonShape.setAsBox(size, size);
//			fdef.shape = polygonShape;
//			world.createBody(def).createFixture(fdef);
//		}

		def.position.set(new Vector2(MathUtils.random(130f, 300f), 450f));
		def.gravityScale = 9.81f;
		polygonShape.setAsBox(7f, 7f);
		fdef.shape = polygonShape;
		heroBody = world.createBody(def);
		heroBody.createFixture(fdef);

		polygonShape.dispose();

//		Body body = world.createBody(def);
//		body.createFixture(fdef);

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
		camera.zoom = 0.7f;
		camera.update();
		isJump = false;
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

		music = Gdx.audio.newMusic(Gdx.files.internal("Main.mp3"));
		music.setLooping(true);
		music.setVolume(0.35f);
		music.play();

		run = Gdx.audio.newSound(Gdx.files.internal("Run.mp3"));
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			heroBody.setLinearVelocity(new Vector2(-40000.0f, 0));
			heroBody.setGravityScale(30f);
//			camera.position.x = camera.position.x - 3f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			heroBody.setLinearVelocity(new Vector2(40000.0f, 0));
			heroBody.setGravityScale(30f);
//			camera.position.x = camera.position.x + 3f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			heroBody.applyForceToCenter(new Vector2(0.0f, -30000.0f), true);
//			camera.position.y = camera.position.y - 3f;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			isJump = true;
			if (lastKey) {
				heroBody.applyLinearImpulse(100000, 100000, heroBody.getPosition().x, heroBody.getPosition().y, isJump = false);
				heroBody.setGravityScale(30f);
			} else {
				heroBody.applyLinearImpulse(-100000f, 100000f, heroBody.getPosition().x, heroBody.getPosition().y, isJump = false);
				heroBody.setGravityScale(30f);

			}
		}
		camera.position.x = heroBody.getPosition().x;
		camera.position.y = heroBody.getPosition().y;
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
				batch.draw(hero.getIdleR(), Gdx.graphics.getWidth() / 2 - 65, 220);
			} if (!lastKey) {
				batch.draw(hero.getIdle(), Gdx.graphics.getWidth() / 2 - 65, 220);
			}
		} if ((Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (!lastKey)) {
			batch.draw(hero.getAttackR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		} if ((Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (lastKey)) {
			batch.draw(hero.getAttack(), Gdx.graphics.getWidth() / 2 - 65, 220);
		} if ((Gdx.input.isKeyPressed(Input.Keys.LEFT)) && (!Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
			batch.draw(hero.getRun(), Gdx.graphics.getWidth() / 2 - 65, 220);
		} if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && (!Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
			batch.draw(hero.getRunR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}

		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (lastKey) && (!stance)){
			run.stop();
			batch.draw(hero.getIdle(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (!lastKey) && (!stance)) {
			run.stop();
			batch.draw(hero.getIdleR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (lastKey) && (stance)){
			run.stop();
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && (!lastKey) && (stance)) {
			run.stop();
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (lastKey) && (!stance)){
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (!lastKey) && (stance)) {
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (!lastKey) && (!stance)){
			batch.draw(hero.getStanceStartL(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (lastKey) && (stance)) {
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (lastKey) && (!stance)){
			batch.draw(hero.getStanceStartR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (!lastKey) && (stance)) {
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.G) && (!lastKey) && (!stance)){
			batch.draw(hero.getIdleStanceR(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F) && (lastKey) && (stance)) {
			batch.draw(hero.getIdleStance(), Gdx.graphics.getWidth() / 2 - 65, 220);
		}


		label.draw(batch, "Coins: " + String.valueOf(score));
		label.drawInstruction(batch, "'Space' to attack \n" + "'F' to draw the weapon \n" + "'G' to hide the weapon \n" + "'Up' to jump");

		for (int i = 0; i < coinList.size(); i++) {
			int state;
			state = coinList.get(i).draw(batch, camera);
			if (coinList.get(i).isOverlaps(hero.getRect(), camera)) {
				if (state == 0) coinList.get(i).setState();
				if (state == 2) {
					coinList.remove(i);
					score++;
				}
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
				score++;
//				heroClr = Color.BLUE;
			}
		}
//		renderer.setColor(heroClr);
//		renderer.rect(heroRect.x, heroRect.y, heroRect.width, heroRect.height);
//		renderer.end();

		world.step(1/60.0f, 3,3);
		debugRenderer.render(world, camera.combined);
	}

		@Override
		public void dispose() {
			batch.dispose();
			coinList.get(0).dispose();
			world.dispose();
			music.stop();
			music.dispose();
			run.stop();
			run.dispose();
		}
	}

