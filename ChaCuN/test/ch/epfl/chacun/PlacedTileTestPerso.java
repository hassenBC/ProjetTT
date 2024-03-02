package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlacedTileTestPerso {
        Zone.Meadow meadowZone1 = new Zone.Meadow(560, new ArrayList<Animal>(), null);
        Zone.Meadow meadowZone1SP = new Zone.Meadow(560, new ArrayList<Animal>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone2 = new Zone.Meadow(562, new ArrayList<Animal>(), null);
        Zone.Meadow meadowZone2SP = new Zone.Meadow(562, new ArrayList<Animal>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Forest forestZone = new Zone.Forest(561, Zone.Forest.Kind.PLAIN);
        Zone.Lake lakeZone = new Zone.Lake(568, 0, null);
        Zone.River riverZone = new Zone.River(563, 0, lakeZone);

        TileSide.Meadow meadow1 = new TileSide.Meadow(meadowZone1);
        TileSide.Meadow meadow1SP = new TileSide.Meadow(meadowZone1SP);
        TileSide.Meadow meadow2 = new TileSide.Meadow(meadowZone2);
        TileSide.Meadow meadow2SP = new TileSide.Meadow(meadowZone2SP);
        TileSide.Forest forest1 = new TileSide.Forest(forestZone);
        TileSide.River river1 = new TileSide.River(meadowZone2, riverZone, meadowZone1);

        Tile start = new Tile(56, Tile.Kind.START, meadow1, forest1, forest1, river1);
        Tile startWithSpecialPowers = new Tile(56,Tile.Kind.START,meadow1SP,forest1,forest1,river1);

        PlacedTile placedTiletest = new PlacedTile(start,PlayerColor.RED,Rotation.NONE,new Pos(0,0),null);
        PlacedTile placedTiletestRight = new PlacedTile(start,PlayerColor.RED,Rotation.RIGHT,new Pos(0,0),null);

        PlacedTile placedTileWithSpecialPowers = new PlacedTile(startWithSpecialPowers,PlayerColor.RED,Rotation.NONE,new Pos(0,0));


        Occupant occupantTestChasseur = new Occupant(Occupant.Kind.PAWN,562);
        PlacedTile getPlacedTiletestWithOccupant = new PlacedTile(start,PlayerColor.RED,Rotation.RIGHT,new Pos(0,0),occupantTestChasseur);

        @Test
        void sideTest () {
            assertEquals(meadow1, placedTiletest.side(Direction.N));
            assertEquals(river1, placedTiletest.side(Direction.W));
            assertEquals(meadow1, placedTiletestRight.side(Direction.E));
            assertEquals(river1, placedTiletestRight.side(Direction.N));
        }

        @Test
        void constructorTest () {
            assertThrows(NullPointerException.class, () -> new PlacedTile(null, null, Rotation.RIGHT, Pos.ORIGIN, new Occupant(Occupant.Kind.PAWN, 562 ) ));
            assertThrows(NullPointerException.class, () -> new PlacedTile(start, null, null, Pos.ORIGIN, new Occupant(Occupant.Kind.PAWN, 562 ) ));
            assertThrows(NullPointerException.class, () -> new PlacedTile(start, null, Rotation.RIGHT, null, new Occupant(Occupant.Kind.PAWN, 562 ) ));


            //assertThrows(NullPointerException.class, () -> new PlacedTile(new Tile(56, Tile.Kind.START, new TileSide.Meadow(new meadow)), null, Rotation.RIGHT, Pos.ORIGIN, new Occupant(Occupant.Kind.PAWN, 562 ) ));

        }

        @Test
        public void kindWorks(){
            assertEquals(Tile.Kind.START,placedTiletest.kind());

        }

        @Test
        public void zoneWithIdWorks(){
            assertThrows(IllegalArgumentException.class, ()-> placedTiletest.zoneWithId(100),"id sans zone");
            assertEquals(meadowZone1,placedTiletest.zoneWithId(560));
            assertEquals(meadowZone2,placedTiletest.zoneWithId(562));
            assertEquals(riverZone,placedTiletest.zoneWithId(563));
            assertEquals(forestZone, placedTiletest.zoneWithId(561));
        }

        @Test
        public void specialPowerZoneWorks(){
            assertNull(placedTiletest.specialPowerZone());
            assertEquals(meadowZone1SP,placedTileWithSpecialPowers.specialPowerZone());


        }

        @Test
        public void forestZoneWorks(){
            Set<Zone.Forest> zoneForestTest = new HashSet<>();
            zoneForestTest.add(forestZone);
            assertEquals(placedTiletest.forestZones(),zoneForestTest);
        }
        @Test
        public void meadowZoneWorks(){
            Set<Zone.Meadow> zonesMeadowTest = new HashSet<>();
            zonesMeadowTest.add(meadowZone1);
            zonesMeadowTest.add(meadowZone2);
            assertEquals(zonesMeadowTest,placedTiletest.meadowZones());
            //tester dans le mauvais ordre
        }
        @Test
        public void riverZoneWorks(){
            Set<Zone.River> zonesRiverTest = new HashSet<>();
            Set<Zone.River> zonesRiverCode = placedTiletest.riverZones();
            zonesRiverTest.add(riverZone);
            assertEquals(zonesRiverTest,zonesRiverCode);
        }

        @Test
        public void potentialOccupantsWorks(){
            Set<Occupant> potentialOccupantsTest = new HashSet<>();
            potentialOccupantsTest.add(new Occupant(Occupant.Kind.PAWN,560));
            potentialOccupantsTest.add(new Occupant(Occupant.Kind.PAWN,561));
            potentialOccupantsTest.add(new Occupant(Occupant.Kind.PAWN,562));
            potentialOccupantsTest.add(new Occupant(Occupant.Kind.PAWN,563));
            potentialOccupantsTest.add(new Occupant(Occupant.Kind.HUT,568));
            assertEquals(potentialOccupantsTest,placedTiletest.potentialOccupants());
        }

        @Test
        public void withOccupantWorks(){
            PlacedTile newPlacedTile = new PlacedTile(start,PlayerColor.RED,Rotation.NONE,new Pos(0,0),occupantTestChasseur);
            assertEquals(newPlacedTile,placedTiletest.withOccupant(occupantTestChasseur));
            assertThrows(IllegalArgumentException.class, ()-> getPlacedTiletestWithOccupant.withOccupant(occupantTestChasseur));
        }

        @Test
        public void withNoOccupantWorks(){
            assertEquals(new PlacedTile(start,PlayerColor.RED,Rotation.RIGHT,new Pos(0,0),null),getPlacedTiletestWithOccupant.withNoOccupant());
            //à vérifier si ça marche vraiement
            assertEquals(placedTiletest,placedTiletest.withNoOccupant());
        }

        @Test
        public void idOfZoneOccupiedByWorks(){
            assertEquals(-1,placedTiletest.idOfZoneOccupiedBy(Occupant.Kind.PAWN));
            //je suis pas sur de cette méthode
            assertEquals(562,occupantTestChasseur.zoneId());

        }



    }


