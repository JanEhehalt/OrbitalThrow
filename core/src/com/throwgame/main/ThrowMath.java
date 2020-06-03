package com.throwgame.main;

import com.badlogic.gdx.math.Vector2;

import model.Projectile;

public class ThrowMath {
    private double tanA;
    private double coefficient;
    private double y0;
    private int xPos0;
    private boolean initialised;

    public ThrowMath(){
        this.tanA = 0;
        this.coefficient = 0;
        this.y0 = 0;
        this.initialised = false;
    }

    public void initThrow(double alpha, double g, double v0, double y0, int xPos0){
        this.tanA = Math.tan(alpha);
        this.coefficient = g / (2 * Math.pow(v0, 2) * Math.pow(Math.cos(alpha), 2));
        this.y0 = y0;
        this.initialised = true;
        this.xPos0 = xPos0;
    }

    public int calculateY(int xPos){
        if(this.initialised){
            double res = 1* (this.y0 + this.tanA * (xPos - this.xPos0) - this.coefficient * Math.pow((xPos - this.xPos0), 2));
            return (int) res;
        }
        else{
            System.out.println("Der Wurf wurde nicht initialisiert!");
            return -1;
        }
    }

    public Vector2 test(Projectile projectile, double g){
        Vector2 lol = new Vector2();

        lol.x = (int) (projectile.getxPos() + projectile.getvX());
        projectile.setvY(projectile.getvY() - g);
        lol.y = (int) (projectile.getyPos() + projectile.getvY());

        return lol;
    }

    public Vector2 pivotGetNewPos(double alpha, int xPosPivot, int yPosPivot, int radius){
        Vector2 vector = new Vector2();
        vector.x = (float) (radius * Math.sin(alpha));
        vector.y = (float) (radius * Math.cos(alpha));

        return vector;
    }
}
