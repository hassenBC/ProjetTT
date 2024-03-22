package ch.epfl.chacun;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTestTony {
    List<Tile> tiles = Tiles.TILES;
    PlacedTile topLeft() {
        return new PlacedTile(tiles.get(0), null, Rotation.NONE, new Pos(-12, -12));
    }
    PlacedTile topRight(){
        return new PlacedTile(tiles.get(1), null, Rotation.NONE, new Pos(12, -12));}

    PlacedTile bottomLeft() {
        return new PlacedTile(tiles.get(2), null, Rotation.NONE, new Pos(-12, 12));
    }
    PlacedTile bottomRight (){
        return new PlacedTile(tiles.get(3), null, Rotation.NONE, new Pos(12,12));
    }
    PlacedTile start() {
        return new PlacedTile(tiles.get(56), null, Rotation.NONE, new Pos(0, 0));
    }
    PlacedTile normal() {
        return new PlacedTile(tiles.get(4), null, Rotation.NONE, new Pos(2, 0));
    }
    Board boardTopLeft() {
        PlacedTile [] placedTiles = new PlacedTile[625];
        placedTiles [0] = topLeft();
        ZonePartitions.Builder zonePartitions = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        zonePartitions.addTile(tiles.get(0));
        Board board = Board.EMPTY;
        board.withNewTile(placedTiles[0]);
        return board;
    }
    /**Board boardTopRight() {
        ZonePartitions.Builder zonePartitions = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        zonePartitions.addTile(tiles.get(1));
        Board board = Board.EMPTY;
        board.withNewTile(topRight());
        return board;
    }
    Board boardBottomLeft() {
        ZonePartitions.Builder zonePartitions = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        zonePartitions.addTile(tiles.get(2));
        Board board = Board.EMPTY;
        board.withNewTile(bottomLeft());
        return board;
    }
    Board boardBottomRight() {
        ZonePartitions.Builder zonePartitions = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        zonePartitions.addTile(tiles.get(3));
        Board board = Board.EMPTY;
        board.withNewTile(bottomRight());
        return board;
    }*/

    Board withNewTileStart() {
        ZonePartitions.Builder zonePartitions = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        zonePartitions.addTile(tiles.get(56));
        Board board = Board.EMPTY;
        board.withNewTile(start());
        return board;
    }


    void insertionPosEmptyBoard () {
        Board board = Board.EMPTY;
        Set<Pos> posSet = new HashSet<>();
        for (int i = -12; i < 13; i++) {
            for (int j = -12 ;j < 13; j++) {
                posSet.add(new Pos(i, j));
            }
        }
        assertEquals(posSet, board.insertionPositions());
    }
    void insertPosEdges(){
        Set<Pos> posSet = new HashSet<>();
        Set<Pos> posSet2 = new HashSet<>();
        Set<Pos> posSet3 = new HashSet<>();
        Set<Pos> posSet4 = new HashSet<>();

        posSet.add(new Pos(-11, -12));
        posSet.add(new Pos(-12, -11));
        posSet2.add(new Pos(11, -12 ));
        posSet2.add(new Pos(12, -11 ));
        posSet3.add(new Pos(-12, 11 ));
        posSet3.add(new Pos(-11, 12 ));
        posSet4.add(new Pos(11, 12 ));
        posSet4.add(new Pos(12, 11 ));

      //  Board

    }


}
