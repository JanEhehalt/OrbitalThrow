/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
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
    ShapeRenderer shapeRenderer;
    Sprite clicktostart;
    boolean movementStart;
    float clicktostartX;
    float clicktostartY;
    
    
    public Levelscreen(int levelAmount, float width, float height, Matrix4 matrix){
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
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix);
        
        
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        
        font.setColor(Color.BLACK);
        
        movementStart = true;
        clicktostartX = GAME_WORLD_WIDTH / 2 - getTextWidth("click to start ...") / 2;
        clicktostartY = GAME_WORLD_HEIGHT / 2;
        
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
                if(clicktostartY < GAME_WORLD_HEIGHT * 0.4)
                    movementStart = true;
                else if(clicktostartY > GAME_WORLD_HEIGHT * 0.5)
                    movementStart = false;
                if(movementStart)
                    clicktostartY = clicktostartY + 3;
                else
                    clicktostartY = clicktostartY - 3;
            }
        },0 , 0.035f);
    }
    
    public void render(SpriteBatch batch, Level level) {
        
        if(selectedLevel > 0){
            buttonLeft.draw(batch);
        }
        if(selectedLevel < levelAmount){
            buttonRight.draw(batch);
        }
        
        
        font.getData().setScale(6);
        font.draw(batch,"LEVEL: "+ (selectedLevel + 1), GAME_WORLD_WIDTH / 2 - getTextWidth("LEVEL: "+ selectedLevel) / 2, GAME_WORLD_HEIGHT * 0.95f);
        
        font.getData().setScale(2);
        clicktostartX = GAME_WORLD_WIDTH / 2 - getTextWidth("click to start ...") / 2;
        font.draw(batch, "click to start ...", clicktostartX,  clicktostartY);
       
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
    public float getTextWidth(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        String item = text;
        glyphLayout.setText(font,item);
        return glyphLayout.width;
    }

}
