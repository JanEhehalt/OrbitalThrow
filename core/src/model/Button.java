/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Jan
 */
public class Button {

    private String text;
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int id;
    private int listId;
    private Rectangle rect;

    public Button(String text, int xPos, int yPos, int width, int height, int id, int listId){
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.id = id;   //0: pivot, 1: goal, 2: rect, 3: addRect
        this.listId = listId;
        rect = new Rectangle(xPos, yPos, width, height);
    }
    
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the xPos
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * @param xPos the xPos to set
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * @return the yPos
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * @param yPos the yPos to set
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setRectangle(Rectangle rect){
        this.rect = rect;
    }
    
    public Rectangle getRectangle(){
        return rect;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public int getListId(){
        return listId;
    }

}
