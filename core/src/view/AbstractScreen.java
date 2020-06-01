/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 *
 * @author Jan
 */
public class AbstractScreen implements Screen{
    protected Game game;
    
    public AbstractScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
     }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
