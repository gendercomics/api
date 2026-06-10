package net.gendercomics.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenderComicsApi {

    private final BuildProperties _buildProperties;

    public static void main(String[] args) {
        SpringApplication.run(GenderComicsApi.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("gendercomics API")
                        .version(_buildProperties.getVersion())
                        .description("The gendercomics.net API provides access to the gendercomics database." +
                                "The database stores the results (comics, authors, artists, publishers, articles, blog posts, etc.) " +
                                "of the research project 'Visualities of Gender in German-language Comics'")
                        .termsOfService("http://gendercomics.net")
                        .contact(new Contact().name("Michael Litschauer").email("michael.litschauer@gmail.com"))
                        .license(new License().name("Apache License Version 2.0").url("https://gendercomics.net/LICENSE")));
    }

}
