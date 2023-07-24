package com.r212.pokemon.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.r212.pokemon.PokemonGame;
import com.r212.pokemon.screen.transition.FadeInTransition;
import com.r212.pokemon.screen.transition.FadeOutTransition;
import com.r212.pokemon.util.Action;

import static com.r212.pokemon.screen.GameScreen.background;

public class InfoScreen extends AbstractScreen {


    public InfoScreen(PokemonGame game) {
        super(game);
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void render(float delta) {
        BitmapFont font1 = new BitmapFont(Gdx.files.internal("res/font/u02KYIgXq4iAjKR8gUI2yuqK.ttf.fnt"));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getApp().batch.begin();
        font1.draw(getApp().batch, "Helpful Tips", Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight() * .9f);
        getApp().font.draw(getApp().batch, "Shift: Run", Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight() * .7f);
        getApp().font.draw(getApp().batch, "Enter: Interact", Gdx.graphics.getWidth() /6, Gdx.graphics.getHeight() * .6f);
        getApp().font.draw(getApp().batch, "F1: Bike Mode :)", Gdx.graphics.getWidth() /6, Gdx.graphics.getHeight() * .5f);
        getApp().font.draw(getApp().batch, "Press enter to continue", Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight() * .25f);

        getApp().font.draw(getApp().batch, "W", Gdx.graphics.getWidth()-125, Gdx.graphics.getHeight() * .6f);
        getApp().font.draw(getApp().batch, "A", Gdx.graphics.getWidth()-175, Gdx.graphics.getHeight() * .5f);
        getApp().font.draw(getApp().batch, "S", Gdx.graphics.getWidth()-125, Gdx.graphics.getHeight() * .5f);
        getApp().font.draw(getApp().batch, "D", Gdx.graphics.getWidth()-75, Gdx.graphics.getHeight() * .5f);
        getApp().font.draw(getApp().batch, "arrow keys too", Gdx.graphics.getWidth()-231, Gdx.graphics.getHeight() * .4f);


        getApp().batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            getApp().startTransition(
                    this,
                    getApp().getGameScreen(),
                    new FadeOutTransition(0.5f, Color.BLACK, getApp().getTweenManager(), getApp().getAssetManager()),
                    new FadeInTransition(0.5f, Color.BLACK, getApp().getTweenManager(), getApp().getAssetManager()),
                    new Action(){
                        @Override
                        public void action() {
                            System.out.println("STATUS UPDATE: Player has entered the realm of R212");
                            background.play();
                        }
                    });
        }
    }
}