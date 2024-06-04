package ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modele.Apprenti;
import modele.Position;
import modele.Scenario;
import modele.Temple;
import resolution.AlgorithmeHeuristique;
import resolution.AlgorithmeOptimal;
import resolution.AlgorithmeTri;
import resolution.ZoneResultats;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class InterfaceGraphique {
    private ZoneResultats zoneResultats;
    private AlgorithmeTri algorithmeTri;
    private AlgorithmeHeuristique algorithmeHeuristique;
    private AlgorithmeOptimal algorithmeOptimal;
    private GameCanvas canvas;
    double zoomFactor = 1;
    Apprenti apprenti = new Apprenti(0, 0);
    Position position = new Position(apprenti.getX(), apprenti.getY());
    Scenario scenario = new Scenario();

    private void effectuerDeplacements(String deplacements, int index) {
        if (index < deplacements.length()) {
            char direction = deplacements.charAt(index);
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
            pause.setOnFinished(event -> {
                // Réinitialisation de la grille après le délai
                canvas.resetGrid(31, 31, zoomFactor);
                canvas.dessinerTemples(scenario.getTemples(), zoomFactor);
                position.moveApprenti(direction, canvas, apprenti);

                effectuerDeplacements(deplacements, index + 1); // Appel récursif pour le prochain mouvement
            });
            pause.play();
        }
    }
    public void show(Stage primaryStage) {
        // Initialiser le canvas et définir le facteur de zoom
        canvas = new GameCanvas(1920, 1080);


        // Créer la racine de la scène
        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        // Créer la scène principale et la lier à la fenêtre principale
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("L'Apprenti Ordonnateur");
        primaryStage.show();

        // Charger le scénario
        scenario.fileLoader("File/scenariotest.txt");

        // Dessiner les éléments sur le canvas
        canvas.dessinerGrille(31, 31, zoomFactor); // Dessiner une grille de 31×31 cases
        canvas.dessinerTemples(scenario.getTemples(), zoomFactor);
        canvas.dessinerCristaux(scenario.getTemples(), zoomFactor);

        // Créer un apprenti et le dessiner sur le canvas

        canvas.dessinerApprenti(apprenti, zoomFactor);

        // Créer une instance de Position pour l'apprenti

        apprenti.setX(position.getAbscisse());
        apprenti.setY(position.getOrdonnee());

        // Chaîne de déplacements
        String deplacements = "ZZZZZZQQQQQZ"; // TEST

        effectuerDeplacements(deplacements, 0);
    }


    // Setters pour associer les composants avec les algorithmes
    public void setZoneResultats(ZoneResultats zoneResultats) {
        this.zoneResultats = zoneResultats;
    }

    public void setAlgorithmeTri(AlgorithmeTri algorithmeTri) {
        this.algorithmeTri = algorithmeTri;
    }

    public void setAlgorithmeHeuristique(AlgorithmeHeuristique algorithmeHeuristique) {
        this.algorithmeHeuristique = algorithmeHeuristique;
    }

    public void setAlgorithmeOptimal(AlgorithmeOptimal algorithmeOptimal) {
        this.algorithmeOptimal = algorithmeOptimal;
    }
}
