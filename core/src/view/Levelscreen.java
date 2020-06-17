/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import model.Goal;
import model.Level;
import model.Projectile;

/**
 *
 * @author Jan
 */
public class Levelscreen{
    
    Sprite[] levelPreview;
    Sprite buttonRight;
    Sprite buttonLeft;
    int selectedLevel;
    BitmapFont font;
    Timer t;
    boolean movement;
    int levelAmount;
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    
    public Levelscreen(int levelAmount, float width, float height){
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;
        levelPreview = new Sprite[levelAmount];
        buttonRight = new Sprite(new Texture("buttonRight.png"));
        buttonRight.setY(GAME_WORLD_HEIGHT/ 2 - buttonRight.getHeight() / 2);
        buttonRight.setX(GAME_WORLD_WIDTH - 10 - buttonRight.getWidth());
        buttonLeft = new Sprite(new Texture("buttonLeft.png"));
        buttonLeft.setY(GAME_WORLD_HEIGHT / 2 - buttonLeft.getHeight() / 2);
        buttonLeft.setX(10);
        selectedLevel = 0;
        this.levelAmount = levelAmount;
        
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        
        t = new Timer();
        
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(buttonLeft.getX() <= 0){
                    movement = true;
                }
                if(buttonLeft.getX() + buttonLeft.getWidth() > GAME_WORLD_WIDTH * 0.12){
                    movement = false;
                }
                if(movement){
                    buttonRight.setX(buttonRight.getX() - 2);
                    buttonLeft.setX(buttonLeft.getX() + 2);
                }
                else{
                    buttonRight.setX(buttonRight.getX() + 2);
                    buttonLeft.setX(buttonLeft.getX() - 2);
                }
            }
        },0 , 0.045f);
    }
    
    public void render(SpriteBatch batch) {
        
        if(selectedLevel > 0){
            buttonLeft.draw(batch);
        }
        if(selectedLevel < levelAmount){
            buttonRight.draw(batch);
        }
        font.draw(batch, "" + selectedLevel, GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2);
        
       
    }
    
    public void dispose(){
        t.clear();
    }
    
    public void setSelectedLevel(int i){
        selectedLevel = i;
    }
    public int getSelectedLevel(){
        return selectedLevel;
    }

}
