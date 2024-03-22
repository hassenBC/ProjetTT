package ch.epfl.chacun;

import java.util.*;

public final class BoardHassen {

    private  PlacedTile[] placedTiles;
    private final int[] indexes;
    private final ZonePartitions zonePartitions;
    private final Set<Animal> cancelledAnimals;


    public BoardHassen(PlacedTile[] placedTiles, int[] indexes, ZonePartitions zonePartitions, Set<Animal> cancelledAnimals) {
        this.placedTiles = placedTiles;
        this.indexes = indexes;
        this.zonePartitions = zonePartitions;
        this.cancelledAnimals = cancelledAnimals;
    }

    public static final int REACH = 12;
    public static final BoardHassen EMPTY = new BoardHassen(new PlacedTile[625], new int[0], ZonePartitions.EMPTY, new HashSet<>());


    public PlacedTile tileAt(Pos pos) {
        if (pos.x() < -12 || pos.x() > 12 || pos.y() < -12 || pos.y() > 12) {
            return null;
        }
        int indexTile = (pos.y() + 12) * 25 + (pos.x() + 12);
        return placedTiles[indexTile];
    }

    public PlacedTile tileWithId(int tileId) {
        //voir si on fait une copie de placedtile ou pas pour l'immuabilité
        for (int tileIndex : indexes) {
            if (placedTiles[tileIndex].id() == tileId) return placedTiles[tileIndex];
        }
        throw new IllegalArgumentException("la tuile n'appartient pas au plateau");
    }

    public Area<Zone.Forest> forestArea(Zone.Forest forest) {
        return zonePartitions.forests().areaContaining(forest);
    }

    public Area<Zone.Meadow> meadowArea(Zone.Meadow meadow) {
        return zonePartitions.meadows().areaContaining(meadow);
    }

    public Area<Zone.River> riverArea(Zone.River riverZone) {
        return zonePartitions.rivers().areaContaining(riverZone);
    }

    public Area<Zone.Water> riverSystemArea(Zone.Water water) {
        return zonePartitions.riverSystems().areaContaining(water);
    }

    // fait une deuxieme boucle avec les lambdas pour les occupants
    public Area<Zone.Meadow> adjacentMeadow(Pos pos, Zone.Meadow meadowZone) {

        Set<Zone.Meadow> adjacentMeadows = new HashSet<>();
        List<PlayerColor> playerColorList = meadowArea(meadowZone).occupants();
        Set<Zone.Meadow> meadowAdjZones = meadowArea(meadowZone).zones();

        for (int index : validIndexes(pos.x(), pos.y()) ) {
            for (Zone.Meadow zone : placedTiles[index].meadowZones()) {
                if (meadowAdjZones.contains(zone)) {
                    adjacentMeadows.add(zone);
                }
            }
        }
        return new Area<>(adjacentMeadows, playerColorList, 0);
    }

    public int occupantCount(PlayerColor player, Occupant.Kind occupantKind) {
        int playerCount = 0;
        for (int tileIndex : indexes) {
            if(placedTiles[tileIndex].occupant() !=null){
            if ( placedTiles[tileIndex].occupant().kind().equals(occupantKind) && placedTiles[tileIndex].placer().equals(player)) {
                playerCount++;
                }
            }
        }
        return playerCount;
    }

    //qui retourne l'ensemble de toutes les aires forêts qui ont été
    // fermées suite à la pose de la dernière tuile, ou un ensemble vide si le plateau est vide,
    public Set<Area<Zone.Forest>> forestsClosedByLastTile() {
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        if (indexes.length == 0) {
            return new HashSet<>();
        }
        for (Zone.Forest zoneForest : placedTiles[indexes.length - 1].forestZones()) {
            forestAreas.add(forestArea(zoneForest));
        }
        forestAreas.removeIf(ForestArea -> ForestArea.openConnections() != 0);

        return forestAreas;
    }

    public Set<Area<Zone.River>> riversClosedByLastTile() {
        Set<Area<Zone.River>> riverAreas = new HashSet<>();
        if (indexes.length == 0) return new HashSet<>();
        for (Zone.River river : placedTiles[indexes.length - 1].riverZones()) {
            riverAreas.add(riverArea(river));
        }
        riverAreas.removeIf(RiverArea -> RiverArea.openConnections() != 0);
        return riverAreas;
    }

    public BoardHassen withOccupant(Occupant occupant) {
        int occupantTileId = Zone.tileId(occupant.zoneId());

        PlacedTile[] updatedPlacedTiles = placedTiles.clone();
        var b = new ZonePartitions.Builder(zonePartitions);
        //builder zone Partition

        for (int tileIndex : indexes) {
            if (placedTiles[tileIndex].tile().id() == occupantTileId) {
                if (placedTiles[tileIndex].occupant() != null)  throw new IllegalArgumentException("la zone est deja occupée");
                updatedPlacedTiles[tileIndex] = updatedPlacedTiles[tileIndex].withOccupant(occupant);
                b.addInitialOccupant(placedTiles[tileIndex].placer(),occupant.kind(),placedTiles[tileIndex].zoneWithId(occupantTileId));
            }
        }
        return new BoardHassen(updatedPlacedTiles, indexes, b.build(), cancelledAnimals);
    }

//    public BoardHassen withOccupant(Occupant occupant){
//        for (int index : indexes){
//            PlacedTile currentPlacedTile = placedTiles[index];
//            Tile currentTile = placedTiles[index].tile();
//            for (Zone zone : currentTile.zones()){
//                if(zone.id() == occupant.zoneId()) {
//                    //Preconditions.checkArgument(currentPlacedTile.occupant() != null);
//                    PlacedTile updatedPlacedTile = new PlacedTile(currentTile, currentPlacedTile.placer(), currentPlacedTile.rotation(), currentPlacedTile.pos(), occupant);
//
//                    PlacedTile[] updatedPlacedTiles = placedTiles.clone();
//                    updatedPlacedTiles[index] = updatedPlacedTile;
//
//                    return new BoardHassen(updatedPlacedTiles, indexes, zonePartitions, cancelledAnimals);
//                }
//            }
//        }
//        throw new IllegalArgumentException("Zone ID does not exist on the board");
//    }

    public BoardHassen withMoreCancelledAnimals(Set<Animal> newlyCancelledAnimals) {
        Set<Animal> newCancelledAnimals = new HashSet<>(cancelledAnimals);
        newCancelledAnimals.addAll(newlyCancelledAnimals);
        newCancelledAnimals = Set.copyOf(newCancelledAnimals);
        return new BoardHassen(placedTiles, indexes, zonePartitions, newCancelledAnimals);

    }

    @Override
    public boolean equals(Object objectBoard) {
        if (this == objectBoard) return true;
        if (objectBoard == null || getClass() != objectBoard.getClass()) return false;
        //transtypage
        BoardHassen board = (BoardHassen) objectBoard;
        return Arrays.equals(placedTiles, board.placedTiles) &&
                Arrays.equals(indexes, board.indexes) &&
                Objects.equals(zonePartitions, board.zonePartitions) &&
                Objects.equals(cancelledAnimals, board.cancelledAnimals);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(zonePartitions, cancelledAnimals);
        result = 31 * result + Arrays.hashCode(placedTiles);
        result = 31 * result + Arrays.hashCode(indexes);
        return result;
    }


    private int getIndexFrom(int x ,int y ){
        if (x < -12 || x > 12 || y < -12 || y > 12) {
            return -1;
        }
        return (y + 12) * 25 + (x + 12);
    }

    private List<Integer> validIndexes(int x , int y){

        List<Integer> validIndexesArray = new ArrayList<>();

        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
        for (int i = 0; i < dx.length; i++) {
            int adjX = x + dx[i];
            int adjY = y + dy[i];
            int index = getIndexFrom(adjX, adjY);

            if (index != -1) {
                validIndexesArray.add(index);
            }
        }
        return validIndexesArray;
    }


}
