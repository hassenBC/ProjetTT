package ch.epfl.chacun;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {
    Zone.Meadow meadowZone1 = new Zone.Meadow(560, null, null);
    Zone.Meadow meadowZone2 = new Zone.Meadow(562, null, null);
    Zone.Forest forestZone = new Zone.Forest(561, Zone.Forest.Kind.PLAIN);
    Zone.Lake lakeZone = new Zone.Lake(568, 0, null)
    Zone.River riverZone = new Zone.River(563, 0, lakeZone);

    TileSide.Meadow meadow1 = new TileSide.Meadow(meadowZone1);
    TileSide.Meadow meadow2 = new TileSide.Meadow(meadowZone2);
    TileSide.Forest forest1 = new TileSide.Forest(forestZone);
    TileSide.River river1 = new TileSide.River(meadowZone2, riverZone, meadowZone1);

    Tile start = new Tile(56, Tile.Kind.START, meadow1, forest1, forest1, river1);

    @Test
    void sidesTest() {
        List <TileSide> sides = new ArrayList<TileSide>();
        sides.add(meadow1);
        sides.add(forest1);
        sides.add(river1);
        assertEquals(sides, start.sides());
    }
    @Test
    void sideZonesTest() {
        Set <Zone> sideZones = new HashSet<>();
        sideZones.add(meadowZone1);
        sideZones.add(forestZone);
        sideZones.add(meadowZone2);
        sideZones.add(riverZone);
        assertEquals(sideZones ,start.sideZones() );
    }
    @Test
    void zoneWorking() {
        Set<Zone> zonesTest1 = new HashSet<>();
        zonesTest1.add(meadowZone1);
        zonesTest1.add(forestZone);
        zonesTest1.add(meadowZone2);
        zonesTest1.add(riverZone);
        zonesTest1.add(lakeZone);
        Set<Zone> zonesTest2 = new HashSet<>();
        zonesTest1.add(forestZone);
        zonesTest1.add(meadowZone1);
        zonesTest1.add(meadowZone2);
        zonesTest1.add(lakeZone);
        zonesTest1.add(riverZone);
        assertEquals(zonesTest1, start.zones());
        assertEquals(zonesTest2, start.zones(),"Le tableau dans le mauvais ordre");
    }

}
