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
}