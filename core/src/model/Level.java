/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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

        traceIndex = 0;
        isTraceInitialised = new boolean[50];
        traces = new Vector2[isTraceInitialised.length];
        for(int i = 0; i < traces.length; i++){
            traces[i] = new Vector2(-10, -10);
        }
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
        this.projectile.setvY(-this.projectile.getvY);
    }

    public void verticalCollision(){
        this.projectile.setvX(-this.projectile.getvX);
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

        System.out.println(Math.toDegrees(angle));

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
}
