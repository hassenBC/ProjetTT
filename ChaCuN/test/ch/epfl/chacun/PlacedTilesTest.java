package ch.epfl.chacun;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlacedTileTest {
    Tile start = new Tile(56, Tile.Kind.START, new TileSide.Meadow(new Zone.Meadow(560, null, null)), new TileSide.Forest(new Zone.Forest(561, )) )

    @Test
    void constructorTest () {
        assertThrows(NullPointerException.class, () -> new PlacedTile(null, null, Rotation.RIGHT, Pos.ORIGIN, new Occupant(Occupant.Kind.PAWN, 562 ) ));
        assertThrows(NullPointerException.class, () -> new PlacedTile(new Tile(56, Tile.Kind.START, new TileSide.Meadow(new meadow)), null, Rotation.RIGHT, Pos.ORIGIN, new Occupant(Occupant.Kind.PAWN, 562 ) ));

    }



}
