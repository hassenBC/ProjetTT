package ch.epfl.chacun;
import ch.epfl.chacun.TileSide;


import java.util.ArrayList;
import java.util.Set;

public record Tile(int id, Kind kind, TileSide n, TileSide e, TileSide s, TileSide w) {
    public enum Kind {
        START, NORMAL, MENHIR }
    public ArrayList <TileSide> sides() {
        ArrayList <ch.epfl.chacun.TileSide> sides = new ArrayList<ch.epfl.chacun.TileSide> ();
        sides.add(n);
        sides.add(e);
        sides.add(s);
        sides.add(w);
        return sides;
    }
    public Set<Zone> sideZones() {
    }


}
