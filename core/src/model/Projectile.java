package model;

public class Projectile {

    private double xPos;
    private double yPos;
    private int mass;
    private int radius;
    private double vX;
    private double vY;
    private final int type;

    public Projectile(int xPos, int yPos, int type){
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        this.vX = 0;
        this.vY = 0;

        switch(type){
            case 0:
                mass = 5;
                radius = 10;
                break;
        }
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public int getType() {
        return type;
    }
}
