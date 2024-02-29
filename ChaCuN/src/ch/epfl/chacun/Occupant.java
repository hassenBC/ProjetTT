package ch.epfl.chacun;

import java.util.Objects;

/**enregistrement des occupants déposés par les joueurs lors de leur tour
 * @author Tony Andriamampianina (sciper)
 * @param kind
 * @param zoneId
 */
public record Occupant (Kind kind, int zoneId) {
    public enum Kind {
        PAWN, HUT
    }

    /**constructeur
     * @author Tony Andriamampianina (sciper)
     * @throws NullPointerException si kind est null
     * @throws IllegalArgumentException is zoneid est strictement négatif
     * @param kind
     * @param zoneId
     */
    public Occupant {
        Objects.requireNonNull(kind);
        Preconditions.checkArgument(zoneId >= 0);
    }

    /** nombre d'occupants de la sorte que possède le joueur
     * @author Tony Andriamampianina
     * @param kind
     * @return nombre d'occupants de la sorte possédés par le joueur
     */
    public static int occupantsCount(Kind kind) {  //méthode pour donner des points en fct de la nature de l'Occupant//
        if (kind.equals(Kind.PAWN)) {return 5;}
        if (kind.equals(Kind.HUT)) {return 3;}
        return 0;

    }
}
