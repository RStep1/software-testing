package com.mycompany.app.domainmodel.entity.intangible;

import com.mycompany.app.domainmodel.entity.animate.ExpressionType;

public class Message {
    private String text;
    private ExpressionType expressionType;

    public Message(String text) {
        this.text = text;
        this.expressionType = ExpressionType.STATEMENT;
    }

    public Message(String text, ExpressionType expressionType) {
        this.text = text;
        this.expressionType = expressionType;
    }

    public String getMessage() {
        return this.text;
    }

    public ExpressionType getExpresstionType() {
        return expressionType;
    }

    public void addToText(String text) {
        this.text = this.text + text;
    }

    public void setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
    }
}
