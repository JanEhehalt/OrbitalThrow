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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

import model.Button;
import model.Goal;
import model.Level;
import model.Projectile;

/**
 *
 * @author Jan
 */
public class Levelscreen{

    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;

    // SPRITES LEFT-RIGHT
    Sprite buttonRight;
    Sprite buttonLeft;
    boolean movement;

    // VARIABLES
    int selectedLevel;
    int levelAmount;

    // SHAPE RENDERER
    ShapeRenderer shapeRenderer;

    // BUTTONS
    ArrayList<Button> buttons;

    // BITMAP FONT
    BitmapFont font;

    // TIMER FOR SPRITE ANIMATION
    Timer t;
    
    public Levelscreen(int levelAmount, float width, float height, Matrix4 matrix){
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;

        // SPRITES LEFT-RIGHT
        buttonRight = new Sprite(new Texture("buttonRight.png"));
        buttonRight.setY(GAME_WORLD_HEIGHT/ 2 - buttonRight.getHeight() / 2);
        buttonRight.setX(GAME_WORLD_WIDTH - 10 - buttonRight.getWidth());
        buttonLeft = new Sprite(new Texture("buttonLeft.png"));
        buttonLeft.setY(GAME_WORLD_HEIGHT / 2 - buttonLeft.getHeight() / 2);
        buttonLeft.setX(10);

        // VARIABLES
        selectedLevel = 0;
        this.levelAmount = levelAmount;

        // SHAPE RENDERER
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix);

        // BUTTONS
        buttons = new ArrayList<>();
        buttons.add(new Button("< back", 20,10,200,80,0));
        
        
        // BITMAP FONT
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);

        // TIMER FOR SPRITES ANIMATION
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

        // LEFT - RIGHT BUTTON
        if(selectedLevel > 0){
            buttonLeft.draw(batch);
        }
        if(selectedLevel < levelAmount){
            buttonRight.draw(batch);
        }

        // LEVEL NAME
        font.getData().setScale(6);
        font.draw(batch,"LEVEL: "+ (selectedLevel + 1), GAME_WORLD_WIDTH / 2 - getTextWidth("LEVEL: "+ selectedLevel) / 2, GAME_WORLD_HEIGHT * 0.95f);
        
        font.getData().setScale(2);
        font.draw(batch, "click to start ...", GAME_WORLD_WIDTH / 2 - getTextWidth("click to start ...") / 2, GAME_WORLD_HEIGHT * 0.1f);
        
        batch.end();

        // PREVIEW
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
            // BORDER
        shapeRenderer.rectLine(0.15f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 0.85f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 3);
        shapeRenderer.rectLine(0.15f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 0.15f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 3);
        shapeRenderer.rectLine(0.85f * GAME_WORLD_WIDTH, 0.15f * GAME_WORLD_HEIGHT, 0.85f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 3);
        shapeRenderer.rectLine(0.15f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 0.85f * GAME_WORLD_WIDTH, 0.85f * GAME_WORLD_HEIGHT, 3);
        
        float previewX = 0.15f * GAME_WORLD_WIDTH;
        float previewY = 0.15f * GAME_WORLD_HEIGHT;
        float previewWidth = 0.7f * GAME_WORLD_WIDTH;
        float previewHeight = 0.7f * GAME_WORLD_HEIGHT;
            // PIVOT
        shapeRenderer.rectLine(previewX + level.getPivotX() * 0.7f, previewY + level.getPivotY() * 0.7f, previewX + (float)level.getProjectile().getxPos() * 0.7f, previewY + (float)level.getProjectile().getyPos()* 0.7f, 3);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(previewX + level.getPivotX() * 0.7f, previewY + level.getPivotY() * 0.7f, 5);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(previewX + (float)level.getProjectile().getxPos() * 0.7f, previewY + (float)level.getProjectile().getyPos()* 0.7f, 8);

            // GOAL
        float x = level.getGoal().getxPos();
        float y = level.getGoal().getyPos();
        float th = level.getGoal().getThickness();
        float w = level.getGoal().getSizeX();
        float h = level.getGoal().getSizeY();
       
        shapeRenderer.rect(previewX + x * 0.7f, previewY + y * 0.7f, (th * w) * 0.7f  ,h * 0.7f);
        shapeRenderer.rect(previewX + (x + th * w) * 0.7f, previewY + y * 0.7f, (th*3 * w) * 0.7f,(th * h)*0.7f);
        shapeRenderer.rect(previewX + (x + th *w + th*3 * w) * 0.7f, previewY + y * 0.7f, (th * w) * 0.7f  ,h * 0.7f);

            // OBSTACLES
        for(Rectangle rect : level.getObjects()){
            shapeRenderer.rect(previewX + rect.getX() * 0.7f, previewY + rect.getY() * 0.7f, rect.getWidth() * 0.7f, rect.getHeight() * 0.7f);
        }
        //

        // BUTTONS
        for(Button button : buttons){
            shapeRenderer.rectLine(button.getxPos(), button.getyPos(), button.getxPos() + button.getWidth(), button.getyPos(), 4);
            shapeRenderer.rectLine(button.getxPos(), button.getyPos(), button.getxPos(), button.getyPos() + button.getHeight(), 4);
            shapeRenderer.rectLine(button.getxPos(), button.getyPos()+button.getHeight(), button.getxPos()+button.getWidth(), button.getyPos() + button.getHeight(), 4);
            shapeRenderer.rectLine(button.getxPos() + button.getWidth(), button.getyPos(),button.getxPos() + button.getWidth(), button.getyPos() + button.getHeight(), 4);
            shapeRenderer.end();
            batch.begin();
            font.getData().setScale(1.5f);
            font.draw(batch, button.getText(),button.getxPos() + button.getWidth()/2 - getTextWidth(button.getText())/2, button.getyPos() + button.getHeight()/2 + getTextHeight(button.getText())/2);
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        }
        shapeRenderer.end();
        batch.begin();
        
        // END
       
    }
    
    public void dispose(){
        t.clear();
    }

    public int touchDown(int x, int y){
        if(Intersector.overlaps(buttons.get(0).getRectangle(), new Rectangle(x,y,1,1))){
            return 0;   // BACK
        }
        else if(x < GAME_WORLD_WIDTH * 0.15){
            return 1;   // LEFT
        }
        else if(x > GAME_WORLD_WIDTH * 0.85){
            return 2;   // RIGHT
        }
        else{
            return 3;   // START LEVEL
        }
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
    public float getTextHeight(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.height;
    }
    

}
