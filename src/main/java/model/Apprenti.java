package model;

import constants.Constants;

/**
 * Classe représentant l'Apprenti dans le jeu.
 */
public class Apprenti implements Constants {
    private int x;
    private int y;
    private int colorCrystal;
    private String sprite_sheet_apprenti;
    private boolean isMoving;
    private boolean running;

    /**
     * Constructeur de la classe Apprenti.
     *
     * @param x La coordonnée x de départ de l'apprenti.
     * @param y La coordonnée y de départ de l'apprenti.
     */
    public Apprenti(int x, int y) {
        this.x = x;
        this.y = y;
        this.colorCrystal = APPRENTI_NO_CRYSTAL;
        this.sprite_sheet_apprenti = APPRENTI_SPRITE_SHEET;
        this.isMoving = false;
        this.running = true;
    }


    // Getters et setters

    /**
     * Retourne la position de l'apprenti.
     *
     * @return La position de l'apprenti.
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

    public int getColorCrystal() {
        return colorCrystal;
    }

    public void setColorCrystal(int colorCrystal) {
        this.colorCrystal = colorCrystal;
    }

    public String getSprite_sheet_apprenti() {
        return sprite_sheet_apprenti;
    }

    public void setSprite_sheet_apprenti(String sprite_sheet_apprenti) {
        this.sprite_sheet_apprenti = sprite_sheet_apprenti;
    }


    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
