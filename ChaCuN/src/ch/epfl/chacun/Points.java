package ch.epfl.chacun;

final class Points {
    // ce constructeur rend la classe impossible à instancier
    private Points(){}
    //peut etre déclarer des attributs finaux static pour le systeme de point rend le code plus modulable

    public static int forClosedForest(int tileCount, int mushroomGroupCount){
        return tileCount*2 + mushroomGroupCount*3;
    }
    public static int forClosedRiver(int tileCount, int fishCount){
        return tileCount + fishCount;
    }

    public static int forMeadow(int mommothCount, int aurochsCount, int deerCount){
        return mommothCount*3 + aurochsCount*2 + deerCount;
    }

    public static int forRiverSystem(int fishCount){
        return fishCount;
    }
    public static int forLogboat(int lakeCount){
        return lakeCount*2;
    }

    public static int forRaft(int lakeCount ){
        return lakeCount;
    }


    //pour les throw expections met check argument et la condition grosse lignes de code

    //checkargument(conditons) pas de if ni de getter ou de jsp quoi
}
