/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.throwgame.main.ThrowMath;

/**
 *
 * @author Jan
 */
public class Level {
    private final double G = 9.81;
    private final int RADIUS = 50;

    private Goal goal;
    private Projectile projectile;
    private ThrowMath math;
    private double angle;
    private int xPosPivot;
    private int yPosPivot;
    private double angleSpeed;
    private boolean isReleased;

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
    }

    public void projectileReleased(){
        this.isReleased = true;
        double v0 = angleSpeed * RADIUS;
        this.math.initThrow(angle, G, v0, projectile.getyPos());
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
        angleSpeed += 0.1;
        angle += angleSpeed;

        Vector2 newPosVector = math.pivotGetNewPos(this.angle, this.xPosPivot, this.yPosPivot, RADIUS);
        this.projectile.setxPos((int) newPosVector.x);
        this.projectile.setyPos((int) newPosVector.y);
    }

    private void stepAir(){
        projectile.setxPos(projectile.getxPos() + 1);
        projectile.setyPos(math.calculateY(projectile.getxPos()));
    }

    public Goal getGoal() {
        return goal;
    }

    public Projectile getProjectile() {
        return projectile;
    }
}
