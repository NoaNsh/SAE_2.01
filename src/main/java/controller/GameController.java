package controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.*;
import view.GameCanvas;
import view.Menu;
import java.awt.Point;
import java.util.List;
import constants.Constants;

/**
 * La classe GameController gère les interactions utilisateur et contrôle le déroulement du jeu.
 */
public class GameController implements Constants {

    private GameCanvas gameCanvas;
    private Position position;
    private Apprenti apprenti;
    private Menu menu;
    private Scenario scenario;

    /**
     * Constructeur de la classe GameController.
     *
     * @param gameCanvas Le canevas du jeu.
     * @param position La position actuelle du joueur.
     * @param apprenti L'apprenti du joueur.
     * @param menu Le menu du jeu.
     * @param scenario Le scénario du jeu.
     */
    public GameController(GameCanvas gameCanvas, Position position, Apprenti apprenti, Menu menu, Scenario scenario) {
        this.gameCanvas = gameCanvas;
        this.position = position;
        this.apprenti = apprenti;
        this.menu = menu;
        this.scenario = scenario;
        this.menu.setChangeScenarioListener(this::loadScenario);
        this.menu.setChangeAlgorithmListener(this::loadAlgorithm);
        this.menu.setResetListener(this::reset);

        setupMouseEvents();
    }

    /**
     * Met en place les événements de la souris pour le canvas.
     */
    public void setupMouseEvents() {
        gameCanvas.setOnMouseClicked(event -> {
            Point gridCoordinates = gameCanvas.convertPixelsToGridCoordinates(event.getX(), event.getY(), ZOOM_FACTOR);
            int targetX = gridCoordinates.x;
            int targetY = gridCoordinates.y;
            // Vérifier si les nouvelles coordonnées sont à l'intérieur de la grille //en considérant que la grille sera toujours impaire (pour le 0,0 au centre)
            if (!(targetX < -(GRID_WIDTH/2) || targetX >= GRID_WIDTH/2+1 || targetY < -(GRID_HEIGHT/2) || targetY >= GRID_HEIGHT/2 + 1)) {
                menu.addEventToHistory("Déplacement vers : (" + targetX + ", " + targetY + ")");
                new Thread(() -> {
                    position.moveApprentiToTarget(targetX, targetY, gameCanvas, apprenti, menu);
                    Platform.runLater(() -> {
                        boolean sorted = false;
                        sorted = position.checkIfAllCrystalsSorted(scenario.getTemples());
                        if (sorted) {
                            menu.addEventToHistory("Cristaux triés !");
                        }});
                }).start();
            }
        });
    }


    /**
     * Gère les événements de saisie clavier.
     *
     * @param event L'événement de saisie clavier.
     */
    public void handleInput(KeyEvent event) {
        if (apprenti.isMoving() || !apprenti.isRunning()) return;

        KeyCode code = event.getCode();
        apprenti.setMoving(true);
        if (code == KeyCode.UP || code == KeyCode.Z || code == KeyCode.W) {
            position.moveApprenti(DIRECTION_UP, gameCanvas, apprenti);
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            position.moveApprenti(DIRECTION_DOWN, gameCanvas, apprenti);
        } else if (code == KeyCode.LEFT || code == KeyCode.Q || code == KeyCode.A) {
            position.moveApprenti(DIRECTION_LEFT, gameCanvas, apprenti);
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            position.moveApprenti(DIRECTION_RIGHT, gameCanvas, apprenti);
        } else if (code == KeyCode.E) {
            position.moveApprenti(SWITCH_CRYSTAL, gameCanvas, apprenti);
        }
        menu.addEventToHistory("Touche pressée : " + code.getName());
        Platform.runLater(() -> menu.updateDeplacements());  // Mise à jour du nombre de déplacements

        // Attendre que l'animation soit terminée avant de remettre isMoving à false
        new Thread(() -> {
            try {
                Thread.sleep(MOVE_SLEEP_DURATION);  // Ajustez cette valeur en fonction de la durée de l'animation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            apprenti.setMoving(false);  // Indiquer que l'apprenti a terminé son déplacement

            boolean sorted = false;
            sorted = position.checkIfAllCrystalsSorted(scenario.getTemples());
            if (sorted){
                menu.addEventToHistory("Cristaux triés !");
            }
        }).start();
    }

    /**
     * Charge un nouveau scénario.
     *
     * @param scenarioFile Le fichier du scénario à charger.
     */
    public void loadScenario(String scenarioFile) {
        Platform.runLater(() -> {
            gameCanvas.supprimerCristaux(scenario.getTemples(), ZOOM_FACTOR);
            scenario.clearTemples(); // Vider la liste des temples
            gameCanvas.clear();
            apprenti.setX(APPRENTI_START_X);
            apprenti.setY(APPRENTI_START_Y);
            apprenti.setColorCrystal(APPRENTI_NO_CRYSTAL);
            position.setNombreDePas(0);
            menu.updateDeplacements();

            scenario.fileLoader(scenarioFile); // Charger le nouveau scénario
            gameCanvas.dessinerGrille(GRID_WIDTH, GRID_HEIGHT, ZOOM_FACTOR); // Redessiner la grille
            gameCanvas.dessinerTemples(scenario.getTemples(), ZOOM_FACTOR); // Redessiner les temples
            gameCanvas.dessinerCristaux(scenario.getTemples(), ZOOM_FACTOR); // Redessiner les cristaux
            gameCanvas.dessinerApprenti(apprenti, ZOOM_FACTOR); // Redessiner l'apprenti à la nouvelle position

            menu.addEventToHistory("Chargement du scénario : " + scenarioFile);
        });
    }

    /**
     * Charge un algorithme spécifique.
     *
     * @param algorithm L'algorithme à charger.
     */
    public void loadAlgorithm(String algorithm) {
        menu.addEventToHistory("Algorithme sélectionné : " + algorithm);
        if ("Tri à bulle".equals(algorithm)) {
            BubbleSortAlgo();
        } else if ("Tri par sélection".equals(algorithm)) {
            SelectionSortAlgo();
        } else if ("Heuristique".equals(algorithm)) {
            HeuristicPersoSortAlgo();
        } else if ("Optimal".equals(algorithm)) {
            OptimalPersoSortAlgo();
        }
    }

    /**
     * Implémente l'algorithme de tri heuristique personnalisé.
     */
    public void HeuristicPersoSortAlgo() {
        menu.addEventToHistory(HEURISTIC_SORT_MESSAGE);

        new Thread(() -> {
            HeuristiquePerso heuristiquePerso = new HeuristiquePerso();
            boolean sorted = false;
            while (!sorted && apprenti.isRunning()) {
                try {
                    Thread.sleep(ALGORITHM_SLEEP_DURATION); // Attendre une courte période
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<Temple> path = heuristiquePerso.generatePath(scenario.getTemples(), apprenti);
                if (!path.isEmpty()) {
                    for (Temple temple : path) {
                        if (!apprenti.isRunning()) return;
                        menu.addEventToHistory("Déplacement vers : (" + temple.getX() + ", " + temple.getY() + ")");
                        position.waitForApprentiToMove(temple.getX(), temple.getY(), gameCanvas, apprenti, menu);
                    }
                }
                sorted = position.checkIfAllCrystalsSorted(scenario.getTemples());
                if (sorted){
                    menu.addEventToHistory("Cristaux triés !");
                }
            }
        }).start();
    }

    /**
     * Implémente l'algorithme de tri optimal personnalisé.
     */
    public void OptimalPersoSortAlgo() {
        menu.addEventToHistory(OPTIMAL_SORT_MESSAGE);

        new Thread(() -> {
            OptimalPerso optimalPerso = new OptimalPerso();
            boolean sorted = false;
            while (!sorted && apprenti.isRunning()) {

                List<Temple> path = optimalPerso.generatePath(scenario.getTemples(),apprenti);
                if (!path.isEmpty()) {
                    for (Temple temple : path) {
                        if (!apprenti.isRunning()) return;
                        menu.addEventToHistory("Déplacement vers : (" + temple.getX() + ", " + temple.getY() + ")");
                        position.waitForApprentiToMove(temple.getX(), temple.getY(), gameCanvas, apprenti, menu);
                        try {
                            Thread.sleep(ALGORITHM_SLEEP_DURATION); // Attendre une courte période avant de vérifier à nouveau
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                sorted = position.checkIfAllCrystalsSorted(scenario.getTemples());
                if (sorted){
                    menu.addEventToHistory("Cristaux triés !");
                }
            }
        }).start();
    }


    /**
     * Implémente l'algorithme de tri à bulle.
     */
    public void BubbleSortAlgo() {
        menu.addEventToHistory(BUBBLE_SORT_MESSAGE);

        // Créer un nouveau thread pour éviter de bloquer l'interface utilisateur
        new Thread(() -> {
            BubbleSort bubbleSortAlgorithm = new BubbleSort(scenario.getTemples(), apprenti);
            boolean sorted = false;
            while (!sorted) {
                // Générer le chemin optimal pour l'apprenti
                List<Position> path = bubbleSortAlgorithm.triBulles();
                System.out.println(path);
                // Déplacer l'apprenti vers chaque temple du chemin
                for (Position position : path) {
                    menu.addEventToHistory("Déplacement vers : (" + position.getAbscisse() + ", " +  position.getOrdonnee() + ")");
                    position.waitForApprentiToMove(position.getAbscisse(), position.getOrdonnee(), gameCanvas, apprenti, menu);
                    try {
                        Thread.sleep(ALGORITHM_SLEEP_DURATION); // Attendre une courte période avant de vérifier à nouveau
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Vérifier si tous les temples sont triés
                    sorted = position.checkIfAllCrystalsSorted(scenario.getTemples());
                    if (sorted) {
                        menu.addEventToHistory("Cristaux triés !");
                        break;
                    }
                }
            }
        }).start();
    }

    /**
     * Implémente l'algorithme de tri par selection.
     */
    public void SelectionSortAlgo() {
        menu.addEventToHistory(SELECTION_SORT_MESSAGE);

        // Créer un nouveau thread pour éviter de bloquer l'interface utilisateur
        new Thread(() -> {
            SelectionSort selectionSortAlgorithm = new SelectionSort(scenario.getTemples(), apprenti);
            boolean sorted = false;
            while (!sorted) {
                // Générer le chemin optimal pour l'apprenti
                List<Position> path = selectionSortAlgorithm.triSelection();
                // Déplacer l'apprenti vers chaque temple du chemin
                for (Position position : path) {
                    menu.addEventToHistory("Déplacement vers : (" + position.getAbscisse() + ", " +  position.getOrdonnee() + ")");
                    position.waitForApprentiToMove(position.getAbscisse(), position.getOrdonnee(),gameCanvas, apprenti, menu);
                    try {
                        Thread.sleep(ALGORITHM_SLEEP_DURATION); // Attendre une courte période avant de vérifier à nouveau
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Vérifier si tous les temples sont triés
                    sorted = position.checkIfAllCrystalsSorted(scenario.getTemples());
                    if (sorted) {
                        menu.addEventToHistory("Cristaux triés !");
                        break;
                    }
                }
            }
        }).start();
    }



    /**
     * Coupe l'algorithme en cours et recharge le scénario.
     */
    public void reset() {
        apprenti.setRunning(false); // Arrêter tous les threads en cours
        apprenti.setMoving(false); // Arrêter tout déplacement en cours

        // Attendre une courte période pour s'assurer que tous les threads sont arrêtés
        try {
            Thread.sleep(RESET_SLEEP_DURATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menu.algorithmComboBox.setValue(DEFAULT_ALGORITHM_COMBOBOX_VALUE); // Réinitialiser la combobox des algorithmes //ne fonctionne mystérieusement pas

        apprenti.setRunning(true);
        loadScenario(menu.getScenarioFile()); // Recharger le scénario en cours

        menu.addEventToHistory("Réinitialisation du scénario : " + menu.getScenarioFile());
    }
}
