package com.mycompany.app.domainmodel.entity.animate;

import com.mycompany.app.domainmodel.entity.intangible.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Entity implements Expressible, NonverbalExpressible {
    private String name;
    private int involvement;

    public Entity(String name, int involvement) {
        this.name = name;
        this.involvement = involvement;
    }

    @Override
    public Message request(String textToAsk) {
        log.info(String.format("--\"%s\" - asked %s", textToAsk, this.getName()));
        return new Message(textToAsk, ExpressionType.QUESTION);
    }

    public String getName() {
        return name;
    }

    public int getInvolvement() {
        return involvement;
    }

    public void increaseInvolvement() {
        this.involvement++;
    }

    public void decreaseInvolvement() {
        this.involvement--;
    }
}
