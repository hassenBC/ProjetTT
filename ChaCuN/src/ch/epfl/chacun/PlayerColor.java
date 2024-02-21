package ch.epfl.chacun;

import java.util.ArrayList;
import java.util.List;

public enum PlayerColor {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    PURPLE;
    // ici la commande List.of va créer un array qui ne change pas qu'avec les élements qu'on lui donne
    //La méthode values() est associée au type Enum PlayerColor et va prendre toutes les valeurs que le type contient
    public final static List<PlayerColor> ALL = List.of(values());




}
