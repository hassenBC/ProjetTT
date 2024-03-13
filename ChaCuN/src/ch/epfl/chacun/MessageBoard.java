package ch.epfl.chacun;

import java.util.*;

import static ch.epfl.chacun.Animal.*;

public record MessageBoard  (TextMaker textMaker, List<Message> messages){
        public MessageBoard{
            messages = Collections.unmodifiableList(List.copyOf(messages));
        }

    /**
     * La méthode points() va itérer sur la List de message et associer chaque scorer à son nombre de points.
     *
     * @return Une Map qui associe chaque scorer à un nombre de points.
     */
    Map<PlayerColor, Integer> points(){
            Map<PlayerColor,Integer> scores = new HashMap<>();
            for ( Message message : messages()){
                for (PlayerColor scorer : message.scorers()){
                    scores.merge(scorer, message.points(), Integer::sum );
                }
            }
            return scores;
        }

    /**
     *
     * @param forest L'Area qui contient nos forests
     * @return Un MessageBoard identique si la foret n'est pas occupée ou un nouveau message en plus
     * si la foret est occupée. À l'aide de TextMaker, on peut respecter les conventions des messages de scores.
     */
    public MessageBoard withScoredForest(Area<Zone.Forest> forest){
        List<Message> scoredForestMessage = new ArrayList<>(messages());
            Set<PlayerColor> majorityOccupants = forest.majorityOccupants();
                int tilesIdsCount = forest.tileIds().size();
                    int mushroomGroupCount = Area.mushroomGroupCount(forest);
                        int forestPoints = Points.forClosedForest(tilesIdsCount,mushroomGroupCount);

        String messageText = textMaker.playersScoredForest(majorityOccupants, forestPoints, mushroomGroupCount,tilesIdsCount );
            Message forestOccupiedMessage = new Message(messageText, forestPoints, messages.getLast().scorers(), forest.tileIds());
                scoredForestMessage.add(forestOccupiedMessage);

        if(!forest.isOccupied()){
            return new  MessageBoard(textMaker(),messages());
        }

        return new MessageBoard(textMaker(),scoredForestMessage);

    }

    /**
     *
     * @param player
     * @param forest
     * @return
     */
    public MessageBoard withClosedForestWithMenhir(PlayerColor player, Area<Zone.Forest> forest){
        //A verifier le forest.majorty occupants si ils sont deux ou pas poser une question sur ED ou au assistants
        int tilesIdsCount = forest.tileIds().size();
        int mushroomGroupCount = Area.mushroomGroupCount(forest);
            int forestPoints = Points.forClosedForest(tilesIdsCount,mushroomGroupCount);
            List<Message> newTurnMenhirMessages = new ArrayList<>(messages());
                String anotherTurnMessage = textMaker.playerClosedForestWithMenhir(player);
                    Message playAnotherTurnMenhir = new Message(anotherTurnMessage, forestPoints, messages.getLast().scorers(),forest.tileIds());

        newTurnMenhirMessages.add(playAnotherTurnMenhir);

        return new MessageBoard(textMaker(),newTurnMenhirMessages);
    }

    /**
     *
     * @param river
     * @return
     */
    public MessageBoard withScoredRiver(Area<Zone.River> river){
        if(!river.isOccupied()){ return new MessageBoard(textMaker(),messages()); }
            int riverFishCount = Area.riverFishCount(river) ;
            int tileIdCount = river.tileIds().size();
            int riverPoints = Points.forClosedRiver(riverFishCount, tileIdCount);
            String winningRiverPointsMessage = textMaker.playersScoredRiver(river.majorityOccupants(),riverPoints,riverFishCount,tileIdCount);
        List<Message> riverScoreFinal = new ArrayList<>(messages());
        Message closedRiverMessage = new Message(winningRiverPointsMessage,riverPoints, messages.getLast().scorers(),river.tileIds());
            riverScoreFinal.add(closedRiverMessage);

            return new MessageBoard(textMaker(),riverScoreFinal);
    }

    /**
     *
     * @param scorer
     * @param adjacentMeadow
     * @return
     */
    public MessageBoard withScoredHuntingTrap(PlayerColor scorer, Area<Zone.Meadow> adjacentMeadow){
        Map<Animal.Kind,Integer> animalsCounterMap = new HashMap<>();
        List<Message> meadowScoreFinal = new ArrayList<>(messages());
        for(Zone.Meadow zone : adjacentMeadow.zones()){
            // on brut force le bail osef si ya un tiger
            for ( Animal animal : zone.animals()){
                animalsCounterMap.put(animal.kind(), animalsCounterMap.getOrDefault(animal.kind(),0)+1);
            }
        }
        // a cause des nullpointer fait attention ça peut faire beuger la classe points
        int scoreHuntingTrapPoints = Points.forMeadow(animalsCounterMap.getOrDefault(Kind.MAMMOTH,0),
                animalsCounterMap.getOrDefault(Kind.AUROCHS,0),animalsCounterMap.getOrDefault(Kind.DEER,0));
        String playerScoreHuntingTrap = textMaker().playerScoredHuntingTrap(scorer,scoreHuntingTrapPoints, animalsCounterMap);
        Message scoredPointsTrap = new Message(playerScoreHuntingTrap,scoreHuntingTrapPoints,messages.getLast().scorers(),adjacentMeadow.tileIds());
        meadowScoreFinal.add(scoredPointsTrap);
        return new MessageBoard(textMaker,meadowScoreFinal);
    }

    /**
     *
     * @param scorer
     * @param riverSystem
     * @return
     */
    public MessageBoard withScoredLogboat(PlayerColor scorer, Area<Zone.Water> riverSystem){
        List<Message> logBoatScoreFinal = new ArrayList<>(messages());
        int lakeCount = Area.lakeCount(riverSystem);
        int riverSysPoints = Points.forLogboat(lakeCount);
        String scoredLogboat = textMaker.playerScoredLogboat(scorer,riverSysPoints,lakeCount);
        //jsp si on doit mettre les occupants de la zone ou celui du dernier message j'en ai acune ideée
        Message nouvMessageRiverSys = new Message(scoredLogboat,riverSysPoints, messages().getLast().scorers(), riverSystem.tileIds());
        logBoatScoreFinal.add(nouvMessageRiverSys);
        return new MessageBoard(textMaker,logBoatScoreFinal);
    }


    public MessageBoard withScoredMeadow(Area<Zone.Meadow> meadow, Set<Animal> cancelledAnimals){
        Map<Animal.Kind,Integer> animalsCounterMap = new HashMap<>();
        //Remplis ma counterMap
        for(Zone.Meadow zone : meadow.zones()){
            for ( Animal animal : zone.animals()){
                animalsCounterMap.put(animal.kind(), animalsCounterMap.getOrDefault(animal.kind(),0)+1);
                }
            }
        //Retire les cancelled animals
        for (Animal huntedAnimal : cancelledAnimals) animalsCounterMap.remove(huntedAnimal.kind());
        //Calcul des points
        int meadowPointsFinal = Points.forMeadow(animalsCounterMap.getOrDefault(Kind.MAMMOTH,0),
                animalsCounterMap.getOrDefault(Kind.AUROCHS,0),animalsCounterMap.getOrDefault(Kind.DEER,0));
        //Conditions de l'énoncé
        if(meadow.isOccupied() && meadowPointsFinal>0){
            List<Message> meadowScoreFinal = new ArrayList<>(messages());
            //poser question cas ou il ya plusieurs majority occupants
            Set<PlayerColor> majorityOccupant = new HashSet<>(meadow.majorityOccupants());

            String withScoreMeadowText = textMaker().playersScoredMeadow(majorityOccupant,meadowPointsFinal,animalsCounterMap);
            Message scoreHunting = new Message(withScoreMeadowText,meadowPointsFinal,majorityOccupant,meadow.tileIds());
            meadowScoreFinal.add(scoreHunting);
            return new MessageBoard(textMaker(),meadowScoreFinal);

        }
        return new MessageBoard(textMaker(),messages());
    }


    public MessageBoard withScoredRiverSystem(Area<Zone.Water> riverSystem){
        int fishCountRiverSystem = Area.riverSystemFishCount(riverSystem);
        int pointsRiverSystem = Points.forRiverSystem(fishCountRiverSystem);
            if(riverSystem.isOccupied() && pointsRiverSystem>0){
                List<Message> nouveauMessages = new ArrayList<>(messages());
                String gainedPointsRSText = textMaker.playersScoredRiverSystem(riverSystem.majorityOccupants(),pointsRiverSystem,fishCountRiverSystem);
                Message gainedPointsMessage = new Message(gainedPointsRSText,pointsRiverSystem,riverSystem.majorityOccupants(),riverSystem.tileIds());
                nouveauMessages.add(gainedPointsMessage);
                return new MessageBoard(textMaker,nouveauMessages);
            }

        return new MessageBoard(textMaker(),messages());
    }


    public MessageBoard withScoredPitTrap(Area<Zone.Meadow> adjacentMeadow, Set<Animal> cancelledAnimals){
        Map<Animal.Kind,Integer> animalsCounterMap = new HashMap<>();
        //Remplis ma counterMap
        for(Zone.Meadow zone : adjacentMeadow.zones()){
            for ( Animal animal : zone.animals()){
                animalsCounterMap.put(animal.kind(), animalsCounterMap.getOrDefault(animal.kind(),0)+1);
            }
        }
        //Retire les cancelled animals
        for (Animal huntedAnimal : cancelledAnimals) animalsCounterMap.remove(huntedAnimal.kind());
        //Calcul des points
        int meadowPointsFinal = Points.forMeadow(animalsCounterMap.getOrDefault(Kind.MAMMOTH,0),
                animalsCounterMap.getOrDefault(Kind.AUROCHS,0),animalsCounterMap.getOrDefault(Kind.DEER,0));
        if(adjacentMeadow.isOccupied() && meadowPointsFinal>0){
            List<Message> nouveauMessages = new ArrayList<>(messages());
            String gainedPointsRSText = textMaker().playersScoredPitTrap(adjacentMeadow.majorityOccupants(),meadowPointsFinal,animalsCounterMap);
            Message gainedPointsMessageMeadow = new Message(gainedPointsRSText,meadowPointsFinal,adjacentMeadow.majorityOccupants(),adjacentMeadow.tileIds());
            nouveauMessages.add(gainedPointsMessageMeadow);
            return new MessageBoard(textMaker(),nouveauMessages);
        }
        return new MessageBoard(textMaker(),messages());
    }

    public MessageBoard withScoredRaft(Area<Zone.Water> riverSystem){
        if(riverSystem.isOccupied()){
            int fishCountRiverSystem = Area.riverSystemFishCount(riverSystem);
            int riverSystemPoints = Points.forRiverSystem(fishCountRiverSystem);

            List<Message> nouvListMessage = new ArrayList<>(messages());
            String raftPointRiverSystemText = textMaker.playersScoredRaft(riverSystem.majorityOccupants(),riverSystemPoints,Area.lakeCount(riverSystem));
            Message nouvMeassageRS = new Message(raftPointRiverSystemText,riverSystemPoints,riverSystem.majorityOccupants(),riverSystem.tileIds());
            nouvListMessage.add(nouvMeassageRS);

            return new MessageBoard(textMaker(),nouvListMessage);
        }

        return new MessageBoard(textMaker(),messages());

    }

    public MessageBoard withWinners(Set<PlayerColor> winners, int points){
        String winningPlayersText = textMaker().playersWon(winners,points);
        Message winningMessage = new Message(winningPlayersText,points,winners,messages().getLast().tileIds());
        List<Message> winningMessageList = new ArrayList<>(messages());
        winningMessageList.add(winningMessage);
        return new MessageBoard(textMaker(),winningMessageList);
    }








    public record Message(String text, int points, Set<PlayerColor> scorers, Set<Integer> tileIds){

        public Message{
            assert text != null;
            assert points > 0;
            scorers = Set.copyOf(scorers);
            tileIds = Set.copyOf(tileIds);
        }

    }
}
