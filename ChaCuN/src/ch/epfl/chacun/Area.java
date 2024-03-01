package ch.epfl.chacun;
import java.util.*;

public record Area<Z>(Set<Z> zones, List<PlayerColor> occupants, int openConnections) {

        public Area{
            Preconditions.checkArgument(openConnections>=0);
            zones = new HashSet<>(zones);
            occupants = new ArrayList<>(occupants);
            Collections.sort(occupants);

        }
}
