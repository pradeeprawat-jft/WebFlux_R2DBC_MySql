package com.psr.webfluxreactiveprogramingwithmysql.service;

import com.psr.webfluxreactiveprogramingwithmysql.entity.Student;
import com.psr.webfluxreactiveprogramingwithmysql.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getStudent(ServerRequest request) {
        Flux<Student> studentFlux = repository.findAll();
        return ServerResponse.ok().body(studentFlux, Student.class).log();
    }

    public Mono<ServerResponse> getStudentById(ServerRequest request) {
        Long studentId = Long.valueOf(request.pathVariable("id"));
        Mono<Student> studentMono = repository.findById(studentId);
        return ServerResponse.ok().body(studentMono, Student.class).log();
    }
    public Mono<ServerResponse> saveStudent(ServerRequest request) {
        Mono<Student> studentMono = request.bodyToMono(Student.class);

        return studentMono
                .flatMap(repository::save)
                .flatMap(savedStudent -> ServerResponse.ok().body(Mono.just(savedStudent), Student.class)).log()
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Failed to save the student: ")).log();
    }

    public Mono<ServerResponse> updateStudent(ServerRequest request) {
        System.out.println("update");
        Long studentId = Long.valueOf(request.pathVariable("id"));
        Mono<Student> studentMono = repository.findById(studentId);
        Mono<Student> updatedStudentMono = request.bodyToMono(Student.class);
        return updatedStudentMono.zipWith(studentMono, (updatedStudent, existingStudent) -> {
                    existingStudent.setName(updatedStudent.getName());
                    existingStudent.setEmail(updatedStudent.getEmail());
                    existingStudent.setSchool(updatedStudent.getSchool());
                    existingStudent.setAge(updatedStudent.getAge());
                    return existingStudent;
                }).log()
                .flatMap(repository::save).log()
                .flatMap(savedStudent -> ServerResponse.ok().body(Mono.just(savedStudent), Student.class)).log()
                .switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).body(Mono.just("id not found "+studentId), String.class)).log()
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Failed to update the student: " + e.getMessage())).log();
    }

    public Mono<ServerResponse> deleteStudent(ServerRequest request) {
        System.out.println("delete");
        Long studentId = Long.valueOf(request.pathVariable("id"));
        return repository.findById(studentId)
                .flatMap(student -> repository.deleteById(studentId)
                        .then(ServerResponse.noContent().build())
                ).log()
                .switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).body(Mono.just("id not found "+studentId), String.class)).log()
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Failed to delete the student: " + e.getMessage())).log();

    }
}
