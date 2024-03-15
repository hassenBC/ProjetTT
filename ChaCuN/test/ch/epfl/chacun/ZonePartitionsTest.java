package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZonePartitionsTest {

    Tile getStartTile () {
        var meadow0 = new Zone.Meadow(560, new ArrayList<>(), null);
        var forest1 = new Zone.Forest(561, Zone.Forest.Kind.PLAIN);
        var meadow2 = new Zone.Meadow(562, new ArrayList<>(), null);
        var lake8 = new Zone.Lake(568, 0, null);
        var river1 = new Zone.River(563, 0, lake8);

        var sn = new TileSide.Meadow(meadow0);
        var se = new TileSide.Forest(forest1);
        var ss = new TileSide.Forest(forest1);
        var sw = new TileSide.River(meadow2, river1, meadow0);

        return new Tile(56, Tile.Kind.START, sn, se, ss, sw);
    }
    ZonePartitions.Builder getStartPartition () {
        var meadow0 = new Zone.Meadow(560, new ArrayList<>(), null);
        var forest1 = new Zone.Forest(561, Zone.Forest.Kind.PLAIN);
        var meadow2 = new Zone.Meadow(562, new ArrayList<>(), null);
        var lake8 = new Zone.Lake(568, 0, null);
        var river1 = new Zone.River(563, 0, lake8);

        var meadowArea = new Area<Zone.Meadow>(Set.of(meadow0), List.of(), 2 );
        var forestArea = new Area <Zone.Forest> (Set.of(forest1), List.of(), 2);
        var meadowArea2 = new Area <Zone.Meadow> (Set.of(meadow2), List.of(), 1);
        var riverArea = new Area <Zone.River> (Set.of(river1), List.of(), 1);
        var RSArea = new Area <Zone.Water> (Set.of(river1,lake8), List.of(), 1);

        var partition = new ZonePartitions(new ZonePartition<Zone.Forest>(Set.of(forestArea)), new ZonePartition<Zone.Meadow>(Set.of(meadowArea, meadowArea2)), new ZonePartition<Zone.River>(Set.of(riverArea)), new ZonePartition<Zone.Water>(Set.of(RSArea))
        );
        return new ZonePartitions.Builder(partition);

    }
    ZonePartitions.Builder getTwoRiversLakeBuilder () {
        var meadow0 = new Zone.Meadow(1_0, new ArrayList<>(), null);
        var lake8 = new Zone.Lake(1_8, 3, null);
        var river1 = new Zone.River(1_1, 0, lake8);
        var meadow2 = new Zone.Meadow(1_2, new ArrayList<>(), null);
        var forest3 = new Zone.Forest(1_3, Zone.Forest.Kind.PLAIN);
        var meadow4 = new Zone.Meadow(1_4, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        var river2 = new Zone.River(1_5, 3, lake8);

        var meadowArea = new Area<Zone.Meadow>(Set.of(meadow0), new ArrayList<>(), 3);
        var meadowArea2 = new Area<Zone.Meadow>(Set.of(meadow2), List.of(), 1);
        var riverArea = new Area <Zone.River>(Set.of(river1), new ArrayList<>(), 1);
        var riverSystemArea = new Area <Zone.Water> (Set.of(river1, river2, lake8), new ArrayList<>(), 2);
        var forestArea = new Area<Zone.Forest>(Set.of(forest3), new ArrayList<>(), 1);
        var meadowArea3 = new Area <Zone.Meadow> (Set.of(meadow4), new ArrayList<>(), 1);
        var riverArea2 = new Area <Zone.River> (Set.of(river2), new ArrayList<>(), 1);

        var partition = new ZonePartitions(new ZonePartition<Zone.Forest>(Set.of(forestArea)), new ZonePartition<Zone.Meadow>(Set.of(meadowArea, meadowArea2, meadowArea3)), new ZonePartition<Zone.River>(Set.of(riverArea, riverArea2)), new ZonePartition<Zone.Water>(Set.of(riverSystemArea)
        ));

        return new ZonePartitions.Builder(partition);
    }
    ZonePartitions.Builder fusedBuilder () {
        var meadow0 = new Zone.Meadow(560, new ArrayList<>(), null);
        var forest1 = new Zone.Forest(561, Zone.Forest.Kind.PLAIN);
        var meadow2 = new Zone.Meadow(562, new ArrayList<>(), null);
        var lake8 = new Zone.Lake(568, 0, null);
        var river1 = new Zone.River(563, 0, lake8);

        var meadowArea = new Area<Zone.Meadow>(Set.of(meadow0), List.of(), 2 );
        var forestArea = new Area <Zone.Forest> (Set.of(forest1), List.of(), 2);
        var meadowArea2 = new Area <Zone.Meadow> (Set.of(meadow2), List.of(), 1);
        var riverArea = new Area <Zone.River> (Set.of(river1), List.of(), 1);
        var RSArea = new Area <Zone.Water> (Set.of(river1,lake8), List.of(), 1);


        var m0 = new Zone.Meadow(1_0, new ArrayList<>(), null);
        var l8 = new Zone.Lake(1_8, 3, null);
        var r1 = new Zone.River(1_1, 0, l8);
        var m2 = new Zone.Meadow(1_2, new ArrayList<>(), null);
        var forest3 = new Zone.Forest(1_3, Zone.Forest.Kind.PLAIN);
        var meadow4 = new Zone.Meadow(1_4, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        var river2 = new Zone.River(1_5, 3, l8);

        var mArea = new Area<Zone.Meadow>(Set.of(m0), new ArrayList<>(), 3);
        var mArea2 = new Area<Zone.Meadow>(Set.of(m2), List.of(), 1);
        var rArea = new Area <Zone.River>(Set.of(r1), new ArrayList<>(), 1);
        var riverSystemArea = new Area <Zone.Water> (Set.of(r1, river2, l8), new ArrayList<>(), 2);
        var fArea = new Area<Zone.Forest>(Set.of(forest3), new ArrayList<>(), 1);
        var mArea3 = new Area <Zone.Meadow> (Set.of(meadow4), new ArrayList<>(), 1);
        var rArea2 = new Area <Zone.River> (Set.of(river2), new ArrayList<>(), 1);
        var partition = new ZonePartitions(new ZonePartition<Zone.Forest>(Set.of(forestArea, fArea)), new ZonePartition<Zone.Meadow>(Set.of(meadowArea, meadowArea2, mArea, mArea2, mArea3)), new ZonePartition<Zone.River>(Set.of(riverArea, rArea, rArea2)), new ZonePartition<Zone.Water>(Set.of(RSArea, riverSystemArea)));
        return new ZonePartitions.Builder(partition);
    }
    Tile getTwoRiversLakeTile () {
        var l0 = new Zone.Lake(1_8, 3, null);
        var z0 = new Zone.Meadow(1_0, List.of(), null);
        var z2 = new Zone.Meadow(1_2, List.of(), null);
        var z4 = new Zone.Meadow(1_4, List.of(), Zone.SpecialPower.PIT_TRAP);

        var z1 = new Zone.River(1_1, 0, l0);
        var z5 = new Zone.River(1_5, 3, l0);

        var z3 = new Zone.Forest(1_3, Zone.Forest.Kind.PLAIN);

        var sN = new TileSide.Meadow(z0);
        var sE = new TileSide.River(z0, z1, z2);
        var sS = new TileSide.Forest(z3);
        var sW = new TileSide.River(z4, z5, z0);

        return new Tile(1, Tile.Kind.NORMAL, sN, sE, sS, sW);

    }
    Tile riverMeadows () {
        var z0 = new Zone.Meadow(1_0, List.of(), null);
        var z1 = new Zone.Meadow(1_2, List.of(), null);
        var r1 = new Zone.River(1_1, 2, null);

        var sN = new TileSide.Meadow(z0);
        var sE = new TileSide.River(z0, r1, z1);
        var sS = new TileSide.Meadow(z1);
        var sW = new TileSide.River(z1, r1, z0);

        return new Tile(1, Tile.Kind.NORMAL, sN, sE, sS, sW);
    }
    ZonePartitions.Builder riverMeadowsBuilder () {
        var z0 = new Zone.Meadow(1_0, List.of(), null);
        var z1 = new Zone.Meadow(1_2, List.of(), null);
        var r1 = new Zone.River(1_1, 2, null);

        var areameadow1 = new Area<Zone.Meadow>(Set.of(z0), List.of(), 3);
        var areameadow2 = new Area <Zone.Meadow> (Set.of(z1), List.of(), 3);
        var riverArea = new Area <Zone.River> (Set.of(r1), List.of(), 2);
        var riverSystemArea = new Area <Zone.Water> (Set.of(r1), List.of(), 2);
        var partition = new ZonePartitions(new ZonePartition<Zone.Forest>(Set.of()), new ZonePartition<Zone.Meadow>(Set.of(areameadow1, areameadow2)), new ZonePartition<Zone.River>(Set.of(riverArea)), new ZonePartition<Zone.Water>(Set.of(riverSystemArea)));

        return new ZonePartitions.Builder(partition);
    }

    @Test


    void addTileTest () {
        var z = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        z.addTile(getStartTile());
        var x = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        x.addTile(getTwoRiversLakeTile());
        var y = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        y.addTile(riverMeadows());
        var w = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        w.addTile(getStartTile());
        w.addTile(getTwoRiversLakeTile());
        var v = new ZonePartitions.Builder(ZonePartitions.EMPTY);
        v.addTile(riverMeadows());



        assertEquals(riverMeadowsBuilder().build(), y.build());
        assertEquals(getStartPartition().build(), z.build());
        assertEquals(getTwoRiversLakeBuilder().build(), x.build());
        assertEquals(fusedBuilder().build(), w.build());
        assertEquals(riverMeadowsBuilder().build(), v.build());

    }
    @Test
    void connectSidesExceptionTest() {
        Tile tile = getStartTile();
        ZonePartitions.Builder Z = getStartPartition();
        assertThrows(IllegalArgumentException.class, () -> Z.connectSides(tile.s(), tile.n()));
        assertThrows(IllegalArgumentException.class, () -> Z.connectSides(tile.e(), tile.w()));
    }
    void connectTwoRiverWithLake () {

    }

    @Test
    void addTileWorksOnNonTrivialCase() {
        //Création d'une liste d'occupants (PlayerColor)
        List<PlayerColor> occupants = new ArrayList<>();
        Collections.addAll(occupants, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN);
        //Création 2 listes d'animaux
        List<Animal> animalsList1 = new ArrayList<>();
        Collections.addAll(animalsList1, new Animal(2, Animal.Kind.TIGER), new Animal(1, Animal.Kind.DEER));
        List<Animal> animalsList2 = new ArrayList<>();
        Collections.addAll(animalsList2, new Animal(3, Animal.Kind.AUROCHS), new Animal(4, Animal.Kind.MAMMOTH));

        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, occupants, 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, occupants, 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Creation Aire 1 de meadows
        Zone.Meadow meadowZone = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone1 = new Zone.Meadow(3, animalsList1, null);
        Set<Zone.Meadow> meadowSet = new HashSet<>();
        Collections.addAll(meadowSet, meadowZone, meadowZone1);
        Area<Zone.Meadow> meadowArea1 = new Area<>(meadowSet, new ArrayList<>(), 3);
        //Création d'ire 2 de meadows
        Zone.Meadow meadowZone2 = new Zone.Meadow(4, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone3 = new Zone.Meadow(6, animalsList2, Zone.SpecialPower.PIT_TRAP);
        Set<Zone.Meadow> meadowSet2 = new HashSet<>();
        Collections.addAll(meadowSet2, meadowZone2, meadowZone3);
        Area<Zone.Meadow> meadowArea2 = new Area<>(meadowSet2, occupants, 3);
        //Création d'un set d'aires de meadows
        Set<Area<Zone.Meadow>> meadowAreas = new HashSet<>();
        Collections.addAll(meadowAreas, meadowArea1, meadowArea2);

        //Création Aire 1 de River
        Zone.Lake lake1 = new Zone.Lake(8, 3, Zone.SpecialPower.LOGBOAT);
        Zone.River river1 = new Zone.River(1, 3, null);
        Zone.River river2 = new Zone.River(3, 2, lake1);
        Set<Zone.River> riverZonesSet1 = new HashSet<>();
        Collections.addAll(riverZonesSet1, river1, river2);
        Area<Zone.River> riverArea1 = new Area<>(riverZonesSet1, occupants, 3);
        //Création d'un set d'aires de River
        Set<Area<Zone.River>> riverAreas = new HashSet<>();
        Collections.addAll(riverAreas, riverArea1);

        //Création Aire 1 riverSystem
        Zone.Lake lake2 = new Zone.Lake(9, 3, Zone.SpecialPower.RAFT);
        Zone.River river3 = new Zone.River(4, 3, lake2);
        Set<Zone.Water> waterZonesSet1 = new HashSet<>();
        Collections.addAll(waterZonesSet1, lake2, river3);
        Area<Zone.Water> riverSystemArea1 = new Area<>(waterZonesSet1, new ArrayList<>(), 2);
        //Création Aire 2 riverSystem
        Set<Zone.Water> waterZonesSet2 = new HashSet<>();
        Collections.addAll(waterZonesSet2, lake1, river2);
        Area<Zone.Water> riverSystemArea2 = new Area<>(waterZonesSet2, new ArrayList<>(), 2);
        //Création set d'aires de riverSystem
        Set<Area<Zone.Water>> riverSystemAreas = new HashSet<>();
        Collections.addAll(riverSystemAreas, riverSystemArea1, riverSystemArea2);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>(meadowAreas);
        ZonePartition<Zone.River> riverZP = new ZonePartition<>(riverAreas);
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>(riverSystemAreas);

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        // Création des bords de la tuile
        Zone.Forest forestNorth = new Zone.Forest(0, Zone.Forest.Kind.PLAIN);
        TileSide north = new TileSide.Forest(forestNorth);
        Zone.Meadow meadowEast = new Zone.Meadow(1, new ArrayList<>(), null);
        TileSide east = new TileSide.Meadow(meadowEast);
        Zone.Lake lake = new Zone.Lake(10, 1, null);
        Zone.River riverSouth = new Zone.River(3, 3, lake);
        Zone.Meadow meadowSouth1 = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        Zone.Meadow meadowSouth2 = new Zone.Meadow(4, animalsList1, null);
        TileSide south = new TileSide.River(meadowSouth1, riverSouth, meadowSouth2);
        Zone.Forest forestWest = new Zone.Forest(5, Zone.Forest.Kind.PLAIN);
        TileSide west = new TileSide.Forest(forestWest);
        //Création de la tuile à ajouter sur le plateau
        Tile tile = new Tile(3, Tile.Kind.NORMAL, north, east, south, west);

        //Expected forest partition
        Set<Area<Zone.Forest>> forests = new HashSet<>();
        forests.addAll(forestAreas);
        Set<Zone.Forest> North = new HashSet<>(Collections.singleton(forestNorth));
        Set<Zone.Forest> West = new HashSet<>(Collections.singleton(forestWest));
        Collections.addAll(forests, new Area<>(North, new ArrayList<>(),1)
                , new Area<>(West, new ArrayList<>(), 1));
        ZonePartition<Zone.Forest> expectedForestPartition = new ZonePartition<>(forests);
        //Expected meadow partition
        Set<Area<Zone.Meadow>> meadows = new HashSet<>();
        meadows.addAll(meadowAreas);
        Set<Zone.Meadow> East = new HashSet<>(Collections.singleton(meadowEast));
        Set<Zone.Meadow> South1 = new HashSet<>(Collections.singleton(meadowSouth1));
        Set<Zone.Meadow> South2 = new HashSet<>(Collections.singleton(meadowSouth2));
        Collections.addAll(meadows, new Area<>(East, new ArrayList<>(), 1),
                new Area<>(South1, new ArrayList<>(), 1),
                new Area<>(South2, new ArrayList<>(), 1));
        ZonePartition<Zone.Meadow> expectedMeadowPartition = new ZonePartition<>(meadows);
        //Expected river partition
        Set<Area<Zone.River>> rivers = new HashSet<>();
        rivers.addAll(riverAreas);
        Set<Zone.River> South = new HashSet<>(Collections.singleton(riverSouth));
        Collections.addAll(rivers, new Area<>(South,new ArrayList<>(), 1));
        ZonePartition<Zone.River> expectedRiverPartition = new ZonePartition<>(rivers);
        //Expected riverSystem partition
        Set<Area<Zone.Water>> riverSystems = new HashSet<>();
        riverSystems.addAll(riverSystemAreas);
        Set<Zone.Water> System = new HashSet<>();
        System.add(riverSouth);
        System.add(lake);
        riverSystems.add(new Area<>(System, new ArrayList<>(), 1));
        ZonePartition<Zone.Water> expectedRiverSystemPartition = new ZonePartition<>(riverSystems);

        //Actual
        builder.addTile(tile);
        ZonePartitions actual = builder.build();

        assertEquals(expectedForestPartition, actual.forests());
        assertEquals(expectedMeadowPartition, actual.meadows());
        assertEquals(expectedRiverPartition, actual.rivers());
        assertEquals(expectedRiverSystemPartition, actual.riverSystems());
    }

    @Test
    void addTileWorksWhenZonePartitionsIsEmpty() {
        //Création 2 listes d'animaux
        List<Animal> animalsList1 = new ArrayList<>();
        Collections.addAll(animalsList1, new Animal(2, Animal.Kind.TIGER), new Animal(1, Animal.Kind.DEER));
        List<Animal> animalsList2 = new ArrayList<>();
        Collections.addAll(animalsList2, new Animal(3, Animal.Kind.AUROCHS), new Animal(4, Animal.Kind.MAMMOTH));

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>();
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>();
        ZonePartition<Zone.River> riverZP = new ZonePartition<>();
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        // Création des bords de la tuile
        Zone.Forest forestNorth = new Zone.Forest(0, Zone.Forest.Kind.PLAIN);
        TileSide north = new TileSide.Forest(forestNorth);
        Zone.Meadow meadowEast = new Zone.Meadow(1, new ArrayList<>(), null);
        TileSide east = new TileSide.Meadow(meadowEast);
        Zone.Lake lake = new Zone.Lake(10, 1, null);
        Zone.River riverSouth = new Zone.River(3, 3, lake);
        Zone.Meadow meadowSouth1 = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        Zone.Meadow meadowSouth2 = new Zone.Meadow(4, animalsList1, null);
        TileSide south = new TileSide.River(meadowSouth1, riverSouth, meadowSouth2);
        Zone.Forest forestWest = new Zone.Forest(5, Zone.Forest.Kind.PLAIN);
        TileSide west = new TileSide.Forest(forestWest);
        //Création de la tuile à ajouter sur le plateau
        Tile tile = new Tile(3, Tile.Kind.NORMAL, north, east, south, west);

        //Expected forest partition
        Set<Area<Zone.Forest>> forests = new HashSet<>();
        Set<Zone.Forest> North = new HashSet<>(Collections.singleton(forestNorth));
        Set<Zone.Forest> West = new HashSet<>(Collections.singleton(forestWest));
        Collections.addAll(forests, new Area<>(North, new ArrayList<>(),1)
                , new Area<>(West, new ArrayList<>(), 1));
        ZonePartition<Zone.Forest> expectedForestPartition = new ZonePartition<>(forests);
        //Expected meadow partition
        Set<Area<Zone.Meadow>> meadows = new HashSet<>();
        Set<Zone.Meadow> East = new HashSet<>(Collections.singleton(meadowEast));
        Set<Zone.Meadow> South1 = new HashSet<>(Collections.singleton(meadowSouth1));
        Set<Zone.Meadow> South2 = new HashSet<>(Collections.singleton(meadowSouth2));
        Collections.addAll(meadows, new Area<>(East, new ArrayList<>(), 1),
                new Area<>(South1, new ArrayList<>(), 1),
                new Area<>(South2, new ArrayList<>(), 1));
        ZonePartition<Zone.Meadow> expectedMeadowPartition = new ZonePartition<>(meadows);
        //Expected river partition
        Set<Area<Zone.River>> rivers = new HashSet<>();
        Set<Zone.River> South = new HashSet<>(Collections.singleton(riverSouth));
        Collections.addAll(rivers, new Area<>(South,new ArrayList<>(), 1));
        ZonePartition<Zone.River> expectedRiverPartition = new ZonePartition<>(rivers);
        //Expected riverSystem partition
        Set<Area<Zone.Water>> riverSystems = new HashSet<>();
        Set<Zone.Water> System = new HashSet<>();
        System.add(riverSouth);
        System.add(lake);
        riverSystems.add(new Area<>(System, new ArrayList<>(), 1));
        ZonePartition<Zone.Water> expectedRiverSystemPartition = new ZonePartition<>(riverSystems);
        //Actual
        builder.addTile(tile);
        ZonePartitions actual = builder.build();

        assertEquals(expectedForestPartition, actual.forests());
        assertEquals(expectedMeadowPartition, actual.meadows());
        assertEquals(expectedRiverPartition, actual.rivers());
        assertEquals(expectedRiverSystemPartition, actual.riverSystems());
    }

    @Test
    void addInitialOccupantWorksOnNonTrivialCase() {

        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, new ArrayList<>(), 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, new ArrayList<>(), 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>();
        ZonePartition<Zone.River> riverZP = new ZonePartition<>();
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        //Actual
        builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.PAWN, forestZone3);
        ZonePartitions actual = builder.build();

        //Expected
        List<PlayerColor> expectedOccupant = new ArrayList<>(Collections.singleton(PlayerColor.GREEN));
        Area<Zone.Forest> expectedForestArea = new Area<>(forestZonesSet2, expectedOccupant, 2);
        Set<Area<Zone.Forest>> expectedForestAreas = new HashSet<>();
        Collections.addAll(expectedForestAreas, forestArea1, expectedForestArea);
        ZonePartition<Zone.Forest> expectedForestZP = new ZonePartition<>(expectedForestAreas);
        ZonePartitions expected = new ZonePartitions(expectedForestZP, meadowZP, riverZP, riverSystemZP);

        assertEquals(expected, actual);

    }

    @Test
    void addInitialOccupantThrowsWhenGivenOccupantCannotOccupyGivenZone() {
        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, new ArrayList<>(), 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, new ArrayList<>(), 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Creation Aire 1 de meadows
        Zone.Meadow meadowZone = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone1 = new Zone.Meadow(3, new ArrayList<>(), null);
        Set<Zone.Meadow> meadowSet = new HashSet<>();
        Collections.addAll(meadowSet, meadowZone, meadowZone1);
        Area<Zone.Meadow> meadowArea1 = new Area<>(meadowSet, new ArrayList<>(), 3);
        //Création d'ire 2 de meadows
        Zone.Meadow meadowZone2 = new Zone.Meadow(4, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone3 = new Zone.Meadow(6, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        Set<Zone.Meadow> meadowSet2 = new HashSet<>();
        Collections.addAll(meadowSet2, meadowZone2, meadowZone3);
        Area<Zone.Meadow> meadowArea2 = new Area<>(meadowSet2, new ArrayList<>(), 3);
        //Création d'un set d'aires de meadows
        Set<Area<Zone.Meadow>> meadowAreas = new HashSet<>();
        Collections.addAll(meadowAreas, meadowArea1, meadowArea2);

        //Création Aire 1 de River
        Zone.Lake lake1 = new Zone.Lake(8, 3, Zone.SpecialPower.LOGBOAT);
        Zone.River river1 = new Zone.River(1, 3, null);
        Zone.River river2 = new Zone.River(3, 2, lake1);
        Set<Zone.River> riverZonesSet1 = new HashSet<>();
        Collections.addAll(riverZonesSet1, river1, river2);
        Area<Zone.River> riverArea1 = new Area<>(riverZonesSet1, new ArrayList<>(), 3);
        //Création d'un set d'aires de River
        Set<Area<Zone.River>> riverAreas = new HashSet<>();
        Collections.addAll(riverAreas, riverArea1);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>(meadowAreas);
        ZonePartition<Zone.River> riverZP = new ZonePartition<>(riverAreas);
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        assertThrows(IllegalArgumentException.class, () -> {
            builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.HUT, forestZone3);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.PAWN, lake1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.HUT, meadowZone2);
        });
    }


    @Test
    void clearGatherersWorksOnNonTrivialCase() {
        //Création d'une liste d'occupants (PlayerColor)
        List<PlayerColor> occupants = new ArrayList<>();
        Collections.addAll(occupants, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN);

        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, occupants, 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, new ArrayList<>(), 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>();
        ZonePartition<Zone.River> riverZP = new ZonePartition<>();
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);
        builder.clearGatherers(forestArea1);
        ZonePartitions actual = builder.build();

        //Expected
        Area<Zone.Forest> expectedEmptyArea = new Area<>(forestZonesSet1, new ArrayList<>(), 4);
        Set<Area<Zone.Forest>> expectedForestArea = new HashSet<>();
        Collections.addAll(expectedForestArea, expectedEmptyArea, forestArea2);
        ZonePartition<Zone.Forest> expectedForestPartition = new ZonePartition<>(expectedForestArea);
        ZonePartitions expected = new ZonePartitions(expectedForestPartition, meadowZP, riverZP, riverSystemZP);

        assertEquals(expected, actual);


    }

    @Test
    void connectSidesWorksOnNonTrivialCase() {
        //exemples des tiles 17 et 56

        //Création des zones de la tile 17
        Zone.Meadow meadow170 = new Zone.Meadow(170, new ArrayList<>(), null);
        List<Animal> listAnimal172 = new ArrayList<>();
        listAnimal172.add(new Animal(172, Animal.Kind.DEER));
        Zone.Meadow meadow172 = new Zone.Meadow(172, listAnimal172, null);
        Zone.River river171 = new Zone.River(171, 0, null);
        Zone.River river173 = new Zone.River(173, 0, null);
        List<Animal> listAnimal174 = new ArrayList<>();
        listAnimal174.add(new Animal(174, Animal.Kind.TIGER));
        Zone.Meadow meadow174 = new Zone.Meadow(174, listAnimal174, null);

        //Création des zones de la tile 56
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        //Création des aires de la tile 17
        Set<Zone.Meadow> meadowSet170 = new HashSet<>();
        meadowSet170.add(meadow170);
        Area<Zone.Meadow> meadowArea170 = new Area<>(meadowSet170, new ArrayList<>(), 4);

        Set<Zone.Meadow> meadowSet172 = new HashSet<>();
        meadowSet172.add(meadow172);
        Area<Zone.Meadow> meadowArea172 = new Area<>(meadowSet172, new ArrayList<>(), 2);

        Set<Zone.River> riverSet171 = new HashSet<>();
        riverSet171.add(river171);
        Area<Zone.River> riverArea171 = new Area<>(riverSet171, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet174 = new HashSet<>();
        meadowSet174.add(meadow174);
        Area<Zone.Meadow> meadowArea174 = new Area<>(meadowSet174, new ArrayList<>(), 2);

        Set<Zone.River> riverSet173 = new HashSet<>();
        riverSet173.add(river173);
        Area<Zone.River> riverArea173 = new Area<>(riverSet173, new ArrayList<>(), 2);

        Set<Zone.Water> riverSystems171 = new HashSet<>();
        riverSystems171.add(river171);
        Area<Zone.Water> riverSystemsArea171 = new Area<>(riverSystems171, new ArrayList<>(), 2);

        Set<Zone.Water> riverSystems173 = new HashSet<>();
        riverSystems173.add(river173);
        Area<Zone.Water> riverSystemsArea173 = new Area<>(riverSystems173, new ArrayList<>(), 2);


        //Création des aires de la tile 56
        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, new ArrayList<>(), 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, new ArrayList<>(), 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        //Création des tilesides de la tile 17
        TileSide north17 = new TileSide.River(meadow170, river171, meadow172);
        TileSide east17 = new TileSide.River(meadow172, river171, meadow170);
        TileSide south17 = new TileSide.River(meadow170, river173, meadow174);
        TileSide west17 = new TileSide.River(meadow174, river173, meadow170);

        //Création des tilesides de la tile 56
        TileSide north56 = new TileSide.Meadow(meadow560);
        TileSide east56 = new TileSide.Forest(forest561);
        TileSide south56 = new TileSide.Forest(forest561);
        TileSide west56 = new TileSide.River(meadow562, river563, meadow560);

        //Création des zonepartitions
        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea170, meadowArea172, meadowArea174, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea171, riverArea173, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563, riverSystemsArea171, riverSystemsArea173);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        //Création de ZonePartitions
        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        zonePartitionsBuilder.connectSides(east17, west56);
        ZonePartitions realZonePartitions = zonePartitionsBuilder.build();

        //Création des areas expected
        Set<Zone.Meadow> meadowSet170_562 = new HashSet<>();
        meadowSet170_562.add(meadow170);
        meadowSet170_562.add(meadow562);
        Area<Zone.Meadow> meadowArea170_562 = new Area<>(meadowSet170_562, new ArrayList<>(), 3);

        Set<Zone.Meadow> meadowSet172_560 = new HashSet<>();
        meadowSet172_560.add(meadow172);
        meadowSet172_560.add(meadow560);
        Area<Zone.Meadow> meadowArea172_560 = new Area<>(meadowSet172_560, new ArrayList<>(), 2);

        Set<Zone.River> riverSet171_563 = new HashSet<>();
        riverSet171_563.add(river171);
        riverSet171_563.add(river563);
        Area<Zone.River> riverArea171_563 = new Area<>(riverSet171_563, new ArrayList<>(), 2);

        Set<Zone.Water> riverSystemsSet = new HashSet<>();
        riverSystemsSet.add(lake564);
        riverSystemsSet.add(river563);
        riverSystemsSet.add(river171);
        Area<Zone.Water> riverSystemsArea = new Area<>(riverSystemsSet, new ArrayList<>(), 1);//nombre de connection?

        //Créations des zonepartitions expected
        Set<Area<Zone.Meadow>> expectedMeadowAreaSet = new HashSet<>();
        Collections.addAll(expectedMeadowAreaSet, meadowArea170_562, meadowArea172_560, meadowArea174);
        ZonePartition<Zone.Meadow> expectedMeadowZonePartition = new ZonePartition<>(expectedMeadowAreaSet);

        Set<Area<Zone.Forest>> expectedForestAreaSet = new HashSet<>();
        Collections.addAll(expectedForestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> expectedForestZonePartition = new ZonePartition<>(expectedForestAreaSet);

        Set<Area<Zone.River>> expectedRiverAreaSet = new HashSet<>();
        Collections.addAll(expectedRiverAreaSet, riverArea171_563, riverArea173);
        ZonePartition<Zone.River> expectedRiverZonePartition = new ZonePartition<>(expectedRiverAreaSet);

        Set<Area<Zone.Water>> expectedRiverSystemsAreaSet = new HashSet<>();
        Collections.addAll(expectedRiverSystemsAreaSet, riverSystemsArea, riverSystemsArea173);
        ZonePartition<Zone.Water> expectedRiverSystemsZonePartition = new ZonePartition<>(expectedRiverSystemsAreaSet);

        //Création de la zonepartitions expected
        ZonePartitions expectedZonePartitions = new ZonePartitions(expectedForestZonePartition, expectedMeadowZonePartition, expectedRiverZonePartition, expectedRiverSystemsZonePartition);

        assertEquals(expectedZonePartitions, realZonePartitions);
    }

    /**
     * Tests pour vérifier que removePawn fonctionne
     */
    @Test
    void removePawnFailsForLakeOrNonOccupiedZone() {
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, new ArrayList<>(), 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, new ArrayList<>(), 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        assertThrows(IllegalArgumentException.class, () -> zonePartitionsBuilder.removePawn(PlayerColor.RED, lake564));
        assertThrows(IllegalArgumentException.class, () -> zonePartitionsBuilder.removePawn(PlayerColor.RED, meadow560));
    }

    @Test
    void removePawnWorksOnNonTrivialCase() {
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        List<PlayerColor> meadowOccupants = new ArrayList<>();
        meadowOccupants.add(PlayerColor.RED);
        meadowOccupants.add(PlayerColor.BLUE);
        meadowOccupants.add(PlayerColor.RED);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, meadowOccupants, 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, new ArrayList<>(), 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        zonePartitionsBuilder.removePawn(PlayerColor.RED, meadow562);
        ZonePartitions actualZonePartitions = zonePartitionsBuilder.build();

        List<PlayerColor> expectedMeadowOccupants = new ArrayList<>();
        expectedMeadowOccupants.add(PlayerColor.RED);
        expectedMeadowOccupants.add(PlayerColor.BLUE);
        Area<Zone.Meadow> expectedMeadowArea562 = new Area<>(meadowSet562, expectedMeadowOccupants, 1);

        Set<Area<Zone.Meadow>> expectedMeadowAreaSet = new HashSet<>();
        Collections.addAll(expectedMeadowAreaSet, expectedMeadowArea562, meadowArea560);
        ZonePartition<Zone.Meadow> expectedMeadowZonePartition = new ZonePartition<>(expectedMeadowAreaSet);

        ZonePartitions expectedZonePartitions = new ZonePartitions(forestZonePartition, expectedMeadowZonePartition, riverZonePartition, riverSystemsZonePartition);

        assertEquals(expectedZonePartitions, actualZonePartitions);
    }

    /**
     * Tests pour vérifier que clearFishers fonctionne
     */
    @Test
    void clearFishersWorksOnNonTrivialCase() {
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        List<PlayerColor> meadowOccupants = new ArrayList<>();
        meadowOccupants.add(PlayerColor.RED);
        meadowOccupants.add(PlayerColor.BLUE);
        meadowOccupants.add(PlayerColor.RED);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, meadowOccupants, 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        List<PlayerColor> riverOccupants = new ArrayList<>();
        riverOccupants.add(PlayerColor.BLUE);
        riverOccupants.add(PlayerColor.RED);
        riverOccupants.add(PlayerColor.GREEN);
        riverOccupants.add(PlayerColor.RED);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, riverOccupants, 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        zonePartitionsBuilder.clearFishers(riverArea563);
        ZonePartitions actualZonePartitions = zonePartitionsBuilder.build();

        Area<Zone.River> expectedRiverArea = new Area<>(riverSet563, new ArrayList<>(), 2);

        Set<Area<Zone.River>> expectedRiverAreaSet = new HashSet<>();
        Collections.addAll(expectedRiverAreaSet, expectedRiverArea);
        ZonePartition<Zone.River> expectedRiverZonePartition = new ZonePartition<>(expectedRiverAreaSet);

        ZonePartitions expectedZonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, expectedRiverZonePartition, riverSystemsZonePartition);

        assertEquals(expectedZonePartitions, actualZonePartitions);
    }

    @Test
    void addTileWorksOnNonTrivialCase1() {
        //Création d'une liste d'occupants (PlayerColor)
        List<PlayerColor> occupants = new ArrayList<>();
        Collections.addAll(occupants, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN);
        //Création 2 listes d'animaux
        List<Animal> animalsList1 = new ArrayList<>();
        Collections.addAll(animalsList1, new Animal(2, Animal.Kind.TIGER), new Animal(1, Animal.Kind.DEER));
        List<Animal> animalsList2 = new ArrayList<>();
        Collections.addAll(animalsList2, new Animal(3, Animal.Kind.AUROCHS), new Animal(4, Animal.Kind.MAMMOTH));

        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, occupants, 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, occupants, 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Creation Aire 1 de meadows
        Zone.Meadow meadowZone = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone1 = new Zone.Meadow(3, animalsList1, null);
        Set<Zone.Meadow> meadowSet = new HashSet<>();
        Collections.addAll(meadowSet, meadowZone, meadowZone1);
        Area<Zone.Meadow> meadowArea1 = new Area<>(meadowSet, new ArrayList<>(), 3);
        //Création d'ire 2 de meadows
        Zone.Meadow meadowZone2 = new Zone.Meadow(4, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone3 = new Zone.Meadow(6, animalsList2, Zone.SpecialPower.PIT_TRAP);
        Set<Zone.Meadow> meadowSet2 = new HashSet<>();
        Collections.addAll(meadowSet2, meadowZone2, meadowZone3);
        Area<Zone.Meadow> meadowArea2 = new Area<>(meadowSet2, occupants, 3);
        //Création d'un set d'aires de meadows
        Set<Area<Zone.Meadow>> meadowAreas = new HashSet<>();
        Collections.addAll(meadowAreas, meadowArea1, meadowArea2);

        //Création Aire 1 de River
        Zone.Lake lake1 = new Zone.Lake(8, 3, Zone.SpecialPower.LOGBOAT);
        Zone.River river1 = new Zone.River(1, 3, null);
        Zone.River river2 = new Zone.River(3, 2, lake1);
        Set<Zone.River> riverZonesSet1 = new HashSet<>();
        Collections.addAll(riverZonesSet1, river1, river2);
        Area<Zone.River> riverArea1 = new Area<>(riverZonesSet1, occupants, 3);
        //Création d'un set d'aires de River
        Set<Area<Zone.River>> riverAreas = new HashSet<>();
        Collections.addAll(riverAreas, riverArea1);

        //Création Aire 1 riverSystem
        Zone.Lake lake2 = new Zone.Lake(9, 3, Zone.SpecialPower.RAFT);
        Zone.River river3 = new Zone.River(4, 3, lake2);
        Set<Zone.Water> waterZonesSet1 = new HashSet<>();
        Collections.addAll(waterZonesSet1, lake2, river3);
        Area<Zone.Water> riverSystemArea1 = new Area<>(waterZonesSet1, new ArrayList<>(), 2);
        //Création Aire 2 riverSystem
        Set<Zone.Water> waterZonesSet2 = new HashSet<>();
        Collections.addAll(waterZonesSet2, lake1, river2);
        Area<Zone.Water> riverSystemArea2 = new Area<>(waterZonesSet2, new ArrayList<>(), 2);
        //Création set d'aires de riverSystem
        Set<Area<Zone.Water>> riverSystemAreas = new HashSet<>();
        Collections.addAll(riverSystemAreas, riverSystemArea1, riverSystemArea2);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>(meadowAreas);
        ZonePartition<Zone.River> riverZP = new ZonePartition<>(riverAreas);
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>(riverSystemAreas);

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        // Création des bords de la tuile
        Zone.Forest forestNorth = new Zone.Forest(0, Zone.Forest.Kind.PLAIN);
        TileSide north = new TileSide.Forest(forestNorth);
        Zone.Meadow meadowEast = new Zone.Meadow(1, new ArrayList<>(), null);
        TileSide east = new TileSide.Meadow(meadowEast);
        Zone.Lake lake = new Zone.Lake(10, 1, null);
        Zone.River riverSouth = new Zone.River(3, 3, lake);
        Zone.Meadow meadowSouth1 = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        Zone.Meadow meadowSouth2 = new Zone.Meadow(4, animalsList1, null);
        TileSide south = new TileSide.River(meadowSouth1, riverSouth, meadowSouth2);
        Zone.Forest forestWest = new Zone.Forest(5, Zone.Forest.Kind.PLAIN);
        TileSide west = new TileSide.Forest(forestWest);
        //Création de la tuile à ajouter sur le plateau
        Tile tile = new Tile(3, Tile.Kind.NORMAL, north, east, south, west);

        //Expected forest partition
        Set<Area<Zone.Forest>> forests = new HashSet<>();
        forests.addAll(forestAreas);
        Set<Zone.Forest> North = new HashSet<>(Collections.singleton(forestNorth));
        Set<Zone.Forest> West = new HashSet<>(Collections.singleton(forestWest));
        Collections.addAll(forests, new Area<>(North, new ArrayList<>(),1)
                , new Area<>(West, new ArrayList<>(), 1));
        ZonePartition<Zone.Forest> expectedForestPartition = new ZonePartition<>(forests);
        //Expected meadow partition
        Set<Area<Zone.Meadow>> meadows = new HashSet<>();
        meadows.addAll(meadowAreas);
        Set<Zone.Meadow> East = new HashSet<>(Collections.singleton(meadowEast));
        Set<Zone.Meadow> South1 = new HashSet<>(Collections.singleton(meadowSouth1));
        Set<Zone.Meadow> South2 = new HashSet<>(Collections.singleton(meadowSouth2));
        Collections.addAll(meadows, new Area<>(East, new ArrayList<>(), 1),
                new Area<>(South1, new ArrayList<>(), 1),
                new Area<>(South2, new ArrayList<>(), 1));
        ZonePartition<Zone.Meadow> expectedMeadowPartition = new ZonePartition<>(meadows);
        //Expected river partition
        Set<Area<Zone.River>> rivers = new HashSet<>();
        rivers.addAll(riverAreas);
        Set<Zone.River> South = new HashSet<>(Collections.singleton(riverSouth));
        Collections.addAll(rivers, new Area<>(South,new ArrayList<>(), 1));
        ZonePartition<Zone.River> expectedRiverPartition = new ZonePartition<>(rivers);
        //Expected riverSystem partition
        Set<Area<Zone.Water>> riverSystems = new HashSet<>();
        riverSystems.addAll(riverSystemAreas);
        Set<Zone.Water> System = new HashSet<>();
        System.add(riverSouth);
        System.add(lake);
        riverSystems.add(new Area<>(System, new ArrayList<>(), 1));
        ZonePartition<Zone.Water> expectedRiverSystemPartition = new ZonePartition<>(riverSystems);

        //Actual
        builder.addTile(tile);
        ZonePartitions actual = builder.build();

        assertEquals(expectedForestPartition, actual.forests());
        assertEquals(expectedMeadowPartition, actual.meadows());
        assertEquals(expectedRiverPartition, actual.rivers());
        assertEquals(expectedRiverSystemPartition, actual.riverSystems());
    }

    @Test
    void addTileWorksWhenZonePartitionsIsEmpty1() {
        //Création 2 listes d'animaux
        List<Animal> animalsList1 = new ArrayList<>();
        Collections.addAll(animalsList1, new Animal(2, Animal.Kind.TIGER), new Animal(1, Animal.Kind.DEER));
        List<Animal> animalsList2 = new ArrayList<>();
        Collections.addAll(animalsList2, new Animal(3, Animal.Kind.AUROCHS), new Animal(4, Animal.Kind.MAMMOTH));

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>();
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>();
        ZonePartition<Zone.River> riverZP = new ZonePartition<>();
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        // Création des bords de la tuile
        Zone.Forest forestNorth = new Zone.Forest(0, Zone.Forest.Kind.PLAIN);
        TileSide north = new TileSide.Forest(forestNorth);
        Zone.Meadow meadowEast = new Zone.Meadow(1, new ArrayList<>(), null);
        TileSide east = new TileSide.Meadow(meadowEast);
        Zone.Lake lake = new Zone.Lake(10, 1, null);
        Zone.River riverSouth = new Zone.River(3, 3, lake);
        Zone.Meadow meadowSouth1 = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        Zone.Meadow meadowSouth2 = new Zone.Meadow(4, animalsList1, null);
        TileSide south = new TileSide.River(meadowSouth1, riverSouth, meadowSouth2);
        Zone.Forest forestWest = new Zone.Forest(5, Zone.Forest.Kind.PLAIN);
        TileSide west = new TileSide.Forest(forestWest);
        //Création de la tuile à ajouter sur le plateau
        Tile tile = new Tile(3, Tile.Kind.NORMAL, north, east, south, west);

        //Expected forest partition
        Set<Area<Zone.Forest>> forests = new HashSet<>();
        Set<Zone.Forest> North = new HashSet<>(Collections.singleton(forestNorth));
        Set<Zone.Forest> West = new HashSet<>(Collections.singleton(forestWest));
        Collections.addAll(forests, new Area<>(North, new ArrayList<>(),1)
                , new Area<>(West, new ArrayList<>(), 1));
        ZonePartition<Zone.Forest> expectedForestPartition = new ZonePartition<>(forests);
        //Expected meadow partition
        Set<Area<Zone.Meadow>> meadows = new HashSet<>();
        Set<Zone.Meadow> East = new HashSet<>(Collections.singleton(meadowEast));
        Set<Zone.Meadow> South1 = new HashSet<>(Collections.singleton(meadowSouth1));
        Set<Zone.Meadow> South2 = new HashSet<>(Collections.singleton(meadowSouth2));
        Collections.addAll(meadows, new Area<>(East, new ArrayList<>(), 1),
                new Area<>(South1, new ArrayList<>(), 1),
                new Area<>(South2, new ArrayList<>(), 1));
        ZonePartition<Zone.Meadow> expectedMeadowPartition = new ZonePartition<>(meadows);
        //Expected river partition
        Set<Area<Zone.River>> rivers = new HashSet<>();
        Set<Zone.River> South = new HashSet<>(Collections.singleton(riverSouth));
        Collections.addAll(rivers, new Area<>(South,new ArrayList<>(), 1));
        ZonePartition<Zone.River> expectedRiverPartition = new ZonePartition<>(rivers);
        //Expected riverSystem partition
        Set<Area<Zone.Water>> riverSystems = new HashSet<>();
        Set<Zone.Water> System = new HashSet<>();
        System.add(riverSouth);
        System.add(lake);
        riverSystems.add(new Area<>(System, new ArrayList<>(), 1));
        ZonePartition<Zone.Water> expectedRiverSystemPartition = new ZonePartition<>(riverSystems);
        //Actual
        builder.addTile(tile);
        ZonePartitions actual = builder.build();

        assertEquals(expectedForestPartition, actual.forests());
        assertEquals(expectedMeadowPartition, actual.meadows());
        assertEquals(expectedRiverPartition, actual.rivers());
        assertEquals(expectedRiverSystemPartition, actual.riverSystems());
    }

    @Test
    void addInitialOccupantWorksOnNonTrivialCase1() {

        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, new ArrayList<>(), 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, new ArrayList<>(), 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>();
        ZonePartition<Zone.River> riverZP = new ZonePartition<>();
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        //Actual
        builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.PAWN, forestZone3);
        ZonePartitions actual = builder.build();

        //Expected
        List<PlayerColor> expectedOccupant = new ArrayList<>(Collections.singleton(PlayerColor.GREEN));
        Area<Zone.Forest> expectedForestArea = new Area<>(forestZonesSet2, expectedOccupant, 2);
        Set<Area<Zone.Forest>> expectedForestAreas = new HashSet<>();
        Collections.addAll(expectedForestAreas, forestArea1, expectedForestArea);
        ZonePartition<Zone.Forest> expectedForestZP = new ZonePartition<>(expectedForestAreas);
        ZonePartitions expected = new ZonePartitions(expectedForestZP, meadowZP, riverZP, riverSystemZP);

        assertEquals(expected, actual);

    }

    @Test
    void addInitialOccupantThrowsWhenGivenOccupantCannotOccupyGivenZone1() {
        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, new ArrayList<>(), 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, new ArrayList<>(), 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Creation Aire 1 de meadows
        Zone.Meadow meadowZone = new Zone.Meadow(2, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone1 = new Zone.Meadow(3, new ArrayList<>(), null);
        Set<Zone.Meadow> meadowSet = new HashSet<>();
        Collections.addAll(meadowSet, meadowZone, meadowZone1);
        Area<Zone.Meadow> meadowArea1 = new Area<>(meadowSet, new ArrayList<>(), 3);
        //Création d'ire 2 de meadows
        Zone.Meadow meadowZone2 = new Zone.Meadow(4, new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow meadowZone3 = new Zone.Meadow(6, new ArrayList<>(), Zone.SpecialPower.PIT_TRAP);
        Set<Zone.Meadow> meadowSet2 = new HashSet<>();
        Collections.addAll(meadowSet2, meadowZone2, meadowZone3);
        Area<Zone.Meadow> meadowArea2 = new Area<>(meadowSet2, new ArrayList<>(), 3);
        //Création d'un set d'aires de meadows
        Set<Area<Zone.Meadow>> meadowAreas = new HashSet<>();
        Collections.addAll(meadowAreas, meadowArea1, meadowArea2);

        //Création Aire 1 de River
        Zone.Lake lake1 = new Zone.Lake(8, 3, Zone.SpecialPower.LOGBOAT);
        Zone.River river1 = new Zone.River(1, 3, null);
        Zone.River river2 = new Zone.River(3, 2, lake1);
        Set<Zone.River> riverZonesSet1 = new HashSet<>();
        Collections.addAll(riverZonesSet1, river1, river2);
        Area<Zone.River> riverArea1 = new Area<>(riverZonesSet1, new ArrayList<>(), 3);
        //Création d'un set d'aires de River
        Set<Area<Zone.River>> riverAreas = new HashSet<>();
        Collections.addAll(riverAreas, riverArea1);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>(meadowAreas);
        ZonePartition<Zone.River> riverZP = new ZonePartition<>(riverAreas);
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);

        assertThrows(IllegalArgumentException.class, () -> {
            builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.HUT, forestZone3);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.PAWN, lake1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            builder.addInitialOccupant(PlayerColor.GREEN, Occupant.Kind.HUT, meadowZone2);
        });
    }


    @Test
    void clearGatherersWorksOnNonTrivialCase1() {
        //Création d'une liste d'occupants (PlayerColor)
        List<PlayerColor> occupants = new ArrayList<>();
        Collections.addAll(occupants, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.GREEN);

        //Création aire 1 de forets
        Zone.Forest forestZone = new Zone.Forest(0, null);
        Zone.Forest forestZone1 = new Zone.Forest(7, Zone.Forest.Kind.PLAIN);
        Zone.Forest forestZone2 = new Zone.Forest(1, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forestZonesSet1 = new HashSet<>();
        Collections.addAll(forestZonesSet1, forestZone, forestZone1, forestZone2);
        Area<Zone.Forest> forestArea1 = new Area<>(forestZonesSet1, occupants, 4);
        //Création de l'aire 2 de forets
        Zone.Forest forestZone3 = new Zone.Forest(5, null);
        Zone.Forest forestZone4 = new Zone.Forest(2, null);
        Zone.Forest forestZone5 = new Zone.Forest(3, null);
        Set<Zone.Forest> forestZonesSet2 = new HashSet<>();
        Collections.addAll(forestZonesSet2, forestZone3, forestZone4, forestZone5);
        Area<Zone.Forest> forestArea2 = new Area<>(forestZonesSet2, new ArrayList<>(), 2);
        //Création d'un set d'aires de forets
        Set<Area<Zone.Forest>> forestAreas = new HashSet<>();
        Collections.addAll(forestAreas, forestArea1, forestArea2);

        //Création des 4 ZonePartition du jeu
        ZonePartition<Zone.Forest> forestZP = new ZonePartition<>(forestAreas);
        ZonePartition<Zone.Meadow> meadowZP = new ZonePartition<>();
        ZonePartition<Zone.River> riverZP = new ZonePartition<>();
        ZonePartition<Zone.Water> riverSystemZP = new ZonePartition<>();

        //Création d'une ZonePartitionS contenant les 4 du jeu
        ZonePartitions zonePartitions = new ZonePartitions(forestZP, meadowZP,
                riverZP, riverSystemZP);

        //Création d'un builder de ZonePartitions4 qui contient les 4 créées
        ZonePartitions.Builder builder = new ZonePartitions.Builder(zonePartitions);
        builder.clearGatherers(forestArea1);
        ZonePartitions actual = builder.build();

        //Expected
        Area<Zone.Forest> expectedEmptyArea = new Area<>(forestZonesSet1, new ArrayList<>(), 4);
        Set<Area<Zone.Forest>> expectedForestArea = new HashSet<>();
        Collections.addAll(expectedForestArea, expectedEmptyArea, forestArea2);
        ZonePartition<Zone.Forest> expectedForestPartition = new ZonePartition<>(expectedForestArea);
        ZonePartitions expected = new ZonePartitions(expectedForestPartition, meadowZP, riverZP, riverSystemZP);

        assertEquals(expected, actual);


    }

    @Test
    void connectSidesWorksOnNonTrivialCase1() {
        //exemples des tiles 17 et 56

        //Création des zones de la tile 17
        Zone.Meadow meadow170 = new Zone.Meadow(170, new ArrayList<>(), null);
        List<Animal> listAnimal172 = new ArrayList<>();
        listAnimal172.add(new Animal(172, Animal.Kind.DEER));
        Zone.Meadow meadow172 = new Zone.Meadow(172, listAnimal172, null);
        Zone.River river171 = new Zone.River(171, 0, null);
        Zone.River river173 = new Zone.River(173, 0, null);
        List<Animal> listAnimal174 = new ArrayList<>();
        listAnimal174.add(new Animal(174, Animal.Kind.TIGER));
        Zone.Meadow meadow174 = new Zone.Meadow(174, listAnimal174, null);

        //Création des zones de la tile 56
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        //Création des aires de la tile 17
        Set<Zone.Meadow> meadowSet170 = new HashSet<>();
        meadowSet170.add(meadow170);
        Area<Zone.Meadow> meadowArea170 = new Area<>(meadowSet170, new ArrayList<>(), 4);

        Set<Zone.Meadow> meadowSet172 = new HashSet<>();
        meadowSet172.add(meadow172);
        Area<Zone.Meadow> meadowArea172 = new Area<>(meadowSet172, new ArrayList<>(), 2);

        Set<Zone.River> riverSet171 = new HashSet<>();
        riverSet171.add(river171);
        Area<Zone.River> riverArea171 = new Area<>(riverSet171, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet174 = new HashSet<>();
        meadowSet174.add(meadow174);
        Area<Zone.Meadow> meadowArea174 = new Area<>(meadowSet174, new ArrayList<>(), 2);

        Set<Zone.River> riverSet173 = new HashSet<>();
        riverSet173.add(river173);
        Area<Zone.River> riverArea173 = new Area<>(riverSet173, new ArrayList<>(), 2);

        Set<Zone.Water> riverSystems171 = new HashSet<>();
        riverSystems171.add(river171);
        Area<Zone.Water> riverSystemsArea171 = new Area<>(riverSystems171, new ArrayList<>(), 2);

        Set<Zone.Water> riverSystems173 = new HashSet<>();
        riverSystems173.add(river173);
        Area<Zone.Water> riverSystemsArea173 = new Area<>(riverSystems173, new ArrayList<>(), 2);


        //Création des aires de la tile 56
        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, new ArrayList<>(), 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, new ArrayList<>(), 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        //Création des tilesides de la tile 17
        TileSide north17 = new TileSide.River(meadow170, river171, meadow172);
        TileSide east17 = new TileSide.River(meadow172, river171, meadow170);
        TileSide south17 = new TileSide.River(meadow170, river173, meadow174);
        TileSide west17 = new TileSide.River(meadow174, river173, meadow170);

        //Création des tilesides de la tile 56
        TileSide north56 = new TileSide.Meadow(meadow560);
        TileSide east56 = new TileSide.Forest(forest561);
        TileSide south56 = new TileSide.Forest(forest561);
        TileSide west56 = new TileSide.River(meadow562, river563, meadow560);

        //Création des zonepartitions
        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea170, meadowArea172, meadowArea174, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea171, riverArea173, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563, riverSystemsArea171, riverSystemsArea173);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        //Création de ZonePartitions
        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        zonePartitionsBuilder.connectSides(east17, west56);
        ZonePartitions realZonePartitions = zonePartitionsBuilder.build();

        //Création des areas expected
        Set<Zone.Meadow> meadowSet170_562 = new HashSet<>();
        meadowSet170_562.add(meadow170);
        meadowSet170_562.add(meadow562);
        Area<Zone.Meadow> meadowArea170_562 = new Area<>(meadowSet170_562, new ArrayList<>(), 3);

        Set<Zone.Meadow> meadowSet172_560 = new HashSet<>();
        meadowSet172_560.add(meadow172);
        meadowSet172_560.add(meadow560);
        Area<Zone.Meadow> meadowArea172_560 = new Area<>(meadowSet172_560, new ArrayList<>(), 2);

        Set<Zone.River> riverSet171_563 = new HashSet<>();
        riverSet171_563.add(river171);
        riverSet171_563.add(river563);
        Area<Zone.River> riverArea171_563 = new Area<>(riverSet171_563, new ArrayList<>(), 2);

        Set<Zone.Water> riverSystemsSet = new HashSet<>();
        riverSystemsSet.add(lake564);
        riverSystemsSet.add(river563);
        riverSystemsSet.add(river171);
        Area<Zone.Water> riverSystemsArea = new Area<>(riverSystemsSet, new ArrayList<>(), 1);//nombre de connection?

        //Créations des zonepartitions expected
        Set<Area<Zone.Meadow>> expectedMeadowAreaSet = new HashSet<>();
        Collections.addAll(expectedMeadowAreaSet, meadowArea170_562, meadowArea172_560, meadowArea174);
        ZonePartition<Zone.Meadow> expectedMeadowZonePartition = new ZonePartition<>(expectedMeadowAreaSet);

        Set<Area<Zone.Forest>> expectedForestAreaSet = new HashSet<>();
        Collections.addAll(expectedForestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> expectedForestZonePartition = new ZonePartition<>(expectedForestAreaSet);

        Set<Area<Zone.River>> expectedRiverAreaSet = new HashSet<>();
        Collections.addAll(expectedRiverAreaSet, riverArea171_563, riverArea173);
        ZonePartition<Zone.River> expectedRiverZonePartition = new ZonePartition<>(expectedRiverAreaSet);

        Set<Area<Zone.Water>> expectedRiverSystemsAreaSet = new HashSet<>();
        Collections.addAll(expectedRiverSystemsAreaSet, riverSystemsArea, riverSystemsArea173);
        ZonePartition<Zone.Water> expectedRiverSystemsZonePartition = new ZonePartition<>(expectedRiverSystemsAreaSet);

        //Création de la zonepartitions expected
        ZonePartitions expectedZonePartitions = new ZonePartitions(expectedForestZonePartition, expectedMeadowZonePartition, expectedRiverZonePartition, expectedRiverSystemsZonePartition);

        assertEquals(expectedZonePartitions, realZonePartitions);
    }

    /**
     * Tests pour vérifier que removePawn fonctionne
     */
    @Test
    void removePawnFailsForLakeOrNonOccupiedZone1() {
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, new ArrayList<>(), 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, new ArrayList<>(), 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        assertThrows(IllegalArgumentException.class, () -> zonePartitionsBuilder.removePawn(PlayerColor.RED, lake564));
        assertThrows(IllegalArgumentException.class, () -> zonePartitionsBuilder.removePawn(PlayerColor.RED, meadow560));
    }

    @Test
    void removePawnWorksOnNonTrivialCase1() {
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        List<PlayerColor> meadowOccupants = new ArrayList<>();
        meadowOccupants.add(PlayerColor.RED);
        meadowOccupants.add(PlayerColor.BLUE);
        meadowOccupants.add(PlayerColor.RED);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, meadowOccupants, 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, new ArrayList<>(), 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        zonePartitionsBuilder.removePawn(PlayerColor.RED, meadow562);
        ZonePartitions actualZonePartitions = zonePartitionsBuilder.build();

        List<PlayerColor> expectedMeadowOccupants = new ArrayList<>();
        expectedMeadowOccupants.add(PlayerColor.RED);
        expectedMeadowOccupants.add(PlayerColor.BLUE);
        Area<Zone.Meadow> expectedMeadowArea562 = new Area<>(meadowSet562, expectedMeadowOccupants, 1);

        Set<Area<Zone.Meadow>> expectedMeadowAreaSet = new HashSet<>();
        Collections.addAll(expectedMeadowAreaSet, expectedMeadowArea562, meadowArea560);
        ZonePartition<Zone.Meadow> expectedMeadowZonePartition = new ZonePartition<>(expectedMeadowAreaSet);

        ZonePartitions expectedZonePartitions = new ZonePartitions(forestZonePartition, expectedMeadowZonePartition, riverZonePartition, riverSystemsZonePartition);

        assertEquals(expectedZonePartitions, actualZonePartitions);
    }

    /**
     * Tests pour vérifier que clearFishers fonctionne
     */
    @Test
    void clearFishersWorksOnNonTrivialCase1() {
        List<Animal> listAnimal560 = new ArrayList<>();
        listAnimal560.add(new Animal(560, Animal.Kind.AUROCHS));
        Zone.Meadow meadow560 = new Zone.Meadow(560, listAnimal560, null);
        Zone.Forest forest561 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Zone.Meadow meadow562 = new Zone.Meadow(562, new ArrayList<>(), null);
        Zone.Lake lake564 = new Zone.Lake(564, 1, null);
        Zone.River river563 = new Zone.River(563, 0, lake564);

        Set<Zone.Meadow> meadowSet560 = new HashSet<>();
        meadowSet560.add(meadow560);
        Area<Zone.Meadow> meadowArea560 = new Area<>(meadowSet560, new ArrayList<>(), 2);

        Set<Zone.Forest> forestSet561 = new HashSet<>();
        forestSet561.add(forest561);
        Area<Zone.Forest> forestArea561 = new Area<>(forestSet561, new ArrayList<>(), 2);

        Set<Zone.Meadow> meadowSet562 = new HashSet<>();
        meadowSet562.add(meadow562);
        List<PlayerColor> meadowOccupants = new ArrayList<>();
        meadowOccupants.add(PlayerColor.RED);
        meadowOccupants.add(PlayerColor.BLUE);
        meadowOccupants.add(PlayerColor.RED);
        Area<Zone.Meadow> meadowArea562 = new Area<>(meadowSet562, meadowOccupants, 1);

        Set<Zone.River> riverSet563 = new HashSet<>();
        riverSet563.add(river563);
        List<PlayerColor> riverOccupants = new ArrayList<>();
        riverOccupants.add(PlayerColor.BLUE);
        riverOccupants.add(PlayerColor.RED);
        riverOccupants.add(PlayerColor.GREEN);
        riverOccupants.add(PlayerColor.RED);
        Area<Zone.River> riverArea563 = new Area<>(riverSet563, riverOccupants, 2);//nb de connections?

        Set<Zone.Water> riverSystemsSet564 = new HashSet<>();
        riverSystemsSet564.add(lake564);
        riverSystemsSet564.add(river563);
        Area<Zone.Water> riverSystemsArea563 = new Area<>(riverSystemsSet564, new ArrayList<>(), 1);//nombre de connection?

        Set<Area<Zone.Meadow>> meadowAreaSet = new HashSet<>();
        Collections.addAll(meadowAreaSet, meadowArea560, meadowArea562);
        ZonePartition<Zone.Meadow> meadowZonePartition = new ZonePartition<>(meadowAreaSet);

        Set<Area<Zone.Forest>> forestAreaSet = new HashSet<>();
        Collections.addAll(forestAreaSet, forestArea561);
        ZonePartition<Zone.Forest> forestZonePartition = new ZonePartition<>(forestAreaSet);

        Set<Area<Zone.River>> riverAreaSet = new HashSet<>();
        Collections.addAll(riverAreaSet, riverArea563);
        ZonePartition<Zone.River> riverZonePartition = new ZonePartition<>(riverAreaSet);

        Set<Area<Zone.Water>> riverSystemsAreaSet = new HashSet<>();
        Collections.addAll(riverSystemsAreaSet, riverSystemsArea563);
        ZonePartition<Zone.Water> riverSystemsZonePartition = new ZonePartition<>(riverSystemsAreaSet);

        ZonePartitions zonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, riverZonePartition, riverSystemsZonePartition);
        ZonePartitions.Builder zonePartitionsBuilder = new ZonePartitions.Builder(zonePartitions);

        zonePartitionsBuilder.clearFishers(riverArea563);
        ZonePartitions actualZonePartitions = zonePartitionsBuilder.build();

        Area<Zone.River> expectedRiverArea = new Area<>(riverSet563, new ArrayList<>(), 2);

        Set<Area<Zone.River>> expectedRiverAreaSet = new HashSet<>();
        Collections.addAll(expectedRiverAreaSet, expectedRiverArea);
        ZonePartition<Zone.River> expectedRiverZonePartition = new ZonePartition<>(expectedRiverAreaSet);

        ZonePartitions expectedZonePartitions = new ZonePartitions(forestZonePartition, meadowZonePartition, expectedRiverZonePartition, riverSystemsZonePartition);

        assertEquals(expectedZonePartitions, actualZonePartitions);
    }

}
