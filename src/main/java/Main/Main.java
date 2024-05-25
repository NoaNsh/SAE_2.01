package Main;

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


    public static void main(String[] args) {
        launch(args);
    }
}
