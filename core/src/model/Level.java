/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.throwgame.main.ThrowMath;

import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class Level {
    private final double G = 0.05;
    private final int RADIUS = 150;
    private final double ELOSS = 1;

    private Goal goal;
    private Projectile projectile;
    private ThrowMath math;
    private double angle;
    private int xPosPivot;
    private int yPosPivot;
    private double angleSpeed;
    private boolean isReleased;

    private int traceIndex;
    public boolean[] isTraceInitialised;
    public Vector2[] traces;
    ArrayList<Rectangle> objects;

    private boolean isWon;

    public Level(Goal goal, Projectile projectile, int xPosPivot, int yPosPivot){
        this.goal = goal;
        this.projectile = projectile;
        this.projectile.setxPos(xPosPivot + RADIUS);
        this.projectile.setyPos(yPosPivot);
        this.math = new ThrowMath();
        this.isReleased = false;

        this.xPosPivot = xPosPivot;
        this.yPosPivot = yPosPivot;
        this.angle = 0;
        this.angleSpeed = 0;
        isWon = false;

        objects = new ArrayList<>();
        
        traceIndex = 0;
        isTraceInitialised = new boolean[50];
        traces = new Vector2[isTraceInitialised.length];
        for(int i = 0; i < traces.length; i++){
            traces[i] = new Vector2(-10, -10);
        }
    }
    public Level(){
    }

    public void projectileReleased(){
        this.isReleased = true;
        double v0 = angleSpeed * RADIUS;
        double tempAngle = angle - Math.PI / 2;
        double vX = v0 * Math.sin(tempAngle);
        //this.math.initThrow(/*Math.PI / 2 + angle*/tempAngle, G, v0, projectile.getyPos(), projectile.getxPos(), vX);
        projectile.setvX(v0 * Math.sin(tempAngle));
        projectile.setvY(v0 * Math.cos(tempAngle));
    }

    public void horizontalCollision(){
        this.projectile.setvY(-this.projectile.getvY() * ELOSS);
        this.projectile.setvX(this.projectile.getvX() * ELOSS);
    }

    public void verticalCollision(){
        this.projectile.setvX(-this.projectile.getvX() * ELOSS);
        this.projectile.setvY(this.projectile.getvY() * ELOSS);
    }

    public void step(){
        if(this.isReleased){
            stepAir();
        }
        else{
            stepPivot();
        }
    }

    private void stepPivot(){
        angleSpeed += 0.0001;
        angle -= angleSpeed;

        //System.out.println(Math.toDegrees(angle));

        Vector2 newPosVector = math.pivotGetNewPos(this.angle, this.xPosPivot, this.yPosPivot, RADIUS);
        this.projectile.setxPos(xPosPivot + (int) newPosVector.x);
        this.projectile.setyPos(yPosPivot + (int) newPosVector.y);
    }

    private void stepAir(){
        /*Vector2 newPos = math.calculateY(projectile.getxPos());
        projectile.setxPos(newPos.x);
        projectile.setyPos(newPos.y);
        */

        Vector2 lol = math.test(projectile, G);
        projectile.setxPos(lol.x);
        projectile.setyPos(lol.y);

        changeTracePos(lol);
    }

    public void changeTracePos(Vector2 newPos){
        if(traceIndex >= traces.length){
            traceIndex = 0;
        }
        traces[traceIndex] = newPos;

        if(!isTraceInitialised[traceIndex]){
            isTraceInitialised[traceIndex] = true;
        }

        traceIndex++;
    }
    
    public void reset(){
        isReleased = false;
        projectile.setxPos(xPosPivot + RADIUS);
        projectile.setyPos(yPosPivot);
        angle = 0;
        angleSpeed = 0;
        isTraceInitialised = new boolean[traces.length];
    }

    public Goal getGoal() {
        return goal;
    }

    public Projectile getProjectile() {
        return projectile;
    }
    
    public int getPivotX(){
        return xPosPivot;
    }
    public int getPivotY(){
        return yPosPivot;
    }
    public boolean released(){
        return isReleased;
    }
    public ArrayList<Rectangle> getObjects(){
        return objects;
    }
    public void addRectangle(int x, int y, int width, int height){
        objects.add(new Rectangle(x,y,width,height));
    }
    
    public void setPivot(int x, int y){
        xPosPivot = x;
        yPosPivot = y;
    }
    public void setGoal(int x, int y){
        goal.setxPos(x);
        goal.setyPos(y);
        goal.setSizeX(250);
        goal.setSizeY(140);
    }
    public void removeObstacle(Rectangle rect){
        objects.remove(rect);
    }

    public void levelWon(){
        isWon = true;
    }
    public boolean isWon(){
        return this.isWon;
    }

    public double getG(){
        return G;
    }
            
}
