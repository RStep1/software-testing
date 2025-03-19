package com.mycompany.app.domainmodel.memento;

import java.util.List;

import com.mycompany.app.domainmodel.entity.animate.Human;
import com.mycompany.app.domainmodel.entity.intangible.Message;
import com.mycompany.app.domainmodel.mice.BrockianUltraCricket;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HistoryMaker {
    private final Originator originator;
    private final Caretaker caretaker;

    public HistoryMaker(Originator originator, Caretaker caretaker) {
        this.originator = originator;
        this.caretaker = caretaker;
    }

    public void makeHistoryAboutMice(Mouse mouse, Mouse creatures, List<Message> questions) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Many many millions of years ago ");
        stringBuilder.append(mouse.getDescription());
        stringBuilder.append("got so fed up ");
        History partOfHistory = new History(stringBuilder.toString());
        originator.setCurrentState(partOfHistory);
        caretaker.doSomething();

        stringBuilder = new StringBuilder();
        stringBuilder.append("with the constant bickering about the meaning of life which used to ");
        stringBuilder.append("interrupt their favorite pastime of ");
        stringBuilder.append(BrockianUltraCricket.class.getName() + " ");
        stringBuilder.append(BrockianUltraCricket.DESCRIPTION);
        stringBuilder.append(" which involved suddenly");

        partOfHistory = new History(stringBuilder.toString());
        originator.setCurrentState(partOfHistory);
        caretaker.doSomething();

        originator.setCurrentState(mouse.hit(new Human("someone", 0)));
        caretaker.doSomething();

        originator.setCurrentState(mouse.runAway());
        caretaker.doSomething();

        stringBuilder = new StringBuilder();
        stringBuilder.append("that they decided to ");
        partOfHistory = new History(stringBuilder.toString());
        originator.setCurrentState(partOfHistory);
        caretaker.doSomething();

        originator.setCurrentState(mouse.figureOut(questions));
        caretaker.doSomething();
    }
}
