package ch.epfl.chacun;

import javax.sound.sampled.FloatControl;
import java.util.*;
import ch.epfl.chacun.Occupant.Kind;


public record ZonePartitions(ZonePartition<Zone.Forest> forests, ZonePartition<Zone.Meadow> meadows, ZonePartition<Zone.River> rivers, ZonePartition<Zone.Water> riverSystems) {
    public static final ZonePartitions EMPTY = new ZonePartitions(new ZonePartition<>(), new ZonePartition<>(), new ZonePartition<>(), new ZonePartition<>());

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
            List<TileSide> tileSides = new ArrayList<>(tile.sides());
            int sideCount = 0;
            //oc de lake ++ à chaque fois qu'on trouve rivière connectée à elle
            if (zone instanceof Zone.Lake) {
                int count = 0;
                for (Zone z : tileZones) {
                    if (z instanceof Zone.River river) {
                        if (river.hasLake()){
                            if (river.lake().id() == zone.id()) {
                                ++ count;
                            }
                        }
                    }
                }
                return count;
            }
            //si la zone n'est pas un lake on compte elle apparait dans combien de TileSides
            else if (zone instanceof Zone.River) {
                System.out.println("river " + zone.id() + " has 2 connections");
                return 2;
            }
            else {
                // on check le nb de fois qu'on trouve la zone donnée dans un tileside
                for (TileSide t : tileSides) {

                    List<Zone> tz = t.zones();
                    for (Zone z : tz) {
                        if (z.localId() == zone.localId()) {
                            ++sideCount;
                        }
                    }
                }
                System.out.println(zone + " has " + sideCount + " connections" );
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
        private Set <Zone.River> addtoPartitions (Tile tile) {
            Map<Integer, Integer> openConnections = ocArray(tile);
            Set<Zone> zones = Set.copyOf(tile.zones());
            Set <Zone.River> newRivers = new HashSet<>();
            for (Zone zone : zones) {
                int connections = openConnections.get(zone.localId());

                //reduce the rivers openC number by 1 in riverSystems if they are connected to a lake
                if (zone instanceof Zone.River river) {
                    int riverConnections = river.hasLake() ? connections - 1 : connections;
                    rivers.addSingleton(river, riverConnections);
                    riverSystems.addSingleton(river, connections);
                    newRivers.add(river);
                    System.out.println("nb of connection in rivers " + riverConnections);
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
            } return newRivers;
            //System.out.println(riverSystems.build().areas());
            //System.out.println(rivers.build().areas());
        }

        /** add adding each zone of the tile to zonepartitions while being careful to connect the rivers to their lake if they have one
         * @author Tony Andriamampianina (363559)
         * @param tile
         */
        public void addTile (Tile tile) {
            Set<Zone.River> newRivers = addtoPartitions(tile);
            //System.out.println();
            for (Zone.River river : newRivers) {
                System.out.println("River " + river.id() + " hasLake: " + river.hasLake());
                if (river.hasLake()) {
                    //System.out.println(riverSystems.build().areas());
                    riverSystems.union(river, river.lake());
                   // System.out.println(riverSystems.build().areas());
                }
            }
        }
            //System.out.println("forests after addtile: " + forests.build());
          //System.out.println("riversyst after addtile" + riverSystems.build());

        /** méthode connectant les côtés de deux différentes tuiles.
         * @author Tony Andriamampianina(363559)
         * @param s1
         * @param s2
         */
       // bien tester le cas où on a deux rivers en tileside
        public void connectSides (TileSide s1, TileSide s2) {
            switch (s1) {
                case TileSide.Meadow (Zone.Meadow m1)
                    // m1 et m2 sont les équivalents de s1.meadow() et s2.meadow()
                    when s2 instanceof TileSide.Meadow (Zone.Meadow m2) ->
                    meadows.union(m1, m2);

                case TileSide.Forest (Zone.Forest f1)
                    when s2 instanceof TileSide.Forest (Zone.Forest f2) ->
                    forests.union(f1, f2);

                // bien vérifier pdt les tests que les meadows des deux tilesides sont bien fusionnées entre elles et pas fusionnées à la mauvaise
                case TileSide.River(Zone.Meadow a, Zone.River r1, Zone.Meadow b)
                        when s2 instanceof TileSide.River(Zone.Meadow x, Zone.River r2, Zone.Meadow y) -> {
                        rivers.union(r1, r2);
                        meadows.union(((TileSide.River) s1).meadow1(), y);
                        meadows.union(((TileSide.River) s1).meadow2(), x);
                }
                default ->
                        throw new IllegalArgumentException("the two sides are not of the same kind");


            }
        }
        private Area zoneArea (Zone zone) {
            if (zone instanceof Zone.River) {
                for (Area <Zone.River> riverArea : rivers.build().areas()) {
                    for (Zone.River river : riverArea.zones()) {
                        if (river.id() == zone.id())
                            return riverArea;
                    }
                }
            }
            if (zone instanceof Zone.Forest) {
                for (Area <Zone.Forest> forestArea : forests.build().areas()) {
                    for (Zone.Forest forest : forestArea.zones()) {
                        if (forest.id() == zone.id())
                            return forestArea;
                        }
                    }
                }

            if (zone instanceof Zone.Meadow) {
                for (Area <Zone.Meadow> meadowArea : meadows.build().areas()) {
                    for (Zone.Meadow meadow : meadowArea.zones()) {
                        if (meadow.id() == zone.id())
                            return meadowArea;
                    }
                }
            }
            if (zone instanceof Zone.Lake) {
                for (Area <Zone.Water> lakeArea : riverSystems.build().areas()) {
                    for (Zone.Water lake : lakeArea.zones()) {
                        if (lake.id() == zone.id())
                            return lakeArea;
                    }
                }
            }
            throw new IllegalArgumentException("doesn't belong to any area");
        }
        public void addInitialOccupant (PlayerColor player, Occupant.Kind occupantKind, Zone occupiedZone) {
            //Area currentArea = zoneArea(occupiedZone);
            switch (occupiedZone) {
                case Zone.Forest forest
                    when occupantKind.equals(Kind.PAWN) ->
                    forests.addInitialOccupant(forest, player);
                case Zone.Meadow meadow
                    when occupantKind.equals(Kind.PAWN) ->
                    meadows.addInitialOccupant(meadow, player);
                case Zone.River river -> {
                    if (occupantKind.equals(Kind.PAWN) && !river.hasLake()) {
                        rivers.addInitialOccupant(river, player);
                    } else if (occupantKind.equals(Kind.HUT) && river.hasLake()) {
                        riverSystems.addInitialOccupant(river, player);
                    }
                }
                case Zone.Lake lake
                        when occupantKind.equals(Kind.HUT) ->
                        riverSystems.addInitialOccupant(lake, player);
                default -> throw new IllegalArgumentException("occupant kind cannot be on this type of zone");
            }
        }

        public void removePawn (PlayerColor player, Zone occupiedZone) {
            switch (occupiedZone) {
                case Zone.Forest forest ->
                        forests.removeOccupant(forest, player);
                case Zone.Meadow meadow ->
                        meadows.removeOccupant(meadow, player);
                case Zone.River river ->
                        rivers.removeOccupant(river, player);
                case Zone.Lake lake ->
                        throw new IllegalArgumentException("occupant kind cannot be on this type of zone");
            }
        }
        public void clearGatherers (Area <Zone.Forest> forest) {
            forests.removeAllOccupantsOf(forest);
        }
        public void clearFishers (Area <Zone.River> river) {
            rivers.removeAllOccupantsOf(river);
        }
        public ZonePartitions build() {return new ZonePartitions(forests.build(), meadows.build(), rivers.build(), riverSystems.build());}
    }
}
