package ch.epfl.chacun;

import javax.sound.sampled.FloatControl;
import java.util.*;

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

        /** trouver le nb de connexions ouvertes de chaque zone
         *
         * @param
         * @return int
         */
        private int zoneConnections (Tile tile, Zone zone) {
            Set<Zone> tileZones = new HashSet<>(tile.zones());
            Set<TileSide> tileSides = new HashSet<>(tile.sides());
            int sideCount = 0;
            //oc de lake ++ à chaque fois qu'on trouve rivière connectée à elle
            if (zone instanceof Zone.Lake) {
                int count = 0;
                for (Zone z : tileZones) {
                    if (z instanceof Zone.River) {
                        if (((Zone.River) z).hasLake()){
                            if (((Zone.River) z).lake().id() == zone.id()) {
                                ++ count;
                            }
                        }
                    }
                }
                return count;
            }
            //si la zone n'est pas un lake on compte elle apparait dans combien de TileSides
            else if (zone instanceof Zone.River) {
                return 2;
            }
            else {
                for (TileSide t : tileSides) {
                    List<Zone> tz = t.zones();
                    for (Zone z : tz) {
                        if (z.localId() == zone.localId()) {
                            ++sideCount;
                        }
                    }
                }
                return sideCount;

            }
        }

        /** Map useful to get the openconnections of each zone indexed by its localid
         * @author Tony Andriamampianina (363559)
         * @param tile
         * @return map of openconnections
         */
        private Map <Integer, Integer> ocArray (Tile tile) {
            Set<Zone> zones = new HashSet<>(tile.zones());
            Map<Integer, Integer> zoneIdToConnexions = new HashMap<>();

            for (Zone zone : zones) {
                int localId = zone.localId();
                int connexions = zoneConnections(tile, zone);
                zoneIdToConnexions.put(localId, connexions);
            }
            return zoneIdToConnexions;

        }

        /** adding each zone in an individual area to the corresponding zonepartition builder
         * @author Tony Andriamampianina (363559)
         * @param tile
         */
        private void addtoPartitions (Tile tile) {
            Map<Integer, Integer> openConnections = ocArray(tile);
            Set<Zone> zones = Set.copyOf(tile.zones());
            for (Zone zone : zones) {
                int connections = openConnections.get(zone.localId());

                //reduce the rivers openC number by 1 in riverSystems if they are connected to a lake
                if (zone instanceof Zone.River river) {
                    int riverConnections = river.hasLake() ? connections - 1 : connections;
                    rivers.addSingleton(river, riverConnections);
                    riverSystems.addSingleton(river, connections);
                }
                else if (zone instanceof Zone.Lake lake) {
                    riverSystems.addSingleton(lake, connections);
                }
                else if (zone instanceof Zone.Forest forest) {
                    forests.addSingleton(forest, connections);
                }
                else if (zone instanceof Zone.Meadow meadow) {
                    meadows.addSingleton(meadow, connections);
                }
            }
        }

        /** add adding each zone of the tile to zonepartitions while being careful to connect the rivers to their lake if they have one
         * @author Tony Andriamampianina (363559)
         * @param tile
         */
        public void addTile (Tile tile) {
            addtoPartitions(tile);
            for (Area <Zone.River> riverArea : rivers.build().areas()) {
                for (Zone.River river : riverArea.zones()) {
                    if (river.hasLake()) {
                        riverSystems.union(river, river.lake());
                    }
                }
            }
        }
    }
}
