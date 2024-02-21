package ch.epfl.chacun;

public record Pos(int x, int y) {
    static final Pos ORIGIN = new Pos(0,0);
    public Pos {
    }
    public Pos translated (int dx, int dy) {
        return new Pos(x + dx, y + dy);
    }

    public Pos neighbour (Direction direction) {
        return switch (direction) {
            case N -> (new Pos(x, y+1));
            case E -> (new Pos (x+1, y));
            case S -> (new Pos (x, y-1));
            case W -> (new Pos (x-1, y));
        };
    }
}

