<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 2b700b00f50ba9b3ad6bd3a111baf5dff187d07e
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Position.
 */
class PositionTest {
    private Position position;
    private Apprenti apprenti;

    /**
     * Méthode exécutée avant chaque test. Elle initialise les objets nécessaires pour les tests.
     */
    @BeforeEach
    void setUp() {
        apprenti = new Apprenti(16, 16);
        position = new Position(apprenti);
    }

    /**
     * Teste la méthode toString.
     * Vérifie que la méthode toString retourne la chaîne attendue.
     */
    @Test
    void testToString() {
        System.out.println("Fonction toString");
        assertEquals("Position{abscisse=16, ordonnee=16}", position.toString());
    }

    /**
     * Teste la méthode moveApprentis.
     * Vérifie que le déplacement de l'apprenti se fait correctement et qu'il ne sort pas de la carte.
     */
    @Test
    void testMoveApprentis() {
        System.out.println("Fonction moveApprenti");
        // Test si l'apprenti sort de la carte
        position.moveApprentis(DIRECTION_RIGHT, apprenti);
        assertEquals(new Position(16, 16), apprenti.getPosition());

        position.moveApprentis(DIRECTION_DOWN, apprenti);
        assertEquals(new Position(16, 16), apprenti.getPosition());

        // Test lorsqu'il ne sort pas de la carte
        position.moveApprentis(DIRECTION_LEFT, apprenti);
        assertEquals(new Position(15, 16), apprenti.getPosition());

        position.moveApprentis(DIRECTION_UP, apprenti);
        assertEquals(new Position(15, 15), apprenti.getPosition());
    }

    /**
     * Teste la méthode equals.
     * Vérifie que la méthode equals fonctionne correctement pour différentes instances de Position.
     */
    @Test
    void testEquals() {
        Position position1 = new Position(5, 5);
        Position position2 = new Position(5, 5);
        Position position3 = new Position(3, 4);

        assertEquals(position1, position2);
        assertNotEquals(position1, position3);
        assertNotEquals(null, position1);
        assertNotEquals(position1, new Object());
    }

    /**
     * Teste la méthode checkIfAllCrystalsSorted.
     * Vérifie que la méthode checkIfAllCrystalsSorted fonctionne correctement pour différentes listes de temples.
     */
    @Test
    void testCheckIfAllCrystalsSorted() {
        Temple a = new Temple(0, 0, 1, 1);
        Temple b = new Temple(1, 1, 2, 2);
        Temple c = new Temple(2, 2, 3, 4);
        ArrayList<Temple> templesTrie = new ArrayList<>(Arrays.asList(a, b));
        ArrayList<Temple> templesNonTrie = new ArrayList<>(Arrays.asList(a, b, c));

        assertEquals(true, position.checkIfAllCrystalsSorted(templesTrie));
        assertEquals(false, position.checkIfAllCrystalsSorted(templesNonTrie));
    }
<<<<<<< HEAD
}
=======
}
=======
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
>>>>>>> a9eee1976180a830caf2dc8b5f1d68467f846181
>>>>>>> 2b700b00f50ba9b3ad6bd3a111baf5dff187d07e
