package ch.epfl.chacun;

import java.util.*;

public record PlacedTile(Tile tile,PlayerColor placer, Rotation rotation, Pos pos, Occupant occupant ) {
    public PlacedTile{
        Objects.requireNonNull(tile);
        Objects.requireNonNull(rotation);
        Objects.requireNonNull(pos);
    }
     public PlacedTile(Tile tile,PlayerColor placer,Rotation rotation,Pos pos){
        this(tile,placer,rotation,pos,null);
    }

    public int id(){
        return tile.id();
    }
    public Tile.Kind kind(){
        return tile.kind();
    }

    public TileSide side(Direction direction){

         Direction newDirection = direction.rotated(rotation.negated());
        return switch (newDirection) {
            case N -> tile.n();
            case E -> tile.e();
            case S -> tile.s();
            case W -> tile.w();
        };//A TESTER RIGOUREUSEMENT !!!!!!!!!!!!!!!!!!!
    }

    /**
     *
     * @param id
     * @return
     */
    public Zone zoneWithId(int id){
        //il va itérer sur toutes les zones de la tuile return celle qui valide l'id qu'on lui donne.
       Set<Zone> zones = this.tile.zones();
       for(Zone zone : zones){
           if(zone.id() == id){return zone;}
       }
       throw new IllegalArgumentException("Tuile sans id valide");
    }

    public Zone specialPowerZone(){
        Set<Zone> zones = this.tile.zones();
        for(Zone zone : zones){
            if(zone.specialPower() != null) {
                return zone;
            }
        }
        return null;
    }

    public Set<Zone.Forest> forestZones(){
        Set<Zone.Forest> zonesForest = new HashSet<>();
        Set<Zone> zones = this.tile.zones();
        for(Zone zone : zones){
            if(zone instanceof Zone.Forest) zonesForest.add((Zone.Forest) zone);
        }
        return zonesForest;
    }
    public Set<Zone.Meadow> meadowZones(){
        Set<Zone.Meadow> zonesMeadow = new HashSet<>();
        Set<Zone> zones = this.tile.zones();//trouver un moyen d'optimiser ça
        for(Zone zone : zones){
            if(zone instanceof Zone.Meadow) zonesMeadow.add((Zone.Meadow) zone);
        }
        return zonesMeadow;
    }

    public Set<Zone.River> riverZones(){
        Set<Zone.River> zonesRiver = new HashSet<>();
        Set<Zone> zones = this.tile.zones();
        for(Zone zone : zones){
            if(zone instanceof Zone.River) zonesRiver.add((Zone.River) zone);
        }
        return zonesRiver;
    }

    public Set<Occupant> potentialOccupants(){
        int i = 0;
        int j =8;
        Set<Zone> zones = this.tile.zones();
        Set<Occupant> potentialOccupants = new HashSet<>();
        if (placer == null){ return potentialOccupants; }

            for(Zone zone : zones){
                if (zone instanceof Zone.Meadow ) {
                     potentialOccupants.add(new Occupant(Occupant.Kind.PAWN,i));// un pion (chasseur) occupant la zone 0 (le pré contenant l'auroch)
                    ++i;
                }
                else if (zone instanceof Zone.Forest) {
                    potentialOccupants.add(new Occupant(Occupant.Kind.PAWN,i));// un pion (cueilleur) occupant la zone 1 (la forêt)
                    ++i;
                }
                else if (zone instanceof Zone.River) {
                    potentialOccupants.add(new Occupant(Occupant.Kind.PAWN,i));// un pion (pêcheur) occupant la zone 3 (la rivière)
                    ++i;
                }
                else if (zone instanceof Zone.Lake ) {
                    potentialOccupants.add(new Occupant(Occupant.Kind.HUT, j));// une Hutte (pêcheur) occupant le lac zone 8
                    ++j;
                }

                //Ajouter une condition comme quoi le nombre d'occupant ne peut pas dépasser le nombre de zones dans la tuile
        }
         return potentialOccupants;
    }
    public PlacedTile withOccupant(Occupant occupant){
        Preconditions.checkArgument(this.occupant==null);
        return new PlacedTile(this.tile, this.placer, this.rotation, this.pos, occupant);
        //on place une tuile avec l'occupant qu'on veut.
    }

    public PlacedTile withNoOccupant(){
        return new PlacedTile(this.tile, this.placer, this.rotation, this.pos);
    }


    public int idOfZoneOccupiedBy(Occupant.Kind occupantKind){
        if(this.occupant != null){
            return this.occupant.zoneId();
        }
        return -1;
    }






}
