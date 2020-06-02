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

/**
 *
 * @author Jan
 */
public class Titlescreen{
    
    BitmapFont font;
    Timer t;
    Sprite clicktostart;
    boolean movement;
    
    public Titlescreen(){
        movement = true;
        clicktostart = new Sprite(new Texture(Gdx.files.internal("clicktostart.png")));
        clicktostart.setX(Gdx.graphics.getWidth() / 2 - clicktostart.getWidth() / 2);
        clicktostart.setY(Gdx.graphics.getHeight() / 2 - clicktostart.getHeight() / 2);
        t = new Timer();
        
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(clicktostart.getY() < Gdx.graphics.getHeight() * 0.4)
                    movement = true;
                else if(clicktostart.getY() > Gdx.graphics.getHeight() * 0.5)
                    movement = false;
                if(movement)
                    clicktostart.setY(clicktostart.getY() + 3);
                else
                    clicktostart.setY(clicktostart.getY() - 3);
            }
        },0 , 0.035f);
        
        
    }
    
    public void render(SpriteBatch batch) {
        clicktostart.draw(batch);
    }
    public void dispose() {
        t.clear();
    }
}
