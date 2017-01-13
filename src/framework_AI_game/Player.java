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
public abstract class Player {
    
    /**
     * Default constructor. The constructor is set to be private so that
     * client can only use the parameterized constructor
     */
    private Player() {}
    
    public Player(String name, List<Move> available_moves, Game game, Turn playerType) {
        _player_name = name;
        _available_moves = available_moves;
        _game_playing = game;
        _player_type = playerType;
    }
    
    public final String getPlayerName() {
        return _player_name;
    }
    
    public final List<Move> getMoves() {
        return _available_moves;
    }
    
    public final void setAvailableMoves(List<Move> moves) {
        _available_moves = moves;
    }
    
    public final Game getCurrentGamePlaying() {
        return _game_playing;
    }
    
    public final void setCurrentGamePlaying(Game game) {
        _game_playing = game;
    }
    
    /**
     * Get the type of this player
     * @return player type
     */
    public final Turn getPlayerType() {
        return _player_type;
    }
    
    public abstract State doMove(State currentState, Move mover);
    
    /**
     * Instance fields
     */
    private String _player_name;
    private List<Move> _available_moves;
    private Game _game_playing;
    private Turn _player_type; // The turn name that this player is using
}
