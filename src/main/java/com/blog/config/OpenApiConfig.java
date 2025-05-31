package com.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info =@Info(
                		   contact=@Contact(name="achuth",
                		                    email="achuht568@gmail.com"),
                		   description = "OpenAPI documentation for springboot backend Blog Application",
                		   title = "BLOG APPLICATION - BACKEND",
                		   version = "1.0",
                		   license = @License(
                		             name="license name",
                		             url = "http:some-url.com"),
                		   termsOfService = "Terms of Service"
		                     ),
                   servers= {
                		   @Server(
                				   description = "Local ENV",
                				   url="http:localhost:8080")
                		    }
                  )
public class OpenApiConfig {

}
