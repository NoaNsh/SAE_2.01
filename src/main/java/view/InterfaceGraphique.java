package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Classe représentant l'interface graphique de l'application.
 * Elle contient le canvas de jeu et le menu, et permet de l'afficher dans une fenêtre JavaFX.
 */
public class InterfaceGraphique {
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
