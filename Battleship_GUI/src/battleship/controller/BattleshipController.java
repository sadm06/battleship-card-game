package battleship.controller;

import battleship.model.BattleshipGame;
import battleship.model.Cards;

import java.util.ArrayList;

/**
 * A controller implementation for a GUI view. This controller creates and uses a {@link BattleshipGame}.
 * It implements a state machine to keep track of the state the view should draw next.
 */

public class BattleshipController implements IBattleshipController {
    private final BattleshipGame game;
    private final IBattleshipView view;
    private GameState state;

    /**
     * Creates a new controller object with the given view and team.
     * The images of GameCards loaded here.
     * @param view The view object that should be used by the controller. Cannot be changed later.
     * @param team The team of the game.
     */
    public BattleshipController (IBattleshipView view, String team) {
        this.view = view;
        this.game = new BattleshipGame(team);
        this.state = GameState.TITLE_SCREEN;
        this.view.loadImages(this.game.getGameCards(), this.game.getHandCards());
    }

    /**
     * Calls the draw methods of the view, depending on the current game state.
     * During the game, this method also updates the all changes of cards and game.
     */
    @Override
    public void nextFrame() {
        switch(state) {
            case TITLE_SCREEN -> view.drawTitleScreen();
            case TITLE_TO_GAME -> state = GameState.GAME;
            case GAME -> {
                view.drawGame();
                if(game.isGameOver()) {
                    state = GameState.GAME_OVER;
                }
            }
            case GAME_OVER -> view.drawGG();
        }
    }

    /**
     * When mouse released depending on the handCard and selectedCard called this method.
     * It calls the move method of game for the enemyCards by using enemyCardIndex.
     * @param handCIndex  The index of selected handCard
     * @param enemyCIndex  The index of clicked enemyCard
     */
    @Override
    public void userInputE(int handCIndex, int enemyCIndex) {
        this.game.move(this.game.getHandCards().get(handCIndex), this.game.getEnemyCards().get(enemyCIndex));
    }

    /**
     * When mouse released depending on the handCard and selectedCard called this method.
     * It calls the move method of game for the own Cards by using gameCardIndex.
     * @param handCIndex  The index of selected handCard
     * @param gameCIndex  The index of clicked own gameCard
     */
    @Override
    public void userInputO(int handCIndex, int gameCIndex) {
        this.game.move(this.game.getHandCards().get(handCIndex), this.game.getGameCards().get(gameCIndex));
    }

    /**
     * Returns the gameCardsList of game. It is used to take updated information from Model,
     * but these can not be changed or saved by view.
     * @return gameCardsList of game.
     */
    @Override
    public ArrayList<Cards> gameCards(){
        return this.game.getGameCards();
    }
    /**
     * Returns the handCardsList of game. It is used to take updated information from Model,
     * but these can not be changed or saved by view.
     * @return handCardsList of game.
     */
    @Override
    public ArrayList<Cards> handCards(){
        return this.game.getHandCards();
    }
    /**
     * Returns the enemyCardsList of game. It is used to take updated information from Model,
     * but these can not be changed or saved by view.
     * @return enemyCardsList of game.
     */
    @Override
    public ArrayList<Cards> enemyCards() { return this.game.getEnemyCards(); }

    /**
     * When the CardsList of players over network shared, that will be saved by controller in
     * model, and the move will be on enemyCards in this way applied.
     * @param enemyC List of enemyCards
     */
    @Override
    public void setEnemyCards(ArrayList<Cards> enemyC) {
        this.game.setEnemyCards(enemyC);
    }
    /**
     * If one side plays on card of other side, this will be occurred in model.
     * But the information of ownCard has to  be updated. Over network received information with
     * the help of controller will be updated.
     * @param index index of card, that has to be updated.
     * @param card the card, that is updated by the model of other side.
     */
    @Override
    public void updateSelfCard(int index, Cards card) {
        game.getGameCards().set(index, card);
    }
    /**
     * Set the game state.
     * @param state state of game.
     */
    @Override
    public void setState(GameState state) {
        this.state = state;
    }
    /**
     * Returns the gameState of game.
     * @return game state.
     */
    @Override
    public GameState getState() {
        return state;
    }

}
