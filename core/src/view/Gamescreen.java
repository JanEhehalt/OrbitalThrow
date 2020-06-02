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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import model.Goal;
import model.Level;
import model.Projectile;

/**
 *
 * @author Jan
 */
public class Gamescreen extends AbstractScreen{
    
    Goal g;
    Projectile p;
    SpriteBatch batch;
    
    //Rectangle goalLeft;
    //Rectangle goalRight;
    //Rectangle goalBottom;
    ShapeRenderer shapeRenderer;
    
    
    
    
    public Gamescreen(Game game, Level level){
        super(game);
        g = level.getGoal();
        p = level.getProjectile();
        // Goal rectangles
        //goalLeft = new Rectangle(g.getxPos(), g.getyPos(), 0.1f * g.getSizeX(), g.getSizeY());
        //goalBottom = new Rectangle(g.getxPos() + 0.1f * g.getSizeX(), g.getyPos(), 0.8f * g.getSizeX(),0.2f * g.getSizeY());
        //goalRight = new Rectangle(g.getxPos() + 0.1f * g.getSizeX() + 0.8f * g.getSizeX(), g.getyPos(), 0.1f * g.getSizeX(),g.getSizeY());
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        
    }
    
    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        
        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(g.getxPos(), g.getyPos(), 0.2f * g.getSizeX(), g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX(), g.getyPos(), 0.6f * g.getSizeX(),0.2f * g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX() + 0.6f * g.getSizeX(), g.getyPos(), 0.2f * g.getSizeX(),g.getSizeY());
        shapeRenderer.circle(p.getxPos(), p.getyPos(), p.getRadius());
        shapeRenderer.end();
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
    
    public void update(){
    
    }
}
