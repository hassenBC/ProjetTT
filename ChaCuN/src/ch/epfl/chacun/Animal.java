package ch.epfl.chacun;

public record Animal(int id, Kind kind) {
    private enum Kind {
        MAMMOTH, AUROCHS, DEER, TIGER;
    }
    public Animal (int id, Kind kind) {
        this.kind = kind;
        this.id = id;
    }
    public int localId(){
        int var = 0;
        if (id % 10 == 1) {
            var = 1;
        }
        else if (id % 10 == 0) {
            var = 0;
        }
        return var;
    }
    public int tileId() {
        int zoneId = (id - localId())/10;
        int localZoneId = zoneId % 10;
        return ((zoneId - localZoneId) / 10);



    }

}
