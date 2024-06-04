package modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Scenario {
    private List<Temple> temples;

    public Scenario() {
        this.temples = new ArrayList<>();
    }
    public void fileLoader(String filepath) {
        try {
            Scanner scanner = new Scanner(new File(filepath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // Supprimer les espaces en début et fin de ligne
                String[] elements = line.split("\\s+"); // Diviser la ligne en fonction des espaces
                if (elements.length == 4) { // Vérifier s'il y a exactement 4 éléments
                    int x = Integer.parseInt(elements[0]);
                    int y = Integer.parseInt(elements[1]);
                    int colorTemple = Integer.parseInt(elements[2]);
                    int colorCrystal = Integer.parseInt(elements[3]);
                    temples.add(new Temple(x, y, colorTemple, colorCrystal));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public List<Temple> getTemples() {
        return temples;
    }

    public void setTemples(List<Temple> temples) {
        this.temples = temples;
    }
}
