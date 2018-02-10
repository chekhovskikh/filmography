package music.util;

import entities.Genre;

import java.util.Comparator;

public class Comparators {
    private Comparators() {
    }

    public static Comparator<Genre> genreIdComparator = Comparator.comparing(Genre::getId);
    public static Comparator<Genre> genreNameComparator = Comparator.comparing(Genre::getName);
}
