package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe HeuristiquePerso implémente une heuristique pour générer un chemin optimisé pour un apprenti
 * afin de trier les temples en fonction de la couleur de leurs cristaux.
 */
public class HeuristiquePerso {

    /**
     * Génère un chemin optimisé pour l'apprenti en fonction de l'état actuel des temples et de l'apprenti.
     * En bref, renvoie le temple correspondant au crystal de l'apprenti.
     *
     * @param temples Liste des temples à visiter.
     * @param apprenti L'apprenti qui doit trier les temples.
     * @return Liste des temples dans l'ordre de visite.
     */
    public List<Temple> generatePath(List<Temple> temples, Apprenti apprenti) {
        List<Temple> path = new ArrayList<>();

        // Trouver le temple le plus proche de l'apprenti
        Temple closestTemple = findClosestNonSortedTemple(apprenti, temples);

        // Si l'apprenti n'a pas de cristal, aller au temple le plus proche non trié
        if (apprenti.getColorCrystal() == -1) {
            path.add(closestTemple);
        } else {
            // Sinon, aller au temple qui correspond à la couleur de son cristal
            for (Temple temple : temples) {
                if (temple.getColorTemple() == apprenti.getColorCrystal()) {
                    closestTemple = temple;
                    break;
                }
            }
            path.add(closestTemple);
        }

        return path;
    }

    /**
     * Trouve le temple non trié le plus proche de l'apprenti.
     *
     * @param apprenti L'apprenti.
     * @param temples Liste des temples.
     * @return Le temple non trié le plus proche.
     */
    public Temple findClosestNonSortedTemple(Apprenti apprenti, List<Temple> temples) {
        Temple closestTemple = null;
        double minDistance = Double.MAX_VALUE;

        for (Temple temple : temples) {
            // Vérifier si le temple n'est pas trié
            if (temple.getColorCrystal() != temple.getColorTemple()) {
                double distance = calculateDistance(apprenti.getX(), apprenti.getY(), temple.getX(), temple.getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestTemple = temple;
                }
            }
        }

        return closestTemple;
    }

    /**
     * Calcule la distance entre deux points.
     *
     * @param x1 Coordonnée x du premier point.
     * @param y1 Coordonnée y du premier point.
     * @param x2 Coordonnée x du deuxième point.
     * @param y2 Coordonnée y du deuxième point.
     * @return La distance entre les deux points.
     */
    public double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }
}
