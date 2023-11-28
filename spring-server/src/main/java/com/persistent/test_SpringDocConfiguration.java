package com.persistent.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfigurationTest {

    @Test
    public void testApiInfo() {
        SpringDocConfiguration configuration = new SpringDocConfiguration();
        OpenAPI openAPI = configuration.apiInfo();

        assertNotNull(openAPI.getInfo());
        assertNotNull(openAPI.getComponents());

        Info info = openAPI.getInfo();
        assertEquals("Swagger Petstore - OpenAPI 3.0", info.getTitle());
        assertEquals("This is a sample Pet Store Server based on the OpenAPI 3.0 specification.  You can find out more about Swagger at [https://swagger.io](https://swagger.io). In the third iteration of the pet store, we've switched to the design first approach! You can now help us improve the API whether it's by making changes to the definition itself or to the code. That way, with time, we can improve the API in general, and expose some of the new features in OAS3.", info.getDescription());
        assertEquals("1.0.11", info.getVersion());

        License license = info.getLicense();
        assertNotNull(license);
        assertEquals("Apache 2.0", license.getName());
        assertEquals("http://www.apache.org/licenses/LICENSE-2.0.html", license.getUrl());

        Components components = openAPI.getComponents();
        assertNotNull(components);

        SecurityScheme apiKeyScheme = components.getSecuritySchemes().get("api_key");
        assertNotNull(apiKeyScheme);
        assertEquals(SecurityScheme.Type.APIKEY, apiKeyScheme.getType());
        assertEquals(SecurityScheme.In.HEADER, apiKeyScheme.getIn());
        assertEquals("api_key", apiKeyScheme.getName());

        SecurityScheme petstoreAuthScheme = components.getSecuritySchemes().get("petstore_auth");
        assertNotNull(petstoreAuthScheme);
        assertEquals(SecurityScheme.Type.OAUTH2, petstoreAuthScheme.getType());
    }

    @Test
    public void testApiInfoBean() {
        SpringDocConfiguration configuration = new SpringDocConfiguration();
        Bean apiInfoBean = configuration.apiInfo();

        assertNotNull(apiInfoBean);
        assertEquals("com.persistent.configuration.SpringDocConfiguration.apiInfo", apiInfoBean.getName());
    }

}