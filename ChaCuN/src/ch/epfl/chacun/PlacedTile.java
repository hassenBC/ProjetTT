package ch.epfl.chacun;

import java.util.*;

/**
 * @author Hassen Ben Chaabane (361366)
 *Constructeur de PlacedTile
 * @param tile
 * @param placer
 * @param rotation
 * @param pos
 * @param occupant
 */
public record PlacedTile(Tile tile,PlayerColor placer, Rotation rotation, Pos pos, Occupant occupant ) {

    /**
     * Construteur compact de PlacedTile pour check les préconditions
     * @param tile
     * @param placer
     * @param rotation
     * @param pos
     * @param occupant
     */
    public PlacedTile{
        Objects.requireNonNull(tile);
        Objects.requireNonNull(rotation);
        Objects.requireNonNull(pos);
    }

    /**
     * Constructeur de copie de PlacedTile qui donne une placedTile identique mais sans occupant
     * @param tile
     * @param placer
     * @param rotation
     * @param pos
     */

     public PlacedTile(Tile tile,PlayerColor placer,Rotation rotation,Pos pos){
        this(tile,placer,rotation,pos,null);
    }

    /**
     * Getter de l'id de la tuile
     * @return L'id de la tuile utilisée
     */

    public int id(){
        return tile.id();
    }

    /**
     * Getter du Kind de la tuile par exemple dans la premiere tuile placée il return START
     * @return Le kind de la tuile.
     */
    public Tile.Kind kind(){
        return tile.kind();
    }

    /**
     * Méthode qui applique la rotation donnée par le constructeur sur la tuile
     * @param direction
     * @return les nouvelles directions des zones de nos tuiles
     */
    public TileSide side(Direction direction){

         Direction newDirection = direction.rotated(rotation.negated());
        return switch (newDirection) {
            case N -> tile.n();
            case E -> tile.e();
            case S -> tile.s();
            case W -> tile.w();
        };
    }

    /**
     *La méthode va itérer sur toutes les zones de la tuile et return celle qui valide l'id qu'on lui donne.
     * @throws IllegalArgumentException si la tuile n'a pas un id valide
     * @param id
     * @return La zone avec l'id rentré en paramètre
     */
    public Zone zoneWithId(int id){
        //il va itérer sur toutes les zones de la tuile return celle qui valide l'id qu'on lui donne.
       Set<Zone> zones = this.tile.zones();
       for(Zone zone : zones){
           if(zone.id() == id){return zone;}
       }
       throw new IllegalArgumentException("Tuile sans id valide");
    }

    /**
     * Comme on sait qu'une tuile peut avoir qu'un seul special power on itère sur les zone et on rends le special power
     * @return special power de la zone ou null si il n'y a pas de special power
     */

    public Zone specialPowerZone(){
        Set<Zone> zones = this.tile.zones();
        for(Zone zone : zones){
            if(zone.specialPower() != null) {
                return zone;
            }
        }
        return null;
    }

    /**
     * Itère sur toutes les zones et rends les zones Forest
     * @return Un Set avec toutes les forets différentes
     */

    public Set<Zone.Forest> forestZones(){
        Set<Zone.Forest> zonesForest = new HashSet<>();
        Set<Zone> zones = this.tile.zones();
        for(Zone zone : zones){
            if(zone instanceof Zone.Forest) zonesForest.add((Zone.Forest) zone);
        }
        return zonesForest;
    }

    /**
     * Itère sur les zones de Tile
     * @return Les zones Meadow de la tuile
     */
    public Set<Zone.Meadow> meadowZones(){
        Set<Zone.Meadow> zonesMeadow = new HashSet<>();
        Set<Zone> zones = this.tile.zones();//trouver un moyen d'optimiser ça
        for(Zone zone : zones){
            if(zone instanceof Zone.Meadow) zonesMeadow.add((Zone.Meadow) zone);
        }
        return zonesMeadow;
    }

    /**
     * Itère sur les zones de Tile
     * @return Les zones Rivers de la tuile
     */
    public Set<Zone.River> riverZones(){
        Set<Zone.River> zonesRiver = new HashSet<>();
        Set<Zone> zones = this.tile.zones();
        for(Zone zone : zones){
            if(zone instanceof Zone.River) zonesRiver.add((Zone.River) zone);
        }
        return zonesRiver;
    }

    /**
     * Itère sur toutes les zones et check la condition Lake de River pour savoir placer un PAWN ou une HUT
     * @return Un set vide si placer est null
     * @return Un set avec tous les potential occupants de notre tuile
     */

    public Set<Occupant> potentialOccupants(){
        //demander aux assistants la correction
        Set<Zone> zones = this.tile.zones();
        Set<Occupant> potentialOccupants = new HashSet<>();
        if (placer == null){ return potentialOccupants; }

            for(Zone zone : zones){
                if (zone instanceof Zone.Meadow ) {
                     potentialOccupants.add(new Occupant(Occupant.Kind.PAWN,zone.id()));// un pion (chasseur) occupant la zone 0 (le pré contenant l'auroch)

                }
                else if (zone instanceof Zone.Forest) {
                    potentialOccupants.add(new Occupant(Occupant.Kind.PAWN,zone.id()));// un pion (cueilleur) occupant la zone 1 (la forêt)

                }
                else if (zone instanceof Zone.River) {
                    potentialOccupants.add(new Occupant(Occupant.Kind.PAWN,zone.id()));// un pion (pêcheur) occupant la zone 3 (la rivière)
                    if(!((Zone.River) zone).hasLake()){
                    potentialOccupants.add(new Occupant(Occupant.Kind.HUT,zone.id()));
                    }
                }
                else if (zone instanceof Zone.Lake ) {
                    potentialOccupants.add(new Occupant(Occupant.Kind.HUT, zone.id()));// une Hutte (pêcheur) occupant le lac zone 8

                }

                //Ajouter une condition comme quoi le nombre d'occupant ne peut pas dépasser le nombre de zones dans la tuile
        }
         return potentialOccupants;
    }

    /**
     * @throws IllegalArgumentException si la tuile ne possède pas un occupant
     * @param occupant
     * @return Une placed TIle identique à celle du constructeur mais avec un paramètre en plus l'occupant
     */
    public PlacedTile withOccupant(Occupant occupant){
        Preconditions.checkArgument(this.occupant==null);
        return new PlacedTile(this.tile, this.placer, this.rotation, this.pos, occupant);
        //on place une tuile avec l'occupant qu'on veut.
    }

    /**
     * Donne une nouvelle PlacedTile sans l'occupant.
     * @return Nouvelle placedTile sans occupant
     */
    public PlacedTile withNoOccupant(){
        return new PlacedTile(this.tile, this.placer, this.rotation, this.pos);
    }

    /**
     *
     * @param occupantKind
     * @return L'id de la zone de l'occupant mis en paramètre par le constructeur de placedtile
     * ou donne -1 si occupant == null
     */


    public int idOfZoneOccupiedBy(Occupant.Kind occupantKind){
        if(this.occupant != null){
            return this.occupant.zoneId();
        }
        return -1;
    }






}
