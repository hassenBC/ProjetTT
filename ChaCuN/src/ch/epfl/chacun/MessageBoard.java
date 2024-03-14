package ch.epfl.chacun;

import java.util.*;

import static ch.epfl.chacun.Animal.*;

public record MessageBoard  (TextMaker textMaker, List<Message> messages){
        public MessageBoard{
            messages = List.copyOf(messages);
        }

    /**
     * La méthode point() va itérer sur la List de message et associer chaque scorer à son nombre de points.
     *
     * @return Une Map qui associe chaque scorer à un nombre de points.
     */
    public  Map<PlayerColor, Integer> points(){
        // pas validé à 100%
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
    public MessageBoard withScoredForest(Area<Zone.Forest> forest){ //validé
        if(!forest.isOccupied()){
            return new  MessageBoard(textMaker(),messages());
        }

        List<Message> scoredForestMessage = new ArrayList<>(messages());
            Set<PlayerColor> majorityOccupants = forest.majorityOccupants();
                int tilesIdsCount = forest.tileIds().size();
                    int mushroomGroupCount = Area.mushroomGroupCount(forest);
                        int forestPoints = Points.forClosedForest(tilesIdsCount,mushroomGroupCount);

        String messageText = textMaker.playersScoredForest(majorityOccupants, forestPoints, mushroomGroupCount,tilesIdsCount );
            Message forestOccupiedMessage = new Message(messageText, forestPoints, majorityOccupants, forest.tileIds());
                scoredForestMessage.add(forestOccupiedMessage);


        return new MessageBoard(textMaker(),scoredForestMessage);
    }

    /**
     *
     * @param player
     * @param forest
     * @return
     */
    public MessageBoard withClosedForestWithMenhir(PlayerColor player, Area<Zone.Forest> forest){//validé 100%
        //À verifier le forest.majorty occupants si ils sont deux ou pas poser une question sur ED ou au assistants
            List<Message> newTurnMenhirMessages = new ArrayList<>(messages());
                String anotherTurnMessage = textMaker.playerClosedForestWithMenhir(player);
                    Message playAnotherTurnMenhir = new Message(anotherTurnMessage, 0, new HashSet<>(),forest.tileIds());

        newTurnMenhirMessages.add(playAnotherTurnMenhir);

        return new MessageBoard(textMaker(),newTurnMenhirMessages);
    }

    /**
     *
     * @param river
     * @return
     */
    public MessageBoard withScoredRiver(Area<Zone.River> river){//validé
        if(!river.isOccupied()){ return new MessageBoard(textMaker(),messages()); }

            int riverFishCount = Area.riverFishCount(river) ;
            int tileIdCount = river.tileIds().size();
            int riverPoints = Points.forClosedRiver(riverFishCount, tileIdCount);
            String winningRiverPointsMessage = textMaker.playersScoredRiver(river.majorityOccupants(),riverPoints,riverFishCount,tileIdCount);
        List<Message> riverScoreFinal = new ArrayList<>(messages());
        Message closedRiverMessage = new Message(winningRiverPointsMessage,riverPoints, river.majorityOccupants(),river.tileIds());
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
        //l'occupant quoi doit return jsq quoi faire avec
        Map<Animal.Kind,Integer> animalsCounterMap = new HashMap<>();
        List<Message> meadowScoreFinal = new ArrayList<>(messages());
        for(Zone.Meadow zone : adjacentMeadow.zones()){
            for ( Animal animal : zone.animals()){
                animalsCounterMap.put(animal.kind(), animalsCounterMap.getOrDefault(animal.kind(),0)+1);
            }
        }
        int scoreHuntingTrapPoints = Points.forMeadow(animalsCounterMap.getOrDefault(Kind.MAMMOTH,0),
                animalsCounterMap.getOrDefault(Kind.AUROCHS,0),animalsCounterMap.getOrDefault(Kind.DEER,0));
        if(scoreHuntingTrapPoints == 0) return new MessageBoard(textMaker(), messages());

        String playerScoreHuntingTrap = textMaker().playerScoredHuntingTrap(scorer,scoreHuntingTrapPoints, animalsCounterMap);
        Message scoredPointsTrap = new Message(playerScoreHuntingTrap,scoreHuntingTrapPoints,new HashSet<>(adjacentMeadow.occupants()),adjacentMeadow.tileIds());
        meadowScoreFinal.add(scoredPointsTrap);
        return new MessageBoard(textMaker,meadowScoreFinal);
    }

    /**
     *
     * @param scorer
     * @param riverSystem
     * @return
     */
    public MessageBoard withScoredLogboat(PlayerColor scorer, Area<Zone.Water> riverSystem){// a revoir
        List<Message> logBoatScoreFinal = new ArrayList<>(messages());
        int lakeCount = Area.lakeCount(riverSystem);
        int riverSysPoints = Points.forLogboat(lakeCount);
        String scoredLogboat = textMaker.playerScoredLogboat(scorer,riverSysPoints,lakeCount);
        //jsp si on doit mettre les occupants de la zone ou celui du dernier message j'en ai acune ideée
        Message nouvMessageRiverSys = new Message(scoredLogboat,riverSysPoints, Set.of(scorer), riverSystem.tileIds());
        logBoatScoreFinal.add(nouvMessageRiverSys);
        return new MessageBoard(textMaker,logBoatScoreFinal);
    }


    public MessageBoard withScoredMeadow(Area<Zone.Meadow> meadow, Set<Animal> cancelledAnimals){ //validé
        Map<Animal.Kind,Integer> animalsCounterMap = new HashMap<>();
        Set<Animal> animalsInArea = new HashSet<>();
        //Remplis ma counterMap
        for(Zone.Meadow zone : meadow.zones()){
            for ( Animal animal : zone.animals()){
                animalsCounterMap.put(animal.kind(), animalsCounterMap.getOrDefault(animal.kind(),0)+1);
                animalsInArea.add(animal);
                }
            }
        //Retire les cancelled animals
        for (Animal huntedAnimal : cancelledAnimals){
            if(animalsCounterMap.containsKey(huntedAnimal.kind())){
                int count = animalsCounterMap.get(huntedAnimal.kind());
                count = Math.max(0,count - 1);
            if(count >= 0) {
                animalsCounterMap.put(huntedAnimal.kind(),count);
            }
            //else animalsCounterMap.remove(huntedAnimal.kind());
            }
        }

        int meadowPointsFinal = Points.forMeadow(animalsCounterMap.getOrDefault(Kind.MAMMOTH,0),
                animalsCounterMap.getOrDefault(Kind.AUROCHS,0),animalsCounterMap.getOrDefault(Kind.DEER,0));
        //Conditions de l'énoncé
        if(meadow.isOccupied() && meadowPointsFinal>0){
            List<Message> meadowScoreFinal = new ArrayList<>(messages());
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
            int riverSystemLakeCount = Area.lakeCount(riverSystem);
            int riverSystemPoints = Points.forRaft(riverSystemLakeCount);

            List<Message> nouvListMessage = new ArrayList<>(messages());
            String raftPointRiverSystemText = textMaker.playersScoredRaft(riverSystem.majorityOccupants(),riverSystemPoints,riverSystemLakeCount);
            Message nouvMeassageRS = new Message(raftPointRiverSystemText,riverSystemPoints,riverSystem.majorityOccupants(),riverSystem.tileIds());
            nouvListMessage.add(nouvMeassageRS);

            return new MessageBoard(textMaker(),nouvListMessage);
        }

        return new MessageBoard(textMaker(),messages());

    }

    public MessageBoard withWinners(Set<PlayerColor> winners, int points){
        String winningPlayersText = textMaker().playersWon(winners,points);
        Message winningMessage = new Message(winningPlayersText,0,Set.of(),Set.of());
        List<Message> winningMessageList = new ArrayList<>(messages());
        winningMessageList.add(winningMessage);
        return new MessageBoard(textMaker(),winningMessageList);
    }








    public record Message(String text, int points, Set<PlayerColor> scorers, Set<Integer> tileIds){

        public Message{
            assert text != null;
            assert points >= 0;
            scorers = Set.copyOf(scorers);
            tileIds = Set.copyOf(tileIds);
        }

    }
}
