package br.com.dimed.test;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DimedApplication {

	public static void main(String[] args) {
		SpringApplication.run(DimedApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
		          .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
		          .build()
		          .apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Dimed informações de tráfego REST API", 
	      "API responsável por integrar os dados contidos em http://www.poatransporte.com.br/ \n Possibilitando consulta com filtros, cadastro de clientes e vincular clientes com linhas de transporte.", 
	      "", 
	      "", 
	      new Contact("Victor Carvalho","", "neohunter_@hotmail.com"), 
	      "", "", Collections.emptyList());
	}
}
