package ch.epfl.chacun;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {
    TileSide.Meadow meadow1 = new TileSide.Meadow(new Zone.Meadow(560, null, null));
    TileSide.Forest forest1 = new TileSide.Forest(new Zone.Forest(561, Zone.Forest.Kind.PLAIN));
    TileSide.River river1 = new TileSide.River(new Zone.Meadow(562, null, null), new Zone.River(563, 0, new Zone.Lake(568, 0, null)), (new Zone.Meadow(560, null, null)));
    Tile start = new Tile(56, Tile.Kind.START, meadow1, forest1, forest1, river1);

    @Test
    void sidesTest() {
        List <TileSide> sides = new ArrayList<TileSide>();
        sides.add(meadow1);
        sides.add(forest1);
        sides.add(river1);
        assertEquals(start.sides(), sides);
    }
    void sideZones() {

    }
}
