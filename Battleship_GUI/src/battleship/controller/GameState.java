package battleship.controller;

/**
 * All possible game states. This class is used to implement a state machine in the controller.
 */

public enum GameState {
    /**
     * The starting state of the game. Transitions to the <code>TITLE_TO_GAME</code> state.
     */
    TITLE_SCREEN,
    /**
     * The screen between TITLE_SCREEN and GAME. Transitions to the <code>GAME</code> state.
     * It is used to hide buttons in this between screen.
     */
    TITLE_TO_GAME,
    /**
     * The state in which the user can play Battleship. Transitions to the <code>GAME_OVER</code> state.
     */
    GAME,
    /**
     * The end state of the game, when the all ship of one side are sank.
     */
    GAME_OVER
}
