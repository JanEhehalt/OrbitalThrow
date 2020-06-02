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
public class Gamescreen{
    
    Goal g;
    Projectile p;
    
    //Rectangle goalLeft;
    //Rectangle goalRight;
    //Rectangle goalBottom;
    ShapeRenderer shapeRenderer;
    int pivotX;
    int pivotY;
    
    
    
    
    public Gamescreen(Level level){
        pivotX = level.getPivotX();
        pivotY = level.getPivotY();
        g = level.getGoal();
        p = level.getProjectile();
        // Goal rectangles
        //goalLeft = new Rectangle(g.getxPos(), g.getyPos(), 0.1f * g.getSizeX(), g.getSizeY());
        //goalBottom = new Rectangle(g.getxPos() + 0.1f * g.getSizeX(), g.getyPos(), 0.8f * g.getSizeX(),0.2f * g.getSizeY());
        //goalRight = new Rectangle(g.getxPos() + 0.1f * g.getSizeX() + 0.8f * g.getSizeX(), g.getyPos(), 0.1f * g.getSizeX(),g.getSizeY());
       
        shapeRenderer = new ShapeRenderer();
        
    }
    

    public void render(SpriteBatch batch, Level level) {
        
        pivotX = level.getPivotX();
        pivotY = level.getPivotY();
        g = level.getGoal();
        p = level.getProjectile();
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(g.getxPos(), g.getyPos(), 0.2f * g.getSizeX(), g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX(), g.getyPos(), 0.6f * g.getSizeX(),0.2f * g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX() + 0.6f * g.getSizeX(), g.getyPos(), 0.2f * g.getSizeX(),g.getSizeY());
        shapeRenderer.circle(p.getxPos(), p.getyPos(), p.getRadius());
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(pivotX, pivotY, 5);
        shapeRenderer.end();
        
    }
    
    public void dispose() {
    }
    
}
