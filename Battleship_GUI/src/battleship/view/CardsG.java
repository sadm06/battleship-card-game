package battleship.view;

import battleship.model.Cards;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CardsG that is sent between players at the beginning of the game. In includes the gameCard list.
 * It is to send for sharing the gameCards with other player.
 * This list will be from other side as enemyCards saved in Model. So that game will run and the
 * Updates can be showed by view.
 */
public class CardsG implements Serializable {

    ArrayList<Cards> gameCards;
    String team;

    /**
     * Creates CardsG instance.
     * @param gameCards gameCards list of player.
     * @param team team of player.
     */
    public CardsG(ArrayList<Cards> gameCards, String team){
        this.team = team;
        this.gameCards = gameCards;
    }

    /**
     * A textual representation about the CardsG object.
     * That is to inform in terminal, what is sent.
     * @return information about what is sent.
     */
    @Override
    public String toString() {
        String s = String.format("EnemyCards of %s team", team);
        return s;
    }
}
