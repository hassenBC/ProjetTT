package ch.epfl.chacun;
import ch.epfl.chacun.TileSide;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** Tuile de jeu 
 * @author Tony Andriamampianina (SCIPER)
 * @param id
 * @param kind
 * @param n
 * @param e
 * @param s
 * @param w
 */
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

    /**
     * ensemble de toutes les zones en bordure de la tuile (qu'on obtient grâce aux attributs tileside)
     * @return un ensemble de Zones sideZones
     */
    public Set<Zone> sideZones() {
        Set <Zone> sideZones = new HashSet<>();
        sideZones.addAll(n.zones());
        sideZones.addAll(e.zones());
        sideZones.addAll(s.zones());
        sideZones.addAll(w.zones());
        return sideZones;
    }
    
    public Set<Zone> zones() {
        Set <Zone> zones = new HashSet<>();
        zones.addAll(sideZones());
        for (Zone zone : zones) {
            if (zone instanceof Zone.River river) {
                /** en faisant instanceof on crée une instance de river dont on utilise les méthodes */
                if ((river.hasLake())) {
                    zones.add(river.lake());
                }
            }
        } return zones;
    }
    
    


}
