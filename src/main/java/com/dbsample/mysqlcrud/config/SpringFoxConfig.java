//package com.dbsample.mysqlcrud.config;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.hateoas.client.LinkDiscoverer;
//import org.springframework.hateoas.client.LinkDiscoverers;
//import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
//import org.springframework.plugin.core.SimplePluginRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
////@EnableWebMvc
//public class SpringFoxConfig {                                    
//    @Bean
//    public Docket api() { 
//        return new Docket(DocumentationType.SWAGGER_2)  
//          .select()                                  
//          .apis(RequestHandlerSelectors.basePackage("com.dbsample.mysqlcrud"))              
//          .paths(PathSelectors.any())          
//          .build();                                           
//    }
//    
//	@Bean
//	public LinkDiscoverers discovers() {
//		List<LinkDiscoverer> plugins = new ArrayList<>();
//		plugins.add(new CollectionJsonLinkDiscoverer());
//		return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
//		
//	}
//}