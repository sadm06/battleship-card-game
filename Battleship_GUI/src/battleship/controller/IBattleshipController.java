package battleship.controller;
import battleship.model.Cards;
import java.util.ArrayList;

/**
 * The interface of a controller from the perspective of a view object.
 * All elements that are not part of this interface are invisible for the view.
 */

public interface IBattleshipController {
    /**
     * This method should be called whenever the controller should decide what the view should display.
     * The controller will call one of the drawX()-Methods from the view.
     */
    void nextFrame();
    /**
     * Method that should be called whenever the user clicks on the game window.
     * @param handCIndex  The index of selected handCard
     * @param gameCIndex  The index of clicked own gameCard
     */
    void userInputO(int handCIndex, int gameCIndex);
    /**
     * Method that should be called whenever the user clicks on the game window.
     * @param handCIndex  The index of selected handCard
     * @param enemyCIndex  The index of clicked enemyCard
     */
    void userInputE(int handCIndex, int enemyCIndex);

    /**
     * Returns the gameCardsList of game. It is used to take updated information from Model,
     * but these can not be changed or saved by view.
     * @return gameCardsList of game.
     */
    ArrayList<Cards> gameCards();

    /**
     * Returns the handCardsList of game. It is used to take updated information from Model,
     * but these can not be changed or saved by view.
     * @return handCardsList of game.
     */
    ArrayList<Cards> handCards();

    /**
     * Returns the enemyCardsList of game. It is used to take updated information from Model,
     * but these can not be changed or saved by view.
     * @return enemyCardsList of game.
     */
    ArrayList<Cards> enemyCards();

    /**
     * When the CardsList of players over network shared, that will be saved by controller in
     * model, and the move will be on enemyCards in this way applied.
     * @param enemyC List of enemyCards
     */
    void setEnemyCards(ArrayList<Cards> enemyC);

    /**
     * Set the game state.
     * @param state state of game.
     */
    void setState(GameState state);
    /**
     * Returns the gameState of game.
     * @return game state.
     */
    GameState getState();

    /**
     * If one side plays on card of other side, this will be occurred in model.
     * But the information of ownCard has to be updated. Over network received information with
     * the help of controller will be updated.
     * @param index index of card, that has to be updated.
     * @param card the card, that is updated by the model of other side.
     */

    void updateSelfCard(int index, Cards card);
}
