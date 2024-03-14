package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
}
