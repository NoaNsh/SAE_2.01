package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * La classe BubbleSort implémente l'algorithme de tri à bulles pour trier une liste de temples
 * en fonction de la couleur de leurs cristaux.
 */
public class BubbleSort {

    private List<Temple> Temples;
    private Apprenti apprenti;

    /**
     * Constructeur de la classe BubbleSort.
     *
     * @param Temples Liste des temples à trier.
     * @param Apprenti L'apprenti qui effectue le tri.
     */
    public BubbleSort(List<Temple> Temples, Apprenti Apprenti) {
        this.Temples = Temples;
        this.apprenti = Apprenti;

        this.Temples.sort(Comparator.comparingInt(Temple::getColorTemple));
    }

    /**
     * Tri les temples en utilisant l'algorithme de tri à bulles et génère la liste de positions
     * des mouvements nécessaires pour effectuer le tri.
     *
     * @return Liste des positions des temples après chaque mouvement de tri.
     */
    public List<Position> triBulles() {
        List<Position> listePosTemple = new ArrayList<>();
        int[] tabColorCristal = new int[Temples.size()];
        Temple[] tabTemples = new Temple[Temples.size()];

        // Initialiser les tableaux
        for (int i = 0; i < Temples.size(); i++) {
            tabTemples[i] = Temples.get(i);
            tabColorCristal[i] = tabTemples[i].getColorCrystal();
        }

        boolean swapped;
        // Parcours de chaque temple pour le tri à bulles
        for (int i = 0; i < tabTemples.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < tabTemples.length - 1 - i; j++) {
                if (tabColorCristal[j] > tabColorCristal[j + 1]) {
                    // Aller chercher le cristal du temple j
                    listePosTemple.add(tabTemples[j].getPosition());

                    // Déposer ce cristal dans la position j+1
                    listePosTemple.add(tabTemples[j + 1].getPosition());

                    // Récupérer le cristal de la position j+1 pour le ramener
                    listePosTemple.add(tabTemples[j].getPosition());

                    // Échanger les valeurs dans le tableau de couleurs cristal
                    int temp = tabColorCristal[j];
                    tabColorCristal[j] = tabColorCristal[j + 1];
                    tabColorCristal[j + 1] = temp;
                    swapped = true;
                }
            }
            // Si aucune permutation n'a été effectuée, le tableau est trié
            if (!swapped) {
                break;
            }
        }

        return listePosTemple;
    }
}
