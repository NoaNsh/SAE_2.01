
package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Scenario.
 */
class ScenarioTest {

        /**
         * Teste la méthode fileLoader.
         * Vérifie que les temples sont correctement chargés à partir d'un fichier.
         */
        @Test
        void fileLoader() {
                // Créer une instance de Scenario
                Scenario scenario = new Scenario();

                // Appeler la méthode fileLoader avec un fichier de test existant
                String file = "File/scenario0.txt";
                scenario.fileLoader(file);

                // Vérifier si la liste de temples a été correctement chargée
                List<Temple> temples = scenario.getTemples();
                assertEquals(4, temples.size()); // Vérifier le nombre de temples chargés

                // Vérifier les coordonnées et les couleurs des temples chargés
                assertEquals(-1, temples.get(0).getX());
                assertEquals(-1, temples.get(0).getY());
                assertEquals(1, temples.get(0).getColorTemple());
                assertEquals(3, temples.get(0).getColorCrystal());

                assertEquals(-1, temples.get(1).getX());
                assertEquals(3, temples.get(1).getY());
                assertEquals(2, temples.get(1).getColorTemple());
                assertEquals(1, temples.get(1).getColorCrystal());

                assertEquals(3, temples.get(2).getX());
                assertEquals(3, temples.get(2).getY());
                assertEquals(3, temples.get(2).getColorTemple());
                assertEquals(4, temples.get(2).getColorCrystal());

                assertEquals(3, temples.get(3).getX());
                assertEquals(-1, temples.get(3).getY());
                assertEquals(4, temples.get(3).getColorTemple());
                assertEquals(2, temples.get(3).getColorCrystal());
        }
}