package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import model.Apprenti;
import model.Temple;
import constants.Constants;
import java.awt.Point;
import java.util.List;
import java.util.Random;
import view.Menu;

/**
 * La classe GameCanvas représente le canevas de jeu où la carte, les temples, les cristaux et l'apprenti sont dessinés.
 * Elle extends la classe Canvas de JavaFX pour fournir des fonctionnalités de dessin.
 */
public class GameCanvas extends Canvas implements Constants {
    private Image tilesetImage;
    private Image wallLeftImage;
    private Image wallRightImage;
    private Image wallHorizontalImage;
    private Image background;
    private int[][] tiles; // Tableau pour stocker les indices des tuiles
    private Apprenti apprenti;
    private TilePane tilePane; // Déclaration de la variable tilePane
    private List<Temple> temples;
    private Menu menu;
    GraphicsContext gc = getGraphicsContext2D();
    // Attribut pour stocker la carte générée aléatoirement
    private int[][] generatedMap;
    private int[][] wallsMap;

    /**
     * Constructeur de la classe GameCanvas.
     *
     * @param width   Largeur du canvas de jeu.
     * @param height  Hauteur du canvas de jeu.
     * @param temples Liste des temples à afficher sur le canvas.
     * @param apprenti L'apprenti à afficher sur le canvas.
     */
    public GameCanvas(double width, double height, List<Temple> temples, Apprenti apprenti, Menu menu) {
        super(width, height);
        tilesetImage = new Image("file:imgs/img/Tileset_Grass.png");
        wallLeftImage = new Image("file:imgs/img/wall_left.png");
        wallRightImage = new Image("file:imgs/img/wall_right.png");
        wallHorizontalImage = new Image("file:imgs/img/wall_horizontal.png");
        background = new Image("file:imgs/img/background.jpg");
        this.apprenti = apprenti;
        this.temples = temples;
        this.menu = menu;
    }

    /**
     * Méthode pour dessiner la grille et stocker la carte générée aléatoirement.
     *
     * @param rows       Nombre de lignes dans la grille.
     * @param cols       Nombre de colonnes dans la grille.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin de la grille.
     */

    public void dessinerGrille(int rows, int cols, double zoomFactor) {
        // Afficher le fond
        gc.drawImage(background, 0, 0, getWidth(), getHeight());

        Random random = new Random();
        int centerX = (int) (getWidth() / 2); // Coordonnée X du point central
        int centerY = (int) (getHeight() / 2); // Coordonnée Y du point central

        // Initialiser le tableau pour stocker la carte générée aléatoirement
        generatedMap = new int[rows][cols];
        wallsMap = new int[rows + 2][cols + 2]; // +2 pour inclure les murs extérieurs

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

        // Générer et dessiner les murets
        generateAndDrawWalls(rows, cols, zoomFactor, random, startX, startY);
    }


    /**
     * Génère et dessine les murets autour de la grille, en stockant les positions des murets
     * dans un tableau pour garantir qu'ils restent constants à chaque réinitialisation.
     *
     * @param rows       Nombre de lignes dans la grille.
     * @param cols       Nombre de colonnes dans la grille.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin des murets.
     * @param random     Instance de Random pour générer des portions aléatoires des murets.
     * @param startX     Coordonnée X de départ pour dessiner la grille.
     * @param startY     Coordonnée Y de départ pour dessiner la grille.
     */
    private void generateAndDrawWalls(int rows, int cols, double zoomFactor, Random random, double startX, double startY) {
        // Générer les murets et stocker dans wallsMap
        for (int row = 0; row < rows + 2; row++) {
            // Mur gauche
            if (row > 0 && row < rows + 1) {
                wallsMap[row][0] = random.nextInt(4) * CELL_SIZE; // Portion aléatoire
            }
            // Mur droit
            if (row > 0 && row < rows + 1) {
                wallsMap[row][cols + 1] = random.nextInt(4) * CELL_SIZE; // Portion aléatoire
            }
        }

        for (int col = 0; col < cols + 2; col++) {
            // Mur haut
            if (col > 0 && col < cols + 1) {
                wallsMap[0][col] = random.nextInt(4) * CELL_SIZE; // Portion aléatoire
            }
            // Mur bas
            if (col > 0 && col < cols + 1) {
                wallsMap[rows + 1][col] = random.nextInt(4) * CELL_SIZE; // Portion aléatoire
            }
        }

        // Dessiner les murets
        drawWalls(rows, cols, zoomFactor, startX, startY);
    }


    /**
     * Dessine les murets autour de la grille en utilisant les positions stockées
     * dans wallsMap, pour garantir que les murets restent constants à chaque réinitialisation.
     *
     * @param rows       Nombre de lignes dans la grille.
     * @param cols       Nombre de colonnes dans la grille.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin des murets.
     * @param startX     Coordonnée X de départ pour dessiner la grille.
     * @param startY     Coordonnée Y de départ pour dessiner la grille.
     */
    private void drawWalls(int rows, int cols, double zoomFactor, double startX, double startY) {
        for (int row = 0; row < rows + 2; row++) {
            // Mur gauche
            if (row > 0 && row < rows + 1) {
                double targetX = startX - CELL_SIZE * zoomFactor;
                double targetY = startY + ((row - 1) * CELL_SIZE * zoomFactor);
                gc.drawImage(wallLeftImage, 0, wallsMap[row][0], WALL_SIZE_WIDTH, CELL_SIZE, targetX + CELL_SIZE, targetY, WALL_SIZE_WIDTH * zoomFactor, CELL_SIZE * zoomFactor);
            }
            // Mur droit
            if (row > 0 && row < rows + 1) {
                double targetX = startX + (cols * CELL_SIZE * zoomFactor);
                double targetY = startY + ((row - 1) * CELL_SIZE * zoomFactor);
                gc.drawImage(wallRightImage, 0, wallsMap[row][cols + 1], WALL_SIZE_WIDTH, CELL_SIZE, targetX - WALL_SIZE_WIDTH, targetY, WALL_SIZE_WIDTH * zoomFactor, CELL_SIZE * zoomFactor);
            }
        }

        for (int col = 0; col < cols + 2; col++) {
            // Mur haut
            if (col > 0 && col < cols + 1) {
                double targetX = startX + ((col - 1) * CELL_SIZE * zoomFactor);
                double targetY = startY - CELL_SIZE * zoomFactor;
                gc.drawImage(wallHorizontalImage, wallsMap[0][col], 0, CELL_SIZE, WALL_SIZE_WIDTH, targetX, targetY + CELL_SIZE, CELL_SIZE * zoomFactor, WALL_SIZE_WIDTH * zoomFactor);
            }
            // Mur bas
            if (col > 0 && col < cols + 1) {
                double targetX = startX + ((col - 1) * CELL_SIZE * zoomFactor);
                double targetY = startY + (rows * CELL_SIZE * zoomFactor);
                gc.drawImage(wallHorizontalImage, wallsMap[rows + 1][col], 0, CELL_SIZE, WALL_SIZE_WIDTH, targetX, targetY - WALL_SIZE_WIDTH, CELL_SIZE * zoomFactor, WALL_SIZE_WIDTH * zoomFactor);
            }
        }
    }


    /**
     * Méthode pour réinitialiser la grille pour recharger l'image et effacer les anciens déplacements.
     *
     * @param rows       Nombre de lignes dans la grille.
     * @param cols       Nombre de colonnes dans la grille.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin de la grille.
     */
    public void resetGrid(int rows, int cols, double zoomFactor) {
        if (generatedMap != null) {
            // Afficher le fond
            gc.drawImage(background, 0, 0, getWidth(), getHeight());

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

            // Dessiner les murets stockés
            drawWalls(rows, cols, zoomFactor, startX, startY);
        }
    }



/**
     * Méthode pour dessiner les temples sur le canvas.
     *
     * @param temples    Liste des temples à dessiner.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin.
     */
    public void dessinerTemples(List<Temple> temples, double zoomFactor) {

        // Charger le sprite sheet des temples
        Image spriteSheet = new Image("file:imgs/img/color_temple_sprite_sheet.png");
        Image templeShadow = new Image("file:imgs/img/Temple_Shadow.png");


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


            gc.drawImage(templeShadow, pixelCoordinates.x-CELL_SIZE, pixelCoordinates.y-20, TEMPLE_SIZE_X+2 * zoomFactor, targetHeight);
            gc.drawImage(spriteSheet, sourceX, 0, TEMPLE_SIZE_X, TEMPLE_SIZE_Y, pixelCoordinates.x-CELL_SIZE, pixelCoordinates.y-20, targetWidth, targetHeight);

        }
    }

    public void dessinerbackground(){

    }



    /**
     * Méthode pour dessiner les cristaux sur le canvas.
     *
     * @param temples    Liste des temples où les cristaux sont placés.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin.
     */
    public void dessinerCristaux(List<Temple> temples, double zoomFactor) {

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
                    BorderPane root = (BorderPane) scene.getRoot();
                    root.getChildren().add(imageView);
            }} else {
                // Dessiner le cristal non animé
                String crystalFileName = getCrystalFileName(crystalColor);
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
                    BorderPane root = (BorderPane ) scene.getRoot(); // Ou un autre type Parent approprié
                    root.getChildren().add(imageView);
                }
            }
        }
    }

    /**
     * Méthode pour obtenir le nom de fichier du cristal animé (donc dans le bon temple) en fonction de sa couleur.
     *
     * @param color Couleur du cristal.
     * @return Le nom de fichier du cristal animé correspondant à la couleur.
     */
    public String getCrystalAnimationFileName(int color) {
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

    /**
     * Méthode pour obtenir le nom de fichier du cristal statique en fonction de sa couleur.
     *
     * @param color Couleur du cristal.
     * @return Le nom de fichier du cristal statique correspondant à la couleur.
     */
    public String getCrystalFileName(int color) {
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




    /**
     * Méthode pour supprimer les cristaux du canvas. (appelé pour actualisé les crystaux sur la map)
     *
     * @param temples    Liste des temples où les cristaux sont placés.
     * @param zoomFactor Facteur de zoom à appliquer lors de la suppression des cristaux.
     */
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



    /**
     * Méthode pour dessiner l'apprenti sur le canvas.
     *
     * @param apprenti   L'apprenti à dessiner.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin de l'apprenti.
     */
    public void dessinerApprenti(Apprenti apprenti, double zoomFactor) {

        int x = apprenti.getX(); // Récupérer la position X de l'apprenti dans la grille
        int y = apprenti.getY(); // Récupérer la position Y de l'apprenti dans la grille

        // Convertir les coordonnées de la grille en pixels
        Point pixelCoordinates = convertGridCoordinatesToPixels(x, y,zoomFactor );

        // Charger l'image de l'apprenti
        Image playerImage = new Image("file:imgs/img/Apprenti.png");

        // Dessiner l'image à la position spécifiée avec le facteur de zoom
        int targetWidth = (int) (CELL_SIZE * 2 * zoomFactor*2);
        int targetHeight = (int) (CELL_SIZE * zoomFactor*2);

        gc.drawImage(playerImage, pixelCoordinates.x-SPRITE_SIZE_X/4, pixelCoordinates.y-(CELL_SIZE-CELL_SIZE/4), targetWidth, targetHeight);

    }



    /**
     * Méthode pour dessiner le déplacement de l'apprenti sur le canvas en y implémentant une animation.
     *
     * @param direction  Direction du déplacement.
     * @param oldX       Ancienne position X de l'apprenti.
     * @param oldY       Ancienne position Y de l'apprenti.
     * @param x          Nouvelle position X de l'apprenti.
     * @param y          Nouvelle position Y de l'apprenti.
     * @param zoomFactor Facteur de zoom à appliquer lors du dessin.
     */
    public void dessinerMoveApprenti(char direction, int oldX,int oldY ,int x, int y, double zoomFactor) {


        int targetWidth = (int) (CELL_SIZE * 2 * zoomFactor * 2);
        int targetHeight = (int) (CELL_SIZE * zoomFactor * 2);
        int speed = SPEED_ANIMATION; //vitesse de l'animation



        final int spriteY;
        switch (direction) {
            case DIRECTION_UP: spriteY = SPRITE_SIZE_Y * 3; break; // Haut
            case DIRECTION_LEFT: spriteY = 0; break; // Gauche
            case DIRECTION_DOWN: spriteY = SPRITE_SIZE_Y * 2; break; // Bas
            case DIRECTION_RIGHT: spriteY = SPRITE_SIZE_Y; break; // Droite
            case SWITCH_CRYSTAL: // Action spéciale (échange de cristaux)
                switchCrystal(x, y);
                supprimerCristaux(temples, zoomFactor);
                dessinerCristaux(temples, zoomFactor);
                spriteY = 0;
                break;
            default: spriteY = 0; // Default
        }
        getApprentiSpriteSheetPath(apprenti.getColorCrystal());
        String spriteSheetPath = apprenti.getSprite_sheet_apprenti();
        Image moveAnimation = new Image("file:imgs/img/" + spriteSheetPath);

        final int startX = oldX;
        final int startY = oldY;
        final int endX = x;
        final int endY = y;


        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);

        for (int i = 0; i < 3; i++) {
            final int spriteX = i * SPRITE_SIZE_X; // Animation des frames (1/3, 2/3, 0)
            double t = (i + 1) / 3.0;

            final int offsetX = (int) ((endX - startX) * CELL_SIZE * t);
            final int offsetY = (int) ((endY - startY) * CELL_SIZE * t);

            final Point startPixelCoordinates = convertGridCoordinatesToPixels(startX, startY, zoomFactor);

            KeyFrame keyFrame = new KeyFrame(Duration.millis(speed * (i + 1)), event -> {
                gc.clearRect(0, 0, getWidth(), getHeight());
                resetGrid(GRID_WIDTH, GRID_HEIGHT, zoomFactor);
                dessinerTemples(temples, zoomFactor);
                gc.drawImage(moveAnimation, spriteX, spriteY, SPRITE_SIZE_X, SPRITE_SIZE_Y,
                        startPixelCoordinates.x + offsetX - SPRITE_SIZE_X / 4,
                        startPixelCoordinates.y + offsetY - (CELL_SIZE - CELL_SIZE / 4),
                        targetWidth, targetHeight);
            });

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(event -> {
            apprenti.setX(endX);
            apprenti.setY(endY);
            final Point pixelCoordinates = convertGridCoordinatesToPixels(endX, endY, zoomFactor);
            gc.clearRect(0, 0, getWidth(), getHeight());
            resetGrid(GRID_WIDTH, GRID_HEIGHT, zoomFactor);
            dessinerTemples(temples, zoomFactor);
            gc.drawImage(moveAnimation, 0, spriteY, SPRITE_SIZE_X, SPRITE_SIZE_Y,
                    pixelCoordinates.x - SPRITE_SIZE_X / 4, pixelCoordinates.y - (CELL_SIZE - CELL_SIZE / 4), targetWidth, targetHeight);
        });

        timeline.play();

    }







    /**
     * Méthode pour définir le path du sprite sheet de l'apprenti en fonction de la couleur du cristal.
     *
     * @param crystalColor Couleur du cristal.
     */
    public void getApprentiSpriteSheetPath(int crystalColor) {
        switch (crystalColor) {
            case 1:
                apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_blue.png");
                break;
            case 2: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_purple.png");
                break;
            case 3: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_green.png");
                break;
            case 4: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_yellow.png");
                break;
            case 5: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_cyan.png");
                break;
            case 6: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_light.png");
                break;
            case 7: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_red.png");
                break;
            case 8: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_orange.png");
                break;
            case 9: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti_dark.png");
                break;
            default: apprenti.setSprite_sheet_apprenti("sprite_sheet_apprenti.png"); // Sprite par défaut si la couleur du cristal est -1 donc il n'y en a pas
                break;

        }
    }

    /**
     * Méthode pour échanger le cristal entre l'apprenti et le temple.
     *
     * @param x Position X du temple.
     * @param y Position Y du temple.
     */
    public void switchCrystal(int x, int y) {
        // Récupérer le temple à la position spécifiée
        Temple temple = getTempleAtPosition(x, y);

        if (temple != null) {

            int templeColor = temple.getColorCrystal();

            // Vérifier si l'apprenti n'a pas de cristal
            if (apprenti.getColorCrystal() == Temple.TEMPLE_EMPTY) {

                // L'apprenti prend le cristal du temple
                apprenti.setColorCrystal(templeColor);
                temple.setColorCrystal(Temple.TEMPLE_EMPTY);
                Platform.runLater(() -> {menu.addEventToHistory("Cristal ramassé : " + templeColor);});

            } else {

                // L'apprenti échange son cristal avec celui du temple
                int apprentiColor = apprenti.getColorCrystal();
                apprenti.setColorCrystal(templeColor);
                temple.setColorCrystal(apprentiColor);
                Platform.runLater(() -> {menu.addEventToHistory("Cristal ramassé : " + templeColor);
                menu.addEventToHistory("Cristal déposé : " + apprentiColor);
                });

            }
        }
    }



    /**
     * Méthode pour obtenir le temple situé à une position spécifiée sur la grille.
     *
     * @param x Position X du temple.
     * @param y Position Y du temple.
     * @return Le temple situé à la position spécifiée, null si aucun temple n'est trouvé.
     */
    public Temple getTempleAtPosition(int x, int y) {

        for (Temple temple : temples) {

            if (temple.getX() == x && temple.getY() == y) {
                return temple;
            }
        }
        return null;
    }



    /**
     * Méthode pour convertir les coordonnées de la grille en pixels.
     *
     * @param gridX      Coordonnée X sur la grille.
     * @param gridY      Coordonnée Y sur la grille.
     * @param zoomFactor Facteur de zoom à appliquer.
     * @return Les coordonnées en pixels du point correspondant sur la grille.
     */
    public Point convertGridCoordinatesToPixels(int gridX, int gridY, double zoomFactor) {
        int centerX = (int) (getWidth() / 2); // Coordonnée X du centre de l'écran
        int centerY = (int) (getHeight() / 2); // Coordonnée Y du centre de l'écran


        // Calculer les coordonnées en pixels du point correspondant sur la grille
        int pixelX =  (int) (centerX + CELL_SIZE * gridX *zoomFactor );
        int pixelY =  (int) (centerY + CELL_SIZE * gridY *zoomFactor);

        return new Point(pixelX, pixelY);
    }

    /**
     * Méthode inverse pour convertir les coordonnées en pixels en coordonnées de la grille.
     *
     * @param pixelX     Position X en pixels.
     * @param pixelY     Position Y en pixels.
     * @param zoomFactor Facteur de zoom à appliquer.
     * @return Les coordonnées de la grille correspondant aux pixels spécifiés.
     */
    public Point convertPixelsToGridCoordinates(double pixelX, double pixelY, double zoomFactor) {
        int centerX = (int) (getWidth() / 2); // Coordonnée X du centre de l'écran
        int centerY = (int) (getHeight() / 2); // Coordonnée Y du centre de l'écran

        // Calculer les coordonnées de la grille à partir des pixels
        int gridX = (int) Math.round((pixelX - centerX) / (CELL_SIZE * zoomFactor)-0.49); //0,49 pour arrondir au plus bas
        int gridY = (int) Math.round((pixelY - centerY) / (CELL_SIZE * zoomFactor)-0.49);


        return new Point(gridX, gridY);
    }

    /**
     * Méthode pour effacer le canvas. entierement
     */
    public void clear() {
        gc.clearRect(0, 0, getWidth(), getHeight());

    }
}
