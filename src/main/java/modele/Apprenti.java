package modele;

public class Apprenti {
    private int x;
    private int y;
    private int colorCrystal;

    public Apprenti(int x, int y) {
        this.x = 0; // Position de départ
        this.y = 0;
        this.colorCrystal = -1; // Pas de cristal au départ
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }


    // Getters et setters pour accéder aux attributs

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
}
