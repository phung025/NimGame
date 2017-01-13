package framework_AI_game;

/**
 *
 *
 */
public abstract class Game {

    public Game(String introduction,
            State initial_game_state,
            Player first_player,
            State p1_winning_state,
            Player second_player,
            State p2_winning_state) {

        // Set game info
        _game_introduction = introduction;

        // Set players
        _player1 = first_player;
        _player2 = second_player;
        _currentPlayer = _player1;

        // Set states
        _current_game_state = initial_game_state;
        _player1WinState = p1_winning_state;
        _player2WinState = p2_winning_state;
        
        // Initial game state for resetting game 
        this._initial_game_state = initial_game_state;
    }

    /**
     * Check if the game is end
     *
     * @return true if game is finish else false
     */
    public abstract boolean isGameEnd();

    ////////////// GAME INFO & OTHERS
    public String getIntroduction() {
        return _game_introduction;
    }

    public abstract void newRandomGame();
    public abstract void resetGame();

    ////////////// FIRST PLAYER
    
    public final void setFirstPlayer(Player p) {
        _player1 = p;
    }
    
    public final Player getFirstPlayer() {
        return _player1;
    }

    public void setFirstPlayerScore(Integer score) {
        _player1_score = score;
    }

    public Integer getFirstPlayerScore() {
        return _player1_score;
    }

    ////////////// SECOND PLAYER
    
    public final void setSecondPlayer(Player p) {
        _player2 = p;
    }
        
    public final Player getSecondPlayer() {
        return _player2;
    }

    public void setSecondPlayerScore(Integer score) {
        _player2_score = score;
    }

    public Integer getSecondPlayerScore() {
        return _player2_score;
    }

    ////////////// CURRENT PLAYER
    public void setCurrentPlayer(Player player) {
        _currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return _currentPlayer;
    }

    public Player getOtherPlayer() {
        return (_currentPlayer.equals(_player1)) ? _player2 : _player1;
    }

    ////////////// GAME STATES
    
    public void setInitialGameState(State init_state) {
        _initial_game_state = init_state;
    }

    public State getInitialGameState() {
        return _initial_game_state;
    }
    
    public void setCurrentGameState(State current_state) {
        _current_game_state = current_state;
    }

    public State getCurrentGameState() {
        return _current_game_state;
    }
    
    ////////////// WINNING GAME STATES

    public final State getPlayer1WinState() {
        return _player1WinState;
    }

    public final State getPlayer2WinState() {
        return _player2WinState;
    }

    public final State getComputerWinState() {

        return (_player1.getClass() == AutomatedPlayer.class) ? _player1WinState
                : (_player2.getClass() == AutomatedPlayer.class) ? _player2WinState : null;
    }

    /**
     * Instance fields
     */
    private Player _currentPlayer;

    private Player _player1;
    private Integer _player1_score = 0;

    private Player _player2;
    private Integer _player2_score = 0;

    // Game states
    private State _current_game_state;
    private State _initial_game_state;
    
    // Winning states
    private final State _player1WinState;
    private final State _player2WinState;

    // Game infos
    private final String _game_introduction;
}
