<<<<<<< HEAD
package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe BubbleSort.
 */
class TriBulleTest {

    private BubbleSort bubbleSort;
    private List<Temple> temples;
    private Apprenti apprenti;
    private Temple a;
    private Temple b;
    private Temple c;
    private Temple d;

    /**
     * Méthode exécutée avant chaque test. Elle initialise les objets nécessaires pour les tests.
     */
    @BeforeEach
    public void setUp() {
        a = new Temple(7, 5, 1, 2);
        b = new Temple(3, 9, 2, 1);
        c = new Temple(4, 1, 3, 4);
        d = new Temple(-2, 1, 4, 3);
        temples = new ArrayList<>(Arrays.asList(a, b, c, d));
        apprenti = new Apprenti(0, 0);
        bubbleSort = new BubbleSort(temples, apprenti);
    }

    /**
     * Teste la méthode triBulles.
     * Vérifie que la méthode triBulles génère le chemin de tri à bulles correct.
     */
    @Test
    public void testTriBulles() {
        List<Position> l = bubbleSort.triBulles();

        assertEquals(new Position(a.getX(), a.getY()), l.get(0));
        assertEquals(new Position(b.getX(), b.getY()), l.get(1));
        assertEquals(new Position(a.getX(), a.getY()), l.get(2));
        assertEquals(new Position(c.getX(), c.getY()), l.get(3));
    }
=======
package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriBulleTest {

    @Test
    public void testBubbleSort() {
        int[] liste = {5, 1, 4, 2, 8};
        int[] attendu = {1, 2, 4, 5, 8};
        TriBulle.bubbleSort(liste);
        assertArrayEquals(attendu, liste);
    }
>>>>>>> a9eee1976180a830caf2dc8b5f1d68467f846181
}