import java.util.*;

/**
 * The game implementation of Battleship. This class is the model of the game.
 * It can be either used in combination with a view+controller or directly through the JShell.
 * To give a textual representation another method,'moveJ()', is defined. It runs the original move() method
 * but in addition to that give about the game situation of players after each move.
 * ArrayList of Enemy Cards ist for GUI implemented. In JShell it won't be used.
 * To play game in JShel it has to be created two games with different team names.
 * HandCards and GameCards has to be taken over the getters methods from Arraylists.
 * These must be given over as parameter in moveJ() or move() methods.
 */

public class BattleshipGame {

    /**
     * Lists and Stacks for the Game Cards.
     */
    // Variables
    private ArrayList<Cards> enemyCards = new ArrayList<>();
    private final ArrayList<Cards> gameCards = new ArrayList<>();
    private final ArrayList<Cards> handCards = new ArrayList<>();
    private final Stack<Cards> takeCards = new Stack<>();
    private final Stack<Cards> playedCards = new Stack<>();
    private final String team;

    /**
     * Creates a new Game instance with given team.
     * {@link #createShips(String team)}
     * {@link #createMissed(String team)}
     * {@link #createMissiles(String team)}
     * {@link #createMayDay(String team)}
     * @param team team of game.
     */
    public BattleshipGame(String team) {
        this.team = team;
        createShips(team);
        createMissed(team);
        shuffleCards(gameCards);
        createMissiles(team);
        createMayDay(team);
        shuffleCards(takeCards);
        setHandCards();
    }

    //Main-Methods

    /**
     * Depending on the played card and the card, on which played,
     * it is called the moveE() method or moveO() method.
     * This method is the main game move method, and it is used in GUI.
     * It also checks whether the gameOver or not.
     * It gives also textual information for JShell.
     * {@link #moveE(Cards handC, Cards enemy)}
     * {@link #moveO(Cards handC, Cards ownCard)}
     * @param handC the played card.
     * @param playedOn ownCard or enemyCard, on which the hand card is played.
     */
    public void move(Cards handC, Cards playedOn) {
        if (isEnemy(playedOn) && handC.getType().equals("missile")) {
            moveE(handC, playedOn);
        } else if (!isEnemy(playedOn)) {
            moveO(handC, playedOn);
        } else {
            System.out.println("false move, try to play on another card or select new card");
        }
        if(isGameOver()) System.out.println("Game Over!" + team + " You are the WINNER!");
        takeNewCard();
    }

    /**
     * This method is the main game move method for JShell.
     * It calls directly the main move() method.
     * It gives also textual information of Game instance for JShell.
     * {@link #move(Cards handC, Cards playedOn)}
     * @param handC the played card.
     * @param playedOn ownCard or enemyCard, on which the hand card is played.
     * @param bsEnemy the Game instance of enemy.
     */
    public void moveJ(Cards handC, Cards playedOn, BattleshipGame bsEnemy) {
        this.move(handC, playedOn);
        System.out.println(this.toString());
        System.out.println(bsEnemy.toString());
    }

    /**
     * Returns if the game over is. Checks the number of sank ships of enemy.
     * If it is 5, it gives the information.
     * @return the number of sank ships of enemy.
     */
    public boolean isGameOver(){
        boolean go;
        int sankN = 0;
        for(Cards c: enemyCards){
            if(c.getType().equals("ship") && c.isSank()) sankN++;
        }
        go = sankN == 5;
        return go;
    }

    /**
     * A textual representation about the current game state. Both the situation of EnemyCards and OwnCards.
     * For example:
     * GameCards of blue
     * [][][][]
     * [][][][]
     * [][][][]
     * HandCards of blue
     * [R2][R1][W1][R2](S)
     * GameCards of red
     * [][][][]
     * [][][][]
     * [][][][]
     * HandCards of red
     * [W1][R1][W1][W1][R2]
     * {@link #printGameCards()}
     * {@link #printHandCards()}
     * @return information of the game state.
     */
    public String toString() {
        String s = "\n";
        s += printGameCards();
        s += printHandCards();
        return s;
    }


    //Setter-Getter

    /**
     * Returns the LIST of GameCards. It includes 'ships' and 'missed out cards'.
     * @return the list of gameCards.
     */
    public ArrayList<Cards> getGameCards() {
        return gameCards;
    }

    /**
     * Returns the LIST of HandCards. It includes 'missile' or 'mayday cards'.
     * They are in Hand and for playing on enemy card or own card.
     * @return the list of handCards.
     */
    public ArrayList<Cards> getHandCards() {
        return handCards;
    }

    /**
     * Returns the LIST of EnemyCards. It includes 'ships' or 'missed out cards'.
     * It is used in GUI version. The by enemy over network communication
     * received cardList on this list saved.
     * @return the list of enemyCards.
     */
    public ArrayList<Cards> getEnemyCards() {
        return enemyCards;
    }

    /**
     * Set the enemyCards list. It is used in GUI.
     * The by enemy over network communication
     * received cardList by Controller here saved.
     * @param enemyCards the list of EnemyCards.
     */
    public void setEnemyCards(ArrayList<Cards> enemyCards) {
        this.enemyCards = enemyCards;
    }

    /**
     * Returns the team of the Game Instance.
     * @return the team of the game instance.
     */
    public String getTeam() {
        return team;
    }

    //Help-Methods

    /**
     * It is the move method to play on enemy card.
     * It has been called by move() and moveJ() methods.
     * Depending on the enemy card, if it is opened or not,
     * it has been called discover() or attack() methods.
     * It gives also textual information for JShell.
     * {@link #discover(Cards handC, Cards enemy)}
     * {@link #attack(Cards handC, Cards enemy)}
     * @param handC the played card. Here it has to be type 'missile'.
     * @param enemy the enemy card, on which the hand card is played.
     */
    private void moveE(Cards handC, Cards enemy) {
        if (!isDiscovered(enemy)) {
            discover(handC, enemy);
        } else if (isDiscovered(enemy)) {
            attack(handC, enemy);
        }
    }

    /**
     * It is the move method to play on own card.
     * It has been called by move() and moveJ() methods.
     * If the on played card ship is, depending on the
     * hand card, whether it is 'repair' or 'shield'
     * the ship is repaired with +1 defence or secured with +2 defence.
     * It gives also textual information for JShell.
     * @param handC the played card. Here it has to be type 'mayday'.
     * @param ownCard the enemy card, on which the hand card is played.
     */
    private void moveO(Cards handC, Cards ownCard) {
        if (isDiscovered(ownCard) && ownCard.getType().equals("ship")) {
            if (handC.getMDType() == 's') {
                ownCard.setDefence(ownCard.getDefence() + 2);
                System.out.println("Reflective shields are active!!! Type: " + ownCard.getShipType() + "\n Defence: " + ownCard.getDefence());
                toPlayedCards(handC);
            } else if (ownCard.isStruck() && handC.getMDType() == 'r') {
                repair(ownCard);
                System.out.println("Ship is repaired! Type: " + ownCard.getShipType() + "\n Defence: " + ownCard.getDefence());
                toPlayedCards(handC);
            } else {
                System.out.println("false move, try to play on another card or select new card");
            }
        } else {
            System.out.println("false move, try to play on another card or select new card");
        }
    }

    /**
     * Depending on the enemyCard situation, if it is not opened,
     * it is called by moveE() method.
     * The enemyCard will be opened with help of this method.
     * If the color of the missile red is it called also attack() method.
     * It gives also textual information for JShell.
     * {@link #attack(Cards handC, Cards enemy)}
     * @param handC the played card. Here it has to be type 'missile'.
     * @param enemy the enemy card, on which the hand card is played.
     */
    private void discover(Cards handC, Cards enemy) {
        if(handC.getType().equals("missile")){
            enemy.setOpened(true);
            if (handC.getColor().equals("white")) {
                if (enemy.getType().equals("ship")) {
                    System.out.println("Enemy-Ship is discovered!!! Type: " + enemy.getShipType() + "\n Defence: " + enemy.getDefence());
                } else if (enemy.getType().equals("missed")) {
                    System.out.println("Missed out!!!");
                }
                toPlayedCards(handC);
            } else if (handC.getColor().equals("red")) {
                if(enemy.getType().equals("missed")) System.out.println("Missed out!!!");
                if (enemy.getType().equals("ship")) {
                    System.out.println("Enemy-Ship is discovered!!! Type: " + enemy.getShipType() + "\n Defence: " + enemy.getDefence());
                    attack(handC, enemy);
                }
            }
        } else {
            System.out.println("false move, try to play on another card or select new card");
        }
    }

    /**
     * Depending on the enemyCard situation, if it is opened,
     * it is called by moveE() method.
     * Depending on the color of missile and type of ship, if it is white and
     * ship is submarine, or red and other types of ships, with the help of this method
     * ships lost defence. It also controls, whether the defence of ship is <= 0.
     * If so, set the ship as sank.
     * It also calls the falseAttack() method, when the ship type is submarine
     * and the color of missile red.
     * Because submarines can be only white missiles attacked.
     * If the color of the missile red is it called also attack() method.
     * It gives also textual information for JShell.
     * {@link #falseAttack(Cards handC)}
     * @param handC the played card. Here it has to be type 'missile'.
     * @param enemy the enemy card, on which the hand card is played.
     */
    private void attack(Cards handC, Cards enemy) {
        if(handC.getType().equals("missile")) {
            if ((handC.getColor().equals("white") && submarine(enemy)) || (handC.getColor().equals("red") && enemy.getType().equals("ship") && !submarine(enemy))) {
                if (battleshipAdvantage()) {
                    enemy.setDefence(enemy.getDefence() - (handC.getPower() + 1));
                } else {
                    enemy.setDefence(enemy.getDefence() - handC.getPower());
                }
                enemy.setStruck(true);
                System.out.println("Enemy-Ship was attacked!!! Type: " + enemy.getShipType() + "\n Defence: " + enemy.getDefence());
                if (enemy.getDefence() <= 0) {
                    System.out.println("Enemy-Ship was sunk!!! Type: " + enemy.getShipType() + "\n Defence: " + enemy.getDefence());
                    enemy.setSank(true);
                }
                toPlayedCards(handC);
            } else if (handC.getColor().equals("red") && submarine(enemy)) {
                falseAttack(handC);
            } else {
                System.out.println("false move, try to play on another card or select new card");
            }
        } else  {
            System.out.println("false move, try to play on another card or select new card");
        }
    }

    /**
     * When the ship type is submarine and the color of missile red,
     * the player lost its missile and gives no attack on enemy submarine.
     * Because submarines can be only white missiles attacked.
     * @param handC the played card. Here it has to be type 'missile' and color of 'red'.
     */
    private void falseAttack(Cards handC) {
        handCards.remove(handC);
        System.out.println("False Attack!!! You have lost your missile!!!");
    }

    /**
     * When player repair card on its struck ship played,
     * the defence of ship will be +1.
     * @param ship the own ship card. Here has to be opened and struck.
     */
    private void repair(Cards ship) {
        ship.setDefence(ship.getDefence() + 1);
    }

    /**
     * If the battleship of a player is opened by enemy,
     * player has this advantage. So each attack on enemy ship will be +1.
     * For example: If player attacked with red missile with power of 1,
     * it will take 2 defence from enemy ship.
     * It is valid until this ship is sank.
     * Returns, if the player has this advantage, or not.
     * @return boolean value for the battleshipAdvantage.
     */
    private boolean battleshipAdvantage() {
        for (Cards c : gameCards) {
            if (c.getShipType() == 'b' && c.isOpened() && !c.isSank()) return true;
        }
        return false;
    }

    /**
     * Returns if the card is submarine. It is used to attack
     * with white missile or to call falseAttack(), when on submarine
     * with red missile attacked.
     * Returns if the enemy card, on which played, is submarine or not.
     * @param card the enemy card, on which the hand card is played.
     * @return boolean value for submarine.
     */
    private boolean submarine(Cards card) {
        return card.getShipType() == 's';
    }

    /**
     * Returns if the card, on which played belongs to enemy.
     * It is used either to call moveO() or moveE().
     * @param card the card, on which the hand card is played.
     * @return boolean value for enemy.
     */
    private boolean isEnemy(Cards card) {
        return !getTeam().equals(card.getTeam());
    }

    /**
     * Returns if the card, on which played opened.
     * It is used call either discover() or attack() methods.
     * @param card the card, on which the hand card is played.
     * @return boolean value for opened.
     */
    private boolean isDiscovered(Cards card) {
        return card.isOpened();
    }

    /**
     * It is used to shuffle cards.
     * It is used in Constructor when all cards are created
     * and in corresponding list saved.
     * @param l list of the cards, that has to be shuffled.
     */
    private void shuffleCards(List l) {
        Collections.shuffle(l);
        Collections.shuffle(l);
    }

    /**
     * Creates all ship cards of game and saved these
     * in corresponding list.
     * @param team team of the game.
     */
    private void createShips(String team) {
        Cards destroyer = new Cards(team, 2, 'd', "destroyer.png");
        Cards cruiser = new Cards(team, 3, 'c', "cruiser.png");
        Cards submarine = new Cards(team, 3, 's', "submarine.png");
        Cards battleship = new Cards(team, 4, 'b', "battleship.png");
        Cards aircraftCarrier = new Cards(team, 5, 'a', "aircraftcarrier.png");
        gameCards.add(destroyer);
        gameCards.add(cruiser);
        gameCards.add(submarine);
        gameCards.add(battleship);
        gameCards.add(aircraftCarrier);
    }

    /**
     * Creates all missed out cards of game and saved these
     * in corresponding list.
     * @param team team of the game.
     */
    private void createMissed(String team) {
        for (int i = 0; i < 7; i++) {
            gameCards.add(new Cards(team));
        }
    }

    /**
     * Creates all missile cards of game and saved these
     * in corresponding list.
     * @param team team of the game.
     */
    private void createMissiles(String team) {
        for (int i = 0; i < 9; i++) {
            takeCards.push(new Cards(team, "white", 1, "whitemissile.png"));
        }
        for (int i = 0; i < 6; i++) {
            takeCards.push(new Cards(team, "red", 1, "redmissile1.png"));
        }
        for (int i = 0; i < 3; i++) {
            takeCards.push(new Cards(team, "red", 2, "redmissile2.png"));
        }
        takeCards.push(new Cards(team, "red", 4, "redmissile4.png"));
    }

    /**
     * Creates all mayday cards of game and saved these
     * in corresponding list.
     * @param team team of the game.
     */
    private void createMayDay(String team) {
        for (int i = 0; i < 2; i++) {
            takeCards.push(new Cards(team, 's', "shield.png"));
        }
        for (int i = 0; i < 2; i++) {
            takeCards.push(new Cards(team, 'r', "repair.png"));
        }
    }

    /**
     * After the cards are created and saved in
     * corresponding lists, with the help of this method in
     * constructor first five play cards are taken
     * and saved in handCards list.
     */
    private void setHandCards() {
        for (int i = 0; i < 5; i++) {
            handCards.add(takeCards.pop());
        }
    }
    /**
     * After each move with this method,
     * played card is sent to
     * playedCards stack and
     * removed from handCards list.
     */
    private void toPlayedCards(Cards card) {
        playedCards.push(card);
        handCards.remove(card);
    }
    /**
     * After each move played card is sent to
     * playedCards stack and removed from handCards list.
     * Instead of this card will be new card with help of
     * this method taken from takeCards stack.
     */
    private void takeNewCard() {
        while (handCards.size() != 5) {
            handCards.add(takeCards.pop());
            if (takeCards.size() == 0) {
                reloadCards();
            }
        }
    }

    /**
     * If the all cards in takeCards stack used,
     * would be the played cards as takeCards saved
     * and shuffled.
     */
    private void reloadCards() {
        takeCards.addAll(playedCards);
        shuffleCards(takeCards);
    }

    //Help-Methods for toString

    /**
     * It is called from toString() method.
     * To print the cards in 4*3 Block,
     * textual information of cards
     * is saved in a String.
     * It also controls if the cards opened or ships are sank.
     * When so is, it will be showed in another form.
     * @param start index of gameCardsBlock
     * @param end index of gameCardsBlock
     * @return textual information for gameCards.
     */
    private String printGameC(int start, int end){
        StringBuilder s = new StringBuilder();
        for(int i = start; i<end; i++ ){
            if(!gameCards.get(i).isOpened()){
                s.append("[]");
            } else {
                if(gameCards.get(i).isSank()){
                    s.append("[/]");
                } else {
                    s.append(gameCards.get(i).toString());
                }
            }
        }
        s.append("\n");
        return s.toString();
    }

    /**
     * It is called from toString() method.
     * To print the cards in 4*3 Block,
     * textual information of cards
     * is saved in a String.
     * @return textual information for gameCards.
     */
    private String printGameCards(){
        String s = "";
        s += String.format("GameCards of %s", team);
        s += "\n";
        s += printGameC(0,4);
        s += printGameC(4,8);
        s += printGameC(8,12);
        s += "\n";
        return s;
    }

    /**
     * It is called from toString() method.
     * To print the handCards,
     * textual information of cards
     * is saved in a String.
     * @return textual information for gameCards.
     */
    private String printHandCards(){
        StringBuilder s = new StringBuilder();
        s.append(String.format("HandCards of %s", team));
        s.append("\n");
        for (Cards handCard : handCards) {
            s.append(handCard.toString());
        }
        return s.toString();
    }


}



/**
 * The Cards class contains all data to represent the game elements of Battleship.
 * For creating different elements (such as Missile, Ship, MayDay Cards and Missed Cards), it has
 * been defined four constructors with different parameters.
 * Here it has been setter/getter methods for Card objects defined. In addition to that it has been defined
 * the linkCreatorF() method to create the String for images links for each 'Cards'.
 */

class Cards {

    /**
     * Single Variables for Cards object.
     * frontImage is path for the card images.
     * Differently variables are used in constructors according the card type.
     */

    // Variables
    private final String frontImage, type, team;
    private boolean opened, struck, sank;
    private int defence, power;
    private char shipType, mdType;
    private String color;

    /**
     * Creates a new Cards object of type 'missed'.
     * @param team team of the card, shows to which side belong that card.
     */

    //Constructor for Missed
    public Cards(String team) {
        this.team = team;
        this.frontImage = linkCreatorF(team, "missed.png");
        this.type = "missed";
        this.opened = false;
    }

    /**
     * Creates a new Cards object of type 'ship'.
     * @param team team of the card, shows to which side belong that card.
     * @param defence the life of ship object.
     * @param shipType the type of ship. 's' for 'submarine', 'd' for 'destroyer',
     *                 'c' for 'cruiser', 'b' for 'battleship' and 'a' for 'aircraftcarrier'.
     * @param name name of card images to create path.
     */

    //Constructor for Ship
    public Cards(String team, int defence, char shipType, String name) {
        this.team = team;
        this.defence = defence;
        this.shipType = shipType;
        this.frontImage = linkCreatorF(team, name);
        this.type = "ship";
        this.opened = false;
        this.struck = false;
        this.sank = false;
    }

    /**
     * Creates a new Cards object of type 'missile'
     * @param team team of the card, shows to which side belong that card.
     * @param color color of missile. 'red' or 'white'. Both are used in different way.
     * @param power power of the missile. Whites have 1, Reds have 1,2,4.
     * @param name name of card images to create path.
     */

    //Constructor for Missile
    public Cards(String team, String color, int power, String name) {
        this.team = team;
        this.color = color;
        this.power = power;
        this.frontImage = linkCreatorF(team, name);
        this.type = "missile";
    }

    /**
     * Creates a new Cards object of type 'mayday'
     * @param team team of the card, shows to which side belong that card.
     * @param mdType type of mayday card. 's' for 'shield' 'r' for 'repair'.
     * @param name name of card images to create path.
     */

    //Constructor for MayDay Cards
    public Cards(String team, char mdType, String name) {
        this.team = team;
        this.mdType = mdType;
        this.frontImage = linkCreatorF(team, name);
        this.type = "mayday";
    }

    //Setter&Getter

    /**
     * Returns the TEAM of the card.
     * @return team of the card.
     */
    public String getTeam() {
        return team;
    }

    /**
     * Returns the PATH of image of the card.
     * @return path of image of the card.
     */
    public String getFrontImage() {
        return frontImage;
    }

    /**
     * Returns the TYPE of the card.
     * @return type of the card.
     */
    public String getType() {
        return type;
    }

    /**
     * Set the parameter of opened true or false, when it has been from enemy on this card played.
     * @param opened boolean value for card opened.
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    /**
     * Returns if the GameCard is OPENED. Can be enemy card or own card.
     * @return true when it is opened, else false.
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * Set the DEFENCE of ship, when it has been on this card played.
     * When from enemy played defence is minus else plus.
     * @param defence defence for ship.
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Returns the DEFENCE of the ship.
     * @return defence of the ship.
     */
    public int getDefence() {
        return defence;
    }

    /**
     * Returns the TYPE of the Ship.
     * 's' for 'submarine', 'd' for 'destroyer',
     * 'c' for 'cruiser', 'b' for 'battleship'
     * and 'a' for 'aircraftcarrier'.
     * @return the type of the ship.
     */
    public char getShipType() {
        return shipType;
    }

    /**
     * Set the variable of 'STRUCK' for ship,
     * when it has been from enemy attacked.
     * @param struck boolean value for ship 'struck'.
     */
    public void setStruck(boolean struck) {
        this.struck = struck;
    }

    /**
     * Returns if the ship is struck from enemy.
     * @return true of false.
     */
    public boolean isStruck() {
        return struck;
    }

    /**
     * Returns if the ship is sunk,
     * when it has lost all its defence by attacked.
     * @return true of false.
     */
    public boolean isSank() {
        return sank;
    }

    /**
     * Set the ship SANK variable true,
     * when it lost all its power.
     * @param sank boolean value for sank.
     */
    public void setSank(boolean sank) {
        this.sank = sank;
    }

    /**
     * Returns the COLOR of the missile. White or Red.
     * @return the color of missile.
     */
    public String getColor() {
        return color;
    }

    /**
     * Returns the POWER of the missile.
     * 1 for whites, 1,2,4 for reds.
     * @return the power of missile.
     */
    public int getPower() {
        return power;
    }

    /**
     * Returns the TYPE of the MAYDAY card.
     * 's' for 'shield' 'r' for 'repair'.
     * @return the type of the mayday.
     */
    public char getMDType() {
        return mdType;
    }

    /**
     * Returns the PATH for the card image. It is for GUI.
     * @param team team of the card.
     * @param name name of the image of the card.
     * @return the path for the card image.
     */
    public String linkCreatorF(String team, String name){
        String linkF = "";
        if(team.equals("red")) linkF = "\\battleship\\images\\red\\" + name;
        if (team.equals("blue")) linkF = "\\battleship\\images\\blue\\" + name;
        return linkF;
    }

    /**
     * A textual representation about the cards and cards information
     * (such as Power of Missile or Defence of Ship)
     * for Example:
     * -White Missile; [W1]
     * -Red Missile with 2 power; [R2]
     * -Shield; (S)
     * -Repair; [+]
     * -Missed; [X]
     * -Destroyer with 2 Defence; [D2]
     * -Cruiser with 2 Defence; [C2]
     * @return information of cards.
     */
    @Override
    public String toString() {
        String s = "";
        switch(type) {
            case "missile" -> s = (this.getColor().equals("white")) ? String.format("[W%d]", this.getPower()) : String.format("[R%d]", this.getPower());
            case "mayday" -> s = (this.getMDType() == 's') ? "(S)" : "[+]";
            case "missed" -> s = "[X]";
            case "ship" -> {
                if(this.getShipType() == 's') s = String.format("[S%d]",this.getDefence());
                if(this.getShipType() == 'd') s = String.format("[D%d]",this.getDefence());
                if(this.getShipType() == 'c') s = String.format("[C%d]",this.getDefence());
                if(this.getShipType() == 'b') s = String.format("[B%d]",this.getDefence());
                if(this.getShipType() == 'a') s = String.format("[A%d]",this.getDefence());
            }
            default -> s = "";
        }
        return s;
    }
}
