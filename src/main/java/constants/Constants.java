package constants;

public interface Constants {
    // Main constants
    int CANVAS_WIDTH = 1750;
    int CANVAS_HEIGHT = 1080;
    String SCENARIO_FILE = "File/scenarioTest.txt";
    double ZOOM_FACTOR = 1;
    // Grid size
    int GRID_WIDTH = 33;
    int GRID_HEIGHT = 33;

    // Apprenti constants
    int APPRENTI_START_X = 0;
    int APPRENTI_START_Y = 0;
    int APPRENTI_NO_CRYSTAL = -1;
    String APPRENTI_SPRITE_SHEET = "sprite_sheet_apprenti.png";

    // Temple constants
    int TEMPLE_EMPTY = -1;


    // Constants for directions
    char DIRECTION_UP = 'Z';
    char DIRECTION_DOWN = 'S';
    char DIRECTION_LEFT = 'Q';
    char DIRECTION_RIGHT = 'D';
    char SWITCH_CRYSTAL = 'E';

    // Constants for thread sleep durations
    int MOVE_SLEEP_DURATION = 100;
    int ALGORITHM_SLEEP_DURATION = 500;
    int WAIT_FOR_MOVE_SLEEP_DURATION = 2000;
    int RESET_SLEEP_DURATION = 3000;

    // Constants for messages
    String BUBBLE_SORT_MESSAGE = "Lancement de l'algorithme de tri par bulles";
    String SELECTION_SORT_MESSAGE = "Lancement de l'algorithme de tri par sélection";
    String HEURISTIC_SORT_MESSAGE = "Lancement de l'algorithme heuristique personnalisé";
    String OPTIMAL_SORT_MESSAGE = "Lancement de l'algorithme de tri optimal";


    // Algorithm ComboBox default value
    String DEFAULT_ALGORITHM_COMBOBOX_VALUE = "Algorithme:";

    //GameCanvas
    int CELL_SIZE = 32; // Taille de chaque case en pixels
    int TEMPLE_SIZE_X = 94; // Taille de chaque temple dans le sprite sheet
    int TEMPLE_SIZE_Y = 72; // Taille de chaque temple dans le sprite sheet
    int SPRITE_SIZE_X = 204; // Largeur d'un sprite dans l'animation
    int SPRITE_SIZE_Y = 112; // Hauteur d'un sprite dans l'animation
    int WALL_SIZE_WIDTH = 8; // Largeur d'un muret
    int WALL_SIZE_LONG = 96; // Longueur d'un muret

    int EMPTY_TILE = -1;
    int WALL_LEFT_TILE = 62; // Valeur unique pour le mur gauche
    int WALL_RIGHT_TILE = 63; // Valeur unique pour le mur droit
    int WALL_HORIZONTAL_TILE = 64; // Valeur unique pour les mur horizontaux


    int SPEED_ANIMATION = 22; // Vitesse de l'animation
}
