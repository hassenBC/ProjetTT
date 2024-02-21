package ch.epfl.chacun;

import org.w3c.dom.ls.LSException;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public interface Zone {
    //les pouvoirs possibles des zones,
    enum SpecialPower {
        SHAMAN, LOGBOAT, HUNTING_TRAP, PIT_TRAP, WILD_FIRE, RAFT;
    }
    static int tileId(int zoneId) {
        return (zoneId - localId(zoneId))/10;
    }
    static int localId(int zoneId) {
        return zoneId % 10;
    }
    public abstract int id ();
    default int tileId(){return 0;}
    default int localId(){return 0;}
    default SpecialPower specialPower() {return null; }

    public interface Forest extends Zone {
         int id = 0;
         Kind kind = null;
         enum Kind{
             PLAIN, WITH_MENHIR, WITH_MUSHROOMS;
        }
    }
    public record Meadow (int id, List <Animal> animals, SpecialPower specialPower) implements Zone{
        public int id() {return id;}
        public Meadow {
            animals = List.copyOf(animals);
        }
    }
    public interface Water extends Zone {
        public abstract int fishCount ();
    }
    public record Lake (int id, int fishCount, SpecialPower specialPower) implements Water {
        @Override
        public int id() {return id;}
    }
    public record River (int id, int fishCount, Lake lake) implements Water{
        boolean hasLake() {
            return lake != null;
    }
    public int fishCount() {return fishCount;}
}
}

