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



    @Test
    void hasMenhir () {
         var z1 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
         var z2 = new Zone.Forest(562, Zone.Forest.Kind.WITH_MENHIR);
         var z3 = new Zone.Forest(563, Zone.Forest.Kind.WITH_MUSHROOMS);
         var z4 = new Zone.Forest(564, Zone.Forest.Kind.PLAIN);
         Set <Zone.Forest> with = new HashSet<>();
         with.add(z1); with.add(z2); with.add(z3); with.add(z4);
         Set <Zone.Forest> without = new HashSet<>();
         without.add(z3); without.add(z4);
         Area <Zone.Forest> withArea = new Area<>(with, new ArrayList<>(), 4);
         Area <Zone.Forest> withoutArea = new Area<>(without, new ArrayList<>(), 4);
         assertTrue(Area.hasMenhir(withArea));
         assertFalse(Area.hasMenhir(withoutArea));
    }

     void mushroomGroupTest(){
         var z1 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
         var z2 = new Zone.Forest(562, Zone.Forest.Kind.WITH_MUSHROOMS);
         var z3 = new Zone.Forest(563, Zone.Forest.Kind.WITH_MUSHROOMS);
         var z4 = new Zone.Forest(564, Zone.Forest.Kind.PLAIN);
         Set <Zone.Forest> with = new HashSet<>();
         with.add(z1); with.add(z2); with.add(z3); with.add(z4);
         Set <Zone.Forest> without = new HashSet<>();
         without.add(z1); without.add(z4);
         Area <Zone.Forest> withArea = new Area<>(with, new ArrayList<>(), 4);
         Area <Zone.Forest> withoutArea = new Area<>(without, new ArrayList<>(), 4);
         assertEquals(2, Area.mushroomGroupCount(withArea));
         assertEquals(0, Area.mushroomGroupCount(withoutArea));
     }
     @Test
     void animalsTest () {
         var a0_0 = new Animal(1000, Animal.Kind.AUROCHS);
         var a0_1 = new Animal(1001, Animal.Kind.DEER);
         var a0_2 = new Animal(1002, Animal.Kind.MAMMOTH);
         var a0_3 = new Animal(1003, Animal.Kind.TIGER);
         var a0_4 = new Animal(1002, Animal.Kind.MAMMOTH);
         var z0 = new Zone.Meadow(560, List.of(a0_2, a0_3, a0_4), null);
         var z1 = new Zone.Meadow(561, List.of(a0_0, a0_1), null);
         var area = new Area<>(Set.of(z0, z1), new ArrayList<>(), 4);
         var c1 = new HashSet<Animal>();
         var c2 = new HashSet<Animal>();
         var c3 = new HashSet<Animal>();
         c1.add(a0_0);
         //tester le cas où y'a un duplicat d'un mm animal dans deux areas différentes
         c1.add(a0_1);
         c1.add(a0_2);
         c1.add(a0_3);
         c1.add(a0_4);
         c2.add(a0_0);
         c2.add(a0_1);
         c2.add(a0_2);
         c2.add(a0_3);
         assertEquals(Set.of(a0_0, a0_1, a0_2, a0_3, a0_4), Area.animals(area, c3));
         assertEquals(Set.of(a0_0, a0_1, a0_2, a0_3, a0_4), Area.animals(area, c3));


     }








}
