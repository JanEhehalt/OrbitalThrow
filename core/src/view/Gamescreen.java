/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

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
    
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    
    public Gamescreen(Level level, float width, float height, OrthographicCamera camera){
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;
        pivotX = level.getPivotX();
        pivotY = level.getPivotY();
        g = level.getGoal();
        p = level.getProjectile();
        // Goal rectangles
        //goalLeft = new Rectangle(g.getxPos(), g.getyPos(), 0.1f * g.getSizeX(), g.getSizeY());
        //goalBottom = new Rectangle(g.getxPos() + 0.1f * g.getSizeX(), g.getyPos(), 0.8f * g.getSizeX(),0.2f * g.getSizeY());
        //goalRight = new Rectangle(g.getxPos() + 0.1f * g.getSizeX() + 0.8f * g.getSizeX(), g.getyPos(), 0.1f * g.getSizeX(),g.getSizeY());
       
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        
    }
    

    public void render(SpriteBatch batch, Level level) {
        
        pivotX = level.getPivotX();
        pivotY = level.getPivotY();
        g = level.getGoal();
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);

        if(level.released()) {
            for (int i = 0; i < level.traces.length; i++) {
                if(level.isTraceInitialised[i]){
                    Vector2 trace = level.traces[i];
                    shapeRenderer.rect(trace.x, trace.y, 3, 3);
                }
            }
        }
        else{
            shapeRenderer.rectLine((float) level.getPivotX(), (float) level.getPivotY(), (float) level.getProjectile().getxPos(), (float) level.getProjectile().getyPos(), 3);
        }

        shapeRenderer.rect(g.getxPos(), g.getyPos(), 0.2f * g.getSizeX(), g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX(), g.getyPos(), 0.6f * g.getSizeX(),0.2f * g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX() + 0.6f * g.getSizeX(), g.getyPos(), 0.2f * g.getSizeX(),g.getSizeY());
        shapeRenderer.circle((float) p.getxPos(), (float) p.getyPos(), p.getRadius());
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(pivotX, pivotY, 5);
        shapeRenderer.circle(0,0,5);
        shapeRenderer.end();
        
        
    }
    
    public void dispose() {
    }
    
}
