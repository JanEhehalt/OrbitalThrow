/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Timer;

import view.AbstractScreen;
import view.Titlescreen;

/**
 *
 * @author Jan
 */
public class Controller extends Game{

    Timer stepTimer;

    @Override
    public void create() {
        setScreen(new Titlescreen(this));

        stepTimer = new Timer();
        stepTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

            }
        }, 0, 0.1f);
    }
    
    @Override
    public void render(){
        
    }
    
    public Screen getScreen(){
        return screen;
    }

   

    
}
