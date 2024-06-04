package Main;

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 2b700b00f50ba9b3ad6bd3a111baf5dff187d07e
import controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Apprenti;
import model.Position;
import model.Scenario;
import view.GameCanvas;
import view.InterfaceGraphique;
import view.Menu;
import constants.Constants;

public class Main extends Application implements Constants {

    @Override
    public void start(Stage primaryStage) {
        // Utilisation des constantes
        int canvasWidth = CANVAS_WIDTH;
        int canvasHeight = CANVAS_HEIGHT;
        String scenarioFile = SCENARIO_FILE;
        double zoomFactor = ZOOM_FACTOR;

        // Charger le scénario
        Scenario scenario = new Scenario();
        scenario.fileLoader(scenarioFile);

        // Initialiser le GameController et l'associer à l'interface graphique
        Apprenti apprenti = new Apprenti(APPRENTI_START_X, APPRENTI_START_Y);
        Position position = new Position(apprenti);

        Menu menu = new Menu();
        // Initialiser le GameCanvas avec les paramètres
        GameCanvas gameCanvas = new GameCanvas(canvasWidth, canvasHeight, scenario.getTemples(), apprenti,menu);

        // Initialiser les composants de l'interface utilisateur
        InterfaceGraphique interfaceGraphique = new InterfaceGraphique(gameCanvas,menu);
        interfaceGraphique.show(primaryStage);

        GameController gameController = new GameController(gameCanvas, position, apprenti, menu, scenario);

        // Dessiner les éléments sur le canvas
        gameCanvas.dessinerGrille(GRID_WIDTH, GRID_HEIGHT, zoomFactor);
        gameCanvas.dessinerTemples(scenario.getTemples(), zoomFactor);
        gameCanvas.dessinerCristaux(scenario.getTemples(), zoomFactor);
        gameCanvas.dessinerApprenti(apprenti, zoomFactor);

        // Lier le contrôleur aux événements du clavier
        Scene scene = primaryStage.getScene();
        scene.setOnKeyPressed(event -> gameController.handleInput(event));

<<<<<<< HEAD

// ancien systeme d'actualisation
//        // Boucle principale du jeu
//        new Thread(() -> {
//            while (true) {
//                // Mettre à jour la carte et le joueur
//                //mettre une méthode qui redessine tout ici
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

=======
        // Boucle principale du jeu
        new Thread(() -> {
            while (true) {
                // Mettre à jour la carte et le joueur
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

=======
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modele.Apprenti;
import modele.Position;
import modele.Scenario;
import resolution.*;
import ui.GameCanvas;
import ui.InterfaceGraphique;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialiser les composants de l'interface utilisateur
        InterfaceGraphique interfaceGraphique = new InterfaceGraphique();
        ZoneResultats zoneResultats = new ZoneResultats();

        // Initialiser le scénario et l'apprenti
        Scenario scenario = new Scenario();
        scenario.fileLoader("File/scenario2.txt");

        // Initialiser les algorithmes
        AlgorithmeTri algorithmeTri = new TriPerso();
        AlgorithmeHeuristique algorithmeHeuristique = new HeuristiquePerso();
        AlgorithmeOptimal algorithmeOptimal = new OptimalPerso(); // Implémentez cet algorithme

        // Associer les composants à l'interface graphique
        interfaceGraphique.setZoneResultats(zoneResultats);

        // Associer les algorithmes à l'interface graphique
        interfaceGraphique.setAlgorithmeTri(algorithmeTri);
        interfaceGraphique.setAlgorithmeHeuristique(algorithmeHeuristique);
        interfaceGraphique.setAlgorithmeOptimal(algorithmeOptimal);

        // Afficher l'interface graphique
        interfaceGraphique.show(primaryStage);
    }


>>>>>>> a9eee1976180a830caf2dc8b5f1d68467f846181
>>>>>>> 2b700b00f50ba9b3ad6bd3a111baf5dff187d07e
    public static void main(String[] args) {
        launch(args);
    }
}
