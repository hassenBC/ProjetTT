package ch.epfl.chacun;

/** position de la tuile
 * @author tony andriamampianina
 * @param x
 * @param y
 */
public record Pos(int x, int y) {
    static public final Pos ORIGIN = new Pos(0,0);

    /** construit une position de tuile avec les attributs x et y
     * @author tony andriamampianina
     * @param x
     * @param y
     */
    public Pos {
    }

    /** instancie une position à partir de celle d'avant
     * @author tony andriamampianina
     * @param dx
     * @param dy
     * @return
     */
    public Pos translated (int dx, int dy) {
        return new Pos(x + dx, y + dy);
    }

    /** crée la position de la tuile voisine en fonction de la direction donnée
     * @author tony Andriamampianina
     * @param direction
     * @return
     */
    public Pos neighbor (Direction direction) {
        return switch (direction) {
            case N -> (new Pos(x, y-1));
            case E -> (new Pos (x+1, y));
            case S -> (new Pos (x, y+1));
            case W -> (new Pos (x-1, y));
        };
    }
}

