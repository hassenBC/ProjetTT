package ch.epfl.chacun;

import java.util.*;

public record MessageBoard(TextMaker textMaker, List<Message> messages) {
        public MessageBoard{
            messages = Collections.unmodifiableList(List.copyOf(messages));
        }


    public record Message(String text, int points, Set<PlayerColor> scorers, Set<Integer> tileIds){

        public Message{
            assert text != null;
            assert points > 0;
            scorers = new HashSet<>(scorers);
            tileIds = new HashSet<>(tileIds);
        }

    }
}
