package com.mycompany.app.domainmodel.entity.animate;

import java.util.List;

import com.mycompany.app.domainmodel.entity.intangible.Message;
import com.mycompany.app.domainmodel.memento.Caretaker;
import com.mycompany.app.domainmodel.memento.History;

public interface Expressible {
    Message communicate(Message message, String textToSay);
    Message request(String textToSay);
    
    default Message tellStory(Caretaker caretaker) {
        List<History> history = caretaker.rollbackAllHistory();
        StringBuilder story = new StringBuilder();
        history.forEach(partOfHistory -> story.append(partOfHistory.circumstances()));
        return new Message(story.toString());
    }
}
