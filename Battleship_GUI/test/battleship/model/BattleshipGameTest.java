package battleship.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit tests for the `BattleshipGame` model.
 */

class BattleshipGameTest {

    BattleshipGame bsR = new BattleshipGame("red");
    BattleshipGame bsB = new BattleshipGame("blue");


    @Test
    void getGameCards() {
        int size = bsR.getGameCards().size();
        int size2 = bsB.getGameCards().size();
        int countOfShip = 0;
        int countOfMissed = 0;
        assertEquals(12, size);
        assertEquals(12, size2);
        for(Cards c: bsR.getGameCards()){
            if(c.getType().equals("ship")) countOfShip++;
            if(c.getType().equals("missed")) countOfMissed++;
        }
        assertEquals(5, countOfShip);
        assertEquals(7, countOfMissed);
        for(int i = 0; i<bsB.getGameCards().size(); i++){
            assertEquals("blue", bsB.getGameCards().get(i).getTeam());
        }
        for(Cards c: bsB.getGameCards()){
            if(c.getType().equals("ship")) assertFalse(c.isSank());
            if(c.getType().equals("ship") && c.getShipType() == 's') assertEquals(3, c.getDefence());
            assertFalse(c.isOpened());
        }
    }

    @Test
    void getHandCards() {
        int size = bsR.getHandCards().size();
        int size2 = bsB.getHandCards().size();
        assertEquals(5, size);
        assertEquals(5, size2);
        for(Cards c: bsR.getHandCards()){
            if(c.getType().equals("mayday") && c.getMDType() == 's') assertEquals("(S)", c.toString());
        }
        for(int i = 0; i<bsR.getHandCards().size(); i++){
            assertEquals("red", bsR.getHandCards().get(i).getTeam());
        }
    }

    @Test
    void getEnemyCards() {
        assertEquals(0, bsR.getEnemyCards().size());
        assertEquals(0, bsB.getEnemyCards().size());
        bsB.setEnemyCards(bsR.getGameCards());
        int countOfShip = 0;
        int countOfMissed = 0;
        for(Cards c: bsB.getEnemyCards()){
            if(c.getType().equals("ship")) countOfShip++;
            if(c.getType().equals("missed")) countOfMissed++;
        }
        assertEquals(5, countOfShip);
        assertEquals(7, countOfMissed);
    }

    @Test
    void setEnemyCards() {
        bsB.setEnemyCards(bsR.getGameCards());
        bsR.setEnemyCards(bsB.getGameCards());

        assertEquals(12, bsR.getEnemyCards().size());
        assertEquals(12, bsB.getEnemyCards().size());


        for(int i = 0; i<bsR.getEnemyCards().size(); i++){
            assertEquals("blue", bsR.getEnemyCards().get(i).getTeam());
        }

        for(Cards c: bsB.getEnemyCards()){
            if(c.getType().equals("ship")) assertFalse(c.isStruck());
            if(c.getType().equals("ship") && c.getShipType() == 'b') assertEquals(4, c.getDefence());
            assertFalse(c.isOpened());
        }

    }


    @Test
    void getTeam() {
        assertEquals("red", bsR.getTeam());
        assertEquals("blue", bsB.getTeam());
    }

    @Test
    void move() {

        Cards missileWRed = new Cards("red", "white", 1, "whitemissile.png");
        Cards missileRRed = new Cards("red", "red", 1, "redmissile.png");
        Cards submarineBlue = new Cards("blue", 3, 's', "submarine.png");
        Cards battleshipBlue = new Cards("blue", 4, 'b', "battleship.png");
        Cards destroyerBlue = new Cards("blue", 2, 'd', "destroyer.png");
        Cards shieldBlue = new Cards("blue", 's', "shield.png");
        Cards repairBlue = new Cards("blue", 'r', "repair.png");


        bsR.getHandCards().set(1, missileWRed);
        bsR.move(bsR.getHandCards().get(1), battleshipBlue);
        Assertions.assertTrue(battleshipBlue.isOpened());

        bsR.getHandCards().set(2, missileWRed);
        bsR.move(bsR.getHandCards().get(2), submarineBlue);
        Assertions.assertTrue(submarineBlue.isOpened());

        bsR.getHandCards().set(3, missileRRed);
        bsR.move(bsR.getHandCards().get(3), submarineBlue);
        assertEquals(3, submarineBlue.getDefence());

        bsR.getHandCards().set(4, missileRRed);
        int defence = battleshipBlue.getDefence();
        bsR.move(bsR.getHandCards().get(4), battleshipBlue);
        assertTrue(battleshipBlue.isStruck());
        assertNotEquals(defence, battleshipBlue.getDefence());
        assertEquals(3, battleshipBlue.getDefence());

        bsR.getHandCards().set(0, missileWRed);
        int defenceS = submarineBlue.getDefence();
        bsR.move(bsR.getHandCards().get(0), submarineBlue);
        assertNotEquals(defenceS, submarineBlue.getDefence());

        bsB.getHandCards().set(1, shieldBlue);
        bsB.move(bsB.getHandCards().get(1), submarineBlue);
        assertEquals(4, submarineBlue.getDefence());

        bsB.getHandCards().set(2, repairBlue);
        bsB.move(bsB.getHandCards().get(2), battleshipBlue);
        assertEquals(4, battleshipBlue.getDefence());




        for(Cards c: bsR.getGameCards()){
            if(c.getType().equals("ship") && c.getShipType() == 'b'){
                c.setOpened(true);
            }
        }

        bsR.getHandCards().set(1, missileRRed);
        bsR.move(bsR.getHandCards().get(1), destroyerBlue);
        assertTrue(destroyerBlue.isOpened());
        assertTrue(destroyerBlue.isStruck());
        assertTrue(destroyerBlue.isSank());


        for(int i = 0; i<bsR.getHandCards().size(); i++){
            if(bsR.getHandCards().get(i).getType().equals("missile")){
                bsR.move(bsR.getHandCards().get(i), bsB.getGameCards().get(i));
                Assertions.assertTrue(bsB.getGameCards().get(i).isOpened());
            }
        }

        for(int i = 0; i<bsB.getHandCards().size(); i++){
            if(bsB.getHandCards().get(i).getType().equals("missile")){
                bsB.move(bsB.getHandCards().get(i), bsR.getGameCards().get(i));
                Assertions.assertTrue(bsR.getGameCards().get(i).isOpened());
            }
        }

        for(int i = 0; i<bsR.getHandCards().size(); i++){
            if(bsR.getHandCards().get(i).getType().equals("missile")){
                bsR.move(bsR.getHandCards().get(i), bsB.getGameCards().get(i));
                Assertions.assertTrue(bsB.getGameCards().get(i).isOpened());
            }
        }

        for(int i = 0; i<bsB.getHandCards().size(); i++){
            if(bsB.getHandCards().get(i).getType().equals("missile")){
                bsB.move(bsB.getHandCards().get(i), bsR.getGameCards().get(i));
                Assertions.assertTrue(bsR.getGameCards().get(i).isOpened());
            }
        }

        for(int i = 0; i<12; i++){
            bsR.getGameCards().get(i).setOpened(true);
            bsR.getGameCards().get(i).setStruck(true);
            bsB.getGameCards().get(i).setOpened(true);
            bsB.getGameCards().get(i).setStruck(true);
        }

        for(int i = 0; i<bsR.getHandCards().size(); i++){
            if(bsR.getHandCards().get(i).getType().equals("mayday") && bsR.getGameCards().get(i).getType().equals("ship")){
                int d = bsR.getGameCards().get(i).getDefence();
                bsR.move(bsR.getHandCards().get(i), bsR.getGameCards().get(i));
                assertFalse((d == bsR.getGameCards().get(i).getDefence()));
            }
        }

        for(int i = 0; i<bsB.getHandCards().size(); i++){
            if(bsB.getHandCards().get(i).getType().equals("mayday") && bsB.getGameCards().get(i).getType().equals("ship")){
                int d = bsB.getGameCards().get(i).getDefence();
                bsB.move(bsB.getHandCards().get(i), bsB.getGameCards().get(i));
                assertFalse((d == bsB.getGameCards().get(i).getDefence()));
            }
        }

        for(int i = 0; i<bsR.getHandCards().size(); i++){
            if(bsR.getHandCards().get(i).getType().equals("mayday") && bsR.getGameCards().get(i).getType().equals("ship")){
                int d = bsR.getGameCards().get(i).getDefence();
                bsR.move(bsR.getHandCards().get(i), bsR.getGameCards().get(i));
                assertFalse((d == bsR.getGameCards().get(i).getDefence()));
            }
        }
        for(int i = 0; i<bsB.getHandCards().size(); i++){
            if(bsB.getHandCards().get(i).getType().equals("mayday") && bsB.getGameCards().get(i).getType().equals("ship")){
                int d = bsB.getGameCards().get(i).getDefence();
                bsB.move(bsB.getHandCards().get(i), bsB.getGameCards().get(i));
                assertFalse((d == bsB.getGameCards().get(i).getDefence()));
            }
        }
    }

    @Test
    void isGameOver() {
        bsB.setEnemyCards(bsR.getGameCards());
        assertFalse(bsB.isGameOver());

        bsR.setEnemyCards(bsB.getGameCards());
        assertFalse(bsR.isGameOver());

        for(Cards c:bsR.getEnemyCards()){
            if(c.getType().equals("ship")) c.setSank(true);
        }
        Assertions.assertTrue(bsR.isGameOver());
    }

    @Test
    void moveJ() {

        Cards missileWRed = new Cards("red", "white", 1, "whitemissile.png");

        Cards battleshipRed = new Cards("red", 4, 'b', "battleship.png");
        Cards shieldRed = new Cards("red", 's', "shield.png");

        Cards missileWBlue = new Cards("blue", "white", 1, "whitemissile.png");
        Cards missileRBlue = new Cards("blue", "red", 1, "redmissile.png");

        Cards submarineBlue = new Cards("blue", 3, 's', "submarine.png");
        Cards repairBlue = new Cards("blue", 'r', "repair.png");

        bsR.getHandCards().set(0, missileWRed);
        bsR.moveJ(bsR.getHandCards().get(0), submarineBlue, bsB);
        assertTrue(submarineBlue.isOpened());

        bsB.getHandCards().set(0, missileWBlue);
        bsB.moveJ(bsB.getHandCards().get(0), battleshipRed, bsR);
        assertTrue(battleshipRed.isOpened());

        bsR.getHandCards().set(1, missileWRed);
        bsR.moveJ(bsR.getHandCards().get(1), submarineBlue, bsB);
        assertTrue(submarineBlue.isStruck());
        assertEquals(2, submarineBlue.getDefence());

        bsB.getHandCards().set(1, missileRBlue);
        bsB.moveJ(bsB.getHandCards().get(1), battleshipRed, bsR);
        assertTrue(battleshipRed.isStruck());
        assertEquals(3, battleshipRed.getDefence());

        bsR.getHandCards().set(2, shieldRed);
        bsR.moveJ(bsR.getHandCards().get(2), battleshipRed, bsB);
        assertEquals(5, battleshipRed.getDefence());

        bsB.getHandCards().set(2, repairBlue);
        bsB.moveJ(bsB.getHandCards().get(2), submarineBlue, bsR);
        assertEquals(3, submarineBlue.getDefence());
    }

    @Test
    void testToString() {
        String s = "\nGameCards of red\n"+"[]".repeat(4)+"\n"+"[]".repeat(4)+"\n"+"[]".repeat(4)+"\n\nHandCards of red\n";
        for(int i=0; i<bsR.getHandCards().size(); i++){
            s += bsR.getHandCards().get(i).toString();
        }
        assertEquals(s, bsR.toString());

        for(Cards c:bsR.getGameCards()){
            c.setOpened(true);
        }
        TestMove:
        for(int i = 0; i<12; i++){
            if(bsR.getGameCards().get(i).getType().equals("ship")){
                bsR.getGameCards().get(i).setSank(true);
                break TestMove;
            }
        }
    }
}