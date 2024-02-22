package ch.epfl.chacun;

public final class Points {
    // ce constructeur rend la classe impossible à instancier
    private Points(){}
    //peut etre déclarer des attributs finaux static pour le systeme de point rend le code plus modulable

    public static int forClosedForest(int tileCount, int mushroomGroupCount){
        Preconditions.checkArgument(tileCount > 1 );
        return tileCount*2 + mushroomGroupCount*3;
    }
    public static int forClosedRiver(int tileCount, int fishCount){

        Preconditions.checkArgument(tileCount > 1 );

        return tileCount + fishCount;
    }

    public static int forMeadow(int mommothCount, int aurochsCount, int deerCount){
        return mommothCount*3 + aurochsCount*2 + deerCount;
    }

    public static int forRiverSystem(int fishCount){
        return fishCount;
    }
    public static int forLogboat(int lakeCount){
        Preconditions.checkArgument(lakeCount > 0);
        return lakeCount*2;
    }

    public static int forRaft(int lakeCount ){
        Preconditions.checkArgument(lakeCount > 0);
        return lakeCount;
    }

}
