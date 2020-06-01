/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import model.Goal;
import model.Projectile;

/**
 *
 * @author Jan
 */
public class Gamescreen extends AbstractScreen{
    
    Goal g;
    Projectile p;
    SpriteBatch batch;
    
    Sprite projectile;
    Rectangle goalLeft;
    Rectangle goalRight;
    Rectangle goalBottom;
    
    
    
    
    public Gamescreen(Game game){
        super(game);
        g = new Goal(800,400,200,150);
        p = new Projectile(250, 250, 0);
        projectile = new Sprite(new Texture("projectile.png"));
        projectile.setPosition(p.getxPos(), p.getyPos());
        goalLeft = new Rectangle(g.getxPos(), g.getyPos(), 0.1f * g.getSizeX(), g.getSizeY());
        goalBottom = new Rectangle(g.getxPos() + goalLeft.getWidth(), g.getyPos(), 0.8f * g.getSizeX(),0.2f * g.getSizeY());
        goalRight = new Rectangle(g.getxPos() + goalLeft.getWidth() + goalBottom.getWidth(), g.getyPos(), 0.1f * g.getSizeX(),g.getSizeY());
        batch = new SpriteBatch();
        
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        projectile.draw(batch);
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
