package framework_AI_game;

import graph.SimpleVertex;


/**
 * This interface represents the state of affairs of a problem-solving
 * domain.  Implementing classes will store the representation details
 * of concrete problem states.
 */
public abstract class State extends SimpleVertex {
    
    /**                                                                                                 
     * Tests for equality between this state and the argument state. 
     * Implementing classes will need to cast the argument to a specific class type.                                                    
     * @param otherState the state to test against this state                                                
     * @return true if this state and the other are equal, false otherwise                                                             
    */
    @Override
    public abstract boolean equals(Object otherState);

    /**
     * 
     * @return 
     */
    @Override
    public abstract int hashCode();
    
    /**                                                                                                 
     *Creates a primitive, non-GUI representation of this State object.                        
     *@return the string representation of this state                                       
    */
    @Override
    public abstract String toString();

    /**
     * Get heuristic values of the state
     * @param finalState the state that the current state will compare with to compute the heuristic
     * @return heuristic value of the state
     */
    public int getHeuristic(State finalState) {
        return 0;
    }

    /**
     * Set the player who is playing this turn
     * @param currentTurn
     */
    public void setCurrentPlayerTurn(Turn currentTurn) {
        _currentTurn = currentTurn;
    }
    
    /**
     * Get the player who is playing this turn
     * @return player that plays this turn
     */
    public Turn getCurrentPlayerTurn() {
        return _currentTurn;
    }
    
    /**
     * Set the player who is playing next turn
     * @param nextTurn
     */
    public void setNextPlayerTurn(Turn nextTurn) {
        _nextTurn = nextTurn;
    }
    
    /**
     * Get the player who is playing next turn
     * @return player that plays next turn
     */
    public Turn getNextPlayerTurn() {
        return _nextTurn;
    }
    
    /**
     * Instance fields
     */
    private Turn _currentTurn;
    private Turn _nextTurn;
}
