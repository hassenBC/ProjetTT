package ch.epfl.chacun;

import java.util.PrimitiveIterator;

public record PlacedTile(Tile tile, PlayerColor placer, Rotation rotation, Pos pos, Occupant occupant) {
    public PlacedTile{
        Preconditions.checkArgument(tile != null);
        Preconditions.checkArgument(pos != null);
        Preconditions.checkArgument(rotation !=null);
    }
     public PlacedTile(Tile tile,PlayerColor placer,Rotation rotation,Pos pos){
        this(tile,placer,rotation,pos,null);
    }

    public int id(){
        return
    }
    public Tile.Kind kind(){
        return

    }





}
