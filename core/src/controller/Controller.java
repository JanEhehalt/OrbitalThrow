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

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import view.Titlescreen;
import view.Winscreen;

/**
 *
 * @author Jan
 */
public class Controller extends ApplicationAdapter implements InputProcessor{

    final float GAME_WORLD_WIDTH = 1600;
    final float GAME_WORLD_HEIGHT = 900;


    Titlescreen ts;
    Levelscreen ls;
    Gamescreen gs;
    Winscreen ws;
    int levelAmount;
    SpriteBatch batch;
    Timer stepTimer;
    Level level;

    OrthographicCamera camera;
    Viewport viewport;



    @Override
    public void create(){

        ts = new Titlescreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        ls = null;
        gs = null;
        ws = null;
        levelAmount = 10;
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);


        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT * aspectRatio, camera);
        viewport.apply();
        camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);

        level = new Level(new Goal(500,200,150,50, 0.2f), new Projectile(0,0,0),200,200);

        stepTimer = new Timer();
        stepTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(gs != null){
                    if(level.getProjectile().getxPos() > Gdx.graphics.getWidth() || level.getProjectile().getxPos() < 0 || level.getProjectile().getyPos() < 0){
                        gs.step(level);
                        level.reset();
                        gs.dispose();
                        stepTimer.stop();
                        gs = null;
                        ws = new Winscreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, false);
                    }
                    else{
                        level.step();
                        gs.step(level);
                        for(Rectangle rect : gs.getGoalRects()){
                            if(Intersector.overlaps(gs.getProjectileCirc(), rect)){
                                if(rect.getHeight() == 1){
                                    level.horizontalCollision();
                                }
                                else if(rect.getWidth() == 1){
                                    level.verticalCollision();
                                }
                                break;
                            }
                        }

                    }
                }
            }
        }, 0, 0.01f);
        stepTimer.stop();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
        camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        if(ts != null) ts.render(batch);
        else if(ls != null) ls.render(batch);
        else if(gs != null){
            gs.render(batch, level);
            if(gs.getWin()){
                gs.dispose();
                stepTimer.stop();
                gs = null;
                ws = new Winscreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, true);
            }
        }
        else if(ws != null) ws.render(batch);
        batch.end();
    }

    @Override
    public void dispose () {

    }

    @Override
    public boolean keyDown(int keycode) {
        camera.translate(5f, 5f);
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
            ls = new Levelscreen(levelAmount, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
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
                level = new Level(new Goal(500,200,150,50, 0.2f), new Projectile(0,0,0),200,200);
                gs = new Gamescreen(level, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
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
                ls = new Levelscreen(levelAmount, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            }
            else if(x < Gdx.graphics.getWidth() * 0.66){
                level = new Level(new Goal(500,200,150,50, 0.2f), new Projectile(0,0,0),200,200);
                gs = new Gamescreen(level, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                stepTimer.start();
            }
            else{
                level = new Level(new Goal(500,200,150,50, 0.2f), new Projectile(0,0,0),200,200);
                gs = new Gamescreen(level, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                stepTimer.start();
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
