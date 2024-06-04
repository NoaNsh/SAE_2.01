package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant un scénario dans le jeu.
 */
public class Scenario {
    private List<Temple> temples;
    private String scenarioFile;

    /**
     * Constructeur de la classe Scenario.
     * Initialise une nouvelle liste de temples.
     */
    public Scenario() {
        this.temples = new ArrayList<>();
    }

    /**
     * Charge un fichier de scénario et initialise les temples en conséquence.
     *
     * @param filepath Le chemin du fichier de scénario à charger.
     */
    public void fileLoader(String filepath) {
        try {
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNextLine()) {
                String[] elements = scanner.nextLine().split(" ");
                int x = Integer.parseInt(elements[0]);
                int y = Integer.parseInt(elements[1]);
                int colorTemple = Integer.parseInt(elements[2]);
                int colorCrystal = Integer.parseInt(elements[3]);
                temples.add(new Temple(x, y, colorTemple, colorCrystal));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vide la liste des temples.
     */
    public void clearTemples() {
        temples.clear();
    }

    /**
     * Retourne la liste des temples.
     *
     * @return La liste des temples.
     */
    public List<Temple> getTemples() {
        return temples;
    }

    /**
     * Définit la liste des temples.
     *
     * @param temples La nouvelle liste de temples.
     */
    public void setTemples(List<Temple> temples) {
        this.temples = temples;
    }

    /**
     * Retourne le chemin du fichier de scénario.
     *
     * @return Le chemin du fichier de scénario.
     */
    public String getScenarioFile() {
        return scenarioFile;
    }
}
