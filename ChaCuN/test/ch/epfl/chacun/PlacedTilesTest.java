package ch.epfl.chacun;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlacedTileTest {
    TileSide.Meadow meadow1 = new TileSide.Meadow(new Zone.Meadow(560, null, null));
    TileSide.Forest forest1 = new TileSide.Forest(new Zone.Forest(561, Zone.Forest.Kind.PLAIN));
    TileSide.Meadow meadow2 = new TileSide.Meadow(new Zone.Meadow(562, null, null));
    TileSide.River river1 = new TileSide.River(new Zone.Meadow(562, null, null), new Zone.River(563, 0, new Zone.Lake(568, 0, null)), (new Zone.Meadow(560, null, null)));
    Tile start = new Tile(56, Tile.Kind.START, meadow1, forest1, forest1, river1);

    @Test
    void constructorTest () {
        assertThrows(NullPointerException.class, () -> new PlacedTile(null, null, Rotation.RIGHT, Pos.ORIGIN, new Occupant(Occupant.Kind.PAWN, 562 ) ));
        assertThrows(NullPointerException.class, () -> new PlacedTile(new Tile(56, Tile.Kind.START, new TileSide.Meadow(new meadow)), null, Rotation.RIGHT, Pos.ORIGIN, new Occupant(Occupant.Kind.PAWN, 562 ) ));

    }



}
