package ch.epfl.chacun;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static ch.epfl.chacun.Tile.Kind.MENHIR;

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
     * @author Tony Andriamampianina (363559)
     *  @param kind
     * @return List Deck
     */
    private List <Tile> fromKindtoDeck (Tile.Kind kind) {
        return switch (kind) {
            case START -> new ArrayList<>(startTiles);
            case NORMAL -> new ArrayList<>(normalTiles);
            case MENHIR -> new ArrayList<>(menhirTiles);
        };
    }


    /** à partir du type de tile en paramètre, retourne la taille du deck
     * @author Tony Andriamampianina (363559)
     * @param kind
     * @return
     */
    public int deckSize(Tile.Kind kind) {
        List<Tile> deck = fromKindtoDeck(kind);
        return deck.size();
    }

    /** méthode retournant lacarte tuile au dessus du deck du type en paramètre
     * @author Tony Andriamampianina (363559)
     * @param kind
     * @return topTile of the deck
     */
    public Tile topTile (Tile.Kind kind) {
        if (deckSize(kind) != 0) {
            List<Tile> deck = fromKindtoDeck(kind);
            return deck.get(0);
        } else {
            return null;}
    }

    /** méthode retournant les trois decks avec celui d'où on a pioché une carte
     * @author Tony Andriamampianina (363559)
     * @param kind
     * @return
     */
    public TileDecks withTopTileDrawn (Tile.Kind kind) {
        if (fromKindtoDeck(kind).isEmpty()) {
            throw new IllegalArgumentException("There are no more cards in the pile.");
        } else {
            List <Tile> deck = fromKindtoDeck(kind);
            deck.remove(0);
            return switch (kind) {
                case START -> (new TileDecks(deck, normalTiles, menhirTiles));
                case NORMAL -> (new TileDecks(startTiles, deck, menhirTiles));
                case MENHIR -> (new TileDecks(startTiles, normalTiles, deck));
            };
        }
    }

    /** méthode qui retourne le deck du type en paramètre duquel on a pioché des cartes jusqu'à en trouver une satisfaisant la condition predicate
     * @author Tony Andriamampianina (363559)
     * @param kind
     * @param predicate
     * @return deck après avoir pioché
     */
    public TileDecks withTopTileDrawnUntil (Tile.Kind kind, Predicate <Tile> predicate) {
        List<Tile> deck = new ArrayList<>(fromKindtoDeck(kind));
        return withTopTileDrawnUntilHelper(deck, kind, predicate);
    }

    /** méthode auxiliaire privée permettant d'éviter les problèmes d'immuabilité dans la récursion
     * @author Tony Andriamampianina (363559)
     * @param deck
     * @param kind
     * @param predicate
     * @return
     */
    private TileDecks withTopTileDrawnUntilHelper(List<Tile> deck, Tile.Kind kind, Predicate<Tile> predicate) {
        if (deck.isEmpty()) {
            System.out.println("le deck est vide");
            return switch (kind) {
                case START -> new TileDecks(deck, normalTiles, menhirTiles);
                case NORMAL -> new TileDecks(startTiles, deck, menhirTiles);
                case MENHIR -> new TileDecks(startTiles, normalTiles, deck);

            };
        } else if (!predicate.test(deck.get(0))) {
            deck.remove(0);
            return withTopTileDrawnUntilHelper(deck, kind, predicate); // Pass the updated deck
        } else {
            // Build and return the new TileDecks object
            return switch (kind) {
                case START -> new TileDecks(deck, normalTiles, menhirTiles);
                case NORMAL -> new TileDecks(startTiles, deck, menhirTiles);
                case MENHIR -> new TileDecks(startTiles, normalTiles, deck);
            };
        }
    }
}
