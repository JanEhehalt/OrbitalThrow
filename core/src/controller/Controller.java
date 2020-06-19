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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import model.Goal;
import model.Level;
import model.Projectile;
import view.Gamescreen;
import view.Levelscreen;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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
    boolean isColliding;
    Level[] level;
    int currentLevel;
    int beatenLevel;
    

    OrthographicCamera camera;
    Viewport viewport;



    @Override
    public void create(){

        ts = new Titlescreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        ls = null;
        gs = null;
        ws = null;
        levelAmount = 9;
        currentLevel = -1;
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT * aspectRatio, camera);
        viewport.apply();
        camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);

        isColliding = false;
        level = new Level[10];
        currentLevel = 0;
        level[0] = new Level(new Goal(500,200,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[1] = new Level(new Goal(700,200,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[2] = new Level(new Goal(560,400,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[3] = new Level(new Goal(900,150,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[4] = new Level(new Goal(500,600,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[5] = new Level(new Goal(400,220,300,100, 0.2f), new Projectile(0,0,0),200,200);
        level[6] = new Level(new Goal(600,700,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[7] = new Level(new Goal(1000,600,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[8] = new Level(new Goal(760,460,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[9] = new Level(new Goal(1000,580,350,100, 0.2f), new Projectile(0,0,0),200,200);

        stepTimer = new Timer();
        stepTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(gs != null){
                    if(level[currentLevel].getProjectile().getxPos() > GAME_WORLD_WIDTH || level[currentLevel].getProjectile().getxPos() < 0 || level[currentLevel].getProjectile().getyPos() < 0){
                        gs.step(level[currentLevel]);
                        level[currentLevel].reset();
                        gs.dispose();
                        stepTimer.stop();
                        gs = null;
                        ws = new Winscreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, false, currentLevel);
                    }
                    else{
                        level[currentLevel].step();
                        gs.step(level[currentLevel]);

                        boolean collision = false;
                        for(Rectangle rect : gs.getGoalRects()){
                            if(Intersector.overlaps(gs.getProjectileCirc(), rect)) {

                                collision = true;
                                if (!isColliding) {
                                    if (rect.getHeight() == 1) {
                                        level[currentLevel].horizontalCollision();
                                    } else if (rect.getWidth() == 1) {
                                        level[currentLevel].verticalCollision();
                                    }
                                    isColliding = true;
                                    break;
                                }
                            }
                        }
                        for(Rectangle rect : gs.getObjectRects()){
                            if(Intersector.overlaps(gs.getProjectileCirc(), rect)) {

                                collision = true;
                                if (!isColliding) {
                                    if (rect.getHeight() == 1) {
                                        level[currentLevel].horizontalCollision();
                                    } else if (rect.getWidth() == 1) {
                                        level[currentLevel].verticalCollision();
                                    }
                                    isColliding = true;
                                    break;
                                }
                            }
                        }

                        if(!collision){
                            isColliding = false;
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
        else if(ls != null){
            ls.render(batch, level[currentLevel]);
            currentLevel = ls.getSelectedLevel();
        }
        else if(gs != null){
            gs.render(batch, level[currentLevel]);
            if(gs.getWin()){
                gs.dispose();
                stepTimer.stop();
                level[currentLevel].reset();
                if(currentLevel == beatenLevel)beatenLevel++;
                gs = null;
                ws = new Winscreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, true, currentLevel);
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
            ls = new Levelscreen(beatenLevel, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
        }
        else if(ls != null){
            if(x < Gdx.graphics.getWidth() * 0.15){
                if(ls.getSelectedLevel() > 0)ls.setSelectedLevel(ls.getSelectedLevel()-1);
            }
            else if(x > Gdx.graphics.getWidth() * 0.85){
                if(ls.getSelectedLevel() < beatenLevel)
                ls.setSelectedLevel(ls.getSelectedLevel()+1);
            }
            else{
                ls.dispose();
                ls = null;
                gs = new Gamescreen(level[currentLevel], GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                stepTimer.start();
            }
        }
        else if(gs != null){
            if(!level[currentLevel].released()){
                level[currentLevel].projectileReleased();
            }
        }
        else if(ws != null){
            if(x < Gdx.graphics.getWidth() * 0.33){
                ls = new Levelscreen(beatenLevel, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                ws = null;
            }
            else if(x < Gdx.graphics.getWidth() * 0.66){
                gs = new Gamescreen(level[currentLevel], GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                stepTimer.start();
                ws = null;
            }
            else if(currentLevel < levelAmount && ws.getWin()){
                currentLevel++;
                gs = new Gamescreen(level[currentLevel], GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                stepTimer.start();
                ws = null;
            }

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
