package com.mycompany.app.domainmodel.entity.animate;

import com.mycompany.app.domainmodel.entity.intangible.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Alien extends Entity {

    public Alien(String name, int involvement) {
        super(name, involvement);
    }

    @Override
    public Message communicate(Message message, String textToSay) {
        switch (message.getExpresstionType()) {
            case QUESTION -> decreaseInvolvement();
            case STATEMENT -> increaseInvolvement();
            case EXCLAMATION -> increaseInvolvement();
        }
        if (this.getInvolvement() <= 0) {
            this.cough();
        }
        if (this.getInvolvement() >= 1 && this.getInvolvement() <= 2) {
            this.keepSilence();
        }
        if (this.getInvolvement() > 2) {
            this.thinking();
        }
        
        Message messageToSay = new Message(textToSay);
        if (this.getInvolvement() > 3) {
            messageToSay.setExpressionType(ExpressionType.EXCLAMATION);
            messageToSay.addToText("!");
        }
        log.info(String.format("--\"%s\" - said %s", messageToSay.getMessage(), this.getName()));
        
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
    }

    
}
