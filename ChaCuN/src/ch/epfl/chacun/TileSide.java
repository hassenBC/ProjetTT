package ch.epfl.chacun;

import java.util.ArrayList;
import java.util.List;

public interface TileSide {
    public abstract List<Zone> zones();
    public abstract boolean isSameKindAs(TileSide that);
    //initialiser nos élèments ???

 record Forest(Zone.Forest forest) implements TileSide{
    @Override
    public List<Zone> zones() {
        //verifie avec une zone autre que forest ptt rajouter un test
        return List.of(forest);
    }

    @Override
    public boolean isSameKindAs(TileSide that) {
        return that instanceof TileSide.Forest;
        }
    }

    record Meadow(Zone.Meadow meadow) implements TileSide{

        @Override
        public List<Zone> zones() {
            //vérifie avec une zone autre que meadow
            return List.of(meadow);
        }

        @Override
        public boolean isSameKindAs(TileSide that) {
            //ou this == that non puisqu'il va comparer directement les références
            return that instanceof TileSide.Meadow;
        }
    }

    record River(Zone.Meadow meadow1, Zone.River river, Zone.Meadow meadow2) implements TileSide{

        @Override
        public List<Zone> zones() {
            //ne pas tout mettre
            return List.of(meadow1,river,meadow2);
        }

        @Override
        public boolean isSameKindAs(TileSide that) {
            return that instanceof TileSide.River;
        }
    }

}
