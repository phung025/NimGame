/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nim;

import framework_AI_game.Game;
import framework_AI_game.Move;
import framework_AI_game.Player;
import framework_AI_game.State;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author user
 */
public class NimGame extends Game {

    public NimGame(String introduction,
            State initial_game_state,
            Player first_player,
            State p1_winning_state,
            Player second_player,
            State p2_winning_state) {

        super(introduction, initial_game_state, first_player, p1_winning_state, second_player, p2_winning_state);
    }

    /**
     * Check if the game is finish
     *
     * @return true if the game's current state is final state
     */
    @Override
    public boolean isGameEnd() {
        return ((NimState) getCurrentGameState()).getAllCoins() == 0;
    }

    @Override
    public void newRandomGame() {

        // Initial game piles
        List<Integer> initialPiles = new ArrayList<>();
        for (int i = 0; i < PILE_SIZE; ++i) {
            // Add a random amount of coins from 6 to 12 to the pile
            initialPiles.add(ThreadLocalRandom.current().nextInt(6, 12));
        }

        // Available move list
        List<Move> moves = new ArrayList<>();
        for (int pile_index = 0; pile_index < initialPiles.size(); ++pile_index) {
            for (int coin_capacity = 1; coin_capacity <= initialPiles.get(pile_index); ++coin_capacity) {
                moves.add(new NimMove("Take " + coin_capacity + " coins from pile " + (pile_index + 1),
                        pile_index + ":" + coin_capacity));
            }
        }

        // Create a new random game state with first player playing first
        NimState newGameState = new NimState(initialPiles, getFirstPlayer().getPlayerType(), getSecondPlayer().getPlayerType());

        // All game players
        getFirstPlayer().setAvailableMoves(moves);
        getSecondPlayer().setAvailableMoves(moves);
        setCurrentPlayer(getFirstPlayer());

        // Set new game state
        setCurrentGameState(newGameState);
        setInitialGameState(newGameState);

    }

    @Override
    public void resetGame() {
        setCurrentPlayer(getFirstPlayer());
        setCurrentGameState(getInitialGameState());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("");

        s.append("\n---------- World of Nim ----------");
        s.append("\nPlayer 1: ".concat(getFirstPlayer().getPlayerName()));
        s.append("\nPlayer 2: ".concat(getSecondPlayer().getPlayerName()));

        s.append("\n----------Current Score ----------");
        s.append("\n".concat(getFirstPlayer().getPlayerName()).concat(": ").concat(String.valueOf(getFirstPlayerScore())).concat(" point(s)"));
        s.append("\n".concat(getSecondPlayer().getPlayerName()).concat(": ").concat(String.valueOf(getSecondPlayerScore())).concat(" point(s)"));

        return s.toString();
    }

    // Size of the game piles.
    public static int PILE_SIZE = 3;
}
