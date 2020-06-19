/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    
    boolean movement;

    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    
    boolean win;
    
    float winX;
    float winY;
    boolean movementWin;

    int lvl;
    
    public Winscreen(float width, float height, boolean win, int lvl){
        t = new Timer();
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;
        this.win = win;
        this.lvl = lvl;
        
        if(win){
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 21;
            font = generator.generateFont(parameter); // font size 12 pixels
            generator.dispose(); // don't forget to dispose to avoid memory leaks!
        
            font.setColor(Color.BLACK);
            
            movementWin = true;
            winX = GAME_WORLD_WIDTH /2 - getTextWidth("Level win") / 2;
            winY = GAME_WORLD_HEIGHT * 0.85f;
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
                    if(winY < GAME_WORLD_HEIGHT * 0.8)
                    movementWin = true;
                else if(winY > GAME_WORLD_HEIGHT * 0.9)
                    movementWin = false;
                if(movementWin)
                    winY = winY + 3;
                else
                    winY = winY - 3;
                }
            },0 , 0.035f);
        }
        
    }
    
    public void render(SpriteBatch batch) {
        if(lvl < 9 && win)next.draw(batch);
        level.draw(batch);
        reset.draw(batch);
        if(win){
            font.getData().setScale(8);
            winX = GAME_WORLD_WIDTH /2 - getTextWidth("Level win") / 2;
            font.draw(batch, "Level win", winX,  winY);
       
        }
        
    }
    public void dispose() {
        t.clear();
    }
    public boolean getWin(){
        return win;
    }
    public float getTextWidth(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        String item = text;
        glyphLayout.setText(font,item);
        return glyphLayout.width;
    }
}
