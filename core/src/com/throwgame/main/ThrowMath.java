package com.throwgame.main;

import com.badlogic.gdx.math.Vector2;

import model.Projectile;

public class ThrowMath {
    private double tanA;
    private double coefficient;
    private double y0;
    private double xPos0;
    private double v;
    private double vX;
    private boolean initialised;

    public ThrowMath(){
        this.tanA = 0;
        this.coefficient = 0;
        this.y0 = 0;
        this.initialised = false;
    }

    public void initThrow(double alpha, double g, double v0, double y0, double xPos0, double vX){
        this.tanA = Math.tan(alpha);
        this.coefficient = g / (2 * Math.pow(v0, 2) * Math.pow(Math.cos(alpha), 2));
        this.y0 = y0;
        this.initialised = true;
        this.xPos0 = xPos0;
        this.vX = vX;
    }

    public Vector2 calculateY(double xPos){
        if(this.initialised){
            Vector2 newPos = new Vector2();

            //this.v = getV(xPos);
            //System.out.println("V: " + this.v);

            double newXPos = (xPos + this.vX);
            newPos.x = (float) newXPos;

            double newYPos = 1* (this.y0 + this.tanA * (newXPos - this.xPos0) - this.coefficient * Math.pow((newXPos - this.xPos0), 2));
            //System.out.println("Neue x: Position " + newXPos);
            //System.out.println("Neue y: Position " + newYPos);
            newPos.y = (float) newYPos;

            return newPos;
        }
        else{
            System.out.println("Der Wurf wurde nicht initialisiert!");
            return new Vector2();
        }
    }

    public double getV(double xPos){
        double v = 1* (this.tanA - this.coefficient * 2 * (xPos - this.xPos0));
        return v;
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
