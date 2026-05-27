package battleship.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is a thread that can be either a client or a server.
 * It automatically listens to new messages and either calls:
 *  {@link battleship.view.Battleship#setEnemyName(String)} if it receives the name of other player.
 *  This will be only by start.
 *  {@link Battleship#setTurn()} if it receives the String "TURN".
 *  After each turn, it has been sent to unlock the other side.
 *  {@link Battleship#setWinnerText()} if it receives the String "YOU".
 *  So the one side is winner and send other side information to set the Game Over too.
 *  {@link battleship.view.Battleship#setEnemyCards(CardsG)} if it receives the CardList of other side.
 *  This will be also occur one time at the beginning.
 *  {@link battleship.view.Battleship#updateSelfCard(PlayedCardE)} if it receives the updated card of own GameCard.
 *  When it is on enemyCard played, is this sent.
 *  {@link battleship.view.Battleship#updateECardDefence(PlayedCardIO)} if it receives the updated card information
 *  about the card of other side. When it is on own Card played, is this sent.
 * If one side ended the game, that will end automatically for other side, with the method of System.exit()
 */

public class ClientServerThread extends Thread{
    private final String ip;
    private final int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream oos;
    private final Battleship battleship;

    /**
     * Constructor for thread.
     * @param bs   Object for callbacks
     * @param ip   IP to connect to
     * @param port Port to connect to
     */
    private ClientServerThread(Battleship bs, String ip, int port) {
        this.battleship = bs;
        this.ip = ip;
        this.port = port;
    }

    /**
     * Constructor for either a client or server.
     * @param ip IP to connect to
     * @param port Port to connect to
     * @param battleship Object for callbacks
     */
    public static ClientServerThread newAny(String ip, int port, Battleship battleship){
        var cst = new ClientServerThread(battleship, ip, port);
        cst.connect();
        return cst;
    }

    /**
     * Creates a new connection either as a client or as a server.
     * @throws IOException
     */
    private void connect() {

        if(socket != null || serverSocket != null) {
            System.out.println("Connection can not be established. System terminated!");
            System.exit(0);
        }
        System.out.println("Connection...");
        try {
            socket = new Socket(ip, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("This is a client");
        } catch (IOException e) {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("This is a server");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Returns if this thread is connected to a client or server.
     * @return true if it is connected, false otherwise.
     */
    public boolean isConnected() {return socket != null && socket.isConnected();}

    /**
     * Returns if thread is a server.
     * @return true if it is server, false otherwise.
     */
    public boolean isServer() {return serverSocket != null;}

    /**
     * Sends the object to the other program.
     * @param obj Object to send
     */
    public void send(Object obj) {
        try {
            if(isConnected() && oos != null) {
                oos.reset();
                oos.writeObject(obj);
                System.out.println(obj.toString() + " has been sent.");
            }
        } catch (IOException e){}
    }

    /**
     * It automatically listens to new messages and either calls:
     *  *  {@link battleship.view.Battleship#setEnemyName(String)} if it receives the name of other player.
     *  *  {@link Battleship#setTurn()} if it receives the String "TURN".
     *  *  {@link Battleship#setWinnerText()} if it receives the String "YOU".
     *  *  {@link battleship.view.Battleship#setEnemyCards(CardsG)} if it receives the CardList of other side.
     *  *  {@link battleship.view.Battleship#updateSelfCard(PlayedCardE)} if it receives the updated card of own GameCard.
     *  *  {@link battleship.view.Battleship#updateECardDefence(PlayedCardIO)} if it receives the updated card information
     *  *  about the card of other side.
     */
    @Override
    public void run() {
        while(true) {
            try {
                if(socket == null) {
                    socket = serverSocket.accept();
                    oos = new ObjectOutputStream(socket.getOutputStream());
                }
                var ois = new ObjectInputStream(socket.getInputStream());
                while(true) {
                    Object obj = ois.readObject();
                    if(obj instanceof String){
                        if(obj.equals("YOU")) {
                            battleship.setWinnerText();
                        } else if(obj.equals("TURN")){
                            battleship.setTurn();
                        } else {
                            battleship.setEnemyName((String) obj);
                        }
                    }
                    if(obj instanceof CardsG){
                        battleship.setEnemyCards((CardsG) obj);
                    }
                    if(obj instanceof PlayedCardE){
                        battleship.updateSelfCard((PlayedCardE) obj);
                    }
                    if(obj instanceof PlayedCardIO){
                        battleship.updateECardDefence((PlayedCardIO) obj);
                    }
                }
            } catch (IOException e) {
                connect();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
