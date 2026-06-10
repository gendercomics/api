package net.gendercomics.api;

import net.gendercomics.api.integrationtest.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:9999/test/jwks.json")
public class GenderComicsApiTests extends AbstractIntegrationTest {

    @Test
    public void contextLoads() {
    }

}
