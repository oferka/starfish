package org.ok.starfish;

import org.ok.starfish.data.content.loader.ContentLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StarfishApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarfishApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadContent(ContentLoader contentLoader) {
        return args -> contentLoader.load();
    }
}
