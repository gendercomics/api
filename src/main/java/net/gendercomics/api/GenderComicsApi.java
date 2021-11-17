package net.gendercomics.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories("net.gendercomics.api.data.repository")
@EnableElasticsearchRepositories("net.gendercomics.api.elastic.repository")
public class GenderComicsApi {

    @Autowired
    private BuildProperties _buildProperties;

    public static void main(String[] args) {
        SpringApplication.run(GenderComicsApi.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(_buildProperties.getGroup())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.gendercomics.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("gendercomics API")
                .description("The gendercomics.net API provides access to the gendercomics database." +
                        "The database stores the results (comics, authors, artists, publishers, articles, bolg post, etc.) " +
                        "of the research project 'Visualities of Gender in German-language Comics'")
                .termsOfServiceUrl("http://gendercomics.net")
                .contact(new Contact("Michael Litschauer", "", "michael.litschauer@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://gendercomics.net/LICENSE")
                .version(_buildProperties.getVersion())
                .build();
    }

}
