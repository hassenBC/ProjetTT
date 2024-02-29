package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class TileDeckTest {

    Zone.Meadow meadowZone1 = new Zone.Meadow(560, new ArrayList<Animal>(), null);
    Zone.Meadow meadowZone2 = new Zone.Meadow(562, new ArrayList<Animal>(), null);
    Zone.Forest forestZone = new Zone.Forest(561, Zone.Forest.Kind.PLAIN);
    Zone.Lake lakeZone = new Zone.Lake(568, 0, null);
    Zone.River riverZone = new Zone.River(563, 0, lakeZone);

    TileSide.Meadow meadow1 = new TileSide.Meadow(meadowZone1);
    TileSide.Meadow meadow2 = new TileSide.Meadow(meadowZone2);
    TileSide.Forest forest1 = new TileSide.Forest(forestZone);
    TileSide.River river1 = new TileSide.River(meadowZone2, riverZone, meadowZone1);

    Tile start = new Tile(56, Tile.Kind.START, meadow1, forest1, forest1, river1);
    Tile start2 = new Tile(57, Tile.Kind.START, meadow1, forest1, forest1, river1);
    Tile start3 = new Tile(58, Tile.Kind.START, meadow1, forest1, forest1, river1);
    Tile startOneZone = new Tile(59, Tile.Kind.START, meadow1, meadow1, meadow1, meadow1);
    List <Tile> deckStart = new ArrayList<>();
    List <Tile> deckNormal = new ArrayList<>();
    List <Tile> deckMenhir = new ArrayList<>();




    @Test
    void topTileTest (){
        deckStart.add(start);
        deckStart.add(start2);
        deckStart.add(start3);
        TileDecks decks = new TileDecks(deckStart, deckNormal, deckMenhir);
        assertEquals(start, decks.topTile(Tile.Kind.START));
        assertNull(decks.topTile(Tile.Kind.MENHIR));
        assertNull(decks.topTile(Tile.Kind.NORMAL));
    }

    @Test
    void withTopTileDrawn () {
        deckStart.add(start);
        deckStart.add(start2);
        deckStart.add(start3);
        TileDecks decks = new TileDecks(deckStart, deckNormal, deckMenhir);
        List <Tile> deckStartEmpty = new ArrayList<>();
        List <Tile> deckStartMoinsUn = new ArrayList<>();
        deckStartMoinsUn.add(start2);
        deckStartMoinsUn.add(start3);
        TileDecks emptyStart = new TileDecks(deckStartEmpty, deckNormal, deckMenhir);
        assertEquals(new TileDecks(deckStartMoinsUn, deckNormal, deckMenhir), decks.withTopTileDrawn(Tile.Kind.START));
        assertThrows(IllegalArgumentException.class, ()-> emptyStart.withTopTileDrawn(Tile.Kind.START));


    }
    final class TileHasOneZone implements Predicate<Tile> {
        @Override
        public boolean test (Tile tile) {
            return tile.zones().size() == 1;
        }
    }
    @Test
    void withTopTileDrawnUntil () {
        deckStart.add(start);
        deckStart.add(start2);
        deckStart.add(start3);
        deckStart.add(startOneZone);
        List <Tile> deckStart2 = new ArrayList<>();
        deckStart2.add(startOneZone);
        List <Tile> deckStart3 = new ArrayList<>();
        deckStart3.add(start);
        deckStart3.add(start2);

        TileDecks decks = new TileDecks(deckStart, deckNormal, deckMenhir);
        assertEquals(new TileDecks(deckStart2, deckNormal, deckMenhir), new TileDecks(deckStart, deckNormal, deckMenhir).withTopTileDrawnUntil(Tile.Kind.START, new TileHasOneZone()));
        assertThrows(IllegalArgumentException.class, () -> new TileDecks(deckStart3, deckNormal, deckMenhir).withTopTileDrawnUntil(Tile.Kind.START, new TileHasOneZone()));

    }
}
