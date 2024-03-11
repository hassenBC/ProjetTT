package ch.epfl.chacun;
import java.util.*;

public record Area<Z extends Zone>(Set<Z> zones, List<PlayerColor> occupants, int openConnections) {

    /**Hassen Ben Chaabane (361366)
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
    public static boolean hasMenhir(Area<Zone.Forest> forest){
        //peut return une erreur si zones n'a pas de forest
            for(Zone.Forest zone : forest.zones()){
            if (zone.kind().equals(Zone.Forest.Kind.WITH_MENHIR)){
                return true;
            }
        }
        return false;
    }

    /**
     * @ mushroomCount compteur des zones avec des mushrooms.
     * @param forest les zones forest de Area
     * @return le nombre de zones forest avec les mushroom
     */

     public static int mushroomGroupCount(Area<Zone.Forest> forest){
        int mushroomCount = 0 ;
        for(Zone.Forest zone : forest.zones()){
            if (zone.kind().equals(Zone.Forest.Kind.WITH_MUSHROOMS)){
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
    public static Set<Animal> animals(Area<Zone.Meadow> meadow, Set<Animal> cancelledAnimals){
        //demander aux assistans si le cas null passe ou pas
         Set<Animal> aliveAnimals = new HashSet<>();
         for(Zone.Meadow zone : meadow.zones()){
             aliveAnimals.addAll(zone.animals());
         }if(cancelledAnimals == null || cancelledAnimals.isEmpty() ){return aliveAnimals;}
         aliveAnimals.removeAll(cancelledAnimals);
         return aliveAnimals;
    }

    /**
     * Itère sur toutes les rivières de l'Area et ajoute à un set et ajoute
     * les lacs qui sont connectés à une rivière à un set pour éviter les doublons.
     * @param river Les zone de rivières fournit
     * @return Un fish count des rivières et aux lacs connectées
     */

    public static int riverFishCount(Area<Zone.River> river){
        int fishCount = 0 ;
        Set<Zone.Lake> lakeSet = new HashSet<>();
        for(Zone.River zone : river.zones()){
            fishCount += zone.fishCount();
            if (zone.hasLake()){
                lakeSet.add(zone.lake());
            }
        }for (Zone.Lake zone : lakeSet){
            fishCount += zone.fishCount();
        }

        return fishCount;
    }

    /**
     * On n'a pas plus peur d'avoir des doublons de lac puisque l'on a un accès direct aux zones Lac,
     * donc on itère sur notre riverSystem pour ajouter les poissons qui y sont.
     * @param riverSystem Zones de rivières et lac de notre Area
     * @return un fishCount des rivières et lacs.
     */
    public static int riverSystemFishCount(Area<Zone.Water> riverSystem){
        int fishCount = 0;
        Set<Zone.Water> zonesWater = riverSystem.zones();
        for (Zone.Water zone : zonesWater){
            fishCount += zone.fishCount();
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

    /**
     * Ici,nous avons décidé de ne pas passer par les index de notre type enum mais d'iterer directement
     * sur notre List de PlayerColor et à l'aide d'un counter trouver le max d'apparition par type de playerColor
     * Les étapes sont documenté en commentaire dans la méthode.
     * @return Un Set avec les occupants majoritaires de notre Aire
     */
    public Set<PlayerColor> majorityOccupants(){
        if (occupants().isEmpty()) {
            return new HashSet<>();
        }
        Set<PlayerColor> majorityColors = new HashSet<>();
        int count = 1;
        int maxCount = 1;

        // On démarre avec le premier occupant comme majoritaire potentiel
        PlayerColor currentColor = occupants().getFirst();
        majorityColors.add(currentColor);

        for (int i = 1; i < occupants().size(); i++) {
            // Comme la liste est triée, on vérifie si l'élément actuel est le même que le précédent
            if (occupants().get(i) == currentColor) {
                count++; // Incrémente le compteur pour la couleur courante
            } else {
                if (count > maxCount) {
                    // Une nouvelle majorité est trouvée, efface l'ensemble et ajoute la nouvelle couleur
                    maxCount = count;
                    majorityColors.clear();
                    majorityColors.add(currentColor);
                } else if (count == maxCount) {
                    // Si la couleur actuelle a le même compte que la couleur majoritaire actuelle, l'ajoute à l'ensemble
                    majorityColors.add(currentColor);
                }
                // Réinitialise le compteur pour la nouvelle couleur et la met à jour comme couleur courante
                count = 1;
                currentColor = occupants().get(i);
            }
        }

        // Vérifie une dernière fois au cas où la dernière couleur est majoritaire
        if (count > maxCount) {
            majorityColors.clear();
            majorityColors.add(currentColor);
        } else if (count == maxCount) {
            majorityColors.add(currentColor);
        }

        return majorityColors;
    }

    /**
     * Méthode qui connecte deux aires en utilisant les conditions données par l'énoncé sur les open connections
     * @param that La zone qu'on veut connecter à l'aire donnée par le constructeur
     * @return Une nouvelle Area composée des deux anciennes areas.
     */

    public Area<Z> connectTo(Area<Z> that){
        int fusedOpenConnections = 0;
        Set<Z> fusedZones = new HashSet<>();
        List<PlayerColor> fusedPlayers = new ArrayList<>();
        fusedZones.addAll(this.zones());
            fusedZones.addAll(that.zones());
                fusedPlayers.addAll(this.occupants());

        if(this.zones().equals(that.zones()) && this.occupants().equals(that.occupants()) && this.openConnections() == that.openConnections() ){
            fusedOpenConnections = this.openConnections() - 2;
        } else {
            fusedOpenConnections = (this.openConnections() + that.openConnections()) - 2;
            fusedPlayers.addAll(that.occupants());
        }

        return new Area<>(fusedZones,fusedPlayers,fusedOpenConnections);

    }

    /**
     * Check si l'area est occupé, si non il va donner une nouvelle area qui est occupée.
     * @param occupant l'occupant qui va occuper notre Area
     * @return Une Area qui possède l'occupant donné en paramètre
     */
    public Area<Z> withInitialOccupant(PlayerColor occupant){
    if(occupants().isEmpty()){
        List<PlayerColor> occupantNew = new ArrayList<>();
        occupantNew.add(occupant);
        return new Area<>(zones(),occupantNew,openConnections());
    }
        throw new IllegalArgumentException("Area is already occupied");
    }

    /**
     *Enlève de la Liste des occupants l'occupant en question
     * @param occupant
     * @return
     */
    public Area<Z> withoutOccupant(PlayerColor occupant){
        List<PlayerColor> newOccupants = occupants();
        if(occupants().contains(occupant)){
            newOccupants.remove(occupant);
            return new Area<>(zones(),newOccupants,openConnections());
        }
        throw new IllegalArgumentException("La couleur données n'est pas dans la List");
    }

    /**
     *
     * @return
     */
    public Area<Z> withoutOccupants(){
        Set<Z> zonesWithoutOccupants = new HashSet<>(zones());
        Area<Z> ActualArea = new Area<>(zonesWithoutOccupants,new ArrayList<>(),openConnections());
        return ActualArea;
    }

    /**
     * Méthode qui itère sur toutes les zones de l'aire pour avoir leur tileID
     * @return Un set d'Integer des tilesIds de toutes les zones.
     */
    public Set<Integer> tileIds(){
        Set<Integer> tileIdsM = new HashSet<>();
        for(Zone zone : zones()){
            tileIdsM.add(zone.tileId());
        }
        return tileIdsM;
    }

    /**
     *Itère sur toutes les zones de l'area pour trouver celle qui possède le specialpower en paramètre
     * @param specialPower Le special Power qu'on cherche
     * @return La zone avec le special power ou null si le special power n'est pas dans l'area.
     */
    public Zone zoneWithSpecialPower(Zone.SpecialPower specialPower){
        for(Zone zone : zones()){
            if(zone.specialPower() != null && zone.specialPower().equals(specialPower)){
                return zone;
            }
        }
        return null;

    }

    /**
     *Va compter le nombre de lacs dans notre riverSystem
     * @param riverSystem Les zones qui constituent le riverSystem
     * @return Un int qui représente le nombre de lacs dans le riverSystem
     */
    public static int lakeCount(Area<Zone.Water> riverSystem){
        Set<Zone.Water> zonesWtWater = riverSystem.zones();
        int lakeCount = 0;
        for(Zone.Water zone : zonesWtWater){
            if (zone instanceof Zone.Lake){
                lakeCount++;
            }
        }
        return lakeCount;
    }



}


