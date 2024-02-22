package ch.epfl.chacun;

import org.w3c.dom.ls.LSException;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/** interface des entités qui peuvent être des zones
 * @author tony andriamampianina
 * @author hassen ben chaabane
 *
 */
public sealed interface Zone {
    /**les pouvoirs possibles des zones */

    enum SpecialPower {
        SHAMAN, LOGBOAT, HUNTING_TRAP, PIT_TRAP, WILD_FIRE, RAFT
    }

    /** id de la tuile où se trouve la zone
     * @author Tony Andriamampianina
     * @param zoneId
     * @return idTuile
     */
    static int tileId(int zoneId) {
        return (zoneId - localId(zoneId))/10;
    }

    /** idlocal de la zone
     * @author Tony Andriamampianina
     * @param zoneId
     * @return idLocal
     */
    static int localId(int zoneId) {
        return zoneId % 10;
    }
    /** id par défault de la tuile où se trouve la zone
     * @author Tony Andriamampianina
     * sans paramètre
     * @return idTuile
     */
    default int tileId(){return (id()-localId())/10 ;}

    /** id local par défault de la zone
     * @author Tony Andriamampianina
     * @return idLocal
     */
    default int localId(){return id() % 10;}

    /** id public de la zone
     * @author Tony Andriamampianina
     */
    public abstract int id ();

    /** specialpower par défault : aucun
     * @author Tony Andriamampianina
     */
    default SpecialPower specialPower() {return null; }

    /** zone foret
     * @author tony andriamampianina
     * @param id
     * @param kind
     */
    public record Forest(int id, Kind kind) implements Zone {
        /** type de forêt*/
        public enum Kind{
             PLAIN, WITH_MENHIR, WITH_MUSHROOMS
        }
    }

    /**zone de type pré
     * @author tony andriamampianina
     * @param id
     * @param animals
     * @param specialPower
     */
    public record Meadow (int id, List <Animal> animals, SpecialPower specialPower) implements Zone{
        public int id() {return id;}

        /** constructeur du pré avec copie défensive de la liste d'animaux
         * @author  tony andriamampianina
         * @param id
         * @param animals
         * @param specialPower
         */
        public Meadow {
            animals = List.copyOf(animals);
        }
    }

    /** interface scellée point d'eau
     * @author  tony andriamampianina
      */
    public sealed interface Water extends Zone {
        public abstract int fishCount ();
    }

    /** zone lac
     * @author  tony andriamampianina
      * @param id
     * @param fishCount
     * @param specialPower
     */
    public record Lake (int id, int fishCount, SpecialPower specialPower) implements Water {
        @Override
        public int id() {return id;}
    }

    /** zone rivière, avec la possibilité d'être connectée à un lac
     * @author hassen ben chaabane
     * @param id
     * @param fishCount
     * @param lake
     */
    public record River (int id, int fishCount, Lake lake) implements Water{
        public boolean hasLake() {
            return lake != null;
    }
    public int fishCount() {return fishCount;}
}
}

