package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe de test pour la classe HeuristiquePerso.
 */
class HeuristiquePersoTest {

    private HeuristiquePerso heuristiquePerso;
    private List<Temple> temples;
    private Apprenti apprenti;
    private Temple a;
    private Temple b;
    private Temple c;
    private Temple d;
    private List<Temple> l;

    /**
     * Méthode exécutée avant chaque test. Elle initialise les objets nécessaires pour les tests.
     */
    @BeforeEach
    public void setUp() {
        heuristiquePerso = new HeuristiquePerso();
        apprenti = new Apprenti(0, 0);
        a = new Temple(6, 2, 3, 1);
        b = new Temple(1, 1, 1, 2);
        c = new Temple(2, 3, 2, 4);
        d = new Temple(3, 3, 4, 3);

        temples = new ArrayList<>(Arrays.asList(a, b, c, d));
        l = heuristiquePerso.generatePath(temples, apprenti);
    }

    /**
     * Teste la méthode generatePath sans cristal.
     * Vérifie que le chemin généré contient le temple attendu.
     */
    @Test
    public void testGeneratePathNoCrystal() {
        assertEquals(1, l.size());
        assertEquals(b, l.get(0));
    }

    /**
     * Teste la méthode generatePath avec un cristal de couleur spécifique.
     * Vérifie que le chemin généré contient le temple attendu.
     */
    @Test
    public void testGeneratePathWithCrystal() {
        apprenti.setColorCrystal(2);
        l = heuristiquePerso.generatePath(temples, apprenti);
        assertEquals(1, l.size());
        assertEquals(c, l.get(0));
    }

    /**
     * Teste la méthode findClosestNonSortedTemple.
     * Vérifie que le temple le plus proche non trié est trouvé correctement.
     */
    @Test
    public void testFindClosestNonSortedTemple() {
        apprenti.setX(c.getX());
        apprenti.setY(c.getY());
        Temple closestTemple = heuristiquePerso.findClosestNonSortedTemple(apprenti, temples);
        assertEquals(c, closestTemple);
    }

    /**
     * Teste la méthode calculateDistance.
     * Vérifie que la distance calculée entre deux points est correcte.
     */
    @Test
    public void testCalculateDistance() {
        double distance = heuristiquePerso.calculateDistance(1, 1, 4, 5);
        assertEquals(7.0, distance);
    }
}