package com.throwgame.main;

import com.badlogic.gdx.math.Vector2;

public class ThrowMath {
    private double tanA;
    private double coefficient;
    private double y0;
    private boolean initialised;

    public ThrowMath(){
        this.tanA = 0;
        this.coefficient = 0;
        this.y0 = 0;
        this.initialised = false;
    }

    public void initThrow(double alpha, double g, double v0, double y0){
        this.tanA = Math.tan(alpha);
        this.coefficient = g / (2 * Math.pow(v0, 2) * Math.pow(Math.cos(alpha), 2));
        this.y0 = y0;
        this.initialised = true;
    }

    public int calculateY(int xPos){
        if(this.initialised){
            double res = this.y0 + this.tanA * xPos - this.coefficient * Math.pow(xPos, 2);
            return (int) res;
        }
        else{
            System.out.println("Der Wurf wurde nicht initialisiert!");
            return -1;
        }
    }

    public Vector2 pivotGetNewPos(double alpha, int xPosPivot, int yPosPivot, int radius){
        Vector2 vector = new Vector2();
        vector.x = (float) (radius * Math.sin(alpha));
        vector.y = (float) (radius * Math.cos(alpha));

        return vector;
    }
}
