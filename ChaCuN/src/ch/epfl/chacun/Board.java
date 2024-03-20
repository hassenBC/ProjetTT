package ch.epfl.chacun;

import java.util.*;

public final class Board {
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
    public static final Board EMPTY = new Board(new PlacedTile[625], new int[625], ZonePartitions.EMPTY, new HashSet<>());

    public Set<Animal> cancelledAnimals () {return Set.copyOf(cancelledAnimals);}
    public Set<Occupant> occupants() {
        Set <Occupant> occupants = new HashSet<>();
        for (PlacedTile placedTile : placedTiles) {
            occupants.add(placedTile.occupant());
        }
        return occupants;
    }
    public Area <Zone.Forest> forestArea(Zone.Forest forest) {
        return zonePartitions.forests().areaContaining(forest);
    }
    public Area <Zone.Meadow> meadowArea(Zone.Meadow meadow) {
        return zonePartitions.meadows().areaContaining(meadow);
    }
    public Set <Area<Zone.Meadow>> meadowAreas() {
        return zonePartitions.meadows().areas();
    }
    public Set <Area <Zone.Water>> riverSystemAreas() {
        return zonePartitions.riverSystems().areas();
    }
    private boolean hasTile(Pos pos) {
        for (PlacedTile placedTile : placedTiles) {
            if (placedTile.pos().equals(pos))
                return true;
        } return false;
    }
    //réfléchir à quand ça sort du damier
    public Set <Pos> insertionPositions() {
        Set <Pos> posSet = new HashSet<>();
        for (int i = -12; i <= 12 ; i++) {
            for (int j = -12; j <= 12 ; j++) {
                Pos pos = new Pos(i, j);
                if (!hasTile(pos) && hasTile(pos.neighbor(Direction.N)) && pos.y()>=-11)
                    posSet.add(pos);
                if (!hasTile(pos) && hasTile(pos.neighbor(Direction.E)) && pos.x()<=11)
                    posSet.add(pos);
                if (!hasTile(pos) && hasTile(pos.neighbor(Direction.S)) && pos.y()<=11)
                    posSet.add(pos);
                if (! hasTile(pos) && hasTile(pos.neighbor(Direction.W)) && pos.x()>=-11)
                    posSet.add(pos);
            }
        }
        return posSet;
    }
    public PlacedTile lastPlacedTile() {
        int j = indexes.length;
        // plateau vide
        if (j==0) {
            return null;
        }
        else {
            // si j==1, retournera la tuile de départ
            return placedTiles[indexes[j-1]];
        }
    }

    //méthode pour vérifier si la tuile voisine à la pos donnée à la direction donnée a le même tileside
    private boolean oppositeSideSameType (PlacedTile placedTile, Direction direction) {
        return switch (direction) {
            case N -> (tileAt(placedTile.pos().neighbor(Direction.N)).side(Direction.S).isSameKindAs(placedTile.side(Direction.N)));
            case E -> (tileAt(placedTile.pos().neighbor(Direction.E)).side(Direction.W).isSameKindAs(placedTile.side(Direction.E)));
            case S -> (tileAt(placedTile.pos().neighbor(Direction.S)).side(Direction.N).isSameKindAs(placedTile.side(Direction.S)));
            case W -> (tileAt(placedTile.pos().neighbor(Direction.W)).side(Direction.E).isSameKindAs(placedTile.side(Direction.W)));
        };
    }

    public boolean canAddTile (PlacedTile tile) {
        TileSide sideN = tile.side(Direction.N);
        TileSide sideE = tile.side(Direction.E);
        TileSide sideS = tile.side(Direction.S);
        TileSide sideW = tile.side(Direction.W);
        if (insertionPositions().contains(tile.pos())) {
            boolean canN = tileAt(tile.pos().neighbor(Direction.N) == null || oppositeSideSameType(tile, Direction.N));
            boolean canE = tileAt(tile.pos().neighbor(Direction.E) == null || oppositeSideSameType(tile, Direction.E));
            boolean canS = tileAt(tile.pos().neighbor(Direction.S) == null || oppositeSideSameType(tile, Direction.S));
            boolean canW = tileAt(tile.pos().neighbor(Direction.W) == null || oppositeSideSameType(tile, Direction.W));
            return (canN && canE && canS && canW);
        } return false;
    }
    public boolean couldPlaceTile (Tile tile ) {
        for (Pos p : insertionPositions()) {
            for (Rotation rotation : Rotation.values()) {
                if (canAddTile(new PlacedTile(tile, null, rotation, p, null))
                    return true;
            }
        } return false;
    }
    public Board withNewTile(PlacedTile tile) {

    }
}
