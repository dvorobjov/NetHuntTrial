package com.nethunt.trial.data;

public class TextItemStatistics {
    private String textItem;
    private double frequency;

    public TextItemStatistics() {
    }

    public TextItemStatistics(String textItem, double frequency) {
        this.textItem = textItem;
        this.frequency = frequency;
    }

    public String getTextItem() {
        return textItem;
    }

    public void setTextItem(String textItem) {
        this.textItem = textItem;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public int getTextItemLength() {
        if (textItem != null) {
            return textItem.length();
        } else {
            return 0;
        }
    }
}
