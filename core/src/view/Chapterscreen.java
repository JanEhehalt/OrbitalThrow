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
public class Chapterscreen{

    int selectedChapter;
    BitmapFont font;
    int chapterAmount;
    float GAME_WORLD_WIDTH;
    float GAME_WORLD_HEIGHT;
    ShapeRenderer shapeRenderer;
    ArrayList<Button> buttons;


    public Chapterscreen(int chapterAmount, float width, float height, Matrix4 matrix){
        GAME_WORLD_WIDTH = width;
        GAME_WORLD_HEIGHT = height;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix);
        buttons = new ArrayList<>();

        buttons.add(new Button("Chapter 1", (int)(0.05 * GAME_WORLD_WIDTH - 0.5 * 3/80 * GAME_WORLD_WIDTH), (int)(0.7 * GAME_WORLD_HEIGHT), (int)((1f/3f) * 0.8 *GAME_WORLD_WIDTH), (int)(0.2 * GAME_WORLD_HEIGHT), 1));
        buttons.add(new Button("Chapter 2", (int)((0.1 * GAME_WORLD_WIDTH + buttons.get(0).getWidth()) - 0.5 * 3/80 * GAME_WORLD_WIDTH), (int)(0.7 * GAME_WORLD_HEIGHT), (int)((1f/3f) * 0.8 *GAME_WORLD_WIDTH), (int)(0.2 * GAME_WORLD_HEIGHT), 2));
        buttons.add(new Button("Chapter 3", (int)(0.15 * GAME_WORLD_WIDTH + buttons.get(0).getWidth()*2 - 0.5 * 3/80 * GAME_WORLD_WIDTH), (int)(0.7 * GAME_WORLD_HEIGHT), (int)((1f/3f) * 0.8 *GAME_WORLD_WIDTH), (int)(0.2 * GAME_WORLD_HEIGHT), 3));
        buttons.add(new Button("Chapter 4", (int)(0.05 * GAME_WORLD_WIDTH - 0.5 * 3/80 * GAME_WORLD_WIDTH), (int)(0.3 * GAME_WORLD_HEIGHT), (int)((1f/3f) * 0.8 *GAME_WORLD_WIDTH), (int)(0.2 * GAME_WORLD_HEIGHT), 4));
        buttons.add(new Button("Chapter 5", (int)(0.1 * GAME_WORLD_WIDTH + buttons.get(0).getWidth() - 0.5 * 3/80 * GAME_WORLD_WIDTH), (int)(0.3 * GAME_WORLD_HEIGHT), (int)((1f/3f) * 0.8 *GAME_WORLD_WIDTH), (int)(0.2 * GAME_WORLD_HEIGHT), 5));
        buttons.add(new Button("coming soon ...",(int)(0.15 * GAME_WORLD_WIDTH + buttons.get(0).getWidth()*2 - 0.5 * 3/80 * GAME_WORLD_WIDTH), (int)(0.3 * GAME_WORLD_HEIGHT), (int)((1f/3f) * 0.8 *GAME_WORLD_WIDTH), (int)(0.2 * GAME_WORLD_HEIGHT), -1));
        buttons.add(new Button("< back", 20,10,200,80,6));

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



    }

    public void dispose(){
    }


    public int touchDown(int x, int y){
        Rectangle mouse = new Rectangle(x, y, 1, 1);
        for(Button button : buttons){
            if(Intersector.overlaps(mouse, button.getRectangle())){
                return button.getId();
            }
        }
        return -1;
    }

    public void setSelectedChapter(int i){
        selectedChapter = i;
    }
    public int getSelectedChapter(){
        return selectedChapter;
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
