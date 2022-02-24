package pers.gxj.security.oauth2.framework.swagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/14 14:07
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private Boolean swagger = true;

    @Bean
    public Docket createRestApi() {
        //添加head参数start
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder member = new ParameterBuilder();
        member.description("请求日志ID").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(member.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(pars)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pers.gxj.security.oauth.api.controller"))
                .paths(swagger? PathSelectors.any():PathSelectors.none())
                .build()
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("", "", "");
        return new ApiInfoBuilder()
                .title("认证验权服务")
                .description("认证验权服务")
                .termsOfServiceUrl("")
                .contact(contact)
                .version("1.0.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList();
    }
}
