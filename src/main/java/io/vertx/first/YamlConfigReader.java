package io.vertx.first;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class YamlConfigReader {

    public static<T> T getConfiguration(T config, String path) {
        Yaml yaml = new Yaml();
        try {
            try( InputStream in = Files.newInputStream(Paths.get(path))) {
                config = yaml.loadAs(in, (Class<T>) config.getClass());
            }
        } catch (IOException e) {
            return null;
        }
        return config;
    }
}
