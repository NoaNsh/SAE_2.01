package ui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import modele.Apprenti;
import modele.Temple;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class GameCanvas extends Canvas {
    private static final int CELL_SIZE = 32; // Taille de chaque case en pixels
    private Image tilesetImage;
    private int[][] tiles; // Tableau pour stocker les indices des tuiles
    private static final int TEMPLE_SIZE_X = 94; // Taille de chaque temple dans le sprite sheet
    private static final int TEMPLE_SIZE_Y = 72; // Taille de chaque temple dans le sprite sheet
    private Apprenti apprenti;
    private TilePane tilePane; // Déclaration de la variable tilePane

    public GameCanvas(double width, double height) {
        super(width, height);

        tilesetImage = new Image("file:imgs/img/Tileset_Grass.png");

    }

    // Attribut pour stocker la carte générée aléatoirement
    private int[][] generatedMap;

    // Méthode pour dessiner la grille et stocker la carte générée aléatoirement
    public void dessinerGrille(int rows, int cols, double zoomFactor) {
        Random random = new Random();
        GraphicsContext gc = getGraphicsContext2D();
        int centerX = (int) (getWidth() / 2); // Coordonnée X du point central
        int centerY = (int) (getHeight() / 2); // Coordonnée Y du point central

        // Initialiser le tableau pour stocker la carte générée aléatoirement
        generatedMap = new int[rows][cols];

        // Calculer les coordonnées de départ pour dessiner la grille autour du point central
        double startX = centerX - (cols / 2) * CELL_SIZE * zoomFactor;
        double startY = centerY - (rows / 2) * CELL_SIZE * zoomFactor;

        // Parcourir le tableau de tuiles et dessiner chaque tuile à la position correspondante
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tileIndex = random.nextInt(61); // Valeur aléatoire entre 0 et 61 (indice des tuiles)
                generatedMap[row][col] = tileIndex; // Stocker l'indice de la tuile dans le tableau
                int sourceX = (tileIndex % 8) * CELL_SIZE; // Coordonnée X de la tuile dans l'image
                int sourceY = (tileIndex / 8) * CELL_SIZE; // Coordonnée Y de la tuile dans l'image
                double targetX = startX + (int) (col * CELL_SIZE * zoomFactor); // Position X de la tuile sur le canvas
                double targetY = startY + (int) (row * CELL_SIZE * zoomFactor); // Position Y de la tuile sur le canvas
                int targetWidth = (int) (CELL_SIZE * zoomFactor); // Largeur de la tuile sur le canvas
                int targetHeight = (int) (CELL_SIZE * zoomFactor); // Hauteur de la tuile sur le canvas
                gc.drawImage(tilesetImage, sourceX, sourceY, CELL_SIZE, CELL_SIZE, targetX, targetY, targetWidth, targetHeight);
            }
        }
    }

    // Méthode pour réinitialiser la grille sans le personnage et les cristaux en utilisant la carte stockée
    public void resetGrid(int rows, int cols, double zoomFactor) {
        if (generatedMap != null) {
            GraphicsContext gc = getGraphicsContext2D();
            int centerX = (int) (getWidth() / 2); // Coordonnée X du point central
            int centerY = (int) (getHeight() / 2); // Coordonnée Y du point central

            // Calculer les coordonnées de départ pour dessiner la grille autour du point central
            double startX = centerX - (cols / 2) * CELL_SIZE * zoomFactor;
            double startY = centerY - (rows / 2) * CELL_SIZE * zoomFactor;

            // Parcourir le tableau de tuiles et dessiner chaque tuile à la position correspondante en utilisant la carte stockée
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int tileIndex = generatedMap[row][col]; // Récupérer l'indice de la tuile à partir de la carte stockée
                    int sourceX = (tileIndex % 8) * CELL_SIZE; // Coordonnée X de la tuile dans l'image
                    int sourceY = (tileIndex / 8) * CELL_SIZE; // Coordonnée Y de la tuile dans l'image
                    double targetX = startX + (int) (col * CELL_SIZE * zoomFactor); // Position X de la tuile sur le canvas
                    double targetY = startY + (int) (row * CELL_SIZE * zoomFactor); // Position Y de la tuile sur le canvas
                    int targetWidth = (int) (CELL_SIZE * zoomFactor); // Largeur de la tuile sur le canvas
                    int targetHeight = (int) (CELL_SIZE * zoomFactor); // Hauteur de la tuile sur le canvas
                    gc.drawImage(tilesetImage, sourceX, sourceY, CELL_SIZE, CELL_SIZE, targetX, targetY, targetWidth, targetHeight);
                }
            }
        }
    }






    // Méthode pour dessiner les temples
    public void dessinerTemples(List<Temple> temples, double zoomFactor) {
        GraphicsContext gc = getGraphicsContext2D();

        // Charger le sprite sheet des temples
        Image spriteSheet = new Image("file:imgs/img/color_temple_sprite_sheet.png");

        for (Temple temple : temples) {
            int x = temple.getX(); // Récupérer la position X du temple dans la grille
            int y = temple.getY(); // Récupérer la position Y du temple dans la grille

            // Convertir les coordonnées de la grille en pixels
            Point pixelCoordinates = convertGridCoordinatesToPixels(x, y,zoomFactor);

            int color = temple.getColorTemple();

            // Calculer les coordonnées source dans le sprite sheet en fonction de la couleur du temple
            int sourceX = (color-1) * TEMPLE_SIZE_X; // La largeur de chaque temple est la même dans le sprite sheet

            // Dessiner l'image du temple à l'emplacement spécifié avec le facteur de zoom
            int targetWidth = (int) (TEMPLE_SIZE_X * zoomFactor);
            int targetHeight = (int) (TEMPLE_SIZE_Y * zoomFactor);

            gc.drawImage(spriteSheet, sourceX, 0, TEMPLE_SIZE_X, TEMPLE_SIZE_Y, pixelCoordinates.x-CELL_SIZE, pixelCoordinates.y-20, targetWidth, targetHeight); // changer les coordonnées Y !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
    }





    // Méthode pour dessiner les cristaux
    public void dessinerCristaux(List<Temple> temples, double zoomFactor) {
        GraphicsContext gc = getGraphicsContext2D();

        // Parcourir chaque temple pour dessiner les cristaux correspondants
        for (Temple temple : temples) {
            int templeX = temple.getX();
            int templeY = temple.getY();
            int templeColor = temple.getColorTemple();
            int crystalColor = temple.getColorCrystal();

            // Vérifier si les couleurs du temple et du cristal correspondent
            if (templeColor == crystalColor) {
                // Dessiner le cristal animé
                String crystalAnimationFileName = getCrystalAnimationFileName(templeColor);
                Image crystalAnimation = new Image("file:imgs/img/" + crystalAnimationFileName);
                ImageView imageView = new ImageView(crystalAnimation);
                Point pixelCoordinates = convertGridCoordinatesToPixels(templeX, templeY, zoomFactor);
                imageView.setX(pixelCoordinates.x);
                imageView.setY(pixelCoordinates.y);
                imageView.setFitWidth(CELL_SIZE * zoomFactor);
                imageView.setFitHeight(CELL_SIZE * zoomFactor);
                // Récupérer la scène parente
                Scene scene = getScene();
                if (scene != null) {
                    Pane root = (Pane) scene.getRoot();
                    root.getChildren().add(imageView);
            }} else {
                // Dessiner le cristal non animé
                String crystalFileName = getCrystalFileName(templeColor);
                Image crystal = new Image("file:imgs/img/" + crystalFileName);
                ImageView imageView = new ImageView(crystal);
                Point pixelCoordinates = convertGridCoordinatesToPixels(templeX, templeY, zoomFactor);
                imageView.setX(pixelCoordinates.x);
                imageView.setY(pixelCoordinates.y);
                imageView.setFitWidth(CELL_SIZE * zoomFactor);
                imageView.setFitHeight(CELL_SIZE * zoomFactor);
                // Récupérer la scène parente
                Scene scene = getScene();
                if (scene != null) {
                    Pane root = (Pane) scene.getRoot(); // Ou un autre type Parent approprié
                    root.getChildren().add(imageView);
                }
            }
        }
    }

    // Méthode pour obtenir le nom de fichier du cristal animé en fonction de sa couleur
    private String getCrystalAnimationFileName(int color) {
        switch (color) {
            case 1: return "blue_crystal_animated.gif";
            case 2: return "purple_crystal_animated.gif";
            case 3: return "green_crystal_animated.gif";
            case 4: return "yellow_crystal_animated.gif";
            case 5: return "cyan_crystal_animated.gif";
            case 6: return "light_crystal_animated.gif";
            case 7: return "red_crystal_animated.gif";
            case 8: return "orange_crystal_animated.gif";
            case 9: return "dark_crystal_animated.gif";
            default: return "";
        }
    }

    // Méthode pour obtenir le nom de fichier du cristal statique en fonction de sa couleur
    private String getCrystalFileName(int color) {
        switch (color) {
            case 1: return "blue_crystal.gif";
            case 2: return "purple_crystal.gif";
            case 3: return "green_crystal.gif";
            case 4: return "yellow_crystal.gif";
            case 5: return "cyan_crystal.gif";
            case 6: return "light_crystal.gif";
            case 7: return "red_crystal.gif";
            case 8: return "orange_crystal.gif";
            case 9: return "dark_crystal.gif";
            default: return "";
        }
    }



    //à refaire pour un unique cristal

    // Méthode pour supprimer les cristaux du canvas
    public void supprimerCristaux(List<Temple> temples, double zoomFactor) {
        // Récupérer la scène parente
        Scene scene = getScene();
        if (scene != null) {
            Pane root = (Pane) scene.getRoot(); // Ou un autre type Parent approprié
            // Parcourir chaque temple pour supprimer les cristaux correspondants
            for (Temple temple : temples) {
                int templeX = temple.getX();
                int templeY = temple.getY();
                // Calculer les coordonnées du temple dans le canvas
                Point pixelCoordinates = convertGridCoordinatesToPixels(templeX, templeY, zoomFactor);
                // Parcourir les enfants du root pour trouver et supprimer les cristaux
                root.getChildren().removeIf(node -> {
                    if (node instanceof ImageView) {
                        ImageView imageView = (ImageView) node;
                        return imageView.getX() == pixelCoordinates.x && imageView.getY() == pixelCoordinates.y;
                    }
                    return false;
                });
            }
        }
    }



    // Méthode pour dessiner l'apprenti
    public void dessinerApprenti(Apprenti apprenti, double zoomFactor) {
        GraphicsContext gc = getGraphicsContext2D();


        int x = apprenti.getX(); // Récupérer la position X de l'apprenti dans la grille
        int y = apprenti.getY(); // Récupérer la position Y de l'apprenti dans la grille

        // Convertir les coordonnées de la grille en pixels
        Point pixelCoordinates = convertGridCoordinatesToPixels(x, y,zoomFactor );

        // Charger l'image de l'apprenti
        Image playerImage = new Image("file:imgs/img/player.png");

        // Dessiner l'image à la position spécifiée avec le facteur de zoom
        int targetWidth = (int) (CELL_SIZE * 2 * zoomFactor*2);
        int targetHeight = (int) (CELL_SIZE * zoomFactor*2);
        gc.drawImage(playerImage, pixelCoordinates.x-50, pixelCoordinates.y-CELL_SIZE, targetWidth, targetHeight); // changer les coordonnées X !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }



    public void dessinerMoveApprenti(char direction, int x, int y,double zoomFactor) {
        GraphicsContext gc = getGraphicsContext2D();


        // Charger l'image de l'animation de déplacement de l'apprenti
        Image moveAnimation = new Image("file:imgs/img/sprite_sheet_apprenti.png");
        int spriteSizeX = 204; // Largeur d'un sprite dans l'animation
        int spriteSizeY = 108; // Hauteur d'un sprite dans l'animation
        int spriteX = 0; // Coordonnée X du sprite dans l'animation
        int spriteY = 0; // Coordonnée Y du sprite dans l'animation

        switch (direction) {
            case 'Z':
                spriteY = 324; //Coordonnée Y pour le déplacement vers le haut
                break;
            case 'Q':
                spriteY = 0; //Coordonnée Y pour le déplacement vers la gauche
                break;
            case 'S':
                spriteY = 216; //Coordonnée Y pour le déplacement vers le bas
                break;
            case 'D':
                spriteY = 108; //Coordonnée Y pour le déplacement vers la droite
                break;
            case 'E':
                // Action spéciale (échange de cristaux)
                break;
        }
        System.out.println(spriteY);
        Point pixelCoordinates = convertGridCoordinatesToPixels(x,y,zoomFactor);
        System.out.println(pixelCoordinates.x+" "+pixelCoordinates.y);
        // Dessiner le sprite de l'animation de déplacement de l'apprenti
        gc.drawImage(moveAnimation, spriteX, spriteY, spriteSizeX, spriteSizeY,
                pixelCoordinates.x, pixelCoordinates.y, spriteSizeX * zoomFactor, spriteSizeY * zoomFactor);
    }


    // Méthode pour gérer l'action spéciale de l'apprenti
    public void actionSpeciale() {

    }


    // Méthode pour convertir les coordonnées de la grille en pixels
    public Point convertGridCoordinatesToPixels(int gridX, int gridY, double zoomFactor) {
        int centerX = (int) (getWidth() / 2); // Coordonnée X du centre de l'écran
        int centerY = (int) (getHeight() / 2); // Coordonnée Y du centre de l'écran


        // Calculer les coordonnées en pixels du point correspondant sur la grille
        int pixelX =  (int) (centerX + CELL_SIZE * gridX *zoomFactor );
        int pixelY =  (int) (centerY + CELL_SIZE * gridY *zoomFactor);

        return new Point(pixelX, pixelY);
    }

}
