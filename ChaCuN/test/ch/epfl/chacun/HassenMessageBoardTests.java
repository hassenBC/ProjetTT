package ch.epfl.chacun;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class HassenMessageBoardTests{
    HassenMessageBoardTests(){
    }

    @Test
    void testPointsTestWorksNormalCase(){
        MessageBoard.Message scorer1 = new MessageBoard.Message("test",1,Set.of(PlayerColor.RED),Set.of(12));
        MessageBoard.Message scorer2 = new MessageBoard.Message("test",1,Set.of(PlayerColor.BLUE, PlayerColor.PURPLE),Set.of(12));
        MessageBoard.Message scorer3 = new MessageBoard.Message("test",1,Set.of(PlayerColor.YELLOW,PlayerColor.RED),Set.of(12));
        List<MessageBoard.Message> scorersMessage = List.of(scorer1,scorer2,scorer3);
        Map<PlayerColor, Integer> pointsManual = new HashMap<>();
        TextMaker textMaker = new ConcreteTextMaker();
        MessageBoard testMessageBoard = new MessageBoard(textMaker ,scorersMessage);
        pointsManual.put(PlayerColor.RED, 2);
        pointsManual.put(PlayerColor.BLUE, 1);
        pointsManual.put(PlayerColor.PURPLE, 1);
        pointsManual.put(PlayerColor.YELLOW, 1);
        Assertions.assertEquals(pointsManual,testMessageBoard.points());
    }

    @Test
    void testWithScoredForestNotOccupied(){
        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));
        TextMaker textMaker = new ConcreteTextMaker();
        List<MessageBoard.Message> messageList = List.of(message1,message2);
        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);
        Set<Zone.Forest> zoneSet = new HashSet<>();
        List<PlayerColor> playerColorList = new ArrayList<>();
        Zone.Forest zoneN = new Zone.Forest(10, Zone.Forest.Kind.PLAIN);
        Zone.Forest zoneE = new Zone.Forest(11, Zone.Forest.Kind.PLAIN);
        Zone.Forest zoneS = new Zone.Forest(12, Zone.Forest.Kind.PLAIN);
        zoneSet.add(zoneN);zoneSet.add(zoneE);zoneSet.add(zoneS);
        Area<Zone.Forest> forestAreaTest = new Area<>(zoneSet,playerColorList,2);
        Assertions.assertEquals(messageBoard,messageBoard.withScoredForest(forestAreaTest));


    }

    @Test
    void testWithScoredOccupied(){
        List<PlayerColor> playerColorList = List.of(PlayerColor.RED,PlayerColor.RED,PlayerColor.GREEN);
        Zone.Forest zoneN = new Zone.Forest(100, Zone.Forest.Kind.PLAIN);
        Zone.Forest zoneE = new Zone.Forest(101, Zone.Forest.Kind.PLAIN);
        Zone.Forest zoneS = new Zone.Forest(112, Zone.Forest.Kind.PLAIN);
        Set<Zone.Forest> zoneSet = new HashSet<>();
        zoneSet.add(zoneN);zoneSet.add(zoneE);zoneSet.add(zoneS);
        Area<Zone.Forest> forestAreaTest = new Area<>(zoneSet,playerColorList,2);

        TextMaker textMaker = new ConcreteTextMaker();

        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));
        MessageBoard.Message messageScoredForest = new MessageBoard.Message(
            textMaker.playersScoredForest(forestAreaTest.majorityOccupants(),2*2,0,2)
                ,2*2,forestAreaTest.majorityOccupants(),Set.of(10,11));


        List<MessageBoard.Message> messageList = List.of(message1,message2);
        List<MessageBoard.Message> messageListTestManual = List.of(message1,message2,messageScoredForest);

        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);
        MessageBoard messageBoardTest = new MessageBoard(textMaker,messageListTestManual);

        Assertions.assertEquals(messageBoardTest,messageBoard.withScoredForest(forestAreaTest));


    }

    @Test
    void withClosedForestMenhirWorksNormalCase(){
        List<PlayerColor> playerColorList = List.of(PlayerColor.RED,PlayerColor.RED,PlayerColor.GREEN);
        Zone.Forest zoneN = new Zone.Forest(100, Zone.Forest.Kind.PLAIN);
        Zone.Forest zoneE = new Zone.Forest(101, Zone.Forest.Kind.PLAIN);
        Zone.Forest zoneS = new Zone.Forest(112, Zone.Forest.Kind.WITH_MENHIR);
        Set<Zone.Forest> zoneSet = new HashSet<>();
        zoneSet.add(zoneN);zoneSet.add(zoneE);zoneSet.add(zoneS);
        Area<Zone.Forest> forestAreaTest = new Area<>(zoneSet,playerColorList,2);

        TextMaker textMaker = new ConcreteTextMaker();

        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));
        MessageBoard.Message messageScoredForestMenhir = new MessageBoard.Message(
                textMaker.playerClosedForestWithMenhir(PlayerColor.RED),0,new HashSet<>(),Set.of(10,11));

        List<MessageBoard.Message> messageList = List.of(message1,message2);
        List<MessageBoard.Message> messageListTestManual = List.of(message1,message2,messageScoredForestMenhir);

        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);
        MessageBoard messageBoardTest = new MessageBoard(textMaker,messageListTestManual);

        Assertions.assertEquals(messageBoardTest,messageBoard.withClosedForestWithMenhir(PlayerColor.RED,forestAreaTest));

    }


    @Test
    void withScoredRiverWorksNormalCaseNotOccupied(){
        List<PlayerColor> playerColorList = List.of();

        Zone.Lake zoneLakeN = new Zone.Lake(108,2,null);
        Zone.River zoneN = new Zone.River(100,0,zoneLakeN);
        Zone.River zoneE = new Zone.River(101,2,null);
        Zone.River zoneS = new Zone.River(112,0,null);
        Set<Zone.River> zoneSet = new HashSet<>();
        zoneSet.add(zoneN);zoneSet.add(zoneE);zoneSet.add(zoneS);
        Area<Zone.River> riverAreaTest = new Area<>(zoneSet,playerColorList,2);

        TextMaker textMaker = new ConcreteTextMaker();

        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));


        List<MessageBoard.Message> messageList = List.of(message1,message2);
        List<MessageBoard.Message> messageListTestManual = List.of(message1,message2);

        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);

        Assertions.assertEquals(messageBoard, messageBoard.withScoredRiver(riverAreaTest));

    }

    @Test
    void withScoredRiverWorksNormalCaseOccupied(){
        List<PlayerColor> playerColorList = List.of(PlayerColor.RED,PlayerColor.RED,PlayerColor.GREEN);

        Zone.Lake zoneLakeN = new Zone.Lake(108,2,null);
        Zone.River zoneN = new Zone.River(100,0,zoneLakeN);
        Zone.River zoneE = new Zone.River(101,2,null);
        Zone.River zoneS = new Zone.River(112,0,null);
        Set<Zone.River> zoneSet = new HashSet<>();
        zoneSet.add(zoneN);zoneSet.add(zoneE);zoneSet.add(zoneS);
        Area<Zone.River> riverAreaTest = new Area<>(zoneSet,playerColorList,2);

        TextMaker textMaker = new ConcreteTextMaker();
        String manualString = textMaker.playersScoredRiver(riverAreaTest.majorityOccupants(),
                Points.forClosedRiver(2,4),4,2);

        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));
        MessageBoard.Message messageRiverManual = new MessageBoard.Message(manualString
                ,Points.forClosedRiver(2,4),riverAreaTest.majorityOccupants(),Set.of(10,11)
        );



        List<MessageBoard.Message> messageList = List.of(message1,message2);
        List<MessageBoard.Message> messageListTestManual = List.of(message1,message2,messageRiverManual);

        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);
        MessageBoard messageBoardManual = new MessageBoard(textMaker,messageListTestManual);

        Assertions.assertEquals(messageBoardManual, messageBoard.withScoredRiver(riverAreaTest));

    }

    @Test
    void withScoreHuntingTrapWorksWithPoints(){
        List<PlayerColor> playerColorList = List.of(PlayerColor.RED,PlayerColor.RED,PlayerColor.GREEN);
        Animal auroch1 = new Animal(101,Animal.Kind.AUROCHS);
        Animal tiger1 = new Animal(101,Animal.Kind.TIGER);
        Animal deer1 = new Animal(112,Animal.Kind.DEER);

        Map<Animal.Kind,Integer> animalMapCounter = Map.of(Animal.Kind.AUROCHS,1,Animal.Kind.TIGER,1,Animal.Kind.DEER,1);

        Zone.Meadow zoneN = new Zone.Meadow(100,new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow zoneE = new Zone.Meadow(101,List.of(auroch1,tiger1), null);
        Zone.Meadow zoneS = new Zone.Meadow(112,List.of(deer1),null);
        Set<Zone.Meadow> zoneSet = new HashSet<>();
        zoneSet.add(zoneE);zoneSet.add(zoneS);
        Area<Zone.Meadow> MeadowAreaTest = new Area<>(zoneSet,playerColorList,2);


        TextMaker textMaker = new ConcreteTextMaker();
        String manualString = textMaker.playerScoredHuntingTrap(PlayerColor.RED,Points.forMeadow(0,1,1),animalMapCounter);

        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));
        MessageBoard.Message messageTrapManual = new MessageBoard.Message(manualString,
                Points.forMeadow(0,1,1),
                new HashSet<>(playerColorList),Set.of(10,11));




        List<MessageBoard.Message> messageList = List.of(message1,message2);
        List<MessageBoard.Message> messageListTestManual = List.of(message1,message2,messageTrapManual);

        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);
        MessageBoard messageBoardManual = new MessageBoard(textMaker,messageListTestManual);

        Assertions.assertEquals(messageBoardManual,messageBoard.withScoredHuntingTrap(PlayerColor.RED,MeadowAreaTest));
        //il marche quand les points son nuls c bon

    }

    @Test
    void withScoredLogBoatWorksNormalCase(){
        List<PlayerColor> playerColorList = List.of(PlayerColor.RED,PlayerColor.RED,PlayerColor.GREEN);
        Zone.Lake zoneLakeN = new Zone.Lake(108,2, Zone.SpecialPower.LOGBOAT);
        Zone.River zoneN = new Zone.River(100,0,zoneLakeN);
        Zone.River zoneE = new Zone.River(101,2,null);
        Zone.River zoneS = new Zone.River(112,0,null);
        Set<Zone.Water> zoneSet = new HashSet<>();
        zoneSet.add(zoneN);zoneSet.add(zoneE);zoneSet.add(zoneS); zoneSet.add(zoneLakeN);
        Area<Zone.Water> riverAreaTest = new Area<>(zoneSet,playerColorList,2);

        TextMaker textMaker = new ConcreteTextMaker();
        String manualText = textMaker.playerScoredLogboat(PlayerColor.RED,Points.forLogboat(1),1);


        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));
        MessageBoard.Message messageManualLogBoat = new MessageBoard.Message(manualText,
                Points.forLogboat(1),Set.of(PlayerColor.RED),Set.of(10,11));

        List<MessageBoard.Message> messageList = List.of(message1,message2);
        List<MessageBoard.Message> messageListTestManual = List.of(message1,message2,messageManualLogBoat);

        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);
        MessageBoard messageBoardManual = new MessageBoard(textMaker,messageListTestManual);

        Assertions.assertEquals(messageBoardManual,messageBoard.withScoredLogboat(PlayerColor.RED,riverAreaTest));
    }


    @Test
    void withScoreMeadowWorks(){
        List<PlayerColor> playerColorList = List.of(PlayerColor.RED,PlayerColor.RED,PlayerColor.GREEN);

        Animal auroch1 = new Animal(101,Animal.Kind.AUROCHS);
        Animal tiger1 = new Animal(101,Animal.Kind.TIGER);
        Animal deer1 = new Animal(112,Animal.Kind.DEER);
        Set<Animal> canceledAnimals = Set.of(deer1);
        //Le test passe blc
        Map<Animal.Kind,Integer> animalMapCounter = new HashMap<>();
        animalMapCounter.put(Animal.Kind.DEER,0);
        animalMapCounter.put(Animal.Kind.AUROCHS,1);
        animalMapCounter.put(Animal.Kind.TIGER,1);

        Zone.Meadow zoneN = new Zone.Meadow(100,new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow zoneE = new Zone.Meadow(101,List.of(auroch1,tiger1), null);
        Zone.Meadow zoneS = new Zone.Meadow(112,List.of(deer1),null);
        Set<Zone.Meadow> zoneSet = new HashSet<>();
        zoneSet.add(zoneE);zoneSet.add(zoneS); zoneSet.add(zoneN);
        Area<Zone.Meadow> meadowAreaTest = new Area<>(zoneSet,playerColorList,2);
        int expectedPoints = Points.forMeadow(0,1,0) ;

        TextMaker textMaker = new ConcreteTextMaker();
        String expectedText = textMaker.playersScoredMeadow(Set.of(PlayerColor.RED),expectedPoints,animalMapCounter);

        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));
        MessageBoard.Message messageExpected = new MessageBoard.Message(expectedText,expectedPoints,Set.of(PlayerColor.RED),Set.of(10,11));

        List<MessageBoard.Message> messageList = List.of(message1,message2);
        List<MessageBoard.Message> messageListTestManual = List.of(message1,message2,messageExpected);

        MessageBoard messageBoard = new MessageBoard(textMaker,messageList);
        MessageBoard messageBoardManual = new MessageBoard(textMaker,messageListTestManual);

        Assertions.assertEquals(messageBoardManual,messageBoard.withScoredMeadow(meadowAreaTest,Set.of(deer1)));




    }

    @Test
    void withScoredPitTrapNoPoints(){
        List<PlayerColor> playerColorList = List.of(PlayerColor.RED,PlayerColor.RED,PlayerColor.GREEN);
        Animal auroch1 = new Animal(101,Animal.Kind.AUROCHS);
        Animal tiger1 = new Animal(101,Animal.Kind.TIGER);
        Animal deer1 = new Animal(112,Animal.Kind.DEER);

        Map<Animal.Kind,Integer> animalMapCounter = Map.of(Animal.Kind.AUROCHS,1,Animal.Kind.TIGER,1,Animal.Kind.DEER,1);
        Map<Animal.Kind,Integer> animalMapCounterTestCanceled = Map.of(Animal.Kind.TIGER,1);

        Zone.Meadow zoneN = new Zone.Meadow(100,new ArrayList<>(), Zone.SpecialPower.HUNTING_TRAP);
        Zone.Meadow zoneE = new Zone.Meadow(101,List.of(auroch1,tiger1), null);
        Zone.Meadow zoneS = new Zone.Meadow(112,List.of(deer1),null);
        Set<Zone.Meadow> zoneSet = new HashSet<>();
        zoneSet.add(zoneE);zoneSet.add(zoneS);
        Area<Zone.Meadow> adjMeadowAreaTest = new Area<>(zoneSet,playerColorList,2);


        TextMaker textMaker = new ConcreteTextMaker();

        MessageBoard.Message message1 = new MessageBoard.Message("message 1",2,new HashSet<>(),Set.of(52));
        MessageBoard.Message message2 = new MessageBoard.Message("message 2 ",3,new HashSet<>(),Set.of(51));


        MessageBoard messageBoard = new MessageBoard(textMaker,List.of(message1,message2));

        Assertions.assertEquals(messageBoard,messageBoard.withScoredPitTrap(adjMeadowAreaTest,Set.of(auroch1,deer1)));

    }







}





class ConcreteTextMaker implements TextMaker{

    @Override
    public String playerName(PlayerColor playerColor) {
        return new StringJoiner(" ")
                .add(String.valueOf(playerColor))
                .toString();
    }

    @Override
    public String points(int points) {
        return new StringJoiner(" ")
                .add(String.valueOf(points))
                .toString();
    }

    @Override
    public String playerClosedForestWithMenhir(PlayerColor player) {
        return new StringJoiner(" ")
                .add(String.valueOf(player))
                .toString();
    }

    @Override
    public String playersScoredForest(Set<PlayerColor> scorers, int points, int mushroomGroupCount, int tileCount) {
        return new StringJoiner(" ")
                .add(scorers.toString())
                .add(String.valueOf(points))
                .add(String.valueOf(mushroomGroupCount))
                .add(String.valueOf(tileCount))
                .toString();
    }

    @Override
    public String playersScoredRiver(Set<PlayerColor> scorers, int points, int fishCount, int tileCount) {
        return new StringJoiner(" ")
                .add(scorers.toString())
                .add(String.valueOf(points))
                .add(String.valueOf(fishCount))
                .add(String.valueOf(tileCount))
                .toString();
    }

    @Override
    public String playerScoredHuntingTrap(PlayerColor scorer, int points, Map<Animal.Kind, Integer> animals) {
        return new StringJoiner(" ")
                .add(String.valueOf(scorer))
                .add(String.valueOf(points))
                .add(animals.toString())
                .toString();
    }

    @Override
    public String playerScoredLogboat(PlayerColor scorer, int points, int lakeCount) {
        return new StringJoiner(" ")
                .add(String.valueOf(scorer))
                .add(String.valueOf(points))
                .add(String.valueOf(lakeCount))
                .toString();
    }

    @Override
    public String playersScoredMeadow(Set<PlayerColor> scorers, int points, Map<Animal.Kind, Integer> animals) {
        return new StringJoiner(" ")
                .add(scorers.toString())
                .add(String.valueOf(points))
                .add(animals.toString())
                .toString();
    }

    @Override
    public String playersScoredRiverSystem(Set<PlayerColor> scorers, int points, int fishCount) {
        return new StringJoiner(" ")
                .add(scorers.toString())
                .add(String.valueOf(points))
                .add(String.valueOf(fishCount))
                .toString();
    }

    @Override
    public String playersScoredPitTrap(Set<PlayerColor> scorers, int points, Map<Animal.Kind, Integer> animals) {
        return new StringJoiner(" ")
                .add(scorers.toString())
                .add(String.valueOf(points))
                .add(animals.toString())
                .toString();
    }

    @Override
    public String playersScoredRaft(Set<PlayerColor> scorers, int points, int lakeCount) {
        return new StringJoiner(" ")
                .add(scorers.toString())
                .add(String.valueOf(points))
                .add(String.valueOf(lakeCount))
                .toString();
    }

    @Override
    public String playersWon(Set<PlayerColor> winners, int points) {
        return new StringJoiner(" ")
                .add(winners.toString())
                .add(String.valueOf(points))
                .toString();
    }

    @Override
    public String clickToOccupy() {
        return null;
    }

    @Override
    public String clickToUnoccupy() {
        return null;
    }
}
