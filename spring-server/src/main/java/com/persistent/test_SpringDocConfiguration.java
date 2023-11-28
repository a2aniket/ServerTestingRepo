package com.persistent.configuration;

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
        SpringDocConfiguration config = new SpringDocConfiguration();

        OpenAPI result = config.apiInfo();

        assertNotNull(result);
    }

    @Test
    public void testAddSecuritySchemes() {
        Components components = new Components();
        SecurityScheme api_key = new SecurityScheme();
        api_key.setType(SecurityScheme.Type.APIKEY);
        api_key.setIn(SecurityScheme.In.HEADER);
        api_key.setName("api_key");
        components.addSecuritySchemes("api_key", api_key);

        SecurityScheme petstore_auth = new SecurityScheme();
        petstore_auth.setType(SecurityScheme.Type.OAUTH2);
        components.addSecuritySchemes("petstore_auth", petstore_auth);

        assertNotNull(components.getSecuritySchemes());
    }

    @Test
    public void testInfo() {
        Info info = new Info();
        info.setTitle("Swagger Petstore - OpenAPI 3.0");
        info.setDescription(
                "This is a sample Pet Store Server based on the OpenAPI 3.0 specification.  You can find out more about Swagger at [https://swagger.io](https://swagger.io). In the third iteration of the pet store, we've switched to the design first approach! You can now help us improve the API whether it's by making changes to the definition itself or to the code. That way, with time, we can improve the API in general, and expose some of the new features in OAS3.");
        License license = new License();
        license.setName("Apache 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
        info.setLicense(license);
        info.setVersion("1.0.11");

        assertNotNull(info);
    }

    @Test
    public void testSecurityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.OAUTH2);

        assertNotNull(securityScheme);
    }

    @Test
    public void testSecuritySchemeIn() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setIn(SecurityScheme.In.HEADER);

        assertNotNull(securityScheme.getIn());
    }

    @Test
    public void testSecuritySchemeName() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName("api_key");

        assertNotNull(securityScheme.getName());
    }

    @Test
    public void testSecuritySchemeType() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.APIKEY);

        assertNotNull(securityScheme.getType());
    }
}