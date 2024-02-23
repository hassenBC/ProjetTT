package ch.epfl.chacun;

import java.util.List;

public interface TileSide {
    public abstract List<Zone> zones();
    public abstract boolean isSameKindAs(TileSide that);
}
 record Forest(Zone.Forest forest) implements TileSide{
    @Override
    public List<Zone> zones() {
        return null;
    }

    @Override
    public boolean isSameKindAs(TileSide that) {
        return that instanceof Zone.Forest;
    }
}
