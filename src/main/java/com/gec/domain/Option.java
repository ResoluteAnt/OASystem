package com.gec.domain;

public class Option {

    private String text;
    private String val;
    public Option(){}
    public Option(String line){
        String[] data = line.split(",");
        this.text = data[0];
        this.val = data[1];
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getVal() {
        return val;
    }
    public void setVal(String val) {
        this.val = val;
    }
    @Override
    public String toString() {
        return "Option [text=" + text + ", val=" + val + "]";
    }
}
