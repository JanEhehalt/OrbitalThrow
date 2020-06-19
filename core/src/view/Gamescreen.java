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
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import java.awt.Point;

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
    
    int x;
    int y;
    float w;
    float h;
    float th;

    //Rectangle goalLeft;
    //Rectangle goalRight;
    //Rectangle goalBottom;
    ShapeRenderer shapeRenderer;
    int pivotX;
    int pivotY;
    
    Rectangle[] goalRects;
    Rectangle goalRect;
    Circle projectileCirc;
    // 0: Left, 1: LeftTop, 2: CenterLeft, 3: CenterBottom, 4: CenterRight, 5: RightTop, 6: Right, 7: Bottom
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    Polygon[] objects;
    
    boolean win;
    
    public Gamescreen(Level level, float width, float height, Matrix4 matrix){
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;
        pivotX = level.getPivotX();
        pivotY = level.getPivotY();
        g = level.getGoal();
        objects = level.getPolygons();
        x = g.getxPos();
        y = g.getyPos();
        w = g.getSizeX();
        h = g.getSizeY();
        th = g.getThickness();
        p = level.getProjectile();
        
        win = false;
        
        
        goalRects = new Rectangle[8];
        
        goalRects[0] = new Rectangle(x              ,y              ,1          ,h         );
        goalRects[1] = new Rectangle(x              ,y + h - 1      ,w * th     ,1         );
        goalRects[2] = new Rectangle(x + th * w-1f  ,y + h * th     ,1          ,h * th*4  );
        goalRects[3] = new Rectangle(x + th * w     ,y + h * th - 1 ,w *th*3    ,1         );
        goalRects[4] = new Rectangle(x + th*4 * w   ,y + h * th     ,1          ,h * th*4  );
        goalRects[5] = new Rectangle(x + th*4 * w   ,y + h - 1f     ,w *th      ,1         );
        goalRects[6] = new Rectangle(x + w-1        ,y              ,1          ,h         );
        goalRects[7] = new Rectangle(x              ,y              ,w          ,1         );
        
        goalRect = new Rectangle(x + w*th, y + h*th, w * th * 3, h * 0.1f);
        projectileCirc = new Circle((float)level.getProjectile().getxPos(), (float)level.getProjectile().getyPos(), level.getProjectile().getRadius());

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
        shapeRenderer.rect(x, y,th * w, h);
        shapeRenderer.rect(x + th * w,y, th*3 * w,th * h);
        shapeRenderer.rect(x + th *w + th*3 * w, y, th * w,h);
        shapeRenderer.circle((float) p.getxPos(), (float) p.getyPos(), p.getRadius());
        shapeRenderer.setColor(Color.RED);

/*
        //goal hitboxes
        for(int i = 0; i < goalRects.length; i++){
            shapeRenderer.rect(goalRects[i].getX(), goalRects[i].getY(), goalRects[i].getWidth(), goalRects[i].getHeight());
        }
        // projectile hitbox
        shapeRenderer.circle(projectileCirc.x, projectileCirc.y, projectileCirc.radius);
        // goal hitbox
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(goalRect.getX(), goalRect.getY(), goalRect.getWidth(), goalRect.getHeight());/*
        // full goal size hitbox;
        shapeRenderer.rect(g.getxPos(), g.getyPos(), g.getSizeX(), g.getSizeY());
        */

        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(pivotX, pivotY, 5);
        
        
        
        shapeRenderer.end();
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for(Polygon object : objects){
            shapeRenderer.polygon(objects[0].getVertices());
        }
        shapeRenderer.end();


    }
    
    public void dispose() {
    }


    public void step(Level level){
        projectileCirc = new Circle((float)level.getProjectile().getxPos(), (float)level.getProjectile().getyPos(), level.getProjectile().getRadius());
        if(Intersector.overlaps(projectileCirc, goalRect)) win = true;

    }
    
    public boolean getWin(){
        return win;
    }

    public Rectangle[] getGoalRects(){
        return goalRects;
    }
    public Circle getProjectileCirc(){
        return projectileCirc;
    }

    
}
