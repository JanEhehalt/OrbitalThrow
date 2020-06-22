/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.awt.Point;
import java.util.ArrayList;
import model.Button;

import model.Goal;
import model.Level;
import model.Projectile;

/**
 *
 * @author Jan
 */
public class Leveleditor{

    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;

    // SHAPE RENDERER
    ShapeRenderer shapeRenderer;

    // BUTTONS
    ArrayList<Button> buttons;

    // VARIABLES
    boolean goalSet;
    boolean pivotSet;
    boolean toSave;
    int state; //-1: nothig selected, 0: place pivot, 2: pivot direction, 3: goal, 4: obstacles

    // BITMAP FONT
    BitmapFont font;

    // LEVEL TO BE EDITED AND SAVED
    Level level;
    
    public Leveleditor(float width, float height, Matrix4 matrix){
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;

        // CREATING LEVEL, TO BE EDITED AND SAVED
        level = new Level(new Goal(0, 0, 200, 150, 0.2f), new Projectile(0, 0, 0), 0, 0);

        // VARIABLES
        state = -1;
        toSave = false;

        // SHAPE RENDERER
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix);

        // CREATE DEFAULT BUTTONS
        buttons = new ArrayList();
        buttons.add(new Button("Pivot", (int)(GAME_WORLD_WIDTH * 0.02),(int) (GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 0));
        buttons.add(new Button("Goal", (int)(GAME_WORLD_WIDTH * 0.02), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 1));
        buttons.add(new Button("new Obstacle", (int)(GAME_WORLD_WIDTH * 0.02), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 2));
        buttons.add(new Button("SAVE", (int)(GAME_WORLD_WIDTH * 0.8), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 4));

        // CREATE BITMAP FONT
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        
        
        
    }
    

    public void render(SpriteBatch batch) {
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        // DRAW PIVOT
        if(pivotSet) {
            shapeRenderer.rectLine((float) level.getPivotX(), (float) level.getPivotY(), (float) level.getProjectile().getxPos(), (float) level.getProjectile().getyPos(), 3);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.circle(level.getPivotX(), level.getPivotY(), 5);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(level.getPivotX(), level.getPivotY() + 150, level.getProjectile().getRadius());
        }

        // DRAW BUTTONS
        if(state == -1){
            for(Button button : buttons){
                if(button.getId() == 4 && (!goalSet || !pivotSet)){

                }
                else{
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
            }
        }

        // DRAW OBSTACLES
        for(Rectangle rect : level.getObjects()){
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        // DRAW GOAL
        shapeRenderer.setColor(Color.BLACK);
        if(goalSet) {
            float x = level.getGoal().getxPos();
            float y = level.getGoal().getyPos();
            float w = level.getGoal().getSizeX();
            float h = level.getGoal().getSizeY();
            float th = level.getGoal().getThickness();
            shapeRenderer.rect(x, y, th * w, h);
            shapeRenderer.rect(x + th * w, y, th * 3 * w, th * h);
            shapeRenderer.rect(x + th * w + th * 3 * w, y, th * w, h);
        }
        shapeRenderer.end();
        batch.begin();


    }
    
    public void dispose() {

    }

    public void touchDown(int x, int y){
        switch(state){
            case -1:    // NO BUTTON SELECTED
                Rectangle mouse = new Rectangle(x, (int)GAME_WORLD_HEIGHT -y, 1, 1);
                for(Button button : buttons){
                    if(Intersector.overlaps(mouse, button.getRectangle())){
                        if(button.getId() == 4 && (!goalSet || !pivotSet)){     // DONT SAVE WHEN GOAL OR PIVOT NOT SET
                        }
                        else if(button.getId() == 4){                           // SAVE IF GOAL AND PIVOT SET
                            save();
                        }
                        else {                                                  // NOT SAVE -> STATE = BUTTONS ID
                            state = button.getId();
                        }
                        break;
                    }
                }
                for(Rectangle rect : level.getObjects()){                       // DELETE RECTANGLE IF PRESSED
                    if(Intersector.overlaps(mouse, rect)){
                        level.removeObstacle(rect);
                        break;
                    }
                }
                break;
            case 0: // SET PIVOT
                level.setPivot(x, (int)GAME_WORLD_HEIGHT - y);
                level.getProjectile().setxPos(x);
                level.getProjectile().setyPos((int) GAME_WORLD_HEIGHT - y + 150);
                pivotSet = true;
                state = -1;
                break;
            case 1: // SET GOAL
                level.setGoal(x, (int)GAME_WORLD_HEIGHT-y);
                state = -1;
                goalSet = true;
                break;
            case 2: // NEW OBSTACLE
                level.addRectangle(x,(int)GAME_WORLD_HEIGHT-y,200,100);
                state = -1;
                break;
            default:
                break;
        }
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

    public boolean getToSave(){
        return toSave;
    }

    public void save(){
        Json json = new Json();
        FileHandle file = Gdx.files.local("levels/level2.json");
        file.writeString(json.toJson(level), false);
        toSave = true;
    }
    
}
