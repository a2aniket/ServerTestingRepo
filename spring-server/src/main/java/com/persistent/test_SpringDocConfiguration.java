@Test
public void testApiInfo() {
    OpenAPI apiInfo = new SpringDocConfiguration().apiInfo();
    assertNotNull(apiInfo);
    assertEquals("Swagger Petstore - OpenAPI 3.0", apiInfo.getInfo().getTitle());
    assertEquals("This is a sample Pet Store Server based on the OpenAPI 3.0 specification.  You can find out more about Swagger at [https://swagger.io](https://swagger.io). In the third iteration of the pet store, we've switched to the design first approach! You can now help us improve the API whether it's by making changes to the definition itself or to the code. That way, with time, we can improve the API in general, and expose some of the new features in OAS3.", apiInfo.getInfo().getDescription());
    assertEquals("Apache 2.0", apiInfo.getInfo().getLicense().getName());
    assertEquals("http://www.apache.org/licenses/LICENSE-2.0.html", apiInfo.getInfo().getLicense().getUrl());
    assertEquals("1.0.11", apiInfo.getInfo().getVersion());
    assertEquals(SecurityScheme.Type.APIKEY, apiInfo.getComponents().getSecuritySchemes().get("api_key").getType());
    assertEquals(SecurityScheme.In.HEADER, apiInfo.getComponents().getSecuritySchemes().get("api_key").getIn());
    assertEquals("api_key", apiInfo.getComponents().getSecuritySchemes().get("api_key").getName());
    assertEquals(SecurityScheme.Type.OAUTH2, apiInfo.getComponents().getSecuritySchemes().get("petstore_auth").getType());
}