package io.vertx.first;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;

@RunWith(VertxUnitRunner.class)
public class MyFirstVerticleTest {

    private Vertx vertx;
    private int port;

    private static final String PATH_FIRST_CONFIG = "src/main/resources/config/first-config.yml";

    @Before
    public void setUp(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        // будет выбран случайно-свободный порт
        ServerSocket socket = new ServerSocket(0);
        port =  socket.getLocalPort();
        socket.close();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.port", port));
        vertx.deployVerticle(MyFirstVerticle.class.getName(), options, context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testMyApplication(TestContext context) {
        final Async async = context.async();

        vertx.createHttpClient().getNow(port, "localhost", "/",
                response -> {
                    response.handler(body -> {
                        context.assertTrue(body.toString().contains("Hello"));
                        async.complete();
                    });
                });
    }

    @Test
    public void testMyConfiguration() {
        FirstConfiguration firstconfig = YamlConfigReader.getConfiguration(new FirstConfiguration(), PATH_FIRST_CONFIG);
        SecondConfiguration secondConfig = YamlConfigReader.getConfiguration(new SecondConfiguration(), firstconfig.getPath());

        assertEquals("<h1>Hello from my first Vert.x 3 application</h1>", secondConfig.getText());
    }
}