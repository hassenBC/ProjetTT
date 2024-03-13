package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZonePartitionsTest {
    @Test
    void addTileTest () {
        var l0 = new Zone.Lake(1_8, 3, null);
        var z0 = new Zone.Meadow(1_0, List.of(), null);
        var z2 = new Zone.Meadow(1_2, List.of(), null);
        var z4 = new Zone.Meadow(1_4, List.of(), null);

        var z1 = new Zone.River(1_1, 0, l0);
        var z5 = new Zone.River(1_5, 0, l0);

        var z3 = new Zone.Forest(1_3, Zone.Forest.Kind.PLAIN);

        var sN = new TileSide.Meadow(z0);
        var sE = new TileSide.River(z0, z1, z2);
        var sS = new TileSide.Forest(z3);
        var sW = new TileSide.River(z4, z5, z0);

        var af1 = new Area<>(Set.of(z3), List.of(), 1);
        var pf1 = new ZonePartition<>(Set.of(af1));

        var ar1 = new Area<>(Set.of(z1, z5), List.of(), 2) ;
        var ars1 = new Area<Zone.Water>(Set.of(z1, z5, l0), List.of(), 4);
        var pr1 = new ZonePartition<>(Set.of(ar1));
        var prs1 = new ZonePartition<>(Set.of(ars1));

        var am1 = new Area<>(Set.of(z0), List.of(), 3);
        var am2 = new Area<>(Set.of(z2), List.of(), 1);
        var am3 = new Area<>(Set.of(z4), List.of(), 1);
        var pm1 = new ZonePartition<>(Set.of (am1, am2, am3));

        var zref = new ZonePartitions.Builder(new ZonePartitions(pf1, pm1, pr1, prs1));
        var z = new ZonePartitions.Builder(new ZonePartitions(new ZonePartition <Zone.Forest>(), new ZonePartition<Zone.Meadow>(), new ZonePartition<Zone.River>(), new ZonePartition<Zone.Water>()));
        z.addTile(new Tile(1, Tile.Kind.NORMAL, sN, sE, sS, sW));

        //assertEquals(, z.build().forests());
        //assertEquals(zref, z);
    }
}
