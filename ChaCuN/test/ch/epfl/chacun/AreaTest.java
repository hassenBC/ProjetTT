package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AreaTest {

   /**static ArrayList <Zone> getZone (Zone zone) {
       var zoneId = 56*10;
       var l0 = new Zone.Lake(zoneId + 8, 1, null);
       var a0_0 = new Animal(zoneId * 100, Animal.Kind.AUROCHS);
       var z0 = new Zone.Meadow(zoneId * 10, List.of(a0_0), null);
       var z1 = new Zone.Forest(zoneId * 10 + 1, Zone.Forest.Kind.WITH_MENHIR);
       var z2 = new Zone.Meadow(zoneId * 10 + 2, List.of(), null);
       var z3 = new Zone.River(zoneId * 10 + 3, 0, l0);
       Set<Zone> zones = new HashSet<>();
       if (zone instanceof Zone.River) {
           zones.add(z3);
       } if (zone instanceof Zone.River) {
           zones.add(z3);
       }
   }*/

   @Test
    void ConstructorTest () {
        Set <Zone.River> rivers = new HashSet<>();
        var l0 = new Zone.Lake(568, 1, null);
        var z3 = new Zone.River(563, 0, l0);
        rivers.add(z3);
        var z1 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Set <Zone.Forest> forests = new HashSet<>();
        forests.add(z1);
        assertThrows(IllegalArgumentException.class, () -> {Area <Zone.Forest> f0 = new Area<>(forests, new ArrayList<>(), -3);});

    }



    // @Test
    //void hasMenhir ()
}
