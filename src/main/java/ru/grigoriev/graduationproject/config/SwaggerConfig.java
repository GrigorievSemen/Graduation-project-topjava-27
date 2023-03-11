package ru.grigoriev.graduationproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import ru.grigoriev.graduationproject.web.constant.SwaggerInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {
    @Value("${1.0}")
    private String swaggerApiVersion;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title(SwaggerInfo.TITLE)
                .description(SwaggerInfo.DESCRIPTION)
                .version(swaggerApiVersion)
        );
    }
}

