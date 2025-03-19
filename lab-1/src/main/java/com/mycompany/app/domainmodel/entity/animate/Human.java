package com.mycompany.app.domainmodel.entity.animate;

import com.mycompany.app.domainmodel.entity.intangible.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Human extends Entity {

    public Human(String name, int involvement) {
        super(name, involvement);
    }
    
    @Override
    public Message communicate(Message message, String textToSay) {
        switch (message.getExpresstionType()) {
            case QUESTION -> decreaseInvolvement();
            case STATEMENT -> increaseInvolvement();
            case EXCLAMATION -> increaseInvolvement();
        }
        
        Message messageToSay = new Message(textToSay);
        log.info(String.format("--\"%s\" - said %s", textToSay, this.getName()));
        if (this.getInvolvement() > 5) {
            messageToSay.addToText("!");
        }
        return messageToSay;
    }

    @Override
    public void keepSilence() {
        log.info(String.format("%s keep silence a little bit", this.getName()));
    }

    @Override
    public void cough() {
        log.info(String.format("%s coughed", this.getName()));   
    }

    @Override
    public void thinking() {
        log.info(String.format("%s, got thoughtful", this.getName()));
        increaseInvolvement();
    }
}
