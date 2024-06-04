package model;

import view.GameCanvas;
import constants.Constants;
import javafx.application.Platform;
import view.Menu;

import java.util.List;

/**
 * La classe Position représente les coordonnées et les mouvements de l'apprenti
 * dans le jeu. Elle contient des méthodes pour déplacer l'apprenti dans différentes
 * directions et pour le déplacer vers une position cible.
 */
public class Position implements Constants {
    private static int nombreDePas = 0; // Nombre de pas effectués par l'apprenti
    private int abscisse; // Coordonnée X de la position
    private int ordonnee; // Coordonnée Y de la position
    private Apprenti apprenti; // Instance de l'apprenti

    /**
     * Constructeur utilisant une instance d'Apprenti pour initialiser les coordonnées.
     *
     * @param apprenti L'instance de l'apprenti
     */
    public Position(Apprenti apprenti) {
        this.apprenti = apprenti;
        this.abscisse = apprenti.getX();
        this.ordonnee = apprenti.getY();
    }

    /**
     * Nouveau constructeur utilisant des coordonnées spécifiques.
     *
     * @param abscisse La coordonnée X
     * @param ordonnee La coordonnée Y
     */
    public Position(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    /**
     * Déplace l'apprenti dans une direction spécifique et redessine sa position sur le canvas.
     *
     * @param direction La direction du déplacement (haut, bas, gauche, droite, ou changement de cristal)
     * @param canvas    Le canvas sur lequel l'apprenti est dessiné
     * @param apprenti  L'instance de l'apprenti
     */
    public void moveApprenti(char direction, GameCanvas canvas, Apprenti apprenti) {
        int newX = apprenti.getX();
        int newY = apprenti.getY();
        int oldX = newX;
        int oldY = newY;

        switch (direction) {
            case DIRECTION_UP:
                newY -= 1; // Déplacement vers le haut
                nombreDePas++;
                break;
            case DIRECTION_LEFT:
                newX -= 1; // Déplacement vers la gauche
                nombreDePas++;
                break;
            case DIRECTION_DOWN:
                newY += 1; // Déplacement vers le bas
                nombreDePas++;
                break;
            case DIRECTION_RIGHT:
                newX += 1; // Déplacement vers la droite
                nombreDePas++;
                break;
            case SWITCH_CRYSTAL:
                break;
        }

        // Vérifier si les nouvelles coordonnées sont à l'intérieur de la grille //en considérant que la grille sera toujours impaire (pour le 0,0 au centre)
        if (newX < -(GRID_WIDTH/2) || newX >= GRID_WIDTH/2+1 || newY < -(GRID_HEIGHT/2) || newY >= GRID_HEIGHT/2 + 1) {
            return;
        }
        canvas.dessinerMoveApprenti(direction, oldX, oldY, newX, newY, ZOOM_FACTOR);

        apprenti.setX(newX);
        apprenti.setY(newY);


    }



    /**
     * Déplace l'apprenti dans une direction spécifique et redessine sa position sur le canvas.
     * VERSION POUR LES TESTS SANS L'APPEL DANS LE CANVAS
     *
     * @param direction La direction du déplacement (haut, bas, gauche, droite, ou changement de cristal)
     * @param apprenti  L'instance de l'apprenti
     */
    public void moveApprentis(char direction, Apprenti apprenti) {
        int newX = apprenti.getX();
        int newY = apprenti.getY();
        int oldX = newX;
        int oldY = newY;

        switch (direction) {
            case DIRECTION_UP:
                newY -= 1; // Déplacement vers le haut
                nombreDePas++;
                break;
            case DIRECTION_LEFT:
                newX -= 1; // Déplacement vers la gauche
                nombreDePas++;
                break;
            case DIRECTION_DOWN:
                newY += 1; // Déplacement vers le bas
                nombreDePas++;
                break;
            case DIRECTION_RIGHT:
                newX += 1; // Déplacement vers la droite
                nombreDePas++;
                break;
            case SWITCH_CRYSTAL:
                break;
        }

        // Vérifier si les nouvelles coordonnées sont à l'intérieur de la grille //en considérant que la grille sera toujours impaire (pour le 0,0 au centre)
        if (newX < -(GRID_WIDTH/2) || newX >= GRID_WIDTH/2+1 || newY < -(GRID_HEIGHT/2) || newY >= GRID_HEIGHT/2 + 1) {
            return;
        }

        apprenti.setX(newX);
        apprenti.setY(newY);


    }

    /**
     * Déplace l'apprenti vers une position cible en utilisant des threads pour gérer les mouvements
     * asynchrones et mettre à jour l'interface utilisateur via Platform.runLater.
     *
     * @param targetX La coordonnée X cible
     * @param targetY La coordonnée Y cible
     * @param canvas  Le canvas sur lequel l'apprenti est dessiné
     * @param apprenti L'instance de l'apprenti
     * @param menu    L'instance du menu pour mettre à jour les déplacements
     */
    public void moveApprentiToTarget(int targetX, int targetY, GameCanvas canvas, Apprenti apprenti, Menu menu) {
        if (apprenti.isMoving() || !apprenti.isRunning()) return;

        apprenti.setMoving(true);
        int apprentiX = apprenti.getX();
        int apprentiY = apprenti.getY();

        while (apprentiX != targetX && apprenti.isRunning()) {
            if (apprentiX < targetX) {
                apprentiX++;
                Platform.runLater(() -> moveApprenti(DIRECTION_RIGHT, canvas, apprenti));
            } else if (apprentiX > targetX) {
                apprentiX--;
                Platform.runLater(() -> moveApprenti(DIRECTION_LEFT, canvas, apprenti));
            }
            final int finalApprentiX = apprentiX;
            final int finalApprentiY = apprentiY;
            Platform.runLater(() -> {
                apprenti.setX(finalApprentiX);
                apprenti.setY(finalApprentiY);
                menu.updateDeplacements();
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (apprentiY != targetY && apprenti.isRunning()) {
            if (apprentiY < targetY) {
                apprentiY++;
                Platform.runLater(() -> moveApprenti(DIRECTION_DOWN, canvas, apprenti));
            } else if (apprentiY > targetY) {
                apprentiY--;
                Platform.runLater(() -> moveApprenti(DIRECTION_UP, canvas, apprenti));
            }
            final int finalApprentiX = apprentiX;
            final int finalApprentiY = apprentiY;
            Platform.runLater(() -> {
                apprenti.setX(finalApprentiX);
                apprenti.setY(finalApprentiY);
                menu.updateDeplacements();
            });
            try {
                Thread.sleep(MOVE_SLEEP_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (apprenti.isRunning()) {
            Platform.runLater(() -> {
                moveApprenti(SWITCH_CRYSTAL, canvas, apprenti);
                menu.updateDeplacements();
            });
        }

        apprenti.setMoving(false);
    }

    /**
     * Attend que l'apprenti se déplace vers une position cible sur la grille du jeu, pour ne pas enchainer 2 déplacement simultanément.
     *
     * @param targetX     La coordonnée X de la position cible sur la grille.
     * @param targetY     La coordonnée Y de la position cible sur la grille.
     * @param gameCanvas  L'objet GameCanvas utilisé pour dessiner la grille du jeu.
     * @param apprenti    L'objet Apprenti représentant le personnage joueur.
     * @param menu        L'objet Menu contenant les éléments d'interface utilisateur.
     */
    public void waitForApprentiToMove(int targetX, int targetY, GameCanvas gameCanvas, Apprenti apprenti, Menu menu) {
        moveApprentiToTarget(targetX, targetY, gameCanvas, apprenti, menu);
        while (apprenti.isMoving() && apprenti.isRunning()) {
            try {
                Thread.sleep(WAIT_FOR_MOVE_SLEEP_DURATION);  // Attendre que le mouvement soit terminé
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Vérifie si tous les cristaux dans les temples sont triés.
     *
     * @param temples La liste des temples à vérifier.
     * @return true si tous les cristaux sont triés dans les temples, sinon false.
     */
    public boolean checkIfAllCrystalsSorted(List<Temple> temples) {
        for (Temple temple : temples) {
            if (temple.getColorCrystal() != temple.getColorTemple()) {
                return false;
            }
        }
        return true;
    }


    /**
     * La méthode deplacementUneCase déplace la position this d’une case
     * pour la rapprocher de celle du paramètre parPosition
     * elle incrémente le champ static nombreDePas.
     *
     * @param parPositionCible La position vers laquelle this se rapproche
     */
    public void deplacementUneCase(Position parPositionCible) {
        nombreDePas++;
        while (!this.equals(parPositionCible)) {
            if (this.abscisse > parPositionCible.abscisse) {
                this.abscisse--;
            } else if (this.abscisse < parPositionCible.abscisse) {
                this.abscisse++;
            }

            if (this.ordonnee > parPositionCible.ordonnee) {
                this.ordonnee--;
            } else if (this.ordonnee < parPositionCible.ordonnee) {
                this.ordonnee++;
            }
            try {
                Thread.sleep(100); // Réduisez ou augmentez cette valeur selon votre préférence
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtient le nombre total de pas effectués par l'apprenti.
     *
     * @return Le nombre de pas
     */
    public static int getNombreDePas() {
        return nombreDePas;
    }

    /**
     * Définit le nombre de pas effectués par l'apprenti.
     *
     * @param nmbrpas Le nouveau nombre de pas
     */
    public void setNombreDePas(int nmbrpas) {
        nombreDePas = nmbrpas;
    }

    /**
     * Obtient la coordonnée X de la position.
     *
     * @return La coordonnée X
     */
    public int getAbscisse() {
        return abscisse;
    }

    /**
     * Définit la coordonnée X de la position.
     *
     * @param x La nouvelle coordonnée X
     */
    public void setAbscisse(int x) {
        this.abscisse = x;
    }

    /**
     * Obtient la coordonnée Y de la position.
     *
     * @return La coordonnée Y
     */
    public int getOrdonnee() {
        return ordonnee;
    }

    /**
     * Définit la coordonnée Y de la position.
     *
     * @param y La nouvelle coordonnée Y
     */
    public void setOrdonnee(int y) {
        this.ordonnee = y;
    }

    /**
     * Vérifie si deux positions sont égales.
     *
     * @param obj L'objet à comparer
     * @return true si les positions sont égales, sinon false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position position = (Position) obj;
        return abscisse == position.abscisse &&
                ordonnee == position.ordonnee;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la position.
     *
     * @return Une chaîne de caractères représentant la position
     */
    @Override
    public String toString() {
        return "Position{" +
                "abscisse=" + abscisse +
                ", ordonnee=" + ordonnee +
                '}';
    }
}
