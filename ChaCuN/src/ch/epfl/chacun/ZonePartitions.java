package ch.epfl.chacun;

import java.util.HashSet;
import java.util.Set;

public record ZonePartitions(ZonePartition<Zone.Forest> forests, ZonePartition<Zone.Meadow> meadows, ZonePartition<Zone.River> rivers, ZonePartition<Zone.Water> riverSystems) {
    private static final ZonePartitions EMPTY = new ZonePartitions(new ZonePartition<>(), new ZonePartition<>(), new ZonePartition<>(), new ZonePartition<>());

    public static final class Builder {
        private ZonePartition.Builder<Zone.Forest> forests;
        private ZonePartition.Builder<Zone.Meadow> meadows;
        private ZonePartition.Builder<Zone.River> rivers;
        private ZonePartition.Builder<Zone.Water> riverSystems;

        public Builder (ZonePartitions initial) {
            this.forests = new ZonePartition.Builder<>(initial.forests());
            this.meadows = new ZonePartition.Builder<>(initial.meadows());
            this.rivers = new ZonePartition.Builder<>(initial.rivers());
            this.riverSystems = new ZonePartition.Builder<>(initial.riverSystems);

        }

        //pour le nb de openconnections de lake : nb de rivières
        //par défaut chaque rivière a deux OC
        public void addTile (Tile tile) {
            Set <Zone> zones = new HashSet<>(tile.zones());
            for (Zone zone : zones) {
                int counter = 0;
                if

            }

        }
    }




}
