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
        font.draw(batch, "click to start ...", GAME_WORLD_WIDTH / 2 - getTextWidth("click to start ...") / 2, GAME_WORLD_HEIGHT * 0.1f);
        
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rectLine(0.15f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 0.85f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 3);
        shapeRenderer.rectLine(0.15f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 0.15f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 3);
        shapeRenderer.rectLine(0.85f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 0.85f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 3);
        shapeRenderer.rectLine(0.15f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 0.85f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 3);
        
        float previewX = 0.15f * GAME_WORLD_WIDTH;
        float previewY = 0.15f * GAME_WORLD_HEIGHT;
        float previewWidth = 0.7f * GAME_WORLD_WIDTH;
        float previewHeight = 0.7f * GAME_WORLD_HEIGHT;
        
        
        shapeRenderer.rectLine(previewX + level.getPivotX() * 0.7f, previewY + level.getPivotY() * 0.7f, previewX + (float)level.getProjectile().getxPos() * 0.7f, previewY + (float)level.getProjectile().getyPos()* 0.7f, 3);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(previewX + level.getPivotX() * 0.7f, previewY + level.getPivotY() * 0.7f, 5);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(previewX + (float)level.getProjectile().getxPos() * 0.7f, previewY + (float)level.getProjectile().getyPos()* 0.7f, 8);
        
        float x = level.getGoal().getxPos();
        float y = level.getGoal().getyPos();
        float th = level.getGoal().getThickness();
        float w = level.getGoal().getSizeX();
        float h = level.getGoal().getSizeY();
       
        shapeRenderer.rect(previewX + x * 0.7f, previewY + y * 0.7f, (th * w) * 0.7f  ,h * 0.7f);
        shapeRenderer.rect(previewX + (x + th * w) * 0.7f, previewY + y * 0.7f, (th*3 * w) * 0.7f,(th * h)*0.7f);
        shapeRenderer.rect(previewX + (x + th *w + th*3 * w) * 0.7f, previewY + y * 0.7f, (th * w) * 0.7f  ,h * 0.7f);
        
        shapeRenderer.end();
        batch.begin();
        
        
       
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
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }
    

}
