package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
<<<<<<< HEAD
import model.HeuristiquePerso;
=======
>>>>>>> c7942fc056b791ec54ddc8d76fba59fa9cfd6ff7


/**
 * Classe représentant l'interface graphique de l'application.
 * Elle contient le canvas de jeu et le menu, et permet de l'afficher dans une fenêtre JavaFX.
 */
<<<<<<< HEAD
public class InterfaceGraphique extends HeuristiquePerso {
=======
public class InterfaceGraphique {
>>>>>>> c7942fc056b791ec54ddc8d76fba59fa9cfd6ff7
    private GameCanvas gameCanvas;
    private Menu menu;

    /**
     * Constructeur de la classe InterfaceGraphique.
     * Initialise le canvas et le menu.
     *
     * @param gameCanvas Le canvas de jeu à afficher.
     */
    public InterfaceGraphique(GameCanvas gameCanvas,Menu menu) {
        this.gameCanvas = gameCanvas;
        this.menu = menu;

    }


    /**
     * Affiche l'interface graphique dans la fenêtre principale spécifiée.
     *
     * @param primaryStage La fenêtre principale dans laquelle afficher l'interface graphique.
     */
    public void show(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setCenter(gameCanvas);
        root.setRight(menu);


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("L'Apprenti Ordonnateur");
        primaryStage.show();
    }


    /**
     * Retourne le menu de l'interface graphique.
     *
     * @return Le menu de l'interface graphique.
     */
    public Menu getMenu() {
        return menu;
    }
}
