package ch.epfl.chacun;

import java.util.List;

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
    public abstract int id (int zoneId);
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
    public record Meadow implements Zone(int id, List <Animal> animals, ) {


    }
    }

}

