package ch.epfl.chacun;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class AreaTest {


    @Test
    void ConstructorTest() {
        Set<Zone.River> rivers = new HashSet<>();
        var l0 = new Zone.Lake(568, 1, null);
        var z3 = new Zone.River(563, 0, l0);
        rivers.add(z3);
        var z1 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> forests = new HashSet<>();
        forests.add(z1);
        assertThrows(IllegalArgumentException.class, () -> {
            Area<Zone.Forest> f0 = new Area<>(forests, new ArrayList<>(), -3);
        });

    }


    @Test
    void hasMenhir() {
        var z1 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        var z2 = new Zone.Forest(562, Zone.Forest.Kind.WITH_MENHIR);
        var z3 = new Zone.Forest(563, Zone.Forest.Kind.WITH_MUSHROOMS);
        var z4 = new Zone.Forest(564, Zone.Forest.Kind.PLAIN);
        Set<Zone.Forest> with = new HashSet<>();
        with.add(z1);
        with.add(z2);
        with.add(z3);
        with.add(z4);
        Set<Zone.Forest> without = new HashSet<>();
        without.add(z3);
        without.add(z4);
        Area<Zone.Forest> withArea = new Area<>(with, new ArrayList<>(), 4);
        Area<Zone.Forest> withoutArea = new Area<>(without, new ArrayList<>(), 4);
        assertTrue(Area.hasMenhir(withArea));
        assertFalse(Area.hasMenhir(withoutArea));
    }

    @Test
    void mushroomGroupTest() {
        var z1 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        var z2 = new Zone.Forest(562, Zone.Forest.Kind.WITH_MUSHROOMS);
        var z3 = new Zone.Forest(563, Zone.Forest.Kind.WITH_MUSHROOMS);
        var z4 = new Zone.Forest(564, Zone.Forest.Kind.PLAIN);
        Set<Zone.Forest> with = new HashSet<>();
        with.add(z1);
        with.add(z2);
        with.add(z3);
        with.add(z4);
        Set<Zone.Forest> without = new HashSet<>();
        without.add(z1);
        without.add(z4);
        Area<Zone.Forest> withArea = new Area<>(with, new ArrayList<>(), 4);
        Area<Zone.Forest> withoutArea = new Area<>(without, new ArrayList<>(), 4);
        assertEquals(2, Area.mushroomGroupCount(withArea));
        assertEquals(0, Area.mushroomGroupCount(withoutArea));
    }

    @Test
    void animalsTest() {
        var a0_0 = new Animal(1000, Animal.Kind.AUROCHS);
        var a0_1 = new Animal(1001, Animal.Kind.DEER);
        var a0_2 = new Animal(1002, Animal.Kind.MAMMOTH);
        var a0_3 = new Animal(1003, Animal.Kind.TIGER);
        var a0_4 = new Animal(1004, Animal.Kind.MAMMOTH);
        var z0 = new Zone.Meadow(560, List.of(a0_2, a0_3, a0_4), null);
        var z1 = new Zone.Meadow(561, List.of(a0_0, a0_1), null);
        var area = new Area<>(Set.of(z0, z1), new ArrayList<>(), 4);
        var c1 = new HashSet<Animal>();
        var c2 = new HashSet<Animal>();
        var c3 = new HashSet<Animal>();
        c1.add(a0_0);
        c1.add(a0_1);
        c1.add(a0_2);
        c1.add(a0_3);
        c1.add(a0_4);
        c2.add(a0_0);
        c2.add(a0_1);
        c2.add(a0_2);
        c2.add(a0_3);
        assertEquals(Set.of(a0_0, a0_1, a0_2, a0_3, a0_4), Area.animals(area, c3));
        assertEquals(Set.of(a0_4), Area.animals(area, c2));
        assertEquals(Set.of(), Area.animals(area, c1));
    }

    @Test
    void riverFishCountTest() {
        var l0 = new Zone.Lake(568, 2, null);
        var z3 = new Zone.River(560, 3, l0);
        var z4 = new Zone.River(561, 6, l0);
        Area<Zone.River> river = new Area<>(Set.of(z3, z4), List.of(), 0);
        assertEquals(11, Area.riverFishCount(river));
    }

    @Test
    void riverSystFishCountTest() {
        var l0 = new Zone.Lake(568, 2, null);
        var z3 = new Zone.River(560, 3, l0);
        var z4 = new Zone.River(561, 6, l0);
        Area<Zone.Water> waterArea = new Area<>(Set.of(z3, z4, l0), List.of(), 0);
        var l1 = new Zone.Lake(570, 2, null);
        var z5 = new Zone.River(565, 3, null);
        var z6 = new Zone.River(566, 6, l0);
        Area<Zone.Water> waterArea1 = new Area<>(Set.of(z5, z6, l1), List.of(), 0);
        var l2 = new Zone.Lake(512, 0, null);
        var z7 = new Zone.River(518, 0, null);
        var z8 = new Zone.River(520, 0, l0);
        Area<Zone.Water> waterArea2 = new Area<>(Set.of(z7, z8, l2), List.of(), 0);
        Area<Zone.Water> waterArea3 = new Area<>(Set.of(z7, z8, l1), List.of(), 0);
        assertEquals(0, Area.riverSystemFishCount(waterArea2));
        assertEquals(2, Area.riverSystemFishCount(waterArea3));
        assertEquals(11, Area.riverSystemFishCount(waterArea));
        assertEquals(11, Area.riverSystemFishCount(waterArea1));

    }

    @Test
    void lakeCountTest() {
        var l0 = new Zone.Lake(568, 2, null);
        var z3 = new Zone.River(560, 3, l0);
        var z4 = new Zone.River(561, 6, l0);
        Area<Zone.Water> waterArea = new Area<>(Set.of(z3, z4, l0), List.of(), 0);
        var l1 = new Zone.Lake(570, 2, null);
        var l2 = new Zone.Lake(571, 0, null);
        var z5 = new Zone.River(565, 3, l2);
        var z6 = new Zone.River(566, 6, l0);
        Area<Zone.Water> waterArea1 = new Area<>(Set.of(z5, z6, l1, l2), List.of(), 0);
        var z7 = new Zone.River(532, 0, null);
        var z8 = new Zone.River(533, 0, null);
        Area<Zone.Water> waterArea2 = new Area<>(Set.of(z7, z8), List.of(), 0);
        assertEquals(1, Area.lakeCount(waterArea));
        assertEquals(2, Area.lakeCount(waterArea1));
        assertEquals(0, Area.lakeCount(waterArea2));
    }

    @Test
    void isClosedTest() {
        var a0_0 = new Animal(1000, Animal.Kind.AUROCHS);
        var a0_1 = new Animal(1001, Animal.Kind.DEER);
        var a0_2 = new Animal(1002, Animal.Kind.MAMMOTH);
        var a0_3 = new Animal(1003, Animal.Kind.TIGER);
        var a0_4 = new Animal(1002, Animal.Kind.MAMMOTH);
        var z0 = new Zone.Meadow(560, List.of(a0_2, a0_3, a0_4), null);
        var z1 = new Zone.Meadow(561, List.of(a0_0, a0_1), null);
        var area = new Area<>(Set.of(z0, z1), new ArrayList<>(), 0);
        var c1 = new HashSet<Animal>();
        var c2 = new HashSet<Animal>();
        var c3 = new HashSet<Animal>();
        var b1 = new Zone.Forest(561, Zone.Forest.Kind.WITH_MENHIR);
        var b2 = new Zone.Forest(562, Zone.Forest.Kind.WITH_MENHIR);
        var area2 = new Area<>(Set.of(b1, b2), new ArrayList<>(), 2);
        c1.add(a0_0);
        c1.add(a0_1);
        c1.add(a0_2);
        c1.add(a0_3);
        c1.add(a0_4);
        c2.add(a0_0);
        c2.add(a0_1);
        c2.add(a0_2);
        c2.add(a0_3);
        assertTrue(area.isClosed());
        assertFalse(area2.isClosed());
        assertTrue(area2.connectTo(area2).isClosed());
    }

    @Test
    void isOccupied() {
        var a0_0 = new Animal(1000, Animal.Kind.AUROCHS);
        var a0_1 = new Animal(1001, Animal.Kind.DEER);
        var a0_2 = new Animal(1002, Animal.Kind.MAMMOTH);
        var a0_3 = new Animal(1003, Animal.Kind.TIGER);
        var a0_4 = new Animal(1002, Animal.Kind.MAMMOTH);
        var z0 = new Zone.Meadow(560, List.of(a0_2, a0_3, a0_4), null);
        var z1 = new Zone.Meadow(561, List.of(a0_0, a0_1), null);
        List<PlayerColor> noOccupant = new ArrayList<>();
        List<PlayerColor> oneOccupant = List.of(PlayerColor.RED);

        var areaEmpty = new Area<>(Set.of(z0, z1), noOccupant, 0);
        var areaOne = new Area<>(Set.of(z0, z1), oneOccupant, 0);
        assertTrue(areaOne.isOccupied());
        assertFalse(areaEmpty.isOccupied());
        assertTrue(areaEmpty.withInitialOccupant(PlayerColor.BLUE).isOccupied());

    }

    @Test
    void majorityOccupantsTest() {
        var z0 = new Zone.Meadow(560, List.of(), null);
        var z1 = new Zone.Meadow(561, List.of(), null);
        var occupants = List.of(PlayerColor.RED, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.BLUE, PlayerColor.PURPLE);
        var area = new Area<>(Set.of(z0, z1), new ArrayList<>(), 0);
        var area2 = new Area<>(Set.of(z0, z1), occupants, 0);
        var area3 = new Area<>(Set.of(z0, z1), List.of(PlayerColor.RED, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.GREEN, PlayerColor.GREEN, PlayerColor.PURPLE), 0);
    }

    @Test
    void connectToTest() {
        var z0 = new Zone.Meadow(560, List.of(), null);
        var z1 = new Zone.Meadow(561, List.of(), null);
        var z2 = new Zone.Meadow(562, List.of(), null);
        var z3 = new Zone.Meadow(563, List.of(), null);
        var area2 = new Area<>(Set.of(z0, z1), new ArrayList<>(), 3);
        var area3 = new Area<>(Set.of(z2, z3), new ArrayList<>(), 2);
        var areaFin = new Area<>(Set.of(z0, z1, z2, z3), new ArrayList<>(), 3);
        var area2bis = new Area<>(Set.of(z0, z1), new ArrayList<>(), 1);

        assertEquals(areaFin, area2.connectTo(area3));
        assertEquals(area2bis, area2.connectTo(area2));
    }

    @Test
    void withInitialOccupant() {
        var z0 = new Zone.Meadow(560, List.of(), null);
        var z1 = new Zone.Meadow(561, List.of(), null);
        var occupants = List.of(PlayerColor.RED, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.BLUE, PlayerColor.PURPLE);
        var areaRef = new Area<>(Set.of(z0, z1), List.of(PlayerColor.RED), 0);
        var areaEmpty = new Area<>(Set.of(z0, z1), List.of(), 0);
        assertThrows(IllegalArgumentException.class, () -> areaRef.withInitialOccupant(PlayerColor.RED));
        assertThrows(IllegalArgumentException.class, () -> areaRef.withInitialOccupant(PlayerColor.GREEN));
        assertEquals(areaRef, areaEmpty.withInitialOccupant(PlayerColor.RED));
    }

    @Test
    void withoutOccupant() {
        var z0 = new Zone.Meadow(560, List.of(), null);
        var z1 = new Zone.Meadow(561, List.of(), null);
        var occupants = List.of(PlayerColor.RED, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.BLUE, PlayerColor.PURPLE);
        var areaRef = new Area<>(Set.of(z0, z1), List.of(PlayerColor.RED, PlayerColor.PURPLE), 0);
        var areaEmpty = new Area<>(Set.of(z0, z1), List.of(), 0);

        assertThrows(IllegalArgumentException.class, () -> areaEmpty.withoutOccupant(PlayerColor.GREEN));
        assertThrows(IllegalArgumentException.class, () -> areaRef.withoutOccupant(PlayerColor.GREEN));
        assertEquals(new Area<>(Set.of(z0, z1), List.of(PlayerColor.RED), 0), areaRef.withoutOccupant(PlayerColor.PURPLE));

    }

    @Test
    void withOccupants() {
        var z0 = new Zone.Meadow(560, List.of(), null);
        var z1 = new Zone.Meadow(561, List.of(), null);
        var occupants = List.of(PlayerColor.RED, PlayerColor.RED, PlayerColor.BLUE, PlayerColor.BLUE, PlayerColor.PURPLE);
        var areaRef = new Area<>(Set.of(z0, z1), List.of(PlayerColor.RED, PlayerColor.PURPLE), 0);
        var areaEmpty = new Area<>(Set.of(z0, z1), List.of(), 0);
        assertEquals(areaEmpty, areaRef.withoutOccupants());
        assertEquals(areaEmpty, areaEmpty.withoutOccupants());
    }

    @Test
    void tileIds() {
        var l0 = new Zone.Lake(568, 2, null);
        var z3 = new Zone.River(560, 3, l0);
        var z4 = new Zone.River(561, 6, l0);
        var l1 = new Zone.Lake(570, 2, null);
        var z5 = new Zone.River(565, 3, null);
        var z6 = new Zone.River(536, 6, l0);
        Area<Zone.Water> waterArea = new Area<>(Set.of(z3, z4, l0, l1, z5, z6), List.of(), 0);
        assertEquals(Set.of(56, 57, 53), waterArea.tileIds());
    }

    @Test
    void zoneWithSpecialPower() {
        var z0 = new Zone.Meadow(560, List.of(), Zone.SpecialPower.SHAMAN);
        var z1 = new Zone.Meadow(561, List.of(), Zone.SpecialPower.RAFT);
        var z2 = new Zone.Meadow(562, List.of(), null);
        var z3 = new Zone.Meadow(563, List.of(), Zone.SpecialPower.LOGBOAT);
        var areaFin = new Area<>(Set.of(z0, z1, z2, z3), new ArrayList<>(), 3);
        assertEquals(z0, areaFin.zoneWithSpecialPower(Zone.SpecialPower.SHAMAN));
        assertNull(areaFin.zoneWithSpecialPower(Zone.SpecialPower.WILD_FIRE));
    }
}
