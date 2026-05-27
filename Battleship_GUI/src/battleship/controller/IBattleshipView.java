package battleship.controller;

import battleship.model.Cards;
import java.util.List;

/**
 * This interface contains all necessary classes of a view to be usable by the controller.
 * View class contain additional methods, which will not accessible by the controller.
 */

public interface IBattleshipView {
   /**
    * This method takes the load the images of GameCards and first five HandCards.
    * It takes the paths from lists of Game with the help of controller.
    * It has not direct access on model.
    * @param gameC the list of GameCards.
    * @param handC the list of HandCards.
    */
   void loadImages(List<Cards> gameC, List<Cards> handC);

   /**
    * Shows the title screen of Battleship.
    */
   void drawTitleScreen();

   /**
    * Shows the current game state of Battleship. This display should display the cards images of GameCards,
    * EnemyCards and HandCards. Also, player names and the infoBoxes for player.
    * If the cards updated, this will also update the images of cards.
    */
   void drawGame();

   /**
    * Shows the game over screen of Battleship.
    */
   void drawGG();

}
