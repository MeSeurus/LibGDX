package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Label {
    BitmapFont bitmapFont;

    public Label() {
//        public Label(int size) {
//        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(""));
//        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        fontParameter.size = size;
//        fontParameter.characters = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ.йцукенгшщзхъфывапролджэячсмитьбю"
//        bitmapFont = fontGenerator.generateFont(fontParameter);
        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(1);
    }

    public void draw(SpriteBatch batch) {
        bitmapFont.draw(batch, "Star Wars - Alone Among the Droids", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }
}
