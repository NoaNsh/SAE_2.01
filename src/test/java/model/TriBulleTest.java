package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriBulleTest {

    @Test
    public void testBubbleSort() {
        int[] liste = {5, 1, 4, 2, 8};
        int[] attendu = {1, 2, 4, 5, 8};
        TriBulle.bubbleSort(liste);
        assertArrayEquals(attendu, liste);
    }
}