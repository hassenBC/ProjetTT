package ch.epfl.chacun;

public record Animal(int id, Kind kind) {
    private enum Kind {
        MAMMOTH, AUROCHS, DEER, TIGER;
    }

}
