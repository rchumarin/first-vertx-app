package io.vertx.first;

public class SecondConfiguration {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SecondConfiguration{" +
                "text='" + text + '\'' +
                '}';
    }
}
