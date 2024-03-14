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
