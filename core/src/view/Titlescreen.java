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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;

/**
 *
 * @author Jan
 */
public class Titlescreen{
    
    BitmapFont font;
    Timer t;
    boolean movement;
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    float clicktostartX;
    float clicktostartY;
    
    public Titlescreen(float width, float height){
        this.GAME_WORLD_WIDTH = width;
        this.GAME_WORLD_HEIGHT = height;
        movement = true;
        
        
        t = new Timer();
        
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        clicktostartX = GAME_WORLD_WIDTH / 2 - getTextWidth("click to start ...") / 2;
        clicktostartY = GAME_WORLD_HEIGHT / 2;
        
        font.setColor(Color.BLACK);
        
        
        t.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(clicktostartY < GAME_WORLD_HEIGHT * 0.4)
                    movement = true;
                else if(clicktostartY > GAME_WORLD_HEIGHT * 0.5)
                    movement = false;
                if(movement)
                    clicktostartY = clicktostartY + 3;
                else
                    clicktostartY = clicktostartY - 3;
            }
        },0 , 0.035f);
        
        
    }
    
    public void render(SpriteBatch batch) {
        font.getData().setScale(6);
        font.draw(batch, "ORBITAL THROW", GAME_WORLD_WIDTH / 2 - getTextWidth("ORBITAL THROW") / 2,  GAME_WORLD_HEIGHT * 0.9f);
        font.getData().setScale(2);
        clicktostartX = GAME_WORLD_WIDTH / 2 - getTextWidth("click to start ...") / 2;
        font.draw(batch, "click to start ...", clicktostartX,  clicktostartY);
    }
    public void dispose() {
        t.clear();
    }
    
    public float getTextWidth(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        String item = text;
        glyphLayout.setText(font,item);
        return glyphLayout.width;
    }
}
