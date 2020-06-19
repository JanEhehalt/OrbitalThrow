/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

/**
 *
 * @author Jan
 */
public class Winscreen{
    
    BitmapFont font;
    Timer t;
    
    Sprite reset;
    Sprite level;
    Sprite next;
    
    Sprite winSprite;
    boolean movement;

    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    
    boolean win;

    int lvl;
    
    public Winscreen(float width, float height, boolean win, int lvl){
        t = new Timer();
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;
        this.win = win;
        this.lvl = lvl;
        
        if(win){
            movement = true;
            winSprite = new Sprite(new Texture(Gdx.files.internal("win.png")));
            winSprite.setX(GAME_WORLD_WIDTH / 2 - winSprite.getWidth() / 2);
            winSprite.setY(GAME_WORLD_HEIGHT * 0.7f - winSprite.getHeight() / 2);
        }
        float w = GAME_WORLD_WIDTH;
        float h = GAME_WORLD_HEIGHT;
        
        next = new Sprite(new Texture("skipicon.png"));
        next.setPosition(w * 0.75f - next.getWidth()/2, h*0.35f - next.getHeight()/2);
        
        level = new Sprite(new Texture("levelicon.png"));
        level.setPosition(w * 0.25f - level.getWidth()/2, h*0.35f - level.getHeight()/2);
        
        reset = new Sprite(new Texture("reseticon.png"));
        reset.setPosition(w/2 - reset.getWidth()/2, h*0.35f - reset.getHeight()/2);
        if(win){
            t.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    if(winSprite.getY() < GAME_WORLD_HEIGHT*0.7){
                        movement = true;}
                    if(winSprite.getY() > GAME_WORLD_HEIGHT * 0.8){
                        movement = false;}
                    if(movement){
                        winSprite.setY(winSprite.getY() + 3);
                    }
                    else{
                        winSprite.setY(winSprite.getY() - 3);
                    }
                }
            },0 , 0.035f);
        }
        
    }
    
    public void render(SpriteBatch batch) {
        if(lvl < 9 && win)next.draw(batch);
        level.draw(batch);
        reset.draw(batch);
        if(win)winSprite.draw(batch);
        
    }
    public void dispose() {
        t.clear();
    }
    public boolean getWin(){
        return win;
    }
}
