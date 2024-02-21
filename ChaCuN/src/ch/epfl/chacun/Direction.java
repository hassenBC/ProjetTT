package ch.epfl.chacun;

import java.util.List;

public enum Direction {
    N,
    E,
    S,
    W;

    public final static List<Direction> ALL = List.of(values());
    public final static int COUNT = ALL.size();

    public Direction rotated(Rotation rotation){
        int rota = (this.ordinal() + rotation.ordinal())%COUNT;
        return ALL.get(rota);
    }

    public Direction opposite(){
        int oppose = (this.ordinal() + 2)%COUNT;
        return ALL.get(oppose);
    }
}
