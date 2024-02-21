package ch.epfl.chacun;

import java.util.Objects;

public record Occupant (Kind kind, int zoneId) {
    public enum Kind {
        PAWN, HUT
    }
    public Occupant {
        Objects.requireNonNull(kind);
        Preconditions.checkArgument(zoneId > 0);
    }
    public static int occupantsCount(Kind kind) {  //méthode pour donner des points en fct de la nature de l'Occupant//
        if (kind.equals(Kind.PAWN)) {return 5;}
        if (kind.equals(Kind.HUT)) {return 3;}
        return 0;

    }
}
