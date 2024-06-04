package controleur;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import modele.Apprenti;
import modele.Position;
import ui.GameCanvas;

import java.awt.*;

public class GameController {
    private GameCanvas gameCanvas;
    private Position position;
    private Apprenti apprenti;

    public GameController(GameCanvas gameCanvas, Position position, Apprenti apprenti) {
        this.gameCanvas = gameCanvas;
        this.position = position;
        this.apprenti = apprenti;
        setupMouseEvents();
    }

    private void setupMouseEvents() {
        gameCanvas.setOnMouseClicked(event -> {
            double zoomFactor = 1; // Assuming zoom factor is 1 for simplicity
            Point gridCoordinates = gameCanvas.convertGridCoordinatesToPixels((int) event.getX(), (int) event.getY(), zoomFactor);

            // Convertir les coordonnées de la souris en coordonnées de la grille
            int targetX = gridCoordinates.x;
            int targetY = gridCoordinates.y;

            // Lancer un thread pour déplacer l'apprenti vers la position cible
            new Thread(() -> moveApprentiToTarget(targetX, targetY)).start();
        });
    }
    private void moveApprentiToTarget(int targetX, int targetY) {
        int apprentiX = apprenti.getX();
        int apprentiY = apprenti.getY();

        while (apprentiX != targetX || apprentiY != targetY) {
            if (apprentiX < targetX) {
                apprentiX++;
                position.moveApprenti('D', gameCanvas, apprenti);
            } else if (apprentiX > targetX) {
                apprentiX--;
                position.moveApprenti('Q', gameCanvas, apprenti);
            }

            if (apprentiY < targetY) {
                apprentiY++;
                position.moveApprenti('S', gameCanvas, apprenti);
            } else if (apprentiY > targetY) {
                apprentiY--;
                position.moveApprenti('Z', gameCanvas, apprenti);
            }

            apprenti.setX(apprentiX);
            apprenti.setY(apprentiY);

            // Pause pour l'animation
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleInput(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.UP || code == KeyCode.Z || code == KeyCode.W) {
            position.moveApprenti('Z', gameCanvas, apprenti);
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            position.moveApprenti('S', gameCanvas, apprenti);
        } else if (code == KeyCode.LEFT || code == KeyCode.Q || code == KeyCode.A) {
            position.moveApprenti('Q', gameCanvas, apprenti);
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            position.moveApprenti('D', gameCanvas, apprenti);
        }
          else if ( code == KeyCode.E){
            position.moveApprenti('E', gameCanvas, apprenti);
        }

    }
}
