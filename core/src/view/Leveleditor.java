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
    
    ShapeRenderer shapeRenderer;
    
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    
    ArrayList<Button> buttons;
    
    float mouseX;
    float mouseY;
    
    int listNumber;
    int selectedRect;
    
    int state; //-1: nothig selected, 0: place pivot, 2: pivot direction, 3: goal, 4: obstacles
    
    BitmapFont font;
    
    Level level;
    
    public Leveleditor(float width, float height, Matrix4 matrix){
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;
        level = new Level(new Goal(0, 0, 200, 150, 0.2f), new Projectile(0, 0, 0), 0, 0);
        state = -1;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix);
        
        buttons = new ArrayList();
        buttons.add(new Button("Pivot", (int)(GAME_WORLD_WIDTH * 0.02),(int) (GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 0, listNumber));
        listNumber++;
        buttons.add(new Button("Goal", (int)(GAME_WORLD_WIDTH * 0.02), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 1, listNumber));
        listNumber++;
        buttons.add(new Button("add Rect", (int)(GAME_WORLD_WIDTH * 0.02), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 2, listNumber));
        listNumber++;
        buttons.add(new Button("SAVE", (int)(GAME_WORLD_WIDTH * 0.8), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 4, listNumber));
        listNumber++;
        
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
        
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(level.getPivotX(), level.getPivotY(), 5);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(level.getPivotX(), level.getPivotY() + 150, level.getProjectile().getRadius());
        if(state == -1){
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
        }
        
        for(Rectangle rect : level.getObjects()){
            shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        
        shapeRenderer.setColor(Color.BLACK);
        float x = level.getGoal().getxPos();
        float y = level.getGoal().getyPos();
        float w = level.getGoal().getSizeX();
        float h = level.getGoal().getSizeY();
        float th = level.getGoal().getThickness();
        shapeRenderer.rect(x, y,th * w, h);
        shapeRenderer.rect(x + th * w,y, th*3 * w,th * h);
        shapeRenderer.rect(x + th *w + th*3 * w, y, th * w,h);
        
        shapeRenderer.end();
        batch.begin();


    }
    
    public void dispose() {
    }

    
    public void setMousePos(float x, float y){
        mouseX = x;
        mouseY = y;
    }
    
    public void setGoal(){}
    
    
    public void touchDown(int x, int y){
        switch(state){
            case -1:
                Rectangle mouse = new Rectangle(x, (int)GAME_WORLD_HEIGHT -y, 1, 1);
                for(Button button : buttons){
                    if(Intersector.overlaps(mouse, button.getRectangle())){
                        state = button.getId();
                        if(button.getId() == 3){
                            selectedRect = button.getListId();
                        }
                        break;
                    }
                }
                break;
            case 0:
                level.setPivot(x, (int)GAME_WORLD_HEIGHT - y);
                state = -1;
                break;
            case 1:
                level.setGoal(x, (int)GAME_WORLD_HEIGHT-y);
                state = -1;
                break;
            case 2:
                buttons.remove(buttons.size()-1);
                buttons.add(new Button("Rectangle", (int)(GAME_WORLD_WIDTH * 0.02), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 3, listNumber));
                listNumber++;
                buttons.add(new Button("add Rect...", (int)(GAME_WORLD_WIDTH * 0.02), (int)(GAME_WORLD_HEIGHT - (buttons.size()+1) * 0.07 * GAME_WORLD_HEIGHT), 200, 50, 2, listNumber));
                listNumber++;
                state = -1;
                break;
            case 3:
                if(buttons.get(selectedRect - 1) != null){
                    level.addRectangle(x,(int)GAME_WORLD_HEIGHT - y, 100, 100);
                }
                state = -1;
                break;
            case 4:
                Json json = new Json();
                FileHandle file = Gdx.files.local("levels/level2.json");
                file.writeString(json.toJson(level), false);
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
    
}
