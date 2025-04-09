package com.wims.iot.config;

import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置
 * <p>
 *
 * @author haoxr
 * @see <a href="https://doc.xiaominfo.com/docs/quick-start">knife4j 快速开始</a>
 * @since 2023/2/17
 */
@Configuration
public class SwaggerConfig {

    /**
     * 接口信息
     */
    // @Bean
    // public OpenAPI openApi() {
    //     return new OpenAPI()
    //             .info(new Info()
    //                     .title("系统接口文档")
    //                     .version("2.4.0")
    //             )
    //             // 全局安全校验项，也可以在对应的controller上加注解SecurityRequirement
    //             .components(new Components()
    //                     .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
    //                             new SecurityScheme()
    //                                     .name(HttpHeaders.AUTHORIZATION)
    //                                     .type(SecurityScheme.Type.APIKEY)
    //                                     .in(SecurityScheme.In.HEADER)
    //                                     .scheme("Bearer")
    //                                     .bearerFormat("JWT")
    //                     )
    //             )
    //             .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION)) ;
    // }

//    @Bean
//    public OpenAPI springShopOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("SpringShop API")
//                        .description("Spring shop sample application")
//                        .version("v0.0.1")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
//                .externalDocs(new ExternalDocumentation()
//                        .description("SpringShop Wiki Documentation")
//                        .url("https://springshop.wiki.github.org/docs"));
//    }


}
