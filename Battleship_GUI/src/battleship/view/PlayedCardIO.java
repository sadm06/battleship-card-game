package battleship.view;

import battleship.model.Cards;

import java.io.Serializable;

/**
 * PlayedCardIO that is sent between players after each move, when on ownCaard played. In includes the updated information
 * of own card. With the method of model over controller: {@link battleship.model.BattleshipGame#move(Cards, Cards)}
 * It is to send for sharing the ownCard updated information with enemy to inform about the update.
 * This card will be sent to other side as updatedCard, which occurs in Model. So that, the other player update the enemyCard
 * information.
 */
public class PlayedCardIO implements Serializable {
    private final int defence;
    private final int index;

    /**
     * Creates PlayedCardE instance.
     * @param defence updated defence information of ownCard.
     * @param index index of own card.
     */
    public PlayedCardIO(int defence, int index) {
        this.defence = defence;
        this.index = index;
    }

    /**
     * Returns the index of own card, on which played.
     * @return index of own card.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the updated defence information of own card, on which played.
     * @return defence information of own card.
     */
    public int getDefence() {
        return defence;
    }

    /**
     * A textual representation about the PlayedCardIO object.
     * That is to inform in terminal, what is sent.
     * @return information about what is sent.
     */
    @Override
    public String toString() {
        String s = String.format("DefenceInformation: %d", defence);
        return s;
    }
}
