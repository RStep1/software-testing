package com.mycompany.app.domainmodel.memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import com.mycompany.app.domainmodel.memento.Originator.Memento;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Caretaker {
    private final Originator originator;
    private final Stack<Memento> history = new Stack<>();

    public Caretaker(Originator originator) {
        this.originator = originator;
    }

    public void doSomething() {
        Memento memento = originator.createSnapshot();
        history.push(memento);
    }

    public void undo() {
        if (history.empty()) {
            log.error("Error: History is empty");
            throw new EmptyStackException();
        }
        Memento memento = history.pop();
        originator.restore(memento);
    }

    public List<History> rollbackAllHistory() {
        List<History> historyInOrder = new ArrayList<>();
        while (!history.empty()) {
            undo();
            historyInOrder.add(originator.getCurrentState());
        }
        Collections.reverse(historyInOrder);
        return historyInOrder;
    }
}
