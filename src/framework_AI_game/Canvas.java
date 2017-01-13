/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework_AI_game;

import javax.swing.JComponent;

/**
 * This class visualize a current state of the problem using graphics
 *
 * @author Nam Phung
 */
public abstract class Canvas extends JComponent {

    /**
     * Parameterized constructor, take in a state to be drawn by the canvas and the game of that state
     *
     * @param game
     * @param frameGUI
     */
    public Canvas(Game game, GUI frameGUI) {
        
        //Set current state, initially current and previous state are the same
        this.game = game;
        this.frameGUI = frameGUI;
    }
    
    public Game getGameCanvasDisplaying() {
        return game;
    }
    
    public void setFrameGUI(GUI frameGUI) {
        this.frameGUI = frameGUI;
    }
    
    public GUI getFrameGUI() {
        return frameGUI;
    }
    
    /**
     * Reload the canvas object
     */
    public abstract void reload();

    /**
     * Render animation for the state of the problem
     */
    public void render() {
        repaint();
    }

    /**
     * Instance fields
     */
    
    /**
     * The game that this canvas is displaying
     */
    private Game game = null;
    
    /**
     * The GUI that contains this canvas
     */
    private GUI frameGUI = null;
    
}
