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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
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
    
    Rectangle[] goalRects; 
    // 0: Left, 1: LeftTop, 2: CenterLeft, 3: CenterBottom, 4: CenterRight, 5: RightTop, 6: Right, 7: Bottom
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    
    public Gamescreen(Level level, float width, float height, Matrix4 matrix){
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
        
        goalRects = new Rectangle[8];
        goalRects[0] = new Rectangle(g.getxPos(),g.getyPos(),1,g.getSizeY());
        goalRects[1] = new Rectangle(g.getxPos(), g.getyPos() + g.getSizeY() - 1, g.getSizeX() * 0.2f, 1);
        goalRects[2] = new Rectangle(g.getxPos() + 0.2f * g.getSizeX()-1f,g.getyPos() + g.getSizeY() * 0.2f, 1, g.getSizeY() * 0.8f );
        goalRects[3] = new Rectangle(g.getxPos() + 0.2f * g.getSizeX(), g.getyPos() + g.getSizeY() * 0.2f - 1, g.getSizeX() * 0.6f, 1);
        goalRects[4] = new Rectangle(g.getxPos() + 0.8f * g.getSizeX(),g.getyPos() + g.getSizeY() * 0.2f,1, g.getSizeY() * 0.8f);
        goalRects[5] = new Rectangle(g.getxPos() + 0.8f * g.getSizeX(), g.getyPos() + g.getSizeY() - 1f, g.getSizeX() * 0.2f, 1);
        goalRects[6] = new Rectangle(g.getxPos() + g.getSizeX()-1, g.getyPos(), 1, g.getSizeY());
        goalRects[7] = new Rectangle(g.getxPos(), g.getyPos(), g.getSizeX(), 1);
       
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix);
        
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
        
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(g.getxPos(), g.getyPos(), 0.2f * g.getSizeX(), g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX(), g.getyPos(), 0.6f * g.getSizeX(),0.2f * g.getSizeY());
        shapeRenderer.rect(g.getxPos() + 0.2f * g.getSizeX() + 0.6f * g.getSizeX(), g.getyPos(), 0.2f * g.getSizeX(),g.getSizeY());
        shapeRenderer.circle((float) p.getxPos(), (float) p.getyPos(), p.getRadius());
        shapeRenderer.setColor(Color.RED);
        for(int i = 0; i < goalRects.length; i++){
            shapeRenderer.rect(goalRects[i].getX(), goalRects[i].getY(), goalRects[i].getWidth(), goalRects[i].getHeight());
        }
        
        //shapeRenderer.rect(g.getxPos(), g.getyPos(), g.getSizeX(), g.getSizeY());
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(pivotX, pivotY, 5);
        shapeRenderer.end();
        
        
    }
    
    public void dispose() {
    }
    
}
