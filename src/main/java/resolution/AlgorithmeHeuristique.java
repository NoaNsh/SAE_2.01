package resolution;

import modele.Apprenti;
import modele.Temple;

import java.util.List;

public interface AlgorithmeHeuristique {
    void FindBestPath(List<Temple> temples, Apprenti apprenti);
}

