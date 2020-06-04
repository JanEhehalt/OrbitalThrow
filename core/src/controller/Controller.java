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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import model.Goal;
import model.Level;
import model.Projectile;
import view.Gamescreen;
import view.Levelscreen;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import view.Titlescreen;
import view.Winscreen;

/**
 *
 * @author Jan
 */
public class Controller extends ApplicationAdapter implements InputProcessor{
    
    Titlescreen ts;
    Levelscreen ls;
    Gamescreen gs;
    Winscreen ws;
    int levelAmount;
    SpriteBatch batch;
    Timer stepTimer;
    Level level;
    
    OrthographicCamera camera;
    FitViewport fitViewport;
    
    @Override
    public void create(){
        
        ts = new Titlescreen();
        ls = null;
        gs = null;
        ws = null;
        levelAmount = 10;
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

        camera = new OrthographicCamera(1280, 720);
        fitViewport = new FitViewport(1280, 720, camera);
        
        level = new Level(new Goal(700,400,200,80), new Projectile(0,0,0),200,200);

        stepTimer = new Timer();
        stepTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(gs != null){
                    if(level.getProjectile().getxPos() > Gdx.graphics.getWidth() || level.getProjectile().getxPos() < 0 || level.getProjectile().getyPos() < 0){
                        level.reset();
                        gs = null;
                        ws = new Winscreen();
                    }
                    else{
                        level.step();
                    }
                }
            }
        }, 0, 0.01f);
        stepTimer.stop();
    }
    
    @Override
    public void render(){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fitViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        batch.begin();
        if(ts != null) ts.render(batch);
        else if(ls != null) ls.render(batch);
        else if(gs != null) gs.render(batch, level);
        else if(ws != null) ws.render(batch);
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
                gs = new Gamescreen(level);
                stepTimer.start();
            }
        }
        else if(gs != null){
            if(!level.released()){
                level.projectileReleased();
            }
        }
        else if(ws != null){
            if(x < Gdx.graphics.getWidth() * 0.33){
                ls = new Levelscreen(levelAmount);
            }
            else if(x < Gdx.graphics.getWidth() * 0.66){
                gs = new Gamescreen(level);
            }
            else{
                gs = new Gamescreen(level);
            }
            ws = null;
        }
        return true;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        if(gs != null){
        }
        return true;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2){
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
