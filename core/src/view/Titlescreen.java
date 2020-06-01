/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Jan
 */
public class Titlescreen extends AbstractScreen{
    
    BitmapFont font;
    SpriteBatch batch;
    
    Sprite clicktostart;
    
    public Titlescreen(Game game){
        super(game);
        
        batch = new SpriteBatch();
        clicktostart = new Sprite(new Texture(Gdx.files.internal("clicktostart.png")));
        clicktostart.setX(Gdx.graphics.getWidth() / 2 - clicktostart.getWidth() / 2);
        clicktostart.setY(Gdx.graphics.getHeight() / 2 - clicktostart.getHeight() / 2);
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.end();
        
        if(Gdx.input.justTouched()){
            game.setScreen(new Levelscreen(game));
        }
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
