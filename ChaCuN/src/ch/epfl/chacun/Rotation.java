package ch.epfl.chacun;

import java.util.List;

/**
 *
 */
public enum     Rotation {
    NONE,
    RIGHT,
    HALF_TURN,
    LEFT;

    public final static List<Rotation> ALL = List.of(values());

    public final static int COUNT = ALL.size();
    /*on utilise COUNT au lieu de 4 dans notre code pour qu'on puisse faire le moins de changement dans nos méthodes
      si on voulait rajouter une rotation de plus*/
    public Rotation add(Rotation that){
        //Comme dans en trigo chaque 4 unités, on fait un tour donc le modulo 4 enlève le tour de plus qu'on a fait
        int addition = ((this.ordinal() + that.ordinal())%COUNT);
        return ALL.get(addition);
    }

    public Rotation negated(){
         // Ici, on ajoute le modulo 4 pour le cas ou this.ordinal() renvoie à 0
       return ALL.get((COUNT - this.ordinal())%COUNT);
    }

    public int quarterTurnsCW(){
        //this fait référence au type enum qui va se faire instancier et ordinal donne son ordre dans l'enum.
        return this.ordinal();
    }

    public int degreesCW(){
        return this.ordinal()*90;
    }

}