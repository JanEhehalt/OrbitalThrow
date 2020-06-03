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
    
    public Winscreen(){
        t = new Timer();
        
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        
        next = new Sprite(new Texture("skipicon.png"));
        next.setPosition(w * 0.75f - next.getWidth()/2, h/2 - next.getHeight()/4);
        
        level = new Sprite(new Texture("levelicon.png"));
        level.setPosition(w * 0.25f - level.getWidth()/2, h/2 - level.getHeight()/4);
        
        reset = new Sprite(new Texture("reseticon.png"));
        reset.setPosition(w/2 - reset.getWidth()/2, h/2 - reset.getHeight()/2);
        
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                
            }
        },0 , 0.035f);
        
        
    }
    
    public void render(SpriteBatch batch) {
        next.draw(batch);
        level.draw(batch);
        reset.draw(batch);
    }
    public void dispose() {
        t.clear();
    }
}
