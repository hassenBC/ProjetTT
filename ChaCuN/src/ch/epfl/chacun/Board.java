package ch.epfl.chacun;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;

import java.util.*;

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
    public static final Board EMPTY = new Board(new PlacedTile[625], new int[0], ZonePartitions.EMPTY, new HashSet<>());

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

    private int posToNumber(Pos pos) {
        int xNorm = pos.x() + 12;
        int yNorm = pos.y() + 12;
        return (yNorm*25 + xNorm+1);
    }
    public PlacedTile tileAt(Pos pos){
        return placedTiles[posToNumber(pos)];
    }

    public PlacedTile tileWithId(int tileId){
        for(int tileIndex : indexes){
            if(placedTiles[tileIndex].id() == tileId) return placedTiles[tileIndex];
        }
        throw new IllegalArgumentException("la tuile n'appartient pas au plateau");
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
            boolean canN = tileAt(tile.pos().neighbor(Direction.N)) == null || oppositeSideSameType(tile, Direction.N);
            boolean canE = tileAt(tile.pos().neighbor(Direction.E)) == null || oppositeSideSameType(tile, Direction.E);
            boolean canS = tileAt(tile.pos().neighbor(Direction.S))== null || oppositeSideSameType(tile, Direction.S);
            boolean canW = tileAt(tile.pos().neighbor(Direction.W)) == null || oppositeSideSameType(tile, Direction.W);
            return (canN && canE && canS && canW);
        } return false;
    }
    public boolean couldPlaceTile (Tile tile ) {
        for (Pos p : insertionPositions()) {
            for (Rotation rotation : Rotation.values()) {
                if (canAddTile(new PlacedTile(tile, null, rotation, p, null)))
                    return true;
            }
        } return false;
    }

    private PlacedTile neighborTile (Pos pos, Direction direction) {
        return switch (direction) {
            case N -> tileAt(pos.neighbor(Direction.N));
            case S -> tileAt(pos.neighbor(Direction.S));
            case E -> tileAt(pos.neighbor(Direction.E));
            case W -> tileAt(pos.neighbor(Direction.W));
        };
    }

    public Board withNewTile(PlacedTile tile) {
        //si le board est vide ou la tile peut être posée
        if (indexes.length == 0 || canAddTile(tile)){
            //maj des arrays de placedtiles et indexes
            PlacedTile [] newPT = Arrays.copyOf(this.placedTiles, this.placedTiles.length);
            newPT [posToNumber(tile.pos())] = tile;
            int [] newInd = Arrays.copyOf(this.indexes, this.indexes.length + 1);
            newInd [newInd.length-1] = posToNumber(tile.pos());

            //maj de zonepartitions
            ZonePartitions.Builder zBuilder = new ZonePartitions.Builder(zonePartitions);
            for (Direction direction : Direction.ALL) {
                if (neighborTile(tile.pos(), direction) != null)
                    zBuilder.connectSides(tile.side(direction), neighborTile(tile.pos(), direction).side(direction.rotated(Rotation.HALF_TURN)));
            }
            return new Board(newPT, newInd, zBuilder.build(), cancelledAnimals());
        } throw new IllegalArgumentException("Board not empty AND tile cannot be added to board");
    }

    public Board withoutOccupant (Occupant occupant) {
        PlacedTile[] PT = placedTiles.clone();
        int [] ind = indexes.clone();
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);
        PlacedTile tile = tileWithId(Zone.tileId(occupant.zoneId()));
        Zone occupiedZone = tile.zoneWithId(occupant.zoneId());
        tile.withNoOccupant();
        builder.removePawn(tile.placer(), occupiedZone);

        return (new Board(PT, ind, builder.build(), cancelledAnimals()));

    }
    public Board withoutGatherersOrFishersIn(Set<Area<Zone.Forest>> forests, Set<Area<Zone.River>> rivers){
        ZonePartitions.Builder zBuilder = new ZonePartitions.Builder(zonePartitions);
        for (Area <Zone.Forest> forest : forests)
            zBuilder.clearGatherers(forest);
        for (Area <Zone.River> river : rivers)
            zBuilder.clearFishers(river);
        return (new Board (placedTiles.clone(), indexes.clone(), zBuilder.build(), cancelledAnimals()));
    }
    public Board withMoreCancelledAnimals (Set<Animal> newlyCancelledAnimals) {
        Set <Animal> newCancelled = new HashSet<>(cancelledAnimals());
        newCancelled.addAll(newlyCancelledAnimals);
        return new Board(placedTiles.clone(), indexes.clone(), new ZonePartitions.Builder(zonePartitions).build(), Collections.unmodifiableSet(newCancelled));
    }

}
