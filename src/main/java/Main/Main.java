package Main;

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

    public static void main(String[] args) {
        launch(args);
    }
}
