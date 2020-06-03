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
    
    Sprite win;
    boolean movement;
    
    public Winscreen(){
        t = new Timer();
        /*
        movement = true;
        win = new Sprite(new Texture(Gdx.files.internal("win.png")));
        win.setX(Gdx.graphics.getWidth() / 2 - win.getWidth() / 2);
        win.setY(Gdx.graphics.getHeight() * 0.7f - win.getHeight() / 2);
        */
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        
        next = new Sprite(new Texture("skipicon.png"));
        next.setPosition(w * 0.75f - next.getWidth()/2, h*0.35f - next.getHeight()/2);
        
        level = new Sprite(new Texture("levelicon.png"));
        level.setPosition(w * 0.25f - level.getWidth()/2, h*0.35f - level.getHeight()/2);
        
        reset = new Sprite(new Texture("reseticon.png"));
        reset.setPosition(w/2 - reset.getWidth()/2, h*0.35f - reset.getHeight()/2);
        
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                /*if(win.getY() < Gdx.graphics.getHeight()*0.8)
                    movement = true;
                else if(win.getY() > Gdx.graphics.getHeight() * 0.7)
                    movement = false;
                if(movement)
                    win.setY(win.getY() + 3);
                else
                    win.setY(win.getY() - 3);*/
            }
        },0 , 0.035f);
        
        
    }
    
    public void render(SpriteBatch batch) {
        next.draw(batch);
        level.draw(batch);
        reset.draw(batch);
        //win.draw(batch);
    }
    public void dispose() {
        t.clear();
    }
}
