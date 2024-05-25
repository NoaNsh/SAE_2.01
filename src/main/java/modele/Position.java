package modele;

import ui.GameCanvas;

import java.util.Timer;
import java.util.TimerTask;

public class Position {
    private static int nombreDePas = 0;
    private int abscisse;
    private int ordonnee;
    private Apprenti apprenti;
    Scenario scenario = new Scenario();


    public Position(int abscisse, int ordonnee) {
        this.apprenti = new Apprenti(abscisse, ordonnee);

        this.abscisse = abscisse;
        this.ordonnee = ordonnee;

    }

    public void moveApprenti(char direction, GameCanvas canvas, Apprenti apprenti) {
        // Obtenez la nouvelle position de l'apprenti en fonction de la direction
        int newX = apprenti.getX();
        int newY = apprenti.getY();
        switch (direction) {
            case 'Z':
                newY -= 1; // Déplacement vers le haut
                break;
            case 'Q':
                newX -= 1; // Déplacement vers la gauche
                break;
            case 'S':
                newY += 1; // Déplacement vers le bas
                break;
            case 'D':
                newX += 1; // Déplacement vers la droite
                break;
        }

        // Mise à jour de la position de l'apprenti
        apprenti.setX(newX);
        apprenti.setY(newY);

        // Redessiner l'apprenti et les cristaux sur le canvas

        canvas.dessinerMoveApprenti(direction, apprenti.getX(), apprenti.getY(),1);

    }




    /**
     * La méthode deplacementUneCase déplace la position this d’une case
     * pour la rapprocher de celle du paramètre parPosition
     * elle incrémente le champ static nombreDePas
     *
     * @param parPosition : la position vers laquelle this se rapproche
     */
    public void deplacementUneCase(Position parPosition) {
        nombreDePas++;
        if (this.abscisse > parPosition.abscisse) {
            this.abscisse--;
            return;
        } else if (this.abscisse < parPosition.abscisse) {
            this.abscisse++;
            return;
        }

        if (this.ordonnee > parPosition.ordonnee) {
            this.ordonnee--;
        } else if (this.ordonnee < parPosition.ordonnee) {
            this.ordonnee++;
        }
    }

    public static int getNombreDePas() {
        return nombreDePas;
    }

    public int getAbscisse() {
        return abscisse;
    }

    public void setAbscisse(int x) {
        this.abscisse = x;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    public void setOrdonnee(int y) {
        this.ordonnee = y;
    }


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

    @Override
    public String toString() {
        return "Position{" +
                "abscisse=" + abscisse +
                ", ordonnee=" + ordonnee +
                '}';
    }
}
