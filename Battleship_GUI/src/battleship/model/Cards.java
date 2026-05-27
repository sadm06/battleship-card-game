package battleship.model;

import java.io.Serializable;

/**
 * The Cards class contains all data to represent the game elements of Battleship.
 * For creating different elements (such as Missile, Ship, MayDay Cards and Missed Cards), it has
 * been defined four constructors with different parameters.
 * Here it has been setter/getter methods for Card objects defined. In addition to that it has been defined
 * the linkCreatorF() method to create the String for images links for each 'Cards'.
 */

public class Cards implements Serializable {

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


