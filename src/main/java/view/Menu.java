package view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import model.Position;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe représentant le menu de l'interface graphique.
 * Cette classe contient des contrôles pour sélectionner des scénarios et des algorithmes,
 * ainsi qu'un bouton de réinitialisation, un label pour afficher les déplacements et une liste pour l'historique.
 */
public class Menu extends VBox {

    private ComboBox<String> scenarioComboBox;
    public ComboBox<String> algorithmComboBox;
    private Label deplacementsLabel;
    private Button resetButton; // Ajout du bouton reset
    private ListView<String> historyListView; // Liste pour afficher l'historique des événements
    private ChangeScenarioListener changeScenarioListener;
    private ChangeAlgorithmListener changeAlgorithmListener;
    private ResetListener resetListener; // Ajout du listener de reset

    /**
     * Constructeur de la classe Menu.
     * Initialise les éléments de l'interface du menu.
     */
    public Menu() {
        scenarioComboBox = new ComboBox<>();
        algorithmComboBox = new ComboBox<>();
        loadScenarioFiles();
        loadAlgorithms();

        scenarioComboBox.setOnAction(e -> changerScenario(scenarioComboBox.getValue()));
        algorithmComboBox.setOnAction(e -> changerAlgorithm(algorithmComboBox.getValue()));

        deplacementsLabel = new Label("Déplacements: " + Position.getNombreDePas());
        deplacementsLabel.setFont(new Font("Helvetica", 16));

        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            if (resetListener != null) {
                resetListener.onReset(); // Appel de la méthode reset du GameController
            }
        });

        historyListView = new ListView<>();
        historyListView.setPrefHeight(200);

        setSpacing(20);
        setPadding(new Insets(10));
        getChildren().addAll(deplacementsLabel, scenarioComboBox, algorithmComboBox, resetButton, historyListView);

        // Ajouter le fichier CSS
        File cssFile = new File("css/menu.css");
        getStylesheets().add(cssFile.toURI().toString());
        getStyleClass().add("root");
    }

    /**
     * Charge les fichiers de scénario disponibles et les ajoute au ComboBox des scénarios.
     */
    public void loadScenarioFiles() {
        File folder = new File("File");
        List<String> scenarios = Arrays.stream(folder.listFiles((dir, name) -> name.endsWith(".txt")))
                .map(File::getName)
                .collect(Collectors.toList());
        scenarioComboBox.getItems().addAll(scenarios);
        if (!scenarios.isEmpty()) {
            scenarioComboBox.setValue("Scénario:"); // Sélectionner le premier scénario par défaut
        }
    }

    /**
     * Charge les noms des algorithmes disponibles et les ajoute au ComboBox des algorithmes.
     */
    public void loadAlgorithms() {
        List<String> algorithms = Arrays.asList("Tri à bulle", "Tri par sélection", "Heuristique", "Optimal");
        algorithmComboBox.getItems().addAll(algorithms);
        if (!algorithms.isEmpty()) {
            algorithmComboBox.setValue("Algorithme:"); // Sélectionner le premier algorithme par défaut
        }
    }

    /**
     * Change le scénario en fonction de la sélection actuelle dans le ComboBox des scénarios.
     *
     * @param scenario Le nom du scénario sélectionné.
     */
    public void changerScenario(String scenario) {
        if (changeScenarioListener != null) {
            changeScenarioListener.onScenarioChange("File/" + scenario);
        }
    }

    /**
     * Change l'algorithme en fonction de la sélection actuelle dans le ComboBox des algorithmes.
     *
     * @param algorithm Le nom de l'algorithme sélectionné.
     */
    public void changerAlgorithm(String algorithm) {
        if (changeAlgorithmListener != null) {
            changeAlgorithmListener.onAlgorithmChange(algorithm);
        }
    }

    /**
     * Définit le listener à appeler lorsqu'un scénario est changé.
     *
     * @param listener L'instance du listener à appeler.
     */
    public void setChangeScenarioListener(ChangeScenarioListener listener) {
        this.changeScenarioListener = listener;
    }

    /**
     * Définit le listener à appeler lorsqu'un algorithme est changé.
     *
     * @param listener L'instance du listener à appeler.
     */
    public void setChangeAlgorithmListener(ChangeAlgorithmListener listener) {
        this.changeAlgorithmListener = listener;
    }

    /**
     * Définit le listener à appeler lorsque le bouton reset est cliqué.
     *
     * @param resetListener L'instance du listener à appeler.
     */
    public void setResetListener(ResetListener resetListener) {
        this.resetListener = resetListener;
    }

    /**
     * Met à jour l'affichage du nombre de déplacements.
     */
    public void updateDeplacements() {
        deplacementsLabel.setText("Déplacements: " + Position.getNombreDePas());
    }

    /**
     * Ajoute un événement à l'historique.
     *
     * @param event La description de l'événement à ajouter.
     */
    public void addEventToHistory(String event) {
        historyListView.getItems().add(event);
    }

    /**
     * Retourne le fichier de scénario sélectionné.
     *
     * @return Le chemin du fichier de scénario sélectionné ou null si aucun scénario n'est sélectionné.
     */
    public String getScenarioFile() {
        String selectedScenario = scenarioComboBox.getValue();
        if (selectedScenario != null && !selectedScenario.equals("Scénario:")) {
            return "File/" + selectedScenario;
        }
        return null;
    }

    /**
     * Interface pour écouter les changements de scénario.
     */
    public interface ChangeScenarioListener {
        void onScenarioChange(String scenarioFile);
    }

    /**
     * Interface pour écouter les changements d'algorithme.
     */
    public interface ChangeAlgorithmListener {
        void onAlgorithmChange(String algorithm);
    }

    /**
     * Interface pour écouter les réinitialisations.
     */
    public interface ResetListener {
        void onReset();
    }
}
