package battleship.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit tests for the `Cards` class.
 */

class CardsTest {

    //List of GameCards
    ArrayList<Cards> gameCardsRed = new ArrayList<>();
    ArrayList<Cards> gameCardsBlue = new ArrayList<>();

    // Missed
    Cards missedRed = new Cards("red");
    Cards missedBlue = new Cards("blue");

    // Ship
    Cards destroyerRed = new Cards("red", 2, 'd', "destroyer.png");
    Cards cruiserRed = new Cards("red", 3, 'c', "cruiser.png");
    Cards submarineRed = new Cards("red", 3, 's', "submarine.png");
    Cards battleshipRed = new Cards("red", 4, 'b', "battleship.png");
    Cards aircraftCarrierRed = new Cards("red", 5, 'a', "aircraftcarrier.png");

    Cards destroyerBlue = new Cards("blue", 2, 'd', "destroyer.png");
    Cards cruiserBlue = new Cards("blue", 3, 'c', "cruiser.png");
    Cards submarineBlue = new Cards("blue", 3, 's', "submarine.png");
    Cards battleshipBlue = new Cards("blue", 4, 'b', "battleship.png");
    Cards aircraftCarrierBlue = new Cards("blue", 5, 'a', "aircraftcarrier.png");

    // Missile
    Cards missileWRed = new Cards("red", "white", 1, "whitemissile.png");
    Cards missileRRed1 = new Cards("red", "red", 1, "redmissile1.png");
    Cards missileRRed2 = new Cards("red","red", 2, "redmissile2.png");
    Cards missileRRed4 = new Cards("red", "red", 4, "redmissile4.png");

    Cards missileWBlue = new Cards("blue", "white", 1, "whitemissile.png");
    Cards missileRBlue1 = new Cards("blue", "red", 1, "redmissile1.png");
    Cards missileRBlue2 = new Cards("blue", "red", 2, "redmissile2.png");
    Cards missileRBlue4 = new Cards("blue", "red", 4, "redmissile4.png");

    // MayDay
    Cards shieldRed = new Cards("red", 's', "shield.png");
    Cards repairRed = new Cards("red", 'r', "repair.png");

    Cards shieldBlue = new Cards("blue", 's', "shield.png");
    Cards repairBlue = new Cards("blue", 'r', "repair.png");


    @Test
    void getTeamGivesRed() {
        assertEquals("red", missedRed.getTeam());
        assertEquals("red", destroyerRed.getTeam());
        assertEquals("red", cruiserRed.getTeam());
        assertEquals("red", submarineRed.getTeam());
        assertEquals("red", battleshipRed.getTeam());
        assertEquals("red", aircraftCarrierRed.getTeam());
        assertEquals("red", missileWRed.getTeam());
        assertEquals("red", missileRRed1.getTeam());
        assertEquals("red", missileRRed2.getTeam());
        assertEquals("red", missileRRed4.getTeam());
        assertEquals("red", shieldRed.getTeam());
        assertEquals("red", repairRed.getTeam());
    }

    @Test
    void getTeamGivesBlue() {
        assertEquals("blue", missedBlue.getTeam());
        assertEquals("blue", destroyerBlue.getTeam());
        assertEquals("blue", cruiserBlue.getTeam());
        assertEquals("blue", submarineBlue.getTeam());
        assertEquals("blue", battleshipBlue.getTeam());
        assertEquals("blue", aircraftCarrierBlue.getTeam());
        assertEquals("blue", missileWBlue.getTeam());
        assertEquals("blue", missileRBlue1.getTeam());
        assertEquals("blue", missileRBlue2.getTeam());
        assertEquals("blue", missileRBlue4.getTeam());
        assertEquals("blue", shieldBlue.getTeam());
        assertEquals("blue", repairBlue.getTeam());
    }

    @Test
    void getFrontImageForRed() {
        assertEquals("\\battleship\\images\\red\\missed.png", missedRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\destroyer.png", destroyerRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\cruiser.png", cruiserRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\submarine.png", submarineRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\battleship.png", battleshipRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\aircraftcarrier.png", aircraftCarrierRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\whitemissile.png", missileWRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\redmissile1.png", missileRRed1.getFrontImage());
        assertEquals("\\battleship\\images\\red\\redmissile2.png", missileRRed2.getFrontImage());
        assertEquals("\\battleship\\images\\red\\redmissile4.png", missileRRed4.getFrontImage());
        assertEquals("\\battleship\\images\\red\\shield.png", shieldRed.getFrontImage());
        assertEquals("\\battleship\\images\\red\\repair.png", repairRed.getFrontImage());
    }

    @Test
    void getFrontImageForBlue() {
        assertEquals("\\battleship\\images\\blue\\missed.png", missedBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\destroyer.png", destroyerBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\cruiser.png", cruiserBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\submarine.png", submarineBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\battleship.png", battleshipBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\aircraftcarrier.png", aircraftCarrierBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\whitemissile.png", missileWBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\redmissile1.png", missileRBlue1.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\redmissile2.png", missileRBlue2.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\redmissile4.png", missileRBlue4.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\shield.png", shieldBlue.getFrontImage());
        assertEquals("\\battleship\\images\\blue\\repair.png", repairBlue.getFrontImage());
    }

    @Test
    void getTypeForRedBlue() {
        assertEquals("missed", missedRed.getType());
        assertEquals("ship", destroyerRed.getType());
        assertEquals("ship", cruiserRed.getType());
        assertEquals("ship", submarineRed.getType());
        assertEquals("ship", battleshipRed.getType());
        assertEquals("ship", aircraftCarrierRed.getType());
        assertEquals("missile", missileWRed.getType());
        assertEquals("missile", missileRRed1.getType());
        assertEquals("missile", missileRRed2.getType());
        assertEquals("missile", missileRRed4.getType());
        assertEquals("mayday", shieldRed.getType());
        assertEquals("mayday", repairRed.getType());

        assertEquals("missed", missedBlue.getType());
        assertEquals("ship", destroyerBlue.getType());
        assertEquals("ship", cruiserBlue.getType());
        assertEquals("ship", submarineBlue.getType());
        assertEquals("ship", battleshipBlue.getType());
        assertEquals("ship", aircraftCarrierBlue.getType());
        assertEquals("missile", missileWBlue.getType());
        assertEquals("missile", missileRBlue1.getType());
        assertEquals("missile", missileRBlue2.getType());
        assertEquals("missile", missileRBlue4.getType());
        assertEquals("mayday", shieldBlue.getType());
        assertEquals("mayday", repairBlue.getType());
    }

    @Test
    void setOpenedGameCardsRedBlue() {
        gameCardsRed.add(missedRed);
        gameCardsRed.add(destroyerRed);
        gameCardsRed.add(cruiserRed);
        gameCardsRed.add(submarineRed);
        gameCardsRed.add(battleshipRed);
        gameCardsRed.add(aircraftCarrierRed);

        for(Cards c: gameCardsRed){
            c.setOpened(true);
            assertTrue(c.isOpened());
        }

        gameCardsBlue.add(missedBlue);
        gameCardsBlue.add(destroyerBlue);
        gameCardsBlue.add(cruiserBlue);
        gameCardsBlue.add(submarineBlue);
        gameCardsBlue.add(battleshipBlue);
        gameCardsBlue.add(aircraftCarrierBlue);

        for(Cards c: gameCardsBlue){
            c.setOpened(true);
            assertTrue(c.isOpened());
        }
    }

    @Test
    void isOpenedGameCardsRedBlue() {

        Cards missedGrau = new Cards("grau");
        Cards cruiserGrau = new Cards("grau", 3, 'c', "cruiser.png");

        gameCardsRed.add(missedRed);
        gameCardsRed.add(destroyerRed);
        gameCardsRed.add(cruiserRed);
        gameCardsRed.add(submarineRed);
        gameCardsRed.add(battleshipRed);
        gameCardsRed.add(aircraftCarrierRed);

        for(Cards c: gameCardsRed){
            assertFalse(c.isOpened());

        }

        gameCardsBlue.add(missedBlue);
        gameCardsBlue.add(destroyerBlue);
        gameCardsBlue.add(cruiserBlue);
        gameCardsBlue.add(submarineBlue);
        gameCardsBlue.add(battleshipBlue);
        gameCardsBlue.add(aircraftCarrierBlue);

        for(Cards c: gameCardsBlue){
            assertFalse(c.isOpened());
        }

        missedGrau.setOpened(true);
        cruiserGrau.setOpened(true);

        assertTrue(missedGrau.isOpened());
        assertTrue(cruiserGrau.isOpened());
    }

    @Test
    void setDefenceGameCardsRedBlue() {

        Cards cruiserGrau = new Cards("grau", 3, 'c', "cruiser.png");
        cruiserGrau.setDefence(7);
        assertEquals(7, cruiserGrau.getDefence());

        gameCardsRed.add(missedRed);
        gameCardsRed.add(destroyerRed);
        gameCardsRed.add(cruiserRed);
        gameCardsRed.add(submarineRed);
        gameCardsRed.add(battleshipRed);
        gameCardsRed.add(aircraftCarrierRed);


        for(int i = 0; i<gameCardsRed.size(); i++){
            gameCardsRed.get(i).setDefence(i+1);
            assertEquals(i+1, gameCardsRed.get(i).getDefence());
        }

        gameCardsBlue.add(missedBlue);
        gameCardsBlue.add(destroyerBlue);
        gameCardsBlue.add(cruiserBlue);
        gameCardsBlue.add(submarineBlue);
        gameCardsBlue.add(battleshipBlue);
        gameCardsBlue.add(aircraftCarrierBlue);

        for(int i = 0; i<gameCardsBlue.size(); i++){
            gameCardsBlue.get(i).setDefence(i+1);
            assertEquals(i+1, gameCardsBlue.get(i).getDefence());
        }

    }

    @Test
    void getDefenceShipCardsBlueRed() {
        assertEquals(2, destroyerRed.getDefence());
        assertEquals(3, cruiserRed.getDefence());
        assertEquals(3, submarineRed.getDefence());
        assertEquals(4, battleshipRed.getDefence());
        assertEquals(5, aircraftCarrierRed.getDefence());

        assertEquals(2, destroyerBlue.getDefence());
        assertEquals(3, cruiserBlue.getDefence());
        assertEquals(3, submarineBlue.getDefence());
        assertEquals(4, battleshipBlue.getDefence());
        assertEquals(5, aircraftCarrierBlue.getDefence());
    }

    @Test
    void getShipType() {
        assertEquals('d', destroyerRed.getShipType());
        assertEquals('c', cruiserRed.getShipType());
        assertEquals('s', submarineRed.getShipType());
        assertEquals('b', battleshipRed.getShipType());
        assertEquals('a', aircraftCarrierRed.getShipType());

        assertEquals('d', destroyerBlue.getShipType());
        assertEquals('c', cruiserBlue.getShipType());
        assertEquals('s', submarineBlue.getShipType());
        assertEquals('b', battleshipBlue.getShipType());
        assertEquals('a', aircraftCarrierBlue.getShipType());
    }

    @Test
    void setStrikedShipCardsRedBlue() {
        Cards cruiserGrau = new Cards("grau", 3, 'c', "cruiser.png");
        cruiserGrau.setStruck(true);
        assertTrue(cruiserGrau.isStruck());

        gameCardsRed.add(destroyerRed);
        gameCardsRed.add(cruiserRed);
        gameCardsRed.add(submarineRed);
        gameCardsRed.add(battleshipRed);
        gameCardsRed.add(aircraftCarrierRed);

        for (Cards cards : gameCardsRed) {
            cards.setStruck(true);
            assertTrue(cards.isStruck());
        }

        gameCardsBlue.add(destroyerBlue);
        gameCardsBlue.add(cruiserBlue);
        gameCardsBlue.add(submarineBlue);
        gameCardsBlue.add(battleshipBlue);
        gameCardsBlue.add(aircraftCarrierBlue);

        for (Cards cards : gameCardsBlue) {
            cards.setStruck(true);
            assertTrue(cards.isStruck());
        }
    }

    @Test
    void isStrikedShipCardsRedBlue() {
        Cards cruiserGrau = new Cards("grau", 3, 'c', "cruiser.png");
        cruiserGrau.setStruck(true);
        assertTrue(cruiserGrau.isStruck());

        gameCardsRed.add(destroyerRed);
        gameCardsRed.add(cruiserRed);
        gameCardsRed.add(submarineRed);
        gameCardsRed.add(battleshipRed);
        gameCardsRed.add(aircraftCarrierRed);

        for (Cards cards : gameCardsRed) {
            cards.setStruck(true);
            assertTrue(cards.isStruck());
        }

        gameCardsBlue.add(destroyerBlue);
        gameCardsBlue.add(cruiserBlue);
        gameCardsBlue.add(submarineBlue);
        gameCardsBlue.add(battleshipBlue);
        gameCardsBlue.add(aircraftCarrierBlue);

        for (Cards cards : gameCardsBlue) {
            assertFalse(cards.isStruck());
        }

    }

    @Test
    void isSankShipCardsBlueRed() {
        Cards cruiserGrau = new Cards("grau", 3, 'c', "cruiser.png");
        cruiserGrau.setSank(true);
        assertTrue(cruiserGrau.isSank());

        gameCardsRed.add(destroyerRed);
        gameCardsRed.add(cruiserRed);
        gameCardsRed.add(submarineRed);
        gameCardsRed.add(battleshipRed);
        gameCardsRed.add(aircraftCarrierRed);

        for (Cards cards : gameCardsRed) {
            assertFalse(cards.isSank());
        }

        gameCardsBlue.add(destroyerBlue);
        gameCardsBlue.add(cruiserBlue);
        gameCardsBlue.add(submarineBlue);
        gameCardsBlue.add(battleshipBlue);
        gameCardsBlue.add(aircraftCarrierBlue);

        for (Cards cards : gameCardsBlue) {
            cards.setSank(true);
            assertTrue(cards.isSank());
        }
    }

    @Test
    void setSankShipCardsRedBlue() {

        gameCardsRed.add(destroyerRed);
        gameCardsRed.add(cruiserRed);
        gameCardsRed.add(submarineRed);
        gameCardsRed.add(battleshipRed);
        gameCardsRed.add(aircraftCarrierRed);

        for (Cards cards : gameCardsRed) {
            cards.setSank(true);
            assertTrue(cards.isSank());
        }

        gameCardsBlue.add(destroyerBlue);
        gameCardsBlue.add(cruiserBlue);
        gameCardsBlue.add(submarineBlue);
        gameCardsBlue.add(battleshipBlue);
        gameCardsBlue.add(aircraftCarrierBlue);

        for (Cards cards : gameCardsBlue) {
            assertFalse(cards.isSank());
        }
    }

    @Test
    void getColorDifferentlyCards() {
        assertNull(missedRed.getColor());
        assertNull(shieldBlue.getColor());
        assertNull(repairRed.getColor());
        assertNull(destroyerRed.getColor());
        assertNull(submarineBlue.getColor());
        assertEquals("white", missileWBlue.getColor());
        assertEquals("white", missileWRed.getColor());
        assertEquals("red", missileRRed4.getColor());
        assertEquals("red", missileRBlue2.getColor());
    }

    @Test
    void getPowerMissileCards() {
        assertEquals(1, missileWBlue.getPower());
        assertEquals(1, missileWRed.getPower());
        assertEquals(1, missileRRed1.getPower());
        assertEquals(2, missileRRed2.getPower());
        assertEquals(4, missileRRed4.getPower());
        assertEquals(1, missileRBlue1.getPower());
        assertEquals(2, missileRBlue2.getPower());
        assertEquals(4, missileRBlue4.getPower());
    }

    @Test
    void getMDTypeForMayDayCards() {
        assertEquals('s', shieldBlue.getMDType());
        assertEquals('s', shieldRed.getMDType());
        assertEquals('r', repairRed.getMDType());
        assertEquals('r', repairBlue.getMDType());
    }

    @Test
    void linkCreatorF() {
        Cards cardShip = new Cards("red");
        String s = cardShip.linkCreatorF("red", "cardship.png");
        assertEquals("\\battleship\\images\\red\\cardship.png", s);

        String s1 = aircraftCarrierBlue.linkCreatorF("blue", "newLink.png");
        assertEquals("\\battleship\\images\\blue\\newLink.png", s1);

    }

    @Test
    void testToString() {
        assertEquals("[X]", missedRed.toString());
        assertEquals("[X]", missedBlue.toString());
        assertEquals("[W1]", missileWRed.toString());
        assertEquals("[W1]", missileWBlue.toString());
        assertEquals("[R1]", missileRBlue1.toString());
        assertEquals("[R1]", missileRRed1.toString());
        assertEquals("[R2]", missileRBlue2.toString());
        assertEquals("[R2]", missileRRed2.toString());
        assertEquals("[R4]", missileRBlue4.toString());
        assertEquals("[R4]", missileRRed4.toString());
        assertEquals("[S3]", submarineBlue.toString());
        assertEquals("[D2]", destroyerRed.toString());
        assertEquals("[C3]", cruiserBlue.toString());
        assertEquals("[B4]", battleshipRed.toString());
        assertEquals("(S)", shieldRed.toString());
        assertEquals("[+]", repairBlue.toString());
        assertEquals("[+]", repairRed.toString());
        assertEquals("(S)", shieldBlue.toString());
        aircraftCarrierRed.setDefence(0);
        assertEquals("[A0]", aircraftCarrierRed.toString());
        aircraftCarrierBlue.setDefence(10);
        assertEquals("[A10]", aircraftCarrierBlue.toString());
    }
}