package model;

import constants.Constants;

/**
 * Classe représentant un Temple dans le jeu.
 */
public class Temple implements Constants {
    private int x;
    private int y;
    private int colorTemple;
    private int colorCrystal;
    private int index;
    public static int TEMPLE_EMPTY;

    /**
     * Constructeur de la classe Temple.
     *
     * @param x           La coordonnée x du temple.
     * @param y           La coordonnée y du temple.
     * @param colorTemple La couleur du temple.
     * @param colorCrystal La couleur du cristal.
     */
    public Temple(int x, int y, int colorTemple, int colorCrystal) {
        this.x = x;
        this.y = y;
        this.colorTemple = colorTemple;
        this.colorCrystal = colorCrystal;
        this.index = index;
    }


    // Getters et setters

    /**
     * Retourne l'index du temple.
     *
     * @return L'index du temple.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Retourne la position du temple.
     *
     * @return La position du temple.
     */
    public Position getPosition() {
        return new Position(x, y);
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColorTemple() {
        return colorTemple;
    }

    public void setColorTemple(int colorTemple) {
        this.colorTemple = colorTemple;
    }

    public int getColorCrystal() {
        return colorCrystal;
    }

    public void setColorCrystal(int colorCrystal) {
        this.colorCrystal = colorCrystal;
    }
}
