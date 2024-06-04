package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test pour la classe OptimalPerso.
 */
class OptimalPersoTest {
    private OptimalPerso optimalPerso;
    private List<Temple> temples;
    private Apprenti apprenti;
    private Temple a;
    private Temple b;
    private Temple c;

    /**
     * Méthode exécutée avant chaque test. Elle initialise les objets nécessaires pour les tests.
     */
    @BeforeEach
    public void setUp() {
        a = new Temple(1, 1, 1, 2);
        b = new Temple(2, 2, 2, 1);
        c = new Temple(5, 5, 3, 3);

        optimalPerso = new OptimalPerso();
        temples = new ArrayList<>();

        // Ajouter des temples de test
        temples.add(a);
        temples.add(b);
        temples.add(c);

        // Initialiser l'apprenti
        apprenti = new Apprenti(1, 1);
    }

    /**
     * Teste la méthode generatePath sans cristal.
     * Vérifie que le chemin généré contient le temple attendu.
     */
    @Test
    public void testGeneratePathWithoutCrystal() {
        // L'apprenti devrait aller au temple le plus proche non trié
        List<Temple> plusProche = new ArrayList<>();
        plusProche.add(temples.get(0));

        List<Temple> chemin = optimalPerso.generatePath(temples, apprenti);

        assertEquals(plusProche, chemin);
    }

    /**
     * Teste la méthode generatePath avec un cristal de couleur spécifique.
     * Vérifie que le chemin généré contient le temple attendu.
     */
    @Test
    public void testGeneratePathWithCrystal() {
        // Donner un cristal de couleur 1 à l'apprenti
        apprenti.setColorCrystal(1);

        // L'apprenti doit aller au temple correspondant à la couleur de son cristal,
        // sauf si un échange avec un autre temple est plus optimal

        List<Temple> cheminOpti = new ArrayList<>();
        cheminOpti.add(temples.get(0)); // Le temple optimal est le a

        List<Temple> cheminActuel = optimalPerso.generatePath(temples, apprenti);

        assertEquals(cheminOpti, cheminActuel);
    }

    /**
     * Teste la méthode findClosestNonSortedTemple.
     * Vérifie que le temple le plus proche non trié est trouvé correctement.
     */
    @Test
    public void testFindClosestNonSortedTemple() {
        Temple expectedTemple = temples.get(0); // Le temple le plus proche non trié est à (1,1)
        Temple actualTemple = optimalPerso.findClosestNonSortedTemple(apprenti, temples);
        assertEquals(expectedTemple, actualTemple, "Le temple non trié le plus proche devrait être celui à la position (1,1)");
    }

    /**
     * Teste la méthode findTempleForCrystal.
     * Vérifie que le temple correspondant au cristal est trouvé correctement.
     */
    @Test
    public void testFindTempleForCrystal() {
        Temple pairTemple = temples.get(0); // Le temple à la position (1,1) avec cristal de couleur 2
        Temple expectedTempleForCrystal = temples.get(1); // Le temple correspondant est à (2,2)
        Temple actualTempleForCrystal = optimalPerso.findTempleForCrystal(pairTemple, temples);

        assertEquals(expectedTempleForCrystal, actualTempleForCrystal, "Le temple correspondant au cristal devrait être celui à la position (2,2)");
    }
}