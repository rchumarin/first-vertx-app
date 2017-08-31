package io.vertx.first;

public class FirstConfiguration {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FirstConfiguration{" +
                "path='" + path + '\'' +
                '}';
    }
}
