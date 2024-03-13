package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        var RSArea = new Area <Zone.Water> (Set.of(river1,lake8), List.of(), 2);

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
        var meadowArea2 = new Area<Zone.Meadow>(Set.of(meadow2), List.of(PlayerColor.RED), 1);
        var riverArea = new Area <Zone.River>(Set.of(river1, river2), new ArrayList<>(), 2);
        var riverSystemArea = new Area <Zone.Water> (Set.of(river1, river2, lake8), new ArrayList<>(), 4);
        var forestArea = new Area<Zone.Forest>(Set.of(forest3), new ArrayList<>(), 1);
        var meadowArea3 = new Area <Zone.Meadow> (Set.of(meadow4), new ArrayList<>(), 1);

        var partition = new ZonePartitions(new ZonePartition<Zone.Forest>(Set.of(forestArea)), new ZonePartition<Zone.Meadow>(Set.of(meadowArea, meadowArea2, meadowArea3)), new ZonePartition<Zone.River>(Set.of(riverArea)), new ZonePartition<Zone.Water>(Set.of(riverSystemArea)
        ));

        return new ZonePartitions.Builder(partition);
    }
    Tile getTwoRiversLakeTile () {
        var l0 = new Zone.Lake(1_8, 3, null);
        var z0 = new Zone.Meadow(1_0, List.of(), null);
        var z2 = new Zone.Meadow(1_2, List.of(), null);
        var z4 = new Zone.Meadow(1_4, List.of(), Zone.SpecialPower.PIT_TRAP);

        var z1 = new Zone.River(1_1, 0, l0);
        var z5 = new Zone.River(1_5, 0, l0);

        var z3 = new Zone.Forest(1_3, Zone.Forest.Kind.PLAIN);

        var sN = new TileSide.Meadow(z0);
        var sE = new TileSide.River(z0, z1, z2);
        var sS = new TileSide.Forest(z3);
        var sW = new TileSide.River(z4, z5, z0);

        return new Tile(1, Tile.Kind.NORMAL, sN, sE, sS, sW);

    }
    @Test


    void addTileTest () {
        var z = new ZonePartitions.Builder(new ZonePartitions(new ZonePartition <Zone.Forest>(), new ZonePartition<Zone.Meadow>(), new ZonePartition<Zone.River>(), new ZonePartition<Zone.Water>()));
        z.addTile(getStartTile());


        assertEquals(getStartPartition().build(), z.build());
        //assertEquals(zref, z);
    }
}
