package resolution;

import modele.Apprenti;
import modele.Temple;

import java.util.List;

public interface AlgorithmeOptimal {
    int movementCounter(List<Temple> temples, Apprenti apprenti);
}

