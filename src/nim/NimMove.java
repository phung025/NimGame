/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nim;

import framework_AI_game.Move;
import framework_AI_game.State;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class NimMove extends Move {

    /**
     * Constructor that takes in the move name. The name of the move should be
     * format as: [pile_position]:[number_of_coins]. [number_of_coins] is the number
     * of coins to be removed from a specific pile
     * @param moveName the name of the move.
     * @param moveCommand the command this move going to make
     */
    public NimMove(String moveName, String moveCommand) {
        super(moveName, moveCommand);
    }

    /**
     * Return the next move from the input nim state
     * @param state
     * @return new state after doing a move, null if the move command is invalid
     * i.e pile position is out of boundary or number of coins to be removed is
     * greater than the actual number of coins in the pile
     */
    @Override
    public State doMove(State state) {
        
        String[] moves = super.getMoveCommand().split(":");
        
        Integer pile_index = Integer.valueOf(moves[0]);
        Integer coins = Integer.valueOf(moves[1]);
        
        NimState currentState = (NimState) state;
        List<Integer> currentPiles = currentState.getCoinPiles();
        
        // Check for valid move
        if (pile_index > currentPiles.size() || coins > currentPiles.get(pile_index)) {
            return null;
        }
        
        List<Integer> newPiles = new ArrayList<>();
        
        // Copy all coins into new piles
        for (int i = 0; i < currentPiles.size(); ++i) {
            newPiles.add(currentPiles.get(i));
        }
        
        // Get the new piles by removing the number of coins to be removed
        // from the chosen pile
        newPiles.set(pile_index, newPiles.get(pile_index) - coins);
        
        NimState returnedState = new NimState(newPiles, currentState.getNextPlayerTurn(), currentState.getCurrentPlayerTurn());
        
        return returnedState;
    }

}
