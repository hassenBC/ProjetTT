package ch.epfl.chacun;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hassen Ben Chaabane (361366)
 */
public interface TileSide {
    List<Zone> zones();

    /**
     *
     * @param that La zone qu'on veut comparer
     * @return False si les zones ne sont pas du meme type, True si oui.
     */
    boolean isSameKindAs(TileSide that);

    /**
     * Record de Forest
     * @param forest La foret qui touche le bord de la tuile
     */
    record Forest(Zone.Forest forest) implements TileSide{
    @Override
    public List<Zone> zones() {
        return List.of(forest);
    }
     /**
      *
      * @param that La zone qu'on veut comparer
      * @return False si les zones ne sont pas du meme type, True si oui.
      */
    @Override
    public boolean isSameKindAs(TileSide that) {
        return that instanceof TileSide.Forest;
        }
    }

    /**
     *
     * @param meadow Le meadow qui touche le bord de la tuile
     */
    record Meadow(Zone.Meadow meadow) implements TileSide{
        /**
         * On sait que Meadow peut être en contact qu'avec les autres meadow
         * @return Les zones qui peuvent etre en contact avec meadwow
         */

        @Override
        public List<Zone> zones() {
            //vérifie avec une zone autre que meadow
            return List.of(meadow);
        }
        /**
         *
         * @param that La zone qu'on veut comparer
         * @return False si les zones ne sont pas du meme type, True si oui.
         */
        @Override
        public boolean isSameKindAs(TileSide that) {
            return that instanceof TileSide.Meadow;
        }
    }

    /**
     * Record qui prend en paramètre la river et les deux meadow qui l'entoure
     * @param meadow1
     * @param river
     * @param meadow2
     */

    record River(Zone.Meadow meadow1, Zone.River river, Zone.Meadow meadow2) implements TileSide{

        /**
         * On sait qu'une River doit etre en contact avec deux meadow
         * @return La river qui peut entrer en contact avec elle et les deux meadows qui l'entourent (dans le sens d'aiguille d'une montre)
         */
        @Override
        public List<Zone> zones() {
            //ne pas tout mettre
            return List.of(meadow1,river,meadow2);
        }
        /**
         *
         * @param that La zone qu'on veut comparer
         * @return False si les zones ne sont pas du meme type, True si oui.
         */
        @Override
        public boolean isSameKindAs(TileSide that) {
            return that instanceof TileSide.River;
        }
    }

}
