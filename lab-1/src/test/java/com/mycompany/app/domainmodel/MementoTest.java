package com.mycompany.app.domainmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.app.domainmodel.memento.Caretaker;
import com.mycompany.app.domainmodel.memento.History;
import com.mycompany.app.domainmodel.memento.Originator;

class MementoTest {

    private Originator originator;
    private Caretaker caretaker;

    @BeforeEach
    void setUp() {
        originator = new Originator();
        caretaker = new Caretaker(originator);
    }

    @Test
    void givenTwoHistories_whenUndo_thenGetInitialHistory() {
        History initialHistory = new History("Initial State");
        originator.setCurrentState(initialHistory);
        caretaker.doSomething();

        History newHistory = new History("New State");
        originator.setCurrentState(newHistory);

        caretaker.undo();

        assertEquals(initialHistory, originator.getCurrentState(), "State should be restored to initial history");
    }

    @Test
    void givenEmptyStack_whenUndo_thenThrowsEmptyStackException() {
        assertThrows(EmptyStackException.class, () -> caretaker.undo(),
            "Undo should throw EmptyStackException when history is empty");
    }

    @Test
    void givenThreeHistories_whenRollbackAllHistory_thenOrderEquals() {
        History history1 = new History("State 1");
        History history2 = new History("State 2");
        History history3 = new History("State 3");

        originator.setCurrentState(history1);
        caretaker.doSomething();
        originator.setCurrentState(history2);
        caretaker.doSomething();
        originator.setCurrentState(history3);
        caretaker.doSomething();

        List<History> histories = caretaker.rollbackAllHistory();
        assertEquals(3, histories.size(), "Should return all 3 histories");
        assertEquals(history1, histories.get(0), "First history should be State 1");
        assertEquals(history2, histories.get(1), "Second history should be State 2");
        assertEquals(history3, histories.get(2), "Third history should be State 3");
    }

    @Test
    void givenHistorySequence_whenUndoAll_thenGetFirstState() {
        History history1 = new History("State1");
        History history2 = new History("State2");

        originator.setCurrentState(history1);
        caretaker.doSomething();
        originator.setCurrentState(history2);
        caretaker.doSomething();

        caretaker.undo();
        caretaker.undo();
        assertEquals(history1, originator.getCurrentState(), "After undo, state should be State 1");
    }
}