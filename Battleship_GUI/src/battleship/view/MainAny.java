package battleship.view;

import processing.core.PApplet;

/**
 * A processing view for the Battleship. Starts a Server or Client thread, which has each own Battleship.
 * If Server not created, it starts first server than client.
 * {@link battleship.view.ClientServerThread}
 *
 *  @implNote Because of the size game should be played
 *  either with extern monitor or with 100% scale rate.
 */

public class MainAny {
    /**
     * Main method to start Battleship.
     * @param args  start parameters (not used)
     */
    public static void main(String[] args) {

        var battleship = Battleship.newAny("localhost", 8080);
        PApplet.runSketch(new String[]{"Battleship"}, battleship);
    }
}
