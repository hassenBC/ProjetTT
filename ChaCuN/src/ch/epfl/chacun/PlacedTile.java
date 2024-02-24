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
        return tile.id();
    }
    public Tile.Kind kind(){
        return tile.kind();
    }

    public TileSide side(Direction direction){

         Direction newDirection = direction.rotated(rotation.negated());
         switch (newDirection){
             case N :
                 return tile.n();
             case E :
                 return tile.e();
             case S:
                 return tile.s();
             case W:
                 return tile.w();

         }//A TESTER RIGOUREUSEMENT !!!!!!!!!!!!!!!!!!!

        return null;
    }






}
