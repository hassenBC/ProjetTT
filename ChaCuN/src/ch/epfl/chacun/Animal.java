package ch.epfl.chacun;

/** classe des animaux qui sont sur les zones
 *
 * @param id
 * @param kind
 * @author Tony Andriamampianina (sciper)
 */
public record Animal(int id, Kind kind) {
    /** liste des types d'animaux
     @author Tony Andriamampinina (sciper)
     */
    public enum Kind {
        MAMMOTH, AUROCHS, DEER, TIGER;
    }

    /** instancie un animal avec un id et un type
     * @author Tony Andriamampianina
     * @param id
     * @param kind
     */
    public Animal (int id, Kind kind) {
        this.kind = kind;
        this.id = id;
    }

    /** retourne l'id local de l'animal dans la zone
     * @author Tony Andriamampianina
     * @return idlocal dans la zone.
     */
    public int localId(){
        int var = 0;
        if (id % 10 == 1) {
            var = 1;
        }
        else if (id % 10 == 0) {
            var = 0;
        }
        return var;
    }

    /** id de la tuile où se trouve l'animal
     * @author Tony Andriamampianina (sciper)
     * @return id de la tuile où se trouve l'animal.
     */
    public int tileId() {
        int zoneId = (id - localId())/10;
        int localZoneId = zoneId % 10;
        return ((zoneId - localZoneId) / 10);



    }

}
