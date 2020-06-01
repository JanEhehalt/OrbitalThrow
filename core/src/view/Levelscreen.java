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
import com.badlogic.gdx.utils.Timer;

/**
 *
 * @author Jan
 */
public class Levelscreen extends AbstractScreen{
    
    Sprite[] levelPreview;
    Sprite buttonRight;
    Sprite buttonLeft;
    int selectedLevel;
    BitmapFont font;
    Timer t;
    boolean movement;
    
    public Levelscreen(Game game){
        super(game);
        levelPreview = new Sprite[5];
        buttonRight = new Sprite(new Texture("buttonRight.png"));
        buttonRight.setY(Gdx.graphics.getHeight() / 2 - buttonRight.getHeight() / 2);
        buttonRight.setX(Gdx.graphics.getWidth() - 10 - buttonRight.getWidth());
        buttonLeft = new Sprite(new Texture("buttonLeft.png"));
        buttonLeft.setY(Gdx.graphics.getHeight() / 2 - buttonLeft.getHeight() / 2);
        buttonLeft.setX(10);
        selectedLevel = 0;
        
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        
        t = new Timer();
        
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(buttonLeft.getX() <= 0){
                    movement = true;
                }
                if(buttonLeft.getX() + buttonLeft.getWidth() > Gdx.graphics.getWidth() * 0.2){
                    movement = false;
                }
                if(movement){
                    buttonRight.setX(buttonRight.getX() - 2);
                    buttonLeft.setX(buttonLeft.getX() + 2);
                }
                else{
                    buttonRight.setX(buttonRight.getX() + 2);
                    buttonLeft.setX(buttonLeft.getX() - 2);
                }
            }
        },0 , 0.04f);
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        SpriteBatch batch = new SpriteBatch();
        
        
        batch.begin();
        if(selectedLevel > 0){
            buttonLeft.draw(batch);
        }
        if(selectedLevel < levelPreview.length){
            buttonRight.draw(batch);
        }
        font.draw(batch, "" + selectedLevel, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();
        
        if(Gdx.input.justTouched()){
            if(Gdx.input.getX() < Gdx.graphics.getWidth() * 0.2){
                if(selectedLevel > 0)
                selectedLevel --;
            }
            else if(Gdx.input.getX() > Gdx.graphics.getWidth() * 0.8){
                if(selectedLevel < levelPreview.length)
                selectedLevel ++;
            }
            else{
                dispose();
                game.setScreen(new Gamescreen(game));
            }
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
        t.clear();
    }
    
}
