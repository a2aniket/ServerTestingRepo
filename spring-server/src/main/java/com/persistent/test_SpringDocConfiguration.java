package com.persistent.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.swagger.v3.oas.models.OpenAPI;

public class SpringDocConfigurationTest {

    @Test
    public void testApiInfo() {
        SpringDocConfiguration springDocConfiguration = new SpringDocConfiguration();
        OpenAPI openAPI = springDocConfiguration.apiInfo();
        assertNotNull(openAPI.getInfo().getTitle());
        assertNotNull(openAPI.getInfo().getDescription());
        assertNotNull(openAPI.getInfo().getVersion());
        assertNotNull(openAPI.getInfo().getLicense());
        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSecuritySchemes().get("api_key"));
        assertNotNull(openAPI.getComponents().getSecuritySchemes().get("petstore_auth"));
    }

    @Test
    public void testSpringDocConfiguration() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringDocConfiguration.class);
        assertNotNull(context.getBean("com.persistent.configuration.SpringDocConfiguration.apiInfo"));
        assertTrue(context.getBeanDefinitionCount() > 0);
    }

}