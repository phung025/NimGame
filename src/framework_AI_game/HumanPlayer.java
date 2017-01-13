/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework_AI_game;

import java.util.List;

/**
 *
 * @author user
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name, List<Move> available_moves, Game game, Turn playerType) {
        super(name, available_moves, game, playerType);
    }

    @Override
    public State doMove(State currentState, Move mover) {

        State nextState = mover.doMove(currentState);
        
        // If current player's move is legal, set the game current's player to the other player
        if (nextState != null) {
            
            // Set the other player to play next turn
            getCurrentGamePlaying().setCurrentPlayer(getCurrentGamePlaying().getOtherPlayer());
        }
        
        return nextState;
    }
    
}
