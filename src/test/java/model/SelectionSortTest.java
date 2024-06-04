package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe SelectionSort.
 */
class SelectionSortTest {

    private SelectionSort selectionSort;
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
        a = new Temple(-4, -7, 4, 2);
        b = new Temple(4, 1, 2, 1);
        c = new Temple(-1, 6, 1, 3);
        d = new Temple(7, 6, 3, 4);
        temples = new ArrayList<>(Arrays.asList(a, b, c, d));
        apprenti = new Apprenti(0, 0);
        selectionSort = new SelectionSort(temples, apprenti);
    }

    /**
     * Teste la méthode triSelection.
     * Vérifie que la méthode triSelection génère le chemin de tri par sélection correct.
     */
    @Test
    public void testTriSelection() {
        List<Position> l = selectionSort.triSelection();

        // Vérifiez que les échanges sont corrects en fonction des cristaux
        assertEquals(new Position(b.getX(), b.getY()), l.get(0));
        assertEquals(new Position(c.getX(), c.getY()), l.get(1));
        assertEquals(new Position(b.getX(), b.getY()), l.get(2));
    }

    /**
     * Teste la méthode indexMinimum.
     * Vérifie que la méthode indexMinimum trouve correctement l'index du minimum dans un tableau de couleurs de cristaux.
     */
    @Test
    public void testIndexMinimum() {
        int[] tabColorCristal = {4, 2, 1, 3};

        // Test pour trouver l'index minimum dans le tableau entier
        assertEquals(2, selectionSort.indexMinimum(tabColorCristal, 0, tabColorCristal.length - 1));

        // Test pour trouver l'index minimum dans une sous-partie du tableau
        assertEquals(1, selectionSort.indexMinimum(tabColorCristal, 0, 1));
        assertEquals(2, selectionSort.indexMinimum(tabColorCristal, 2, 3));
    }
}