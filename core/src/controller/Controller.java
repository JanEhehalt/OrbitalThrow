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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;
import java.util.ArrayList;

import model.Goal;
import model.Level;
import model.Projectile;
import view.Chapterscreen;
import view.Gamescreen;
import view.Levelscreen;
import view.Leveleditor;
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
    Leveleditor le;
    Chapterscreen cs;
    int levelAmount;
    SpriteBatch batch;
    Timer stepTimer;
    boolean isColliding;
    ArrayList<ArrayList<Level>> level;
    ArrayList<Level> ownLevels;
    int currentLevel;
    int currentChapter;
    int beatenLevel = 9;
    

    OrthographicCamera camera;
    Viewport viewport;



    @Override
    public void create(){
        

        ts = new Titlescreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        ls = null;
        gs = null;
        ws = null;
        le = null;
        cs = null;
        levelAmount = 9;
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);

        float aspectRatio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(GAME_WORLD_WIDTH/*  *aspectRatio*/, GAME_WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);

        isColliding = false;
        level = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            level.add(new ArrayList<Level>());
        }
        ownLevels = new ArrayList<>();

        currentLevel = 0;
        currentChapter = 1;
        Json json = new Json();

        /*
        Level lol = new Level(new Goal(1000,580,350,100, 0.2f), new Projectile(0,0,0),200,200);
        FileHandle fileblub = Gdx.files.local("levellol.json");
        fileblub.writeString(json.toJson(lol), false);

        /*
        level[1] = new Level(new Goal(700,200,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[2] = new Level(new Goal(560,400,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[3] = new Level(new Goal(900,150,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[4] = new Level(new Goal(500,600,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[5] = new Level(new Goal(400,220,300,100, 0.2f), new Projectile(0,0,0),200,200);
        level[6] = new Level(new Goal(600,700,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[7] = new Level(new Goal(1000,600,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[8] = new Level(new Goal(760,460,450,100, 0.2f), new Projectile(0,0,0),200,200);
        level[9] = new Level(new Goal(1000,580,350,100, 0.2f), new Projectile(0,0,0),200,200);
        level[9].addRectangle(400, 400, 50,200);
         */


        FileHandle directory;
        FileHandle[] files;
        for(int chapter = 0; chapter < level.size(); chapter++) {

            directory = Gdx.files.internal("levels/chapter" + (chapter + 1));
            files = directory.list();

            for(FileHandle file : files){
                if(file.exists()){
                    Level tempLevel = json.fromJson(Level.class, file.readString());
                    level.get(chapter).add(tempLevel);
                }
            }
        }

        directory = Gdx.files.internal("levels/own");
        files = directory.list();
        for(FileHandle file : files){
            if(file.exists()){
                Level tempLevel = json.fromJson(Level.class, file.readString());
                ownLevels.add(tempLevel);
            }
        }

        stepTimer = new Timer();
        stepTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(gs != null){
                    if(level.get(currentChapter).get(currentLevel).getProjectile().getxPos() > GAME_WORLD_WIDTH || level.get(currentChapter).get(currentLevel).getProjectile().getxPos() < 0 || level.get(currentChapter).get(currentLevel).getProjectile().getyPos() < 0){
                        gs.step(level.get(currentChapter).get(currentLevel));
                        level.get(currentChapter).get(currentLevel).reset();
                        gs.dispose();
                        stepTimer.stop();
                        gs = null;
                        ws = new Winscreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, false, currentLevel);
                    }
                    else{
                        level.get(currentChapter).get(currentLevel).step();
                        gs.step(level.get(currentChapter).get(currentLevel));

                        boolean collision = false;
                        double tempX = level.get(currentChapter).get(currentLevel).getProjectile().getxPos();
                        double tempY = level.get(currentChapter).get(currentLevel).getProjectile().getyPos();

                        for(Rectangle rect : gs.getGoalRects()){
                            if(Intersector.overlaps(gs.getProjectileCirc(), rect)) {

                                collision = true;
                                if (rect.getHeight() == 1) {
                                    level.get(currentChapter).get(currentLevel).horizontalCollision();
                                } else if (rect.getWidth() == 1) {
                                    level.get(currentChapter).get(currentLevel).verticalCollision();
                                }
                                break;
                            }
                        }
                        if(gs.getObjectRects() != null) {
                            for (Rectangle rect : gs.getObjectRects()) {
                                if (Intersector.overlaps(gs.getProjectileCirc(), rect)) {

                                    collision = true;
                                    if (rect.getHeight() == 1) {
                                        level.get(currentChapter).get(currentLevel).horizontalCollision();
                                    } else if (rect.getWidth() == 1) {
                                        level.get(currentChapter).get(currentLevel).verticalCollision();
                                    }
                                    break;

                                }
                            }
                        }

                        if(collision){
                            level.get(currentChapter).get(currentLevel).getProjectile().setxPos(tempX);
                            level.get(currentChapter).get(currentLevel).getProjectile().setyPos(tempY);
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
            currentLevel = ls.getSelectedLevel();
            ls.render(batch, level.get(currentChapter).get(currentLevel));
        }
        else if(gs != null){
            gs.render(batch, level.get(currentChapter).get(currentLevel));
            if(gs.getWin()){
                gs.dispose();
                stepTimer.stop();
                level.get(currentChapter).get(currentLevel).reset();
                if(currentLevel == beatenLevel && currentLevel < levelAmount)beatenLevel++;
                gs = null;
                ws = new Winscreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, true, currentLevel);
            }
        }
        else if(ws != null) ws.render(batch);
        else if(le != null){ 
            le.render(batch);
            if(le.getToSave()){
                le.dispose();
                le = null;
                ts = new Titlescreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            }
        }
        else if(cs != null){
            cs.render(batch);
        }
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
        x = (int)((float)x / (float)Gdx.graphics.getWidth() * (float)GAME_WORLD_WIDTH);
        y = (int)GAME_WORLD_HEIGHT-(int)((float)y / Gdx.graphics.getHeight() * GAME_WORLD_HEIGHT);
        if(ts != null){
            if(x > 0.05 * GAME_WORLD_WIDTH){
                ts.dispose();
                ts = null;
                //ls = new Levelscreen(beatenLevel, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                cs = new Chapterscreen(5, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
            }
            else{
                ts.dispose();
                ts = null;
                le = new Leveleditor(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
            }
        }
        else if(ls != null){
            int n = ls.touchDown(x,y);
            switch(n){
                case 0:
                    ls.dispose();
                    ls = null;
                    cs = new Chapterscreen(5,GAME_WORLD_WIDTH,GAME_WORLD_HEIGHT,camera.combined);
                    break;
                case 1:
                    if(ls.getSelectedLevel() > 0)ls.setSelectedLevel(ls.getSelectedLevel()-1);
                    break;
                case 2:
                    if(ls.getSelectedLevel() < beatenLevel && beatenLevel <= levelAmount)
                    ls.setSelectedLevel(ls.getSelectedLevel()+1);
                    break;
                case 3:
                    ls.dispose();
                    ls = null;
                    gs = new Gamescreen(level.get(currentChapter).get(currentLevel), GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                    stepTimer.start();
                    break;
            }
        }
        else if(gs != null){
            if(!level.get(currentChapter).get(currentLevel).released()){
                level.get(currentChapter).get(currentLevel).projectileReleased();
            }
        }
        else if(ws != null){
            if(x < GAME_WORLD_WIDTH * 0.33){
                ls = new Levelscreen(beatenLevel, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                ws = null;
            }
            else if(x < GAME_WORLD_WIDTH * 0.66){
                gs = new Gamescreen(level.get(currentChapter).get(currentLevel), GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                stepTimer.start();
                ws = null;
            }
            else if(currentLevel < levelAmount && ws.getWin()){
                currentLevel++;
                gs = new Gamescreen(level.get(currentChapter).get(currentLevel), GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
                stepTimer.start();
                ws = null;
            }

        }
        else if(le != null){
            le.touchDown(x,y);
        }
        else if(cs != null){
            if(cs.touchDown(x,y) == -1){

            }
            else if (cs.touchDown(x,y) >= 0 && cs.touchDown(x,y) <= 5){
                currentChapter = cs.touchDown(x,y);
                cs = null;
                ls = new Levelscreen(level.get(currentChapter).size()-1, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera.combined);
            }
            else if(cs.touchDown(x,y) == 6){
                cs.dispose();
                cs = null;
                ts = new Titlescreen(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            }
            else{
                cs.dispose();
                cs = null;
                ls = new Levelscreen(9,GAME_WORLD_WIDTH,GAME_WORLD_HEIGHT,camera.combined);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return true;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2){
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
            float x = ((float)i / (float)Gdx.graphics.getWidth()) *(float) GAME_WORLD_WIDTH;
            float y = GAME_WORLD_HEIGHT - ((float)i1 / (float)Gdx.graphics.getHeight()) * (float)GAME_WORLD_HEIGHT;
            //System.out.println("x:" + x + "   y:" + y);
        return true;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
