package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AreaTest {

   static ArrayList <Zone> getZone (Zone zone) {
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

    Area ConstructorTest () {
        Area <Zone.Forest> f0 = new Area<>()

    }



    // @Test
    //void hasMenhir ()
}
