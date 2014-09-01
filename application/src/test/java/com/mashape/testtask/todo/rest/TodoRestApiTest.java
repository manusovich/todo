package com.mashape.testtask.todo.rest;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Test for Rest services
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
public class TodoRestApiTest {
    private static final String URL = "https://mashapetodo2.herokuapp.com";
    private Client client;

    @Before
    public void before() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonFeature.class);
        client = ClientBuilder.newClient(clientConfig);
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void test404() throws IOException {
        {
            WebTarget webTarget = client.target(URL + "/api");
            Builder request = webTarget.request();
            request.header("Content-type", MediaType.APPLICATION_JSON);
            Response response = request.get();
            Assert.assertTrue(response.getStatus() == 404);
        }
    }

    @Test
    public void testGet() throws IOException {
        {
            WebTarget webTarget = client.target(URL + "/api/todo");
            Builder request = webTarget.request();
            request.header("Content-type", MediaType.APPLICATION_JSON);
            Response response = request.get();
            Assert.assertTrue(response.getStatus() == 200);
        }

        {
            WebTarget webTarget = client.target(URL + "/api/session");
            Builder request = webTarget.request();
            request.header("Content-type", MediaType.APPLICATION_JSON);
            Response response = request.get();
            Assert.assertTrue(response.getStatus() == 200);
        }
    }
}
