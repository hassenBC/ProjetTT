package ch.epfl.chacun;

public final class Preconditions {
    /**
     * @author hassen ben chaabane ()
     * Le constructeur Precondition ne peut pas se faire instancier
     */
    private Preconditions(){}
    //on ajoute la méthode checkArgument, elle est statique

    /**
     * @author hassen ben chaabane ()
     * @throws IllegalArgumentException si l'argument donné est faux.
     * @param shouldBeTrue
     */
    public static void checkArgument(boolean shouldBeTrue){
        if(!shouldBeTrue) {
            throw new IllegalArgumentException();
        }
    }

}
