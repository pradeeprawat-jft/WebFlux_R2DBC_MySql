package com.psr.webfluxreactiveprogramingwithmysql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Long id;
    private String name;
    private String email;
    private String school;
    private Integer age;
}
