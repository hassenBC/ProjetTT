package ch.epfl.chacun;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record Area(Set<Zone> zones, List<PlayerColor> occupants, int openConnections) {
        public Area{
            Preconditions.checkArgument(openConnections>=0);

            //Sort mes couleurs
            //copie défensive comment faire
            //zones = Set.copyOf(zones); avant de faire la copie il faut les trier
        }
}
