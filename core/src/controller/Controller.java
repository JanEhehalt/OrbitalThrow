/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import model.Goal;
import model.Level;
import model.Projectile;
import view.Gamescreen;
import view.Levelscreen;
import com.badlogic.gdx.utils.Timer;
import view.Titlescreen;

/**
 *
 * @author Jan
 */
public class Controller extends ApplicationAdapter implements InputProcessor{
    
    Titlescreen ts;
    Levelscreen ls;
    Gamescreen gs;
    int levelAmount;
    SpriteBatch batch;
    Timer stepTimer;
    
    @Override
    public void create(){
        ts = new Titlescreen();
        ls = null;
        gs = null;
        levelAmount = 10;
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

        stepTimer = new Timer();
        stepTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

            }
        }, 0, 0.1f);
    }
    
    @Override
    public void render(){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if(ts != null) ts.render(batch);
        else if(ls != null) ls.render(batch);
        else if(gs != null) gs.render(batch, new Level(new Goal(500,500,200,150), new Projectile(100,100,0),50,50));
        batch.end();
    }
    
    @Override
    public void dispose () {

    }
    
    
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int i2, int i3) {
        if(ts != null){
            ts.dispose();
            ts = null;
            ls = new Levelscreen(levelAmount);
        }
        else if(ls != null){
            if(x < Gdx.graphics.getWidth() * 0.15){
                if(ls.getSelectedLevel() > 0)ls.setSelectedLevel(ls.getSelectedLevel()-1);
            }
            else if(x > Gdx.graphics.getWidth() * 0.85){
                if(ls.getSelectedLevel() < levelAmount)
                ls.setSelectedLevel(ls.getSelectedLevel()+1);
            }
            else{
                ls.dispose();
                ls = null;
                gs = new Gamescreen(new Level(new Goal(500,500,200,150), new Projectile(100,100,0),50,50));
            }
        }
        else if(gs != null){
            gs = null;
            ls = new Levelscreen(levelAmount);
        }
        return true;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

}
