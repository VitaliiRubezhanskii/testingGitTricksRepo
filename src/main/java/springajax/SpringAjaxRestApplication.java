package springajax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springajax.localstorage.BookCustomStorage;


@SpringBootApplication
public class SpringAjaxRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAjaxRestApplication.class, args);
    }


    @Bean
    public static BookCustomStorage bookCustomStorage(){
        return new BookCustomStorage();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/liba/books").allowedOrigins("http://localhost:8080");
            }
        };
    }
}
