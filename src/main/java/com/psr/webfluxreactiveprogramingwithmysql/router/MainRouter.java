package com.psr.webfluxreactiveprogramingwithmysql.router;

import com.psr.webfluxreactiveprogramingwithmysql.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MainRouter {

    private final StudentService service;

    public MainRouter(StudentService service) {
        this.service = service;
    }

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
                .GET("/api/students", service::getStudent)
                .GET("/api/students/{id}",service::getStudentById)
                .POST("/api/students",service::saveStudent)
                .PUT("/api/students/{id}",service::updateStudent)
                .DELETE("/api/students/{id}",service::deleteStudent)
                .build();
    }

}


