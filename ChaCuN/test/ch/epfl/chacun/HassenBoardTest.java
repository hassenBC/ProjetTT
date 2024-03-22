package ch.epfl.chacun;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HassenBoardTest {

    HassenBoardTest(){

    }

    @Test
    void tileAtWorksOnNormalCase(){
       //je vais créer un boardHassen
        PlacedTile [] placedTileArray = new PlacedTile[625];
        int[] indexes = new int[1];
        indexes[0] = 312;
        ZonePartitions mesZonesPartitions = ZonePartitions.EMPTY;
        Set<Animal> cancelledAnimals = new HashSet<>();



        var zoneN = new Zone.Forest(101, Zone.Forest.Kind.PLAIN);
        var zoneE = new Zone.Forest(102, Zone.Forest.Kind.PLAIN);
        var zoneS = new Zone.Forest(103, Zone.Forest.Kind.PLAIN);
        var zoneW = new Zone.Forest(104, Zone.Forest.Kind.PLAIN);

        var sideN = new TileSide.Forest(zoneN);
        var sideE = new TileSide.Forest(zoneE);
        var sideS = new TileSide.Forest(zoneS);
        var sideW = new TileSide.Forest(zoneW);

        var tile = new Tile(1, Tile.Kind.NORMAL, sideN, sideE, sideS, sideW);
        var placedTile = new PlacedTile(tile, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        placedTileArray[312] = placedTile;


        BoardHassen board = new BoardHassen(placedTileArray,indexes,mesZonesPartitions,cancelledAnimals);

        Assertions.assertEquals(placedTile,board.tileAt(new Pos(0,0)));

    }

    @Test
    void tileAtWorksOnNull(){
        PlacedTile [] placedTileArray = new PlacedTile[625];
        int[] indexes = new int[1];
        indexes[0] = 312;
        ZonePartitions mesZonesPartitions = ZonePartitions.EMPTY;
        Set<Animal> cancelledAnimals = new HashSet<>();



        var zoneN = new Zone.Forest(101, Zone.Forest.Kind.PLAIN);
        var zoneE = new Zone.Forest(102, Zone.Forest.Kind.PLAIN);
        var zoneS = new Zone.Forest(103, Zone.Forest.Kind.PLAIN);
        var zoneW = new Zone.Forest(104, Zone.Forest.Kind.PLAIN);

        var sideN = new TileSide.Forest(zoneN);
        var sideE = new TileSide.Forest(zoneE);
        var sideS = new TileSide.Forest(zoneS);
        var sideW = new TileSide.Forest(zoneW);

        var tile = new Tile(1, Tile.Kind.NORMAL, sideN, sideE, sideS, sideW);
        var placedTile = new PlacedTile(tile, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        placedTileArray[312] = placedTile;


        BoardHassen board = new BoardHassen(placedTileArray,indexes,mesZonesPartitions,cancelledAnimals);
        Assertions.assertNull(board.tileAt(new Pos(-13, -13)));

    }

    @Test
    void tileWithIdWorksNormalCase(){
        PlacedTile [] placedTileArray = new PlacedTile[625];
        int[] indexes = new int[1];
        indexes[0] = 312;
        ZonePartitions mesZonesPartitions = ZonePartitions.EMPTY;
        Set<Animal> cancelledAnimals = new HashSet<>();


        var zoneN = new Zone.Forest(101, Zone.Forest.Kind.PLAIN);
        var zoneE = new Zone.Forest(102, Zone.Forest.Kind.PLAIN);
        var zoneS = new Zone.Forest(103, Zone.Forest.Kind.PLAIN);
        var zoneW = new Zone.Forest(104, Zone.Forest.Kind.PLAIN);

        var sideN = new TileSide.Forest(zoneN);
        var sideE = new TileSide.Forest(zoneE);
        var sideS = new TileSide.Forest(zoneS);
        var sideW = new TileSide.Forest(zoneW);

        var tile = new Tile(10, Tile.Kind.NORMAL, sideN, sideE, sideS, sideW);
        var placedTile = new PlacedTile(tile, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        placedTileArray[312] = placedTile;


        BoardHassen board = new BoardHassen(placedTileArray,indexes,mesZonesPartitions,cancelledAnimals);

        Assertions.assertEquals(placedTile,board.tileWithId(10));

    }

    @Test
    void tileWithIdThrowsError(){
        PlacedTile [] placedTileArray = new PlacedTile[625];
        int[] indexes = new int[1];
        indexes[0] = 312;
        ZonePartitions mesZonesPartitions = ZonePartitions.EMPTY;
        Set<Animal> cancelledAnimals = new HashSet<>();



        var zoneN = new Zone.Forest(101, Zone.Forest.Kind.PLAIN);
        var zoneE = new Zone.Forest(102, Zone.Forest.Kind.PLAIN);
        var zoneS = new Zone.Forest(103, Zone.Forest.Kind.PLAIN);
        var zoneW = new Zone.Forest(104, Zone.Forest.Kind.PLAIN);

        var sideN = new TileSide.Forest(zoneN);
        var sideE = new TileSide.Forest(zoneE);
        var sideS = new TileSide.Forest(zoneS);
        var sideW = new TileSide.Forest(zoneW);

        var tile = new Tile(1, Tile.Kind.NORMAL, sideN, sideE, sideS, sideW);
        var placedTile = new PlacedTile(tile, PlayerColor.RED, Rotation.NONE, new Pos(0, 0));
        placedTileArray[312] = placedTile;



        BoardHassen board = new BoardHassen(placedTileArray,indexes,mesZonesPartitions,cancelledAnimals);

        Assertions.assertThrows(IllegalArgumentException.class, ()-> board.tileWithId(11));
    }

    @Test
    void adjacentMeadowWorksEdgeCase(){
        PlacedTile placeTileMGauche = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-12,-12));
        PlacedTile placedTileMilieu = new PlacedTile(Tiles.TILES.get(61),PlayerColor.BLUE,Rotation.NONE,new Pos(-11,-12));
        PlacedTile placedTileMDroit = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-10,-12));
        PlacedTile placeTileBasGauche = new PlacedTile(Tiles.TILES.get(26),PlayerColor.BLUE,Rotation.NONE,new Pos(-12,-11));
        PlacedTile placedTileMilieuBas = new PlacedTile(Tiles.TILES.get(40),PlayerColor.RED,Rotation.NONE,new Pos(-11,-11));
        PlacedTile placedTileBasDroit = new PlacedTile(Tiles.TILES.get(35),PlayerColor.RED,Rotation.NONE,new Pos(-10,-11));

        var a0_0 = new Animal(61_0_0, Animal.Kind.MAMMOTH);
        var z0 = new Zone.Meadow(61_0, List.of(a0_0), null);
        var z2 = new Zone.Meadow(50_2, List.of(), null);

        PlacedTile [] placedTiles = new PlacedTile[625];

        placedTiles[0] = placeTileMGauche;
        placedTiles[1] = placedTileMilieu;
        placedTiles[2] = placedTileMDroit;
        placedTiles[25] = placeTileBasGauche;
        placedTiles[26] = placedTileMilieuBas;
        placedTiles[27] = placedTileBasDroit;

        int[] indexes = new int[6];
        indexes[0] = 0;
        indexes[1] = 1;
        indexes[2] = 2;
        indexes[3] = 25;
        indexes[4] = 26;
        indexes[5] = 27;

        var emptyPartitions = new ZonePartitions(
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>());
         var b = new ZonePartitions.Builder(emptyPartitions);
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(61));
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(26));
        b.addTile(Tiles.TILES.get(40));
        b.addTile(Tiles.TILES.get(35));
        b.connectSides(Tiles.TILES.get(50).e(),Tiles.TILES.get(61).w());
        b.connectSides(Tiles.TILES.get(40).n(),Tiles.TILES.get(61).s());
        b.connectSides(Tiles.TILES.get(50).w(),Tiles.TILES.get(61).e());
        b.addInitialOccupant(PlayerColor.RED, Occupant.Kind.PAWN,z2);
        var partitions = b.build();

        BoardHassen boardTest = new BoardHassen(placedTiles,indexes,partitions,new HashSet<>());

        Area<Zone.Meadow> expectedArea = partitions.meadows().areaContaining(z0);
        Set<Zone.Meadow> trueExpectedZones = new HashSet<>(expectedArea.zones());
        trueExpectedZones.remove(z0);

        Area<Zone.Meadow> expectedAreaTrueTest = new Area<>(trueExpectedZones,expectedArea.occupants(),0);
        System.out.println(expectedAreaTrueTest.toString());

        Assertions.assertEquals(expectedAreaTrueTest,boardTest.adjacentMeadow(new Pos(-11,-12),z0));

    }

    @Test
    void occupantCountWorksOnNormalCase(){
        Occupant occupant1 = new Occupant(Occupant.Kind.PAWN,50);
        Occupant occupant2 = new Occupant(Occupant.Kind.PAWN,61);
        PlacedTile placeTileMGauche = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-12,-12),occupant1);
        PlacedTile placedTileMilieu = new PlacedTile(Tiles.TILES.get(61),PlayerColor.BLUE,Rotation.NONE,new Pos(-11,-12),occupant2);
        PlacedTile placedTileMDroit = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-10,-12),occupant1);
        PlacedTile placeTileBasGauche = new PlacedTile(Tiles.TILES.get(26),PlayerColor.BLUE,Rotation.NONE,new Pos(-12,-11));
        PlacedTile placedTileMilieuBas = new PlacedTile(Tiles.TILES.get(40),PlayerColor.RED,Rotation.NONE,new Pos(-11,-11));
        PlacedTile placedTileBasDroit = new PlacedTile(Tiles.TILES.get(35),PlayerColor.RED,Rotation.NONE,new Pos(-10,-11));
        PlacedTile [] placedTiles = new PlacedTile[625];

        placedTiles[0] = placeTileMGauche;
        placedTiles[1] = placedTileMilieu;
        placedTiles[2] = placedTileMDroit;
        placedTiles[25] = placeTileBasGauche;
        placedTiles[26] = placedTileMilieuBas;
        placedTiles[27] = placedTileBasDroit;

        int[] indexes = new int[6];
        indexes[0] = 0;
        indexes[1] = 1;
        indexes[2] = 2;
        indexes[3] = 25;
        indexes[4] = 26;
        indexes[5] = 27;

        var emptyPartitions = new ZonePartitions(
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>());
        var b = new ZonePartitions.Builder(emptyPartitions);
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(61));
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(26));
        b.addTile(Tiles.TILES.get(40));
        b.addTile(Tiles.TILES.get(35));
        b.connectSides(Tiles.TILES.get(50).e(),Tiles.TILES.get(61).w());
        b.connectSides(Tiles.TILES.get(40).n(),Tiles.TILES.get(61).s());
        b.connectSides(Tiles.TILES.get(50).w(),Tiles.TILES.get(61).e());
        var partitions = b.build();


        BoardHassen boardTest = new BoardHassen(placedTiles,indexes,partitions,new HashSet<>());
        Assertions.assertEquals(2,boardTest.occupantCount(PlayerColor.RED, Occupant.Kind.PAWN));


    }

    @Test
    void occupantCountWorksOnAllNullCase(){
        PlacedTile placeTileMGauche = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-12,-12));
        PlacedTile placedTileMilieu = new PlacedTile(Tiles.TILES.get(61),PlayerColor.BLUE,Rotation.NONE,new Pos(-11,-12));
        PlacedTile placedTileMDroit = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-10,-12));
        PlacedTile placeTileBasGauche = new PlacedTile(Tiles.TILES.get(26),PlayerColor.BLUE,Rotation.NONE,new Pos(-12,-11));
        PlacedTile placedTileMilieuBas = new PlacedTile(Tiles.TILES.get(40),PlayerColor.RED,Rotation.NONE,new Pos(-11,-11));
        PlacedTile placedTileBasDroit = new PlacedTile(Tiles.TILES.get(35),PlayerColor.RED,Rotation.NONE,new Pos(-10,-11));
        PlacedTile [] placedTiles = new PlacedTile[625];

        placedTiles[0] = placeTileMGauche;
        placedTiles[1] = placedTileMilieu;
        placedTiles[2] = placedTileMDroit;
        placedTiles[25] = placeTileBasGauche;
        placedTiles[26] = placedTileMilieuBas;
        placedTiles[27] = placedTileBasDroit;

        int[] indexes = new int[6];
        indexes[0] = 0;
        indexes[1] = 1;
        indexes[2] = 2;
        indexes[3] = 25;
        indexes[4] = 26;
        indexes[5] = 27;

        var emptyPartitions = new ZonePartitions(
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>());
        var b = new ZonePartitions.Builder(emptyPartitions);
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(61));
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(26));
        b.addTile(Tiles.TILES.get(40));
        b.addTile(Tiles.TILES.get(35));
        b.connectSides(Tiles.TILES.get(50).e(),Tiles.TILES.get(61).w());
        b.connectSides(Tiles.TILES.get(40).n(),Tiles.TILES.get(61).s());
        b.connectSides(Tiles.TILES.get(50).w(),Tiles.TILES.get(61).e());
        var partitions = b.build();


        BoardHassen boardTest = new BoardHassen(placedTiles,indexes,partitions,new HashSet<>());
        Assertions.assertEquals(0,boardTest.occupantCount(PlayerColor.RED, Occupant.Kind.PAWN));


    }

    @Test
    void withOccupantWorksNormalCase(){
        Occupant occupant1 = new Occupant(Occupant.Kind.PAWN,50_2);

        PlacedTile placeTileMGauche = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-12,-12));
        PlacedTile placedTileMilieu = new PlacedTile(Tiles.TILES.get(61),PlayerColor.BLUE,Rotation.NONE,new Pos(-11,-12));
        PlacedTile placedTileMDroit = new PlacedTile(Tiles.TILES.get(50),PlayerColor.RED,Rotation.NONE,new Pos(-10,-12));
        PlacedTile placeTileBasGauche = new PlacedTile(Tiles.TILES.get(26),PlayerColor.BLUE,Rotation.NONE,new Pos(-12,-11));
        PlacedTile placedTileMilieuBas = new PlacedTile(Tiles.TILES.get(40),PlayerColor.RED,Rotation.NONE,new Pos(-11,-11));
        PlacedTile placedTileBasDroit = new PlacedTile(Tiles.TILES.get(35),PlayerColor.RED,Rotation.NONE,new Pos(-10,-11));
        PlacedTile [] placedTiles = new PlacedTile[625];

        placedTiles[0] = placeTileMGauche;
        placedTiles[1] = placedTileMilieu;
        placedTiles[2] = placedTileMDroit;
        placedTiles[25] = placeTileBasGauche;
        placedTiles[26] = placedTileMilieuBas;
        placedTiles[27] = placedTileBasDroit;

        int[] indexes = new int[6];
        indexes[0] = 0;
        indexes[1] = 1;
        indexes[2] = 2;
        indexes[3] = 25;
        indexes[4] = 26;
        indexes[5] = 27;

        var emptyPartitions = new ZonePartitions(
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>(),
                new ZonePartition<>());
        var b = new ZonePartitions.Builder(emptyPartitions);
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(61));
        b.addTile(Tiles.TILES.get(50));
        b.addTile(Tiles.TILES.get(26));
        b.addTile(Tiles.TILES.get(40));
        b.addTile(Tiles.TILES.get(35));
        b.connectSides(Tiles.TILES.get(50).e(),Tiles.TILES.get(61).w());
        b.connectSides(Tiles.TILES.get(40).n(),Tiles.TILES.get(61).s());
        b.connectSides(Tiles.TILES.get(50).w(),Tiles.TILES.get(61).e());
        var partitions = b.build();


        BoardHassen boardTest = new BoardHassen(placedTiles,indexes,partitions,new HashSet<>());



    }


//    @Test
//    void myTileAtTest() {
//        // Initialization of some placedTiles
//        var l0 = new Zone.Lake(4_8, 1, null);
//        var z0 = new Zone.Meadow(4_0, List.of(), null);
//        var z1 = new Zone.River(4_1, 0, l0);
//        var a2_0 = new Animal(4_2_0, Animal.Kind.DEER);
//        var z2 = new Zone.Meadow(4_2, List.of(a2_0), null);
//        var z3 = new Zone.Forest(4_3, Zone.Forest.Kind.PLAIN);
//        var z4 = new Zone.Meadow(4_4, List.of(), null);
//        var z5 = new Zone.River(4_5, 0, l0);
//        var sN = new TileSide.River(z0, z1, z2);
//        var sE = new TileSide.Forest(z3);
//        var sS = new TileSide.River(z4, z5, z0);
//        var sW = new TileSide.Meadow(z0);
//        var tile4 = new Tile(4, Tile.Kind.NORMAL, sN, sE, sS, sW);
//        var placedTile4 = new PlacedTile(tile4, PlayerColor.RED, Rotation.NONE, new Pos(0, 1), null);
//
//        var l00 = new Zone.Lake(2_8, 3, null);
//        var z00 = new Zone.Meadow(2_0, List.of(), null);
//        var z11 = new Zone.River(2_1, 0, l00);
//        var z22 = new Zone.Meadow(2_2, List.of(), null);
//        var z33 = new Zone.Forest(2_3, Zone.Forest.Kind.PLAIN);
//
//        var z55 = new Zone.River(2_5, 0, l00);
//        var sNN = new TileSide.River(z00, z55, z22);
//        var sEE = new TileSide.Forest(z33);
//        var sSS = new TileSide.River(z22, z11, z00);
//        var sWW = new TileSide.Meadow(z00);
//        var tile2 = new Tile(2, Tile.Kind.NORMAL, sNN, sEE, sSS, sWW);
//        var placedTile2 = new PlacedTile(tile2, PlayerColor.BLUE, Rotation.NONE, new Pos(1, 0), null);
//
//        var l000 = new Zone.Lake(3_8, 1, null);
//        var z000 = new Zone.Meadow(3_0, List.of(), null);
//        var z111 = new Zone.River(3_1, 0, l000);
//        var a2_00 = new Animal(3_2_0, Animal.Kind.DEER);
//        var z222 = new Zone.Meadow(3_2, List.of(a2_00), null);
//        var z333 = new Zone.Forest(3_3, Zone.Forest.Kind.PLAIN);
//        var z555 = new Zone.River(3_5, 0, l000);
//        var sNNN = new TileSide.River(z000, z111, z222);
//        var sEEEE = new TileSide.Meadow(z222);
//        var sSSSS = new TileSide.River(z222, z555, z000);
//        var sWWW = new TileSide.Forest(z333);
//        var tile3 = new Tile(3, Tile.Kind.NORMAL, sNNN, sEEEE, sSSSS, sWWW);
//        var placedTile3 = new PlacedTile(tile3, PlayerColor.GREEN, Rotation.NONE, new Pos(1, 1), null);
//
//        var meadow = new Zone.Meadow(1_0, List.of(), null);
//        var meadow2 = new Zone.Meadow(1_2, List.of(), null);
//        var river = new Zone.River(1_1, 0, null);
//        var north = new TileSide.River(meadow, river, meadow2);
//        var est = new TileSide.Meadow(meadow2);
//        var sud = new TileSide.River(meadow2, river, meadow);
//        var west = new TileSide.Meadow(meadow);
//        var tile1 = new Tile(1, Tile.Kind.NORMAL, north, est, sud, west);
//        var occupant = new Occupant(Occupant.Kind.PAWN, 10);
//        var placedTile1 = new PlacedTile(tile1, PlayerColor.YELLOW, Rotation.NONE, new Pos(0, 0), occupant);
//
//
//        /**ZonePartitions.Builder builder = new ZonePartitions.Builder(ZonePartitions.EMPTY);
//
//         builder.addTile(tile1);
//         builder.addTile(tile2);
//         builder.addTile(tile3);
//         builder.addTile(tile4);
//         builder.connectSides(tile1.e(), tile2.w());
//         builder.connectSides(tile1.n(), tile4.s());
//         builder.connectSides(tile2.n(), tile3.s());
//         builder.connectSides(tile3.w(), tile4.e());
//         builder.addInitialOccupant(PlayerColor.YELLOW, Occupant.Kind.PAWN, meadow);
//         builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.PAWN, z22);
//         builder.addInitialOccupant(PlayerColor.BLUE, Occupant.Kind.PAWN, z00);
//         builder.addInitialOccupant(PlayerColor.RED, Occupant.Kind.PAWN, z0);
//         */
//        // ZonePartitions zonePartitions = builder.build();
//
//        BoardHassen board = Board.EMPTY;
//        board.withNewTile(placedTile1);
//        board.withNewTile(placedTile2);
//        board.withNewTile(placedTile3);
//        board.withNewTile(placedTile4);
//
//        //board = new Board(placedTiles,tileIndex,zonePartitions,Set.of());
//
//        PlacedTile placedTile11 = board.tileAt(new Pos(0, 0));
//        PlacedTile placedTile22 = board.tileAt(new Pos(1, 0));
//        PlacedTile placedTile33 = board.tileAt(new Pos(1, 1));
//        PlacedTile placedTile44 = board.tileAt(new Pos(0, 0));
//        PlacedTile nullPlacedTileInboard1 = board.tileAt(new Pos(2, 0));
//        PlacedTile nullPlacedTileInboard2 = board.tileAt(new Pos(3, 8));
//        PlacedTile nullPlacedTileInboard3 = board.tileAt(new Pos(5, 11));
//        PlacedTile nullPlacedTileOutsideBoard1 = board.tileAt(new Pos(13, 13));
//        PlacedTile nullPlacedTileOutsideBoard2 = board.tileAt(new Pos(0, 45));
//        PlacedTile nullPlacedTileOutsideBoard3 = board.tileAt(new Pos(-16, 0));
//        PlacedTile nullPlacedTileOutsideBoard4 = board.tileAt(new Pos(-26, 36));
//        PlacedTile nullPlacedTileOutsideBoard5 = board.tileAt(new Pos(-26, -36));
//
//        Assertions.assertEquals(placedTile1, placedTile11);
//
//
//    }
}
