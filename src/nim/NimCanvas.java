/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nim;

import framework_AI_game.AutomatedPlayer;
import framework_AI_game.Canvas;
import framework_AI_game.GUI;
import framework_AI_game.Game;
import framework_AI_game.State;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Stack;

/**
 *
 * @author user
 */
public class NimCanvas extends Canvas {

    public NimCanvas(Game game, GUI frameGUI) {
        super(game, frameGUI);
        this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Reload the canvas
        this.reload();
    }

    @Override
    public void reload() {

        // Create the piles stack
        pilesStack = new Stack();
        for (int i = 0; i < PILES_SIZE; ++i) {
            pilesStack.add(new Stack<>());
        }

        //Get current state
        NimState currentState = (NimState) getGameCanvasDisplaying().getCurrentGameState();

        // Add the coin shapes to shapes stacks
        for (int i = 0; i < PILES_SIZE; ++i) {
            for (int j = 0; j < currentState.getCoinPiles().get(i); ++j) {

                NimCoinShape coin = new NimCoinShape(
                        new Rectangle2D.Double(-40 + (60 * (i + 1)), baseLine - ((j + 1) * COIN_HEIGHT), COIN_WIDTH, COIN_HEIGHT),
                        BLACK_COLOR,
                        new Rectangle2D.Double(-40 + (60 * (i + 1)), baseLine - ((j + 1) * COIN_HEIGHT), COIN_WIDTH, COIN_HEIGHT),
                        COIN_COLOR);
                pilesStack.get(i).push(coin);
            }
        }

        removeMouseListener(canvasAdapter);
        removeMouseMotionListener(canvasAdapter);
        canvasAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                for (int i = 0; i < PILES_SIZE; ++i) {
                    for (int j = 0; j < pilesStack.get(i).size(); ++j) {

                        NimCoinShape coin = pilesStack.get(i).get(j);
                        Point cursorPoint = e.getPoint();

                        if (coin.getBorder().contains(cursorPoint)) {

                            // Get the GUI Frame containing the canvas
                            GUI frameGUI = getFrameGUI();

                            // Current state of the game
                            Game game = getGameCanvasDisplaying();
                            State currentGameState = game.getCurrentGameState();

                            // Get number of coins & the pile index which they will be taken from
                            int pile_taking_from = i;
                            int total_coins_be_taken = pilesStack.get(i).size() - j;

                            // Get the move from the mouse clicked command
                            NimMove move = new NimMove("Take " + total_coins_be_taken + " coins from pile " + (pile_taking_from + 1),
                                    pile_taking_from + ":" + total_coins_be_taken);

                            // Do human's move
                            State nextState = game.getCurrentPlayer().doMove(currentGameState, move);

                            if (nextState != null) {

                                // Set next game state
                                game.setCurrentGameState(nextState);

                                // Update frame GUI
                                frameGUI.updateStatusTextArea(game.getOtherPlayer().getPlayerName() + " takes " + total_coins_be_taken + " coin(s) from pile " + (1 + pile_taking_from));
                                frameGUI.updateStatusTextArea(game.getCurrentGameState().toString());

                                // Reload canvas
                                reload();

                                // Check if game ends
                                State current_state = game.getCurrentGameState();
                                if (current_state.equals(game.getPlayer1WinState())) {

                                    // Update scores
                                    game.setFirstPlayerScore(game.getFirstPlayerScore() + 1);

                                    String newStatus = game.getFirstPlayer().getPlayerName() + " won the game";

                                    frameGUI.display(newStatus);
                                    frameGUI.updateStatusTextArea(newStatus);
                                    frameGUI.updateScoreTextArea();

                                    // Reset game
                                    frameGUI.newRandomGame();
                                } else if (current_state.equals(game.getPlayer2WinState())) {

                                    // Update scores
                                    game.setSecondPlayerScore(game.getSecondPlayerScore() + 1);

                                    String newStatus = game.getSecondPlayer().getPlayerName() + " won the game";

                                    frameGUI.display(newStatus);
                                    frameGUI.updateStatusTextArea(newStatus);
                                    frameGUI.updateScoreTextArea();

                                    // Reset game
                                    frameGUI.newRandomGame();
                                }

                                // Since the next turn is set, the current player is now changed,
                                // Check if the current player of this turn is a computer or not,
                                // Do a computer move if it's is an AutomatedPlayer
                                if (game.getCurrentPlayer().getClass() == AutomatedPlayer.class) {

                                    // Do computer's move
                                    State nextState1 = game.getCurrentPlayer().doMove(game.getCurrentGameState(), null);

                                    // End game if computer resign
                                    if (nextState1 == null) {

                                        String newStatus = game.getOtherPlayer().getPlayerName() + " resigned. " + game.getCurrentPlayer().getPlayerName() + " won the game.";

                                        // Update score
                                        if (game.getCurrentPlayer().equals(game.getFirstPlayer())) {
                                            game.setFirstPlayerScore(game.getFirstPlayerScore() + 1);
                                        } else {
                                            game.setSecondPlayerScore(game.getSecondPlayerScore() + 1);
                                        }

                                        frameGUI.display(newStatus);
                                        frameGUI.updateStatusTextArea(newStatus);
                                        frameGUI.updateScoreTextArea();

                                        // Reset game
                                        frameGUI.newRandomGame();

                                    } else { // Computer found a possible move

                                        int coins_computer_took = ((NimState) game.getCurrentGameState()).getAllCoins() - ((NimState) nextState1).getAllCoins();
                                        int pile_computer_took = -1;

                                        // Find the pile that the computer took the coins from
                                        for (int p_index = 0; i < ((NimState) game.getCurrentGameState()).getCoinPiles().size(); ++p_index) {
                                            int diff = ((NimState) game.getCurrentGameState()).getCoinPiles().get(p_index) - ((NimState) nextState1).getCoinPiles().get(p_index);
                                            if (diff != 0) {
                                                pile_computer_took = p_index;
                                                break;
                                            }
                                        }

                                        // Set state after computer's move
                                        game.setCurrentGameState(nextState1);

                                        // Update frame GUI
                                        frameGUI.updateStatusTextArea(game.getOtherPlayer().getPlayerName() + " takes " + coins_computer_took + " coin(s) from pile " + (1 + pile_computer_took));
                                        frameGUI.updateStatusTextArea(game.getCurrentGameState().toString());

                                        // Reload canvas
                                        reload();

                                        // Check if the computer win the game
                                        // This conditional statement is implemented to handle cases when
                                        // computer is first, or second player.
                                        if (game.getCurrentGameState().equals(game.getComputerWinState())) {
                                            if (game.getCurrentPlayer().getClass() == AutomatedPlayer.class) {

                                                // Update score
                                                if (game.getCurrentPlayer().equals(game.getFirstPlayer())) {
                                                    game.setFirstPlayerScore(game.getFirstPlayerScore() + 1);
                                                } else {
                                                    game.setSecondPlayerScore(game.getSecondPlayerScore() + 1);
                                                }

                                                String newStatus = game.getCurrentPlayer().getPlayerName() + " won the game";
                                                frameGUI.display(newStatus);
                                                frameGUI.updateStatusTextArea(newStatus);
                                                frameGUI.updateScoreTextArea();
                                            } else {

                                                // Update score
                                                if (game.getOtherPlayer().equals(game.getFirstPlayer())) {
                                                    game.setFirstPlayerScore(game.getFirstPlayerScore() + 1);
                                                } else {
                                                    game.setSecondPlayerScore(game.getSecondPlayerScore() + 1);
                                                }

                                                String newStatus = game.getOtherPlayer().getPlayerName() + " won the game";
                                                frameGUI.display(newStatus);
                                                frameGUI.updateStatusTextArea(newStatus);
                                                frameGUI.updateScoreTextArea();
                                            }

                                            // Reset game
                                            frameGUI.newRandomGame();
                                        }
                                    }
                                }
                            } else {

                                // Display error, invalid move
                                frameGUI.display("Invalid move.");
                            }

                        }
                    }
                }

                // Reload canvas
                reload();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                // If the cursor is pointing outside the piles, unselect all coins
                if (!isCursorInsidePiles(e.getPoint())) {
                    for (int i = 0; i < PILES_SIZE; ++i) {
                        for (int j = 0; j < pilesStack.get(i).size(); ++j) {
                            NimCoinShape coin = pilesStack.get(i).get(j);
                            coin.setColor(COIN_COLOR);
                            repaint();
                        }
                    }
                }

                // Find which coin the cursor is selecting
                for (int i = 0; i < PILES_SIZE; ++i) {
                    for (int j = 0; j < pilesStack.get(i).size(); ++j) {

                        NimCoinShape coin = pilesStack.get(i).get(j);
                        Point cursorPoint = e.getPoint();

                        if (coin.getBorder().contains(cursorPoint)) {

                            // Paint the selected coins above
                            for (int k = j; k < pilesStack.get(i).size(); ++k) {
                                pilesStack.get(i).get(k).setColor(COIN_COLOR_SELECTED);
                            }

                            // Paint the unselected coins below
                            for (int k = j - 1; k > 0; --k) {
                                pilesStack.get(i).get(k).setColor(COIN_COLOR);
                            }
                            repaint();
                        }
                    }
                }
            }
        };
        addMouseListener(canvasAdapter);
        addMouseMotionListener(canvasAdapter);

        // Repaint
        repaint();
    }

    private boolean isCursorInsidePiles(Point cursorPoint) {
        boolean isInside = true;

        for (int i = 0; i < PILES_SIZE; ++i) {
            for (int j = 0; j < pilesStack.get(i).size(); ++j) {
                NimCoinShape coin = pilesStack.get(i).get(j);
                isInside = coin.getBorder().contains(cursorPoint) && isInside;
            }
        }

        return isInside;
    }

    /**
     * Paint the current state of the block problem
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        // Graphics 
        Graphics2D g2 = (Graphics2D) g;

        // Clear canvas before redrawing
        g2.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        //Fill the background and draw a border around it
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        //Draw the border around
        g2.setColor(BLACK_COLOR);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        // Draw the base line
        g2.drawLine(20, baseLine, CANVAS_WIDTH - 20, baseLine);

        // Draw the stack name
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        for (int i = 0; i < PILES_SIZE; ++i) {
            g2.drawString(String.valueOf((i + 1)), 35 + (i * 60), baseLine + 15);
        }

        // Draw the piles
        for (int i = 0; i < PILES_SIZE; ++i) {
            for (int j = 0; j < pilesStack.get(i).size(); ++j) {

                // Draw coin
                g2.setColor(pilesStack.get(i).get(j).getColor());
                g2.fill(pilesStack.get(i).get(j).getShape());

                // Draw coin border
                g2.setColor(pilesStack.get(i).get(j).getBorderColor());
                g2.draw(pilesStack.get(i).get(j).getBorder());
            }
        }
    }

    // Coin dimensions
    final private int COIN_WIDTH = 40;
    final private int COIN_HEIGHT = 10;

    // Canvas dimensions
    final private int CANVAS_WIDTH = (NimGame.PILE_SIZE * 40) + ((NimGame.PILE_SIZE + 1) * 20);
    final private int CANVAS_HEIGHT = 350;

    // Base line of the stacks
    final private int baseLine = CANVAS_HEIGHT - 20;

    // Colors
    final private Color BACKGROUND_COLOR = Color.cyan;
    final private Color COIN_COLOR = Color.yellow;
    final private Color COIN_COLOR_SELECTED = Color.ORANGE;
    final private Color BLACK_COLOR = Color.black;

    // Mouse adapter
    private MouseAdapter canvasAdapter;

    // Stacks containing coins shapes
    private Stack<Stack<NimCoinShape>> pilesStack = new Stack();

    // Number of piles
    private final int PILES_SIZE = NimGame.PILE_SIZE;
}
