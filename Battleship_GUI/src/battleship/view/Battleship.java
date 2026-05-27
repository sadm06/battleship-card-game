package battleship.view;

import controlP5.*;
import controlP5.Button;
import battleship.controller.BattleshipController;
import battleship.controller.IBattleshipController;
import battleship.controller.IBattleshipView;
import battleship.model.Cards;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static controlP5.ControlP5Constants.*;
import static battleship.controller.GameState.*;

/**
 * View class of Battleship that contains all methods and variables to show the game.
 * These are ol for how to draw game scenes. Battleship instance created {@link battleship.view.MainAny}
 * with the help of newAny() method. {@link battleship.view.Battleship#newAny(String ip, int port)}
 * It creates a 'Battleship thread' either as 'Server' or 'Client' with the help of newAny() method of
 * ClientServerThread class. {@link battleship.view.ClientServerThread#newAny(String, int, Battleship)}
 * So that game is drawn and threads start to listen network and depending on action to send messages.
 */

public class Battleship extends PApplet implements IBattleshipView {
    private IBattleshipController controller;
    private ClientServerThread thread;
    private Textfield playerName;
    private Button startButton;
    private Boolean[] enemyStruck;
    boolean cGSent, turn;
    private PImage[] gameCardsI, enemyCardsI, handCardsI;
    private PImage titleScreen, gameBG, backCardG, backCardE, handCI, shipW;
    private PFont gameFU, gameFS, gameFGO;
    int indexP, indexH;
    char playedOnType;
    boolean isSentN, isServerSentN, isWin, infoGG;
    private String team, name, enemyName, winnerText, warning;
    private CardsG cardsG;

    /**
     * Creates a new Processing view of Battleship.
     */
    public Battleship() {}

    /**
     * Sets the size of game. It is default 1000*1000.
     * @implNote Because of the size game should be played
     * either with extern monitor or with 100% scale rate.
     */
    @Override
    public void settings() {
        setSize(1000, 1000);
    }

    /**
     * Initial setup of game.
     * For that used following methods of Battleship.
     * {@link Battleship#initializeVariables()}
     * {@link Battleship#setTeam()}
     * {@link Battleship#loadSetupImages()}
     * {@link Battleship#loadEnemyImages()}
     * {@link Battleship#titleScreenController()}
     */
    @Override
    public void setup() {
        initializeVariables();
        setTeam();
        loadSetupImages();
        loadEnemyImages();
        this.controller = new BattleshipController(this, team);
        cardsG = new CardsG(controller.gameCards(), team);
        titleScreenController();
    }

    /**
     * Shows the game scenes depending on {@link battleship.controller.GameState}.
     * This occurs with the help of {@link IBattleshipController#nextFrame()}
     */
    @Override
    public void draw() {
        controller.nextFrame();
    }

    /**
     * Creates either a server or client thread and assign this to game of Battleship.
     * Returns this game of Battleship as server or client.
     * @param ip IP to connect to
     * @param port Port to connect to
     * @return game of battleship (Processing view)
     */
    public static Battleship newAny(String ip, int port) {
        var battleship = new Battleship();
        battleship.thread = ClientServerThread.newAny(ip, port, battleship);
        battleship.thread.start();
        return battleship;
    }

    /**
     * Control method for pressed keys. Depending on key 
     * calls the corresponding methods of thread or view.
     * {@link battleship.view.ClientServerThread#send(Object)}
     * {@link battleship.view.Battleship#setNewSelection(int)}
     */
    @Override
    public void keyPressed() {
        if (controller.getState() == GAME) {
            switch (key) {
                case ' ' -> {
                    if (!cGSent && thread.isConnected()) {
                        thread.send(cardsG);
                        if (thread.isConnected()) {
                            cGSent = true;
                        }
                    }
                    System.out.println(cGSent);
                }
                case '1' -> {
                    if (turn && controller.enemyCards().size() == 12) setNewSelection(0);
                }
                case '2' -> {
                    if (turn && controller.enemyCards().size() == 12) setNewSelection(1);
                }
                case '3' -> {
                    if (turn && controller.enemyCards().size() == 12) setNewSelection(2);
                }
                case '4' -> {
                    if (turn && controller.enemyCards().size() == 12) setNewSelection(3);
                }
                case '5' -> {
                    if (turn && controller.enemyCards().size() == 12) setNewSelection(4);
                }
                default -> setNewSelection(-1);
            }
        }
    }

    /**
     * Control method for mouse release.
     * Depending on statements it calls the
     * {@link IBattleshipController#userInputE(int, int)}
     * or {@link IBattleshipController#userInputO(int, int)}
     */
    @Override
    public void mouseReleased() {
        if (controller.getState() == GAME && indexH >= 0) {
            indexP = getPlayedOn();
            System.out.println(indexP);
            System.out.println(playedOnType);

            if (indexP >= 0) {
                if (playedOnType == 's' && controller.handCards().get(indexH).getType().equals("mayday")) {
                    if (controller.gameCards().get(indexP).isOpened() && controller.gameCards().get(indexP).getType().equals("ship") && !controller.gameCards().get(indexP).isSank()) {
                        if (controller.handCards().get(indexH).getMDType() == 's' || controller.gameCards().get(indexP).isStruck()) {
                            controller.userInputO(indexH, indexP);
                            updateHandC();
                            PlayedCardIO pCI = new PlayedCardIO(controller.gameCards().get(indexP).getDefence(), indexP);
                            thread.send(pCI);
                            turn = false;
                            thread.send("TURN");
                            warning = String.format("Defence of ship %c. %d", controller.gameCards().get(indexP).getShipType(), controller.gameCards().get(indexP).getDefence());
                            resetIndexes();
                        } else {
                            warning = "Your ship doesnt\nneed a repairment.\nMake another move.";
                        }
                    } else {
                        warning = "Card is not opened\nor not ship or Sank.\nMake another move.";
                    }
                } else if (playedOnType == 'e' && controller.handCards().get(indexH).getType().equals("missile")) {
                    if (!controller.enemyCards().get(indexP).isOpened() || ((controller.enemyCards().get(indexP).getType().equals("ship") && !controller.enemyCards().get(indexP).isSank()) && (controller.enemyCards().get(indexP).getShipType() == 's' || controller.handCards().get(indexH).getColor().equals("red")))) {
                        String hCcolor = controller.handCards().get(indexH).getColor();
                        controller.userInputE(indexH, indexP);
                        updateHandC();
                        updateEnemyCards(controller.enemyCards().get(indexP), indexP);
                        PlayedCardE pC = new PlayedCardE(controller.enemyCards().get(indexP), indexP);
                        thread.send(pC);
                        turn = false;
                        thread.send("TURN");
                        if (controller.enemyCards().get(indexP).getShipType() == 's' && hCcolor.equals("red")) {
                            warning = "Attacked on Submarine\nwith red missile.\nLost your missile.";
                        } else if (controller.enemyCards().get(indexP).getType().equals("ship")) {
                            warning = String.format("Defence of ship %c. %d", controller.enemyCards().get(indexP).getShipType(), controller.enemyCards().get(indexP).getDefence());
                        } else {
                            warning = "Missed out.";
                        }
                        resetIndexes();
                    } else {
                        warning = "Not opened or not Ship.\nPlayed on false card.\nMake another move.";
                    }
                } else {
                    warning = "Played on false Card.\nMake another move.";
                }
            }
        }
    }

    /**
     * Shows the title screen of Battleship.
     */
    @Override
    public void drawTitleScreen() {
        background(titleScreen);
        textFont(gameFU);
        textAlign(CENTER);
        fill(255);
        text("BATTLESHIP", width / (float) 2.0, 160);

        noStroke();
        rect(200, 427, 425, 21);
        textFont(gameFS);
        textAlign(LEFT);
        fill(color(117, 14, 28));
        text(" Enter the player name. Only letters or numbers.", 200, 445);
    }

    /**
     * Shows the current game state of Battleship. This display should display the cards images of GameCards,
     * EnemyCards and HandCards. Also, player names and the infoBoxes for player.
     * If the cards updated, this will also update the images of cards.
     */
    @Override
    public void drawGame() {
        drawCards();
        sentPlayerName();
        String s = "   INSTRUCTIONS\n1. Select HandCard 1 to 5\n2. Click on GameCard";
        drawInfoBox(254, 43, 84, 54, 114, 145, s, (float) (3.0), 805, 280, 185, 95, 19, 810, 310);
        drawInfoBox(254, 43, 84, 117, 14, 28, warning, (float) (3.0), 805, 480, 185, 95, 19, 810, 510);
        if (turn) {
            drawInfoBox(66, 280, 237, 16, 64, 196, " your turn", (float) (3.0), 829, 10, 135, 40, 30, 830, 39);
        } else {
            drawInfoBox(66, 280, 237, 16, 64, 196, " enemy turn", (float) (3.0), 829, 10, 150, 40, 30, 828, 39);
        }
        if (!thread.isConnected())
            drawInfoBox(231, 237, 66, 117, 14, 28, "Wait for connection", (float) (5.0), 291, 375, 220, 40, 22, 318, 402);
        if (thread.isConnected() && !cGSent)
            drawInfoBox(231, 237, 66, 117, 14, 28, "Press Space to set up the GAME", (float) (5.0), 253, 375, 300, 40, 22, 267, 402);
    }

    /**
     * Shows the game over screen of Battleship.
     */
    @Override
    public void drawGG() {
        background(0);
        textFont(gameFU);
        textAlign(CENTER);
        fill(255);
        text("BATTLESHIP", width / (float) 2.0, 160);

        textFont(gameFGO);
        textAlign(CENTER);
        text("GAME OVER", width / (float) 2.0, height / (float) 2.0);
        textFont(gameFS);
        textSize(50);
        text(String.format(winnerText, name), width / (float) 2.0, height / (float) 2.0 + 75);
        if (!infoGG) {
            infoGG = true;
            thread.send("YOU");
        }
    }

    //Setup-Methods (in Controller)
    /**
     * This method takes the load the images of GameCards and first five HandCards.
     * It takes the paths from lists of Game with the help of controller.
     * It has not direct access on model.
     * @param gameC the list of GameCards.
     * @param handC the list of HandCards.
     */
    @Override
    public void loadImages(List<Cards> gameC, List<Cards> handC) {
        int cardsPerThread = 6;
        int threadCount = 3;
        int k, start, end;
        for (int i = 0; i < threadCount; i++) {
            if (i == 0 || i == 1) {
                k = 0;
                start = i * cardsPerThread;
                end = (i + 1) * cardsPerThread;
            } else {
                k = 1;
                start = 0;
                end = handCardsI.length;
            }

            int finalK = k;
            int finalStart = start;
            int finalEnd = end;

            Thread t = new Thread(() -> {
                for (int ci = finalStart; ci < finalEnd; ci++) {
                    if (finalK == 0) {
                        gameCardsI[ci] = loadImage(gameC.get(ci).getFrontImage());
                    } else {
                        handCardsI[ci] = loadImage(handC.get(ci).getFrontImage());
                    }
                }
            });
            t.start();
        }
    }

    //Thread-Methods (in ClientServerThread)

    /**
     * Sets the name of enemy, if thread receives the name of other player.
     * This will be only by start.
     * @param enemyN name of the enemy player.
     */
    public void setEnemyName(String enemyN) {
        this.enemyName = enemyN;
        System.out.println("EnemyName received");
    }

    /**
     * Sets the enemy cards with help of the controller. 
     * {@link IBattleshipController#setEnemyCards(ArrayList)}
     * if thread receives the CardList of other side
     * @param cardsG that contains gameCardsList of other side.
     */
    public void setEnemyCards(CardsG cardsG) {
        controller.setEnemyCards(cardsG.gameCards);
        System.out.println("EnemyCards received");
    }

    /**
     * Updates the self card, on which by enemy played, with help of the controller.
     * {@link IBattleshipController#updateSelfCard(int, Cards)}
     * @param pC that contains card of other side.
     */
    public void updateSelfCard(PlayedCardE pC) {
        controller.updateSelfCard(pC.getIndex(), pC.getPlayedC());
        System.out.println("PlayedCard from Enemy received");
    }

    /**
     * Updates the defence information of other side, with the help of controller.
     * {@link IBattleshipController#enemyCards()}
     * @param pCI that contains information of updated card of other side.
     */
    public void updateECardDefence(PlayedCardIO pCI) {
        controller.enemyCards().get(pCI.getIndex()).setDefence(pCI.getDefence());
        System.out.println("DefenceInformation from Enemy received");
    }

    /**
     * Sets the winnerText, if thread receives
     * from other side the String 'YOU'.
     * So the side is winner, which 'YOU' send
     * and send to other side information to set the Game Over too.
     */
    public void setWinnerText() {
        winnerText = "%s, YOU HAVE LOST";
        infoGG = true;
        controller.setState(GAME_OVER);
    }

    /**
     * Set the turn true, if thread receives the String 'TURN'.
     * After each turn, it has been sent to unlock the other side.
     * So that the player can play.
     */
    public void setTurn() {
        turn = true;
    }

    //Help-Methods

    /**
     * Initializes the all setup variables.
     */
    private void initializeVariables() {
        enemyStruck = new Boolean[12];
        enemyCardsI = new PImage[12];
        gameCardsI = new PImage[12];
        handCardsI = new PImage[5];
        cGSent = false;
        isSentN = false;
        isServerSentN = false;
        isWin = false;
        infoGG = false;
        indexH = -1;
        name = "";
        enemyName = "";
        warning = "";
        gameFU = createFont("\\battleship\\fonts\\BravoDestroyer.ttf", 120);
        gameFGO = createFont("\\battleship\\fonts\\GunMetal.ttf", 120);
        gameFS = createFont("\\battleship\\fonts\\MaldiniBold.ttf", 22);
        winnerText = "%s, YOU ARE THE WINNER";
    }

    /**
     * Set the color of team and turn.
     * If thread is server team will be red and turn true.
     * Otherwise blue and false.
     * Starter of game is always server.
     */
    private void setTeam() {
        if (thread.isServer()) {
            team = "red";
            turn = true;
        } else {
            team = "blue";
            turn = false;
        }
    }

    /**
     * Loads the setupImages 'title screen', 'game background' and 'sank ship'.
     * Depending on team loads also the images of not opened cards for
     * own cards and enemy cards.
     */
    private void loadSetupImages() {
        titleScreen = loadImage("\\battleship\\images\\TitleScreen.png");
        gameBG = loadImage("\\battleship\\images\\GameBG.png");
        shipW = loadImage("\\battleship\\images\\shipwreck.png");
        if (team.equals("red")) {
            backCardG = loadImage("\\battleship\\images\\red\\back2.png");
            backCardE = loadImage("\\battleship\\images\\blue\\back2.png");
        } else if (team.equals("blue")) {
            backCardG = loadImage("\\battleship\\images\\blue\\back2.png");
            backCardE = loadImage("\\battleship\\images\\red\\back2.png");
        }
    }

    /**
     * Loads the enemyCard images for the not opened cards.
     */
    private void loadEnemyImages() {
        for (int i = 0; i < 12; i++) {
            enemyCardsI[i] = backCardE;
        }
    }

    /**
     * Setup method for the controller objects of controlP5.
     * These are only on Title Screen to show. It contains also a button
     * to set name of player, and to change the game state.
     */
    private void titleScreenController() {
        PFont gameFB = createFont("\\battleship\\fonts\\SgtJhonO.ttf", 100);
        if (controller.getState() == TITLE_SCREEN) {
            ControlP5 cp5 = new ControlP5(this);
            cp5.setFont(gameFB, 25);
            cp5.setColorValueLabel(color(117, 14, 28));
            playerName = cp5.addTextfield("");
            playerName.setColorCursor(color(0));
            playerName.setColorBackground(color(85, 230, 136));
            playerName.setValue(String.format(" PLAYER %s", team));
            playerName.setPosition(200, 450);
            playerName.setSize(250, 50);
            startButton = cp5.addButton("START");
            startButton.setPosition(250, 510);
            startButton.setSize(150, 40);
            startButton.setColorBackground(194);
            startButton.addListenerFor(ACTION_RELEASE, callbackEvent -> {
                try {
                    name = (playerName.getText().trim().equals("")) ? String.format("PLAYER %s", team) : playerName.getText().trim();
                    System.out.println(name);
                    controller.setState(TITLE_TO_GAME);
                    playerName.hide();
                    startButton.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Sets the index number of selected hand cards
     * when the {@link Battleship#keyPressed()}
     * @param i index of handCard
     */
    private void setNewSelection(int i) {
        indexH = i;
        System.out.println(indexH);
        if (indexH >= 0) handCI = handCardsI[i];
    }

    /**
     * Sets the index of own card or enemy card and what type of card is this,
     * when {@link Battleship#mouseReleased()}. (Depending on the position of mouseX and mouseY)
     * for Example:
     * 'e' for enemyCard and 0...11 for the index of enemyCard.
     * 's' for enemyCard and 0...11 for the index of gameCard.
     * These will be handed over on control with {@link IBattleshipController#userInputE(int, int)}
     * or {@link IBattleshipController#userInputO(int, int)}
     * @return index of game or enemy card, on which played.
     */
    private int getPlayedOn() {
        if ((mouseX > 20 && mouseX < 184)) {
            if ((mouseY > 20 && mouseY < 115) || (mouseY > 415 && mouseY < 510)) indexP = 0;
            if ((mouseY > 150 && mouseY < 245) || (mouseY > 545 && mouseY < 640)) indexP = 4;
            if ((mouseY > 280 && mouseY < 375) || (mouseY > 675 && mouseY < 770)) indexP = 8;
        } else if ((mouseX > 219 && mouseX < 383)) {
            if ((mouseY > 20 && mouseY < 115) || (mouseY > 415 && mouseY < 510)) indexP = 1;
            if ((mouseY > 150 && mouseY < 245) || (mouseY > 545 && mouseY < 640)) indexP = 5;
            if ((mouseY > 280 && mouseY < 375) || (mouseY > 675 && mouseY < 770)) indexP = 9;
        } else if ((mouseX > 418 && mouseX < 582)) {
            if ((mouseY > 20 && mouseY < 115) || (mouseY > 415 && mouseY < 510)) indexP = 2;
            if ((mouseY > 150 && mouseY < 245) || (mouseY > 545 && mouseY < 640)) indexP = 6;
            if ((mouseY > 280 && mouseY < 375) || (mouseY > 675 && mouseY < 770)) indexP = 10;
        } else if ((mouseX > 617 && mouseX < 781)) {
            if ((mouseY > 20 && mouseY < 115) || (mouseY > 415 && mouseY < 510)) indexP = 3;
            if ((mouseY > 150 && mouseY < 245) || (mouseY > 545 && mouseY < 640)) indexP = 7;
            if ((mouseY > 280 && mouseY < 375) || (mouseY > 675 && mouseY < 770)) indexP = 11;
        }
        if ((mouseX < 20 || mouseX > 781) || (mouseY < 20 || mouseY > 770)) {
            indexP = -1;
            playedOnType = 'f';
        }
        if ((mouseX > 20 && mouseX < 781)) {
            if ((mouseY > 20 && mouseY < 375)) {
                playedOnType = 'e';
            } else if ((mouseY > 415 && mouseY < 770)) {
                playedOnType = 's';
            }
        }
        return indexP;
    }

    /**
     * Updates the images of HandCard after each move.
     * Because the played HandCard is deleted from the list handCards
     * and is taken another card from the list of takeCards.
     */
    private void updateHandC() {
        for (int i = 0; i < handCardsI.length; i++) {
            handCardsI[i] = loadImage(controller.handCards().get(i).getFrontImage());
        }
    }

    /**
     * Resets the indexes of hand- and playedCards after each successfully move.
     */
    private void resetIndexes() {
        indexH = -1;
        indexP = -1;
        playedOnType = 'f';
    }

    /**
     * Updates the image of enemyCard, if it is opened.
     * And sets true if the ship is sunk or struck.
     * Image updates occurs over controller in mouseReleased().
     * @see {@link #mouseReleased()}
     * @param enemyC the card of enemy, on which played.
     * @param index index of enemyCard.
     */
    private void updateEnemyCards(Cards enemyC, int index) {
        if (enemyC.isOpened()) enemyCardsI[index] = loadImage(enemyC.getFrontImage());
        if (enemyC.isSank()) enemyCardsI[index] = shipW;
        if (enemyC.isStruck()) enemyStruck[index] = true;
    }

    /**
     * Draws the GameCards with the help of
     * {@link #drawGameCards(List)}
     * {@link #drawEnemyCards()}
     * {@link #drawHandCards()}
     * That is handled by controller with the help of
     * {@link BattleshipController#nextFrame()}
     */
    private void drawCards() {
        background(gameBG);
        drawGameCards(controller.gameCards());
        drawEnemyCards();
        drawHandCards();
        strokeWeight((float) (3.0));
        stroke(color(254, 43, 84));
        fill(color(74, 79, 75));
        rect(8, 390, 784, 20);
        if (handCI != null) image(handCI, 750, 800, 125, 194);
    }

    /**
     * Draws the GameCards with the help of
     * {@link #drawGameC(List, PImage[], PImage, int, int, int)}
     * This draws cards in 4*3 bloc.
     * @param gameC
     */
    private void drawGameCards(List<Cards> gameC) {
        drawGameC(gameC, gameCardsI, backCardG, 0, 4, 410);
        drawGameC(gameC, gameCardsI, backCardG, 4, 8, 540);
        drawGameC(gameC, gameCardsI, backCardG, 8, 12, 670);
        stroke(color(92, 237, 66));
        strokeWeight((float) (3.0));
        fill(255);
        rect(799, 670, 130, 25);
        textFont(gameFS);
        textAlign(LEFT);
        fill(color(0));
        text(" " + name, 800, 691);
    }

    /**
     * Draws the cards in 4*3 Bloc.
     * @param gameC List of GameCards
     * @param frontCards List of images of opened cards
     * @param backCard image of not opened card
     * @param j start index of bloc
     * @param k end index of bloc
     * @param y vertical position of images
     */
    private void drawGameC(List<Cards> gameC, PImage[] frontCards, PImage backCard, int j, int k, int y) {
        int l = 0;
        for (int i = j; i < k; i++) {
            if (gameC.get(i).isSank() && !frontCards[i].equals(shipW)) {
                frontCards[i] = shipW;
            }
            if (!gameC.get(i).isOpened()) {
                if (backCard != null) {
                    image(backCard, 5 + 199 * l, y, 194, 125);
                    l++;
                }
            } else {
                if (frontCards[i] != null) {
                    image(frontCards[i], 5 + 199 * l, y, 194, 125);
                    if (gameC.get(i).isStruck()) {
                        stroke(color(252, 3, 3));
                        strokeWeight(4);
                        noFill();
                        rect(5 + 199 * l, y, 194, 125);
                    }
                    l++;
                }
            }
        }
    }

    /**
     * Draws the EnemyCards with the help of
     * {@link #drawEnemyC(PImage[], int, int, int)}
     * This draws cards in 4*3 bloc.
     */
    private void drawEnemyCards() {
        drawEnemyC(enemyCardsI, 0, 4, 5);
        drawEnemyC(enemyCardsI, 4, 8, 135);
        drawEnemyC(enemyCardsI, 8, 12, 265);
        stroke(color(255, 131, 0));
        strokeWeight((float) (3.0));
        fill(255);
        rect(799, 90, 130, 25);
        textFont(gameFS);
        textAlign(LEFT);
        fill(color(0));
        text(" " + enemyName, 800, 111);
    }

    /**
     * Draws the cards in 4*3 Bloc.
     * @param enemyCards List of images of enemy cards
     * @param j start index of bloc
     * @param k end index of bloc
     * @param y vertical position of images
     */
    private void drawEnemyC(PImage[] enemyCards, int j, int k, int y) {
        int l = 0;
        for (int i = j; i < k; i++) {
            image(enemyCards[i], 5 + 199 * l, y, 194, 125);
            if (enemyStruck[i] != null && enemyStruck[i]) {
                stroke(color(252, 3, 3));
                strokeWeight(4);
                noFill();
                rect(5 + 199 * l, y, 194, 125);
            }
            l++;
        }
    }

    /**
     * Draws the HandCards in a line.
     */
    private void drawHandCards() {
        for (int i = 0; i < 5; i++) {
            if (handCardsI[i] != null) {
                image(handCardsI[i], 5 + 130 * i, 800, 125, 194);
            }
        }
    }

    /**
     * Draws the info boxes on Game Screen depending on Parameters.
     * @param r1 r parameter for color of stroke
     * @param g1 g parameter for color of stroke
     * @param b1 b parameter for color of stroke
     * @param r2 r parameter for color of text
     * @param g2 g parameter for color of stroke
     * @param b2 b parameter for color of stroke
     * @param text text, that will be shown in infobox
     * @param stroke stroke of infobox
     * @param x1 horizontal position of infobox
     * @param y1 vertical position of infobox
     * @param width width of infobox
     * @param height height of infobox
     * @param textSize size of text
     * @param x2 horizontal position of text
     * @param y2 vertical position of text
     */
    private void drawInfoBox(int r1, int g1, int b1, int r2, int g2, int b2, String text, float stroke, int x1, int y1, int width, int height, int textSize, int x2, int y2) {
        stroke(color(r1, g1, b1));
        strokeWeight(stroke);
        fill(255);
        rect(x1, y1, width, height);
        fill(color(r2, g2, b2));
        textSize(textSize);
        text(text, x2, y2);
    }
    /**
     * Sends the player name to other side.
     * It occurs bilateral with threads.
     * It is called in {@link #drawGame()}
     * but only at the beginning of the game,
     * not in each frame change.
     * Thanks to boolean variable of isSentN
     * it is blocked to be sent in each frame.
     */
    private void sentPlayerName() {
        if (!isSentN) {
            thread.send(name);
            isSentN = true;
        }
        if (thread.isServer() && !enemyName.equals("") && !isServerSentN) {
            thread.send(name);
            isServerSentN = true;
        }
    }

}
