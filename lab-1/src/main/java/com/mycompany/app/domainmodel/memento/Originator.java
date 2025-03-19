package com.mycompany.app.domainmodel.memento;

public class Originator {
    private History history;

    class Memento {
        private History history;
        
        private Memento(History history) {
            Memento.this.history = history;
        }

        private History getState() {
            return Memento.this.history;
        }
    }

    public Memento createSnapshot() {
        return new Memento(Originator.this.history);
    }

    public void restore(Memento memento) {
        Originator.this.history = memento.getState();
    }

    public History getCurrentState() {
        return history;
    }

    public void setCurrentState(History history) {
        this.history = history;
    }
}
