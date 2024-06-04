package modele;

public class Temple {
    private int x;
    private int y;
    private int colorTemple;
    private int colorCrystal;
    public static final int EMPTY = -1;
    public Temple(int x, int y, int colorTemple, int colorCrystal) {
        this.x = x;
        this.y = y;
        this.colorTemple = colorTemple;
        this.colorCrystal = colorCrystal;
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
