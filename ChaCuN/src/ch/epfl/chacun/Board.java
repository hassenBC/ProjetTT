package ch.epfl.chacun;

import java.util.HashSet;
import java.util.Set;

public final class Board {

    //initialise les valeurs
    private final PlacedTile [] placedTiles;
    private final int [] indexes;
    private final ZonePartitions zonePartitions;
    private final Set<Animal> cancelledAnimals;


    private Board(PlacedTile[] placedTiles, int[] indexes, ZonePartitions zonePartitions, Set<Animal> cancelledAnimals) {
    this.placedTiles = placedTiles;
    this.indexes = indexes;
    this.zonePartitions = zonePartitions;
    this.cancelledAnimals = cancelledAnimals;
    }
    public static final int REACH = 12;
    public static final Board EMPTY = new Board(new PlacedTile[625], new int[96], ZonePartitions.EMPTY, new HashSet<>());

    public Set<Animal> cancelledAnimals () {return Set.copyOf(cancelledAnimals);}
    public Set<Occupant> occupants() {

    }


}
