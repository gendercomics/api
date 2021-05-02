package net.gendercomics.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ContextConfiguration(classes = {GenderComicsApiTests.TestContextConfiguration.class, GenderComicsApi.class})
public class GenderComicsApiTests {

    @Disabled("FIX keycloak properties")
    @Test
    public void contextLoads() {
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public BuildProperties buildProperties() {
            return mock(BuildProperties.class);
        }

        @Bean
        public KeycloakSpringBootProperties keycloakSpringBootProperties() {
            return mock(KeycloakSpringBootProperties.class);
        }
    }

}
