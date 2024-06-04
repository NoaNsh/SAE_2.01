package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * La classe SelectionSort implémente l'algorithme de tri par sélection pour trier une liste de temples
 * en fonction de la couleur de leurs cristaux.
 */
public class SelectionSort {

    private List<Temple> Temples;
    private Apprenti apprenti;

    /**
     * Constructeur de la classe SelectionSort.
     *
     * @param Temples Liste des temples à trier.
     * @param Apprenti L'apprenti qui effectue le tri.
     */
    public SelectionSort(List<Temple> Temples, Apprenti Apprenti) {
        this.Temples = Temples;
        this.apprenti = Apprenti;

        this.Temples.sort(Comparator.comparingInt(Temple::getColorTemple));
    }

    /**
     * Trouve l'index de l'élément minimum dans un sous-tableau.
     *
     * @param tab Tableau dans lequel chercher.
     * @param deb Index de début du sous-tableau.
     * @param fin Index de fin du sous-tableau.
     * @return Index de l'élément minimum.
     */
    public int indexMinimum(int[] tab, int deb, int fin) {
        int iMin = deb;
        for (int i = deb + 1; i <= fin; i++) {
            if (tab[i] < tab[iMin]) {
                iMin = i;
            }
        }
        return iMin;
    }

    /**
     * Tri les temples en utilisant l'algorithme de tri par sélection et génère la liste de positions
     * des mouvements nécessaires pour effectuer le tri.
     *
     * @return Liste des positions des temples après chaque mouvement de tri.
     */
    public List<Position> triSelection() {
        List<Position> listePosTemple = new ArrayList<>();
        int[] tabColorCristal = new int[Temples.size()];
        Temple[] tabTemples = new Temple[Temples.size()];

        // Initialiser les tableaux
        for (int i = 0; i < Temples.size(); i++) {
            tabTemples[i] = Temples.get(i);
            tabColorCristal[i] = tabTemples[i].getColorCrystal();
        }

        // Parcours de chaque temple sauf le dernier
        for (int i = 0; i < tabTemples.length - 1; i++) {
            // Trouver l'index du temple avec la plus petite valeur
            int minIndex = indexMinimum(tabColorCristal, i, tabTemples.length - 1);

            // Si minIndex est égal à i, ignorer cet échange car le cristal est déjà au bon endroit
            if (minIndex == i) {
                continue;
            }

            // Aller chercher le cristal le plus bas
            listePosTemple.add(tabTemples[minIndex].getPosition());

            // Déposer ce cristal dans la position actuelle
            listePosTemple.add(tabTemples[i].getPosition());

            // Récupérer le cristal de la position actuelle pour le ramener
            listePosTemple.add(tabTemples[minIndex].getPosition());

            // Échanger les valeurs dans le tableau de couleurs cristal
            int temp = tabColorCristal[minIndex];
            tabColorCristal[minIndex] = tabColorCristal[i];
            tabColorCristal[i] = temp;
        }

        // Ajouter la position du dernier temple
        listePosTemple.add(tabTemples[tabTemples.length - 1].getPosition());

        return listePosTemple;
    }
}
