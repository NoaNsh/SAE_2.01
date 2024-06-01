package model;

import controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.GameCanvas;
import view.InterfaceGraphique;

import static org.junit.jupiter.api.Assertions.*;


class PositionTest {
    //private GameCanvas canvas;
    private Position position;
    private Apprenti apprenti;
    //private GameCanvas canvas;

    @BeforeEach
    void setUp() {
        //Scenario scenario = new Scenario();
        //scenario.fileLoader("File/scenarioTest.txt");
        //GameCanvas canvas = new GameCanvas(1750, 1000, scenario.getTemples(), apprenti);
        apprenti = new Apprenti(0, 0);
        position = new Position(apprenti);
    }

  /**  @Test
    void testMoveApprenti() {
        System.out.println("Fonction moveApprenti");
        position.moveApprenti('D', canvas, apprenti);
        assertEquals(1, apprenti.getX());
        assertEquals(0, apprenti.getY());
        position.moveApprenti('Q', canvas, apprenti);
        assertEquals(0, apprenti.getX());
        assertEquals(0, apprenti.getY());
        position.moveApprenti('S', canvas, apprenti);
        assertEquals(0, apprenti.getX());
        assertEquals(-1, apprenti.getY());
        position.moveApprenti('Z', canvas, apprenti);
        assertEquals(0, apprenti.getX());
        assertEquals(0, apprenti.getY());
    }
**/
    @Test
    void testToString() {
        System.out.println("Fonction toString");
        assertEquals("Position{abscisse=0, ordonnee=0}", position.toString());
    }

    @Test
    void testEquals(){
        System.out.println("Fonction equals");
        assertTrue(position.equals(new Object()));
    }
}
