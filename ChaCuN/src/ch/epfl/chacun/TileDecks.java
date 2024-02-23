package ch.epfl.chacun;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/** enregistrement des piles de tuiles
 * @author Tony Andriamampianina (363559)
 * @param startTiles
 * @param normalTiles
 * @param menhirTiles
 */
public final record TileDecks(List<Tile> startTiles, List <Tile> normalTiles, List <Tile> menhirTiles) {
    /** constructeur chargé de faire une copie défensive de chacune des listes
     * @param startTiles
     * @param normalTiles
     * @param menhirTiles
     */
    public TileDecks {
        startTiles = List.copyOf(startTiles);
        normalTiles = List.copyOf(normalTiles);
        menhirTiles = List.copyOf(menhirTiles);
    }

    /** méthode utile pour return le deck du kind donné en paramètre
     * @param kind
     * @return List Deck
     */
    private List <Tile> fromKindtoDeck (Tile.Kind kind) {
        List<Tile> deck = new ArrayList<Tile>();
        switch (kind) {
            case START -> deck = List.copyOf(startTiles);
            case NORMAL -> deck = List.copyOf(normalTiles);
            case MENHIR -> deck = List.copyOf(menhirTiles);
        }
        return deck;
    }


    /** à partir du type de tile en paramètre, retourne la taille du deck
     * @param kind
     * @return
     */
    public int deckSize(Tile.Kind kind) {
        List<Tile> deck = fromKindtoDeck(kind);
        return deck.size();
    }
    public Tile topTile (Tile.Kind kind) {
        if (deckSize(kind) != 0) {
            List<Tile> deck = fromKindtoDeck(kind);
            return deck.get(0);
        } else {
            return null;}
    }
    public TileDecks withTopTileDrawn (Tile.Kind kind) {
        if (fromKindtoDeck(kind).isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            List <Tile> deck = fromKindtoDeck(kind);
            deck.removeFirst();
            return switch (kind) {
                case START -> (new TileDecks(deck, normalTiles, menhirTiles));
                case NORMAL -> (new TileDecks(startTiles, deck, menhirTiles));
                case MENHIR -> (new TileDecks(startTiles, normalTiles, deck));
            };
        }
    }
    public TileDecks withTopTileDrawnUntil (Tile.Kind kind, Predicate <Tile> predicate) {
        List <Tile> deck = fromKindtoDeck(kind);
        if (!predicate.test(topTile(kind)) && !deck.isEmpty()) {
            deck.removeFirst();
            withTopTileDrawnUntil(kind, predicate);
        }
        return switch (kind) {
            case START -> (new TileDecks(deck, normalTiles, menhirTiles));
            case NORMAL -> (new TileDecks(startTiles, deck, menhirTiles));
            case MENHIR -> (new TileDecks(startTiles, normalTiles, deck));
        };



    }

    
}
