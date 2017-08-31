package io.vertx.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MyFirstVerticle extends AbstractVerticle {

    private static final String PATH_FIRST_CONFIG = "src/main/resources/config/first-config.yml";

    @Override
    public void start(Future<Void> fut) {

        FirstConfiguration firstconfig = YamlConfigReader.getConfiguration(new FirstConfiguration(), PATH_FIRST_CONFIG);
        SecondConfiguration secondConfig = null;
        if (firstconfig != null) {
            secondConfig = YamlConfigReader.getConfiguration(new SecondConfiguration(), firstconfig.getPath());
        }

        SecondConfiguration finalSecondConfig = secondConfig;
        vertx
            .createHttpServer()
            .requestHandler(r -> {
                r.response().end(finalSecondConfig != null ? finalSecondConfig.getText() : "<h1>NOT FOUND YAML-CONFIG FILE</h1>");
            })
            .listen(
                config().getInteger("http.port", 8080),
                result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }
}
