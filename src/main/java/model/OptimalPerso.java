package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe OptimalPerso implémente un algorithme optimal pour générer un chemin
 * qui permet à un apprenti de trier les temples en fonction de la couleur de leurs cristaux.
 */
public class OptimalPerso {

    /**
     * Génère un chemin optimisé pour l'apprenti en fonction de l'état actuel des temples et de l'apprenti.
     * En bref, renvoie le temple correspondant au crystal de l'apprenti mais calcule toujours
     * la distance des potentiels pairs de temples à échangé plus efficace en termes de nombre de pas.
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
            Temple targetTemple = null;
            for (Temple temple : temples) {
                if (temple.getColorTemple() == apprenti.getColorCrystal()) {
                    targetTemple = temple;
                    break;
                }
            }

            // Vérifier s'il est possible d'effectuer un échange optimal
            Temple pairTemple = findOptimalExchangeTemple(apprenti, temples, targetTemple);

            if (pairTemple != null) {
                // Calculer la distance totale pour l'échange et comparer avec la distance pour aller au temple cible
                double totalDistance = calculateDistance(apprenti.getX(), apprenti.getY(), pairTemple.getX(), pairTemple.getY());
                double distanceToTargetTemple = calculateDistance(apprenti.getX(), apprenti.getY(), targetTemple.getX(), targetTemple.getY());

                if (totalDistance < distanceToTargetTemple && totalDistance > 0.0) {
                    // Effectuer l'échange s'il est plus efficace
                    path.add(pairTemple);
                    Temple exchangeTargetTemple = findTempleForCrystal(pairTemple, temples);
                    if (exchangeTargetTemple != null) {
                        path.add(exchangeTargetTemple);
                        path.add(pairTemple);
                    }
                } else {
                    // Aller directement au temple cible
                    path.add(targetTemple);
                }
            } else {
                // Aller directement au temple cible
                path.add(targetTemple);
            }
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

    /**
     * Trouve le temple optimal pour effectuer un échange avec l'apprenti et le temple cible.
     *
     * @param apprenti L'apprenti.
     * @param temples Liste des temples.
     * @param targetTemple Le temple cible.
     * @return Le temple optimal pour l'échange.
     */
    public Temple findOptimalExchangeTemple(Apprenti apprenti, List<Temple> temples, Temple targetTemple) {
        Temple optimalPairTemple = null;
        double minTotalDistance = calculateDistance(apprenti.getX(), apprenti.getY(), targetTemple.getX(), targetTemple.getY());
        List<Temple> optimalPairs = new ArrayList<>();

        for (Temple temple1 : temples) {
            for (Temple temple2 : temples) {
                if (temple1 != temple2 && temple1.getPosition() != targetTemple.getPosition() &&
                        (temple1.getColorTemple() == temple2.getColorCrystal() && temple2.getColorTemple() == temple1.getColorCrystal()) &&
                        (temple1.getColorCrystal() != temple1.getColorTemple() && temple2.getColorCrystal() != temple2.getColorTemple())) {
                    double totalDistance = calculateDistance(temple1.getX(), temple1.getY(), temple2.getX(), temple2.getY()) +
                            calculateDistance(apprenti.getX(), apprenti.getY(), temple1.getX(), temple1.getY());
                    if (totalDistance < minTotalDistance) {
                        optimalPairs.add(temple1);
                    }
                }
            }
        }
        optimalPairTemple = findClosestNonSortedTemple(apprenti, optimalPairs);
        return optimalPairTemple;
    }

    /**
     * Trouve le temple qui correspond à la couleur du cristal d'un temple donné.
     *
     * @param pairTemple Le temple avec le cristal à échanger.
     * @param temples Liste des temples.
     * @return Le temple correspondant à la couleur du cristal.
     */
    public Temple findTempleForCrystal(Temple pairTemple, List<Temple> temples) {
        for (Temple temple : temples) {
            if (temple.getColorTemple() == pairTemple.getColorCrystal()) {
                return temple;
            }
        }
        return null;
    }
}
