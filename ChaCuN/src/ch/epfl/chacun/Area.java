package ch.epfl.chacun;
import java.util.*;

public record Area<Z>(Set<Z> zones, List<PlayerColor> occupants, int openConnections) {

    /**Hassen Ben Chaabane
     * Constructeur compact d'Area
      * @param zones Copie défensive des Zones de Area
     * @param occupants Copie défensive d'occupants
     * @param openConnections Les connections de notre Area
     * @throws IllegalArgumentException si les open connection sont inférieurs ou égal à 0.
     */
    public Area{
            Preconditions.checkArgument(openConnections>=0);
            zones = new HashSet<>(zones);
            occupants = new ArrayList<>(occupants);
            Collections.sort(occupants);

        }

    /**
     * Itère sur toutes les Zones forest de zones et cherche les forets avec Menhir
     * @param forest Les forests de l'Area.
     * @return true si l'Area a des forets avec menhir, False si non.
     */
    static boolean hasMenhir(Area<Zone.Forest> forest){
        //peut return une erreur si zones n'a pas de forest
            for(Zone.Forest zone : forest.zones()){
            if (zone.kind() == Zone.Forest.Kind.WITH_MENHIR){
                return true;
            }
        }
        return false;
    }

    /**
     * @ mushroomCount compteur des zones avec des mushrooms.
     * @param forest les zones forest de Area
     * @return le nombre de zone forest avec les mushroom
     */

     static int mushroomGroupCount(Area<Zone.Forest> forest){
        int mushroomCount = 0 ;
        for(Zone.Forest zone : forest.zones()){
            if (zone.kind() == Zone.Forest.Kind.WITH_MUSHROOMS){
                ++mushroomCount;
            }
        }
        return mushroomCount;
    }

    /**
     *  On ajoute tous les animaux de nos Meadows et on enlève les elements en commun avec cancelledAnimals
     * @param meadow L'ensemble des zones Meadow de l'Area
     * @param cancelledAnimals Une liste des animaux à remove de notre area
     * @return la liste des animaux qui sont "vivant" qui n'ont pas été remove
     */
    static Set<Animal> animals(Area<Zone.Meadow> meadow, Set<Animal> cancelledAnimals){
         Set<Animal> aliveAnimals = new HashSet<>();
         for(Zone.Meadow zone : meadow.zones()){
             aliveAnimals.addAll(zone.animals());
         }
         aliveAnimals.removeAll(cancelledAnimals);
         return aliveAnimals;
    }

    static int riverFishCount(Area<Zone.River> river){
        int fishCount = 0 ;
        Set<Zone.Lake> lakeSet = new HashSet<>();
        for(Zone.River zone : river.zones()){
            fishCount =+ zone.fishCount();
            if (zone.hasLake()){
                lakeSet.add(zone.lake());
            }
        }for (Zone.Lake zone : lakeSet){
            fishCount =+zone.fishCount();
        }

        return fishCount;
    }


    /**
     * Une Area est closed si elle n'a pas de open connections
     * @return True si openConnection est nul, False s'il est > 0
     */
    public boolean isClosed(){
        return this.openConnections() == 0;
    }

    /**
     *Méthode qui regarde si la liste des joueurs est vide
     * @return True si la liste possède au moins un élèment, false si la liste est vide
     */
    public boolean isOccupied(){
        return !occupants.isEmpty();

    }

    public Set<PlayerColor> majorityOccupants(){
        Set<PlayerColor> setMajorityOccupants = new HashSet<>();
        int count = 1;
        int maxCount =1;
        if(occupants.isEmpty()){return new HashSet<>();}

        for (int i = 1; i<occupants().size();++i){
            if(occupants().get(i) == occupants().get(i-1)){
                count++;

            } else {
                if (count > maxCount) {
                    maxCount = count;

                }
                count = 1;
            }
        }
        if (maxCount == 1 && occupants().size() != 1) {
            //no majority occupants
            return new HashSet<>();
        }


        return null;
    }












    //Area<Z> withInitialOccupant(PlayerColor occupant){
        //Preconditions.checkArgument(flemme);
    //}

}


