package ch.epfl.chacun;

import java.util.HashSet;
import java.util.Set;

public class BoardHassen {
    private final PlacedTile [] placedTiles;
    private final int [] indexes;
    private final ZonePartitions zonePartitions;
    private final Set<Animal> cancelledAnimals;


    private BoardHassen(PlacedTile[] placedTiles, int[] indexes, ZonePartitions zonePartitions, Set<Animal> cancelledAnimals) {
        this.placedTiles = placedTiles;
        this.indexes = indexes;
        this.zonePartitions = zonePartitions;
        this.cancelledAnimals = cancelledAnimals;
    }


    public PlacedTile tileAt(Pos pos){
        int indexTile  = (pos.y() - 12) * 25 + (pos.x() - 12);
        return placedTiles[indexTile];
    }

    public PlacedTile tileWithId(int tileId){
        for(PlacedTile tile : placedTiles){
            if(tile.id() == tileId) return tile;
        }
        throw new IllegalArgumentException("la tuile n'appartient pas au plateau");
    }



    public static final int REACH = 12;
    public static final BoardHassen EMPTY = new BoardHassen(new PlacedTile[625], new int[625], ZonePartitions.EMPTY, new HashSet<>());
}
