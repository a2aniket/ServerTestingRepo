package com.persistent.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.swagger.v3.oas.models.OpenAPI;

@SpringBootTest
class SpringDocConfigurationTests {

    @Test
    void testApiInfo() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringDocConfiguration.class);
        OpenAPI openAPI = context.getBean("com.persistent.configuration.SpringDocConfiguration.apiInfo", OpenAPI.class);

        Assertions.assertEquals("Swagger Petstore - OpenAPI 3.0", openAPI.getInfo().getTitle());
        Assertions.assertEquals("This is a sample Pet Store Server based on the OpenAPI 3.0 specification.  You can find out more about Swagger at [https://swagger.io](https://swagger.io). In the third iteration of the pet store, we've switched to the design first approach! You can now help us improve the API whether it's by making changes to the definition itself or to the code. That way, with time, we can improve the API in general, and expose some of the new features in OAS3.", openAPI.getInfo().getDescription());
        Assertions.assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        Assertions.assertEquals("http://www.apache.org/licenses/LICENSE-2.0.html", openAPI.getInfo().getLicense().getUrl());
        Assertions.assertEquals("1.0.11", openAPI.getInfo().getVersion());
    }

    @Test
    void testComponents() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringDocConfiguration.class);
        OpenAPI openAPI = context.getBean("com.persistent.configuration.SpringDocConfiguration.apiInfo", OpenAPI.class);

        Assertions.assertEquals(2, openAPI.getComponents().getSecuritySchemes().size());
        Assertions.assertEquals(SecurityScheme.Type.APIKEY, openAPI.getComponents().getSecuritySchemes().get("api_key").getType());
        Assertions.assertEquals(SecurityScheme.In.HEADER, openAPI.getComponents().getSecuritySchemes().get("api_key").getIn());
        Assertions.assertEquals("api_key", openAPI.getComponents().getSecuritySchemes().get("api_key").getName());
        Assertions.assertEquals(SecurityScheme.Type.OAUTH2, openAPI.getComponents().getSecuritySchemes().get("petstore_auth").getType());
    }
}