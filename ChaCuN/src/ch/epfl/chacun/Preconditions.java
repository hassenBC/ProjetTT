package ch.epfl.chacun;

public final class Preconditions {
    private Preconditions(){}
    //on ajoute la méthode checkArgument, elle est statique
    public static void checkArgument(boolean shouldBeTrue){
        if(!shouldBeTrue) {
            throw new IllegalArgumentException();
        }
    }

}
