/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nim;

import framework_AI_game.Turn;
import framework_AI_game.State;
import java.util.List;

/**
 *
 * @author user
 */
public class NimState extends State {

    public NimState() throws IllegalAccessException {
        throw new IllegalAccessException("Public constructor not allowed. Call \"NimState(List<Integer> piles, Turn current_turn, Turn next_turn)\" instead");
    }
    
    /**
     * Parameterized constructor that takes in a list containing n-piles with n
     * is the size of that list. Each value corresponds to the item in the list
     * is the number of coins in the pile
     *
     * @param piles list of size n represents number of piles and each pile has
     * p coins
     * @param current_turn The player playing this turn
     * @param next_turn The player playing after this turn
     */
    public NimState(List<Integer> piles, Turn current_turn, Turn next_turn) {
        this._coinPiles = piles;
        setCurrentPlayerTurn(current_turn);
        setNextPlayerTurn(next_turn);
    }

    /**
     * Get heuristic value of the current state
     *
     * @param finalState
     * @return integer heuristic value
     */
    @Override
    public int getHeuristic(State finalState) {

        int heuristic = 0;

        if (getNextPlayerTurn() == Turn.COMPUTER) {
            heuristic += 1;
            if (getNimSum() != 0) {
                heuristic += 1;
            }
        }

        return heuristic;
    }

    /**
     * Get all the piles of this state
     *
     * @return List of all the coin piles
     */
    public List<Integer> getCoinPiles() {
        return this._coinPiles;
    }

    /**
     * Check if the argument state is equal to the current state
     *
     * @param otherState
     * @return true if they're equal else false
     */
    @Override
    public boolean equals(Object otherState) {

        if (otherState == null) {
            return false;
        }

        /*
         // If there's only 1 coin left, return true that 2 states equal 
         if (((NimState)otherState).getAllCoins().equals(getAllCoins()) &&
         ((NimState)otherState).getCurrentPlayerTurn() == this.getCurrentPlayerTurn()) {
         return true;
         }
         */
        return (((NimState) otherState).getCoinPiles().equals(this.getCoinPiles()))
                && ((NimState) otherState).getCurrentPlayerTurn().equals(this.getCurrentPlayerTurn())
                && (((NimState) otherState).getNextPlayerTurn().equals(this.getNextPlayerTurn()));
    }

    public Integer getAllCoins() {
        // Get number of all coins in the piles
        Integer sum = 0;
        sum = this.getCoinPiles().stream().map((i) -> i).reduce(sum, Integer::sum);

        return sum;
    }

    @Override
    public String toString() {
        StringBuilder stateStringBuilder = new StringBuilder("");

        for (int i = 0; i < _coinPiles.size(); ++i) {
            Integer coins = _coinPiles.get(i);
            stateStringBuilder = stateStringBuilder.append(" - Pile ".concat(String.valueOf(i + 1)).concat(" has ").concat(String.valueOf(coins)).concat(" coin(s) - "));
        }

        return stateStringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return _coinPiles.hashCode();
    }

    private int getNimSum() {

        int sum = 0;

        for (Integer coins : _coinPiles) {
            sum = sum ^ coins;
        }

        return sum;
    }

    /**
     * Instances field
     */
    // List of all piles of coins in the current state
    private final List<Integer> _coinPiles;
}
