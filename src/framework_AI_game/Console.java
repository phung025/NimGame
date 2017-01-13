package framework_AI_game;

import java.util.*;

/**
 * This class has methods for interacting with a user who is trying to solve a
 * problem through a console interface. Note that this class is concrete; it is
 * neither an interface nor abstract. It also does not depend on any particular
 * problem domain.
 */
public class Console {

    /**
     * Creates a new problem console user interface.
     *
     * @param problem the problem
     */
    public Console(Game problem) {

        this._game = problem;
        options = makeOptions();
        scanner = new Scanner(System.in);

    }

    /**
     * Displays the introduction and begins to process user moves.
     */
    public void start() {

        display(_game.getIntroduction() + "\n");
        display("Initial game:");
        processMoves();
    }

    /**
     * Getter for the legal moves as a string of numbered options.
     *
     * @return the string of numbered options
     */
    private String getOptions() {
        return options;
    }

    /**
     * Getter for the problem object associated with this console.
     *
     * @return this console's problem object
     */
    private Game getGame() {
        return _game;
    }

    /**
     * Makes a list of move options from the list of legal moves.
     *
     * @return the options string
     */
    private String makeOptions() {

        StringBuilder buffer = new StringBuilder();

        List<Move> available_moves = getGame().getCurrentPlayer().getMoves();

        int n = 1;
        buffer.append("Options:\n");
        for (Move move : available_moves) {
            buffer.append("  ").append(n++).append(". ").append(move.getMoveName()).append("\n");
        }
        buffer.append("\nChoose 1-").append(available_moves.size()).append(" (zero to quit): ");
        return buffer.toString();
    }

    /**
     * Gets and processes moves from the user. First displays the current state
     * and checks for success. If success, congratulations are given along with
     * the number of moves used and the program exits. Otherwise the available
     * options are displayed and prompted for. Bad input causes a message and
     * re-prompt. Input of zero causes a message of number of moves attempted
     * and the program exits. Valid input causes the move to be attempted. If
     * the result is
     * <b>null</b>, the move could not be performed so a message is given and
     * the user is re-prompted. Otherwise the result is installed as the new
     * problem current state and <b>getMove</b> is repeated.
     */
    private void processMoves() {

        display(_game.getCurrentGameState().toString());

        Player current_player = _game.getCurrentPlayer();
        Player other_player = _game.getOtherPlayer();

        {
            State current_state = _game.getCurrentGameState();
            if (current_state.equals(_game.getComputerWinState())) {
                if (current_player.getClass() == AutomatedPlayer.class) {
                    display(current_player.getPlayerName() + " won the game");
                } else {
                    display(other_player.getPlayerName() + " won the game");
                }

                // Exit game
                endGame();
            } else if (current_state.equals(_game.getPlayer1WinState())) {

                display(_game.getFirstPlayer().getPlayerName() + " won the game");
                endGame();
            } else if (current_state.equals(_game.getPlayer2WinState())) {

                display(_game.getSecondPlayer().getPlayerName() + " won the game");
                endGame();
            }
        }
        
        // Display empty line
        display("");
        display(current_player.getPlayerName() + " is playing this turn");
        
        // Do Human Move Turn
        if (current_player.getClass() == HumanPlayer.class) {

            display(options);
            Move move = getOption();
            if (move == null) {
                display("User quit.  Game ended.\n");
                endGame();
            }

            // Current player play his/her next move
            State newState = current_player.doMove(_game.getCurrentGameState(), move);
            if (newState == null) {
                display("That move is not possible.  Try again.\n");
            } else {

                // Do human move
                display(current_player.getPlayerName() + " do a move, current game state is:"); 
                _game.setCurrentGameState(newState);
            }

        } else { // Do Computer Move Turn
           
            display(current_player.getPlayerName() + " do a move, current game state is:"); 
            State nextState = current_player.doMove(_game.getCurrentGameState(), null);
                            
            // End game if computer resign
            if (nextState == null) {
                display(current_player.getPlayerName() + " resigned. You won the game!");
                endGame();
            }
            _game.setCurrentGameState(nextState);
        }

        // Continue playing
        processMoves();
    }

    /**
     * Prompts for a move option, re-prompting if necessary.
     *
     * @return the move associated with the (valid) option
     */
    private Move getOption() {

        List<Move> moves = _game.getCurrentPlayer().getMoves();

        if (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                int n = scanner.nextInt();
                if (1 <= n && n <= moves.size()) {
                    return moves.get(n - 1);
                }
                if (n == 0) {
                    return null;
                }
                display("Option out of range.\n");
                display(_game.getCurrentGameState().toString());
                display(options);
                return getOption();
            } else {
                String dum = scanner.next();
                display("Option not an integer.\n");
                display(_game.getCurrentGameState().toString());
                display(options);
                return getOption();
            }
        } else {
            return null;
        }
    }

    /**
     * Displays a string to the console.
     *
     * @param string the string to be displayed
     */
    private void display(String string) {
        System.out.print("\n" + string);
    }

    /**
     * End the game
     */
    private void endGame() {
        display("");
        System.exit(0);
    }

    /**
     * The puzzle problem object
     */
    private final Game _game;

    /**
     * A prompt string of move options
     */
    private final String options;

    /**
     * A scanner for reading input
     */
    private final Scanner scanner;

}
