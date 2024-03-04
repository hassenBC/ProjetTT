package ch.epfl.chacun;

import java.util.*;
import java.util.HashSet;
import static java.util.Set.copyOf;
import java.util.Set;
public record ZonePartition <Z extends Zone> (Set <Area<Z>> areas) {
    public ZonePartition {
        areas = Set.copyOf(areas);
    }
    public ZonePartition () {
        this (new HashSet<Area<Z>>());
    }
    public Area <Z> areaContaining (Z zone) {
        for (Area<Z> area : areas ) {
            if (area.zones().contains(zone)) {
                return area;
            }
        }
        throw new IllegalArgumentException();
    }

    public static final class Builder <Z extends Zone>{
        private Set <Area<Z>> areas = new HashSet <Area<Z>>();
        public Builder (ZonePartition <Z> partition){
            this.areas = partition.areas();

        }
        //demander si j'ai le droit de rendre areacontaining static
        public void addSingleton (Z zone, int openConnections) {
            Set <Z> singleZone = new HashSet<>();
            singleZone.add(zone);
            Area <Z> singleArea = new Area<>(singleZone, new ArrayList<PlayerColor>(), openConnections);
            areas.add(singleArea);
        }

        public void addInitialOccupant (Z zone, PlayerColor color) {
            boolean updated = false;
            Area <Z> areaToUpdate = null;
            for (Area <Z> area : areas) {
                if (area.zones().contains(zone) && !area.isOccupied()) {
                    areaToUpdate = area;
                    updated = true;
                    break;
                }
            }
            if (updated) {
                List<PlayerColor> newOccupant = new ArrayList<>();
                newOccupant.add(color);
                Area<Z> newArea = new Area<>(areaToUpdate.zones(), newOccupant, areaToUpdate.openConnections());
                areas.remove(areaToUpdate);
                areas.add(newArea);
            }
            else {
                    throw new IllegalArgumentException("Zone does not belong to any area or area is already occupied.");
                }
        }

        public void removeOccupant (Z zone, PlayerColor color) {
            boolean updated = false;
            Area <Z> areaToUpdate = null;
            for (Area <Z> area : areas) {
                if (area.zones().contains(zone) && area.occupants().contains(color)) {
                    areaToUpdate = area;
                    updated = true;
                    break;
                }
            }
            if (updated) {
                List<PlayerColor> oldOccupant = new ArrayList<>(areaToUpdate.occupants());
                oldOccupant.remove(color);
                Area<Z> newArea = new Area<>(areaToUpdate.zones(), oldOccupant, areaToUpdate.openConnections());
                areas.remove(areaToUpdate);
                areas.add(newArea);
            }
            else {
                throw new IllegalArgumentException("Zone does not belong to any area or area is not occupied.");
            }
        }

        public void removeAllOccupantsOf (Area <Z> area) {
            if (areas.contains(area)) {
                List <PlayerColor> noOccupant = new ArrayList<>();
                Area <Z> newArea = new Area <> (area.zones(), noOccupant, area.openConnections());
                areas.remove(area);
                areas.add(newArea);
            } else {
                throw new IllegalArgumentException("Area does not belong in the partition");
            }
        }
        public void union (Z zone1, Z zone2) {
            boolean zone1Belongs = false;
            boolean zone2Belongs = false;
            boolean sameArea = false;
            for (Area <Z> area : areas) {
                if (area.zones().contains(zone1) && area.zones().contains(zone2)) {
                    Area <Z> newArea = new Area<>(area.zones(), area.occupants(), area.openConnections()-2);
                    areas.remove(area);
                    areas.add(newArea);
                    sameArea = true;
                    break;
                } else if (area.zones().contains(zone1) && !area.zones().contains(zone2)) {
                    zone1Belongs = true;
                    
                } else if (!area.zones().contains(zone1) && area.zones().contains(zone2)) {
                    
                }
            }
        }
    }
}


