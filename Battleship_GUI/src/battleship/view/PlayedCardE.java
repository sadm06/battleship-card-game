package battleship.view;

import battleship.model.Cards;

import java.io.Serializable;

/**
 * PlayedCardE that is sent between players after each move, when on enemyCard played. In includes the updated enemyCard
 * after {@link battleship.model.BattleshipGame#move(Cards, Cards)} method of model.
 * It is to send for sharing the enemyCard with enemy to inform about the update.
 * This card will be sent to other side as updatedCard, which occurs in Model. So that, the other player update own card
 * and shows the updated scene.
 */
public class PlayedCardE implements Serializable {
    private final Cards playedCE;
    private final int index;

    /**
     * Creates PlayedCardE instance.
     * @param playedCE enemy card, on which played.
     * @param index index of enemy card.
     */
    public PlayedCardE(Cards playedCE, int index) {
        this.playedCE = playedCE;
        this.index = index;
    }

    /**
     * Returns the index of enemy card, on which played.
     * @return index of enemy card.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the enemy card, on which played.
     * @return enemy card.
     */
    public Cards getPlayedC() {
        return playedCE;
    }

    /**
     * A textual representation about the PlayedCardE object.
     * That is to inform in terminal, what is sent.
     * @return information about what is sent.
     */
    @Override
    public String toString() {
        String s = String.format("PlayedCard of %s team", playedCE.getTeam());
        return s;
    }
}
