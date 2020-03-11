package main;

import java.awt.event.ActionListener;

public class ButtonInfo {
    private final String text;
    private final ActionListener actionListener;

    public ButtonInfo(String text, ActionListener actionListener) {
        super();
        this.text = text;
        this.actionListener = actionListener;
    }

    public boolean equals(Object other) {
        if (other instanceof ButtonInfo) {
            ButtonInfo otherPair = (ButtonInfo) other;
            return
                    ((  this.text == otherPair.text ||
                            ( this.text != null && otherPair.text != null &&
                                    this.text.equals(otherPair.text))) &&
                            (  this.actionListener == otherPair.actionListener ||
                                    ( this.actionListener != null && otherPair.actionListener != null &&
                                            this.actionListener.equals(otherPair.actionListener))) );
        }

        return false;
    }

    public String toString()
    {
        return "(" + text + ", " + actionListener + ")";
    }

    public String getText() {
        return text;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }
}