/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.throwgame.main.ThrowMath;

/**
 *
 * @author Jan
 */
public class Level {

    private Goal goal;
    private Projectile projectile;
    private ThrowMath math;

    public Level(Goal goal, Projectile projectile){
        this.goal = goal;
        this.projectile = projectile;
        this.math = new ThrowMath();
    }

    public void projectileReleased(){

    }

    public void step(){
        
    }

    public Goal getGoal() {
        return goal;
    }

    public Projectile getProjectile() {
        return projectile;
    }
}
