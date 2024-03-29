package ch.epfl.chacun;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy;

import java.util.*;

/**
 * @author Hassen Ben Chaabane(361366), Tony Andriamampianina (363559)
 */
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

    /**
     * @author Tony Andriamampianina
     * @return copie protégée des cancelledanimals
     */
    public Set<Animal> cancelledAnimals () {return Set.copyOf(cancelledAnimals);}
    public Set<Occupant> occupants() {
        Set <Occupant> occupants = new HashSet<>();
        for (int i : indexes) {
            if (placedTiles[i].occupant() != null)
                occupants.add(placedTiles[i].occupant());
        }
        return occupants;
    }

    /**
     * @author Tony Andriamampianina
     * @return areas de meadows
     */
    public Set <Area<Zone.Meadow>> meadowAreas() {
        return zonePartitions.meadows().areas();
    }
    public Set <Area <Zone.Water>> riverSystemAreas() {
        return zonePartitions.riverSystems().areas();
    }

    private int posToNumber(Pos pos) {
        int xNorm = pos.x() + 12;
        int yNorm = pos.y() + 12;
        return (yNorm*25 + xNorm);
    }

    /**
     * Hassen Ben Chaabane
     * @param pos
     * @return placedtile
     */
    public PlacedTile tileAt(Pos pos) {
        if (pos.x() < -12 || pos.x() > 12 || pos.y() < -12 || pos.y() > 12) {
            return null;
        }
        int indexTile = (pos.y() + 12) * 25 + (pos.x() + 12);
        return placedTiles[indexTile];
    }

    /** tuile avec l'id
     * @author Hassen Ben Chaabane
     * @param tileId
     * @return placedtile
     */
    public PlacedTile tileWithId(int tileId) {

        for (int tileIndex : indexes) {
            if (placedTiles[tileIndex].id() == tileId) return placedTiles[tileIndex];
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

    /** positions pouvant accueillir une tuile
     * @author Tony Andriamampianina (363559)
     * @return set de pos
     */
    public Set <Pos> insertionPositions() {
        boolean hasNeighbour = false;
        Set<Pos> posSet = new HashSet<>();
        if (indexes.length == 0) {
            posSet.add(new Pos(0, 0));

        } else {
            for (int i = -12; i <= 12; i++) {
                for (int j = -12; j <= 12; j++) {
                    Pos pos = new Pos(i, j);
                    if (tileAt(pos) == null && tileAt(pos.neighbor(Direction.N)) != null)
                        posSet.add(pos);
                    if (tileAt(pos) == null && tileAt(pos.neighbor(Direction.E)) != null)
                        posSet.add(pos);
                    if (tileAt(pos) == null && tileAt(pos.neighbor(Direction.S)) != null)
                        posSet.add(pos);
                    if (tileAt(pos) == null && tileAt(pos.neighbor(Direction.W)) != null)
                        posSet.add(pos);
                }
            }
        } return posSet;
    }

    /** dernière tuile posée sur le board
     * @author Tony Andriamampianina (363559)
     * @return placedtile
     */
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
        return (tileAt(placedTile.pos().neighbor(direction)).side(direction.opposite()).isSameKindAs(placedTile.side(direction)));
    }

    /** possibilité d'ajouter les tiles
     * @author Tony Andriamampianina (363559)
     * @param tile
     * @return booléen
     */
    public boolean canAddTile (PlacedTile tile) {
        if (insertionPositions().contains(tile.pos())) {
            boolean canN = tileAt(tile.pos().neighbor(Direction.N)) == null || oppositeSideSameType(tile, Direction.N);
            boolean canE = tileAt(tile.pos().neighbor(Direction.E)) == null || oppositeSideSameType(tile, Direction.E);
            boolean canS = tileAt(tile.pos().neighbor(Direction.S))== null || oppositeSideSameType(tile, Direction.S);
            boolean canW = tileAt(tile.pos().neighbor(Direction.W)) == null || oppositeSideSameType(tile, Direction.W);
            return (canN && canE && canS && canW);
        } else {
            return false;
        }
    }

    /**
     * @author Tony Andriamampianina
     * @param tile
     * @return booléen
     */
    public boolean couldPlaceTile (Tile tile ) {
        for (Pos p : insertionPositions()) {
            for (Rotation rotation : Rotation.values()) {
                if (canAddTile(new PlacedTile(tile, null, rotation, p, null)))
                    return true;
            }
        } return false;
    }

    private PlacedTile neighborTile (Pos pos, Direction direction) {
        return tileAt(pos.neighbor(direction));
    }

    /** ajout nouvelle tuile et fusion des tilesides des tuiles voisines
     * @author Tony Andriamampianina
     * @param tile
     * @return board
     */

    public Board withNewTile(PlacedTile tile) {
        //si le board est vide ou la tile peut être posée
        Preconditions.checkArgument(indexes.length == 0 || canAddTile(tile));
        //maj des arrays de placedtiles et indexes
        PlacedTile [] newPT = Arrays.copyOf(this.placedTiles, this.placedTiles.length);
        newPT [posToNumber(tile.pos())] = tile;
        int [] newInd = Arrays.copyOf(this.indexes, this.indexes.length + 1);
        newInd [newInd.length-1] = posToNumber(tile.pos());

        //maj de zonepartitions
        ZonePartitions.Builder zBuilder = new ZonePartitions.Builder(zonePartitions);
        zBuilder.addTile(tile.tile());
        for (Direction direction : Direction.ALL) {
            if (neighborTile(tile.pos(), direction) != null)
                zBuilder.connectSides(tile.side(direction), neighborTile(tile.pos(), direction).side(direction.opposite()));
        }
        return new Board(newPT, newInd, zBuilder.build(), cancelledAnimals());
    }

    /** supprimer l'occupant grâce à l'id de sa zone
     * @author Tony Andriamampianina (363559)
     * @param occupant
     * @return board
     */
    public Board withoutOccupant (Occupant occupant) {
        PlacedTile[] PT = placedTiles.clone();
        int [] ind = indexes.clone();

        PlacedTile tile = tileWithId(Zone.tileId(occupant.zoneId()));
        Zone occupiedZone = tile.zoneWithId(occupant.zoneId());
        tile.withNoOccupant();

        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);
        builder.removePawn(tile.placer(), occupiedZone);

        for (int i : ind) {
            if (PT[i].id() == tile.id()) {
                PT[i] = tile;
            }
        }
        return new Board(PT, ind, builder.build(), cancelledAnimals());
    }


    /** supprimer chasseurs et pécheurs
     * @author Tony Andriamampianina (363559)
     * @param forests
     * @param rivers
     * @return
     */
    public Board withoutGatherersOrFishersIn(Set<Area<Zone.Forest>> forests, Set<Area<Zone.River>> rivers){
        PlacedTile[] PT = placedTiles.clone();
        int [] ind = indexes.clone();
        PlacedTile [] newPlacedT = new PlacedTile[placedTiles.length];

        Set <Integer> zoneIds = new HashSet<>();
        forests.forEach(area -> area.zones().forEach(zone -> zoneIds.add(zone.id())));
        rivers.forEach(area -> area.zones().forEach(zone -> zoneIds.add(zone.id())));

        ZonePartitions.Builder zBuilder = new ZonePartitions.Builder(zonePartitions);
        for (Area <Zone.Forest> forest : forests) {
            zBuilder.clearGatherers(forest);
        } for (Area <Zone.River> river : rivers) {
            zBuilder.clearFishers(river);
        }

        for (int i : ind) {
            PlacedTile placedTile = PT[i];
            if (placedTile.occupant() != null && zoneIds.contains(placedTile.id())){
                newPlacedT[i] = placedTile.withNoOccupant();
            }else {
                newPlacedT[i] = placedTile;
            }
        } return new Board(newPlacedT, ind, zBuilder.build(), cancelledAnimals());
    }

    /** méthode qui met à jour les cancelled animals
     * @author Tony Andriamampianina (363559)
     * @param newlyCancelledAnimals
     * @return
     */
    public Board withMoreCancelledAnimals (Set<Animal> newlyCancelledAnimals) {
        Set <Animal> newCancelled = new HashSet<>(cancelledAnimals());
        newCancelled.addAll(newlyCancelledAnimals);
        return new Board(placedTiles.clone(), indexes.clone(), new ZonePartitions.Builder(zonePartitions).build(), Collections.unmodifiableSet(newCancelled));
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    //**************************************************************************************************************************************
    //--------------------------------------------------------------------------------------------------------------------------------------


    /**
     * Hassen Ben Chaabane(361366)
     */


    /**
     * La méthode va chercher dans les partitions l'aire qui rempli la condition
     * @param forest
     * @return L'aire qui contient la zone forest passé en paramètre
     */
    public Area<Zone.Forest> forestArea(Zone.Forest forest) {
        return zonePartitions.forests().areaContaining(forest);
    }

    /**
     *
     * @param meadow
     * @return l'aire qui contient la zone meadow
     */
    public Area<Zone.Meadow> meadowArea(Zone.Meadow meadow) {
        return zonePartitions.meadows().areaContaining(meadow);
    }

    /**
     *
     * @param riverZone
     * @return la river qui contient la zone en question
     */
    public Area<Zone.River> riverArea(Zone.River riverZone) {
        return zonePartitions.rivers().areaContaining(riverZone);
    }

    /**
     *
     * @param water
     * @return la water Area qui contient la zone water
     */
    public Area<Zone.Water> riverSystemArea(Zone.Water water) {
        return zonePartitions.riverSystems().areaContaining(water);
    }

    /**
     *
     * @param pos
     * @param meadowZone
     * @return une Area qui contient les Zones adjacentes à la meadowZone
     */
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

    /**
     * Itère sur le tableau pour avoir les occupants qui correspondent aux attributs
     * @param player
     * @param occupantKind
     * @return Un int qui représente le nombre d'apparition du joueur
     */
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


    /**
     * Va get les aires qui appartiennent à la dernière tuile
     * @return Une area qui respecte la condition ne pas avoir de openConnection
     */
    public Set<Area<Zone.Forest>> forestsClosedByLastTile() {
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        if (indexes.length == 0 || indexes.length == 1) {
            return new HashSet<>();
        }
        for (Zone.Forest zoneForest : lastPlacedTile().forestZones()) {
            forestAreas.add(forestArea(zoneForest));
        }
        forestAreas.removeIf(ForestArea -> ForestArea.openConnections() != 0);

        return forestAreas;
    }

    /**
     * fait la meme chose que ForestClosedTile
     * @return une RiverArea qui a été fermé.
     */

    public Set<Area<Zone.River>> riversClosedByLastTile() {
        Set<Area<Zone.River>> riverAreas = new HashSet<>();

        if (indexes.length == 0) return new HashSet<>();

        if(lastPlacedTile() == null){ return riverAreas; }

        for (Zone.River river : lastPlacedTile().riverZones()) {
            riverAreas.add(riverArea(river));
        }
        riverAreas.removeIf(RiverArea -> RiverArea.openConnections() != 0 || !RiverArea.isClosed());

        return riverAreas;
    }

    /**
     *
     * @param occupant
     * @return une nouvelle Board qui ajoute l'occupant donné en attribut
     */

    public Board withOccupant(Occupant occupant) {
        int occupantTileId = Zone.tileId(occupant.zoneId());

        PlacedTile[] updatedPlacedTiles = placedTiles.clone();
        ZonePartitions.Builder b = new ZonePartitions.Builder(zonePartitions);

        for (int tileIndex : indexes) {
            if (placedTiles[tileIndex].tile().id() == occupantTileId) {
                if (placedTiles[tileIndex].occupant() != null)  throw new IllegalArgumentException("la zone est deja occupée");
                updatedPlacedTiles[tileIndex] = updatedPlacedTiles[tileIndex].withOccupant(occupant);
                b.addInitialOccupant(placedTiles[tileIndex].placer(),occupant.kind(),placedTiles[tileIndex].zoneWithId(occupant.zoneId()));
            }
        }
        return new Board(updatedPlacedTiles, indexes, b.build(), cancelledAnimals);
    }

//    public Board withMoreCancelledAnimals(Set<Animal> newlyCancelledAnimals) {
//        Set<Animal> newCancelledAnimals = new HashSet<>(cancelledAnimals);
//        newCancelledAnimals.addAll(newlyCancelledAnimals);
//        newCancelledAnimals = Set.copyOf(newCancelledAnimals);
//        return new Board(placedTiles, indexes, zonePartitions, newCancelledAnimals);
//
//    }

    /**
     *
     * @param objectBoard
     * @return
     */
    @Override
    public boolean equals(Object objectBoard) {
        if (this == objectBoard) return true;
        if (objectBoard == null || getClass() != objectBoard.getClass()) return false;
        //transtypage
        Board board = (Board) objectBoard;
        return Arrays.equals(placedTiles, board.placedTiles) &&
                Arrays.equals(indexes, board.indexes) &&
                Objects.equals(zonePartitions, board.zonePartitions) &&
                Objects.equals(cancelledAnimals, board.cancelledAnimals);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(zonePartitions, cancelledAnimals);
        result = 31 * result + Arrays.hashCode(placedTiles);
        result = 31 * result + Arrays.hashCode(indexes);
        return result;
    }

    /**
     *
     * @param x
     * @param y
     * @return -1 si les coordonnées ne sont pas valide un index si il est valide
     */
    private int getIndexFrom(int x ,int y ){
        if (x < -12 || x > 12 || y < -12 || y > 12) {
            return -1;
        }
        return (y + 12) * 25 + (x + 12);
    }

    /**
     * La méthode va check si les 8 index autour de l'adj meadow sont valides
     * @param x
     * @param y
     * @return une liste d'index qui sont dans le board
     */
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

