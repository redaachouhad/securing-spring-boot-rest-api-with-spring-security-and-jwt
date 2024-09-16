package com.example.restapispringsecurity.controller;


import com.example.restapispringsecurity.dto.JwtTokenDto;
import com.example.restapispringsecurity.dto.LoginUserDto;
import com.example.restapispringsecurity.dto.RegisterUserDto;
import com.example.restapispringsecurity.dto.RoleName;
import com.example.restapispringsecurity.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/register/student")
    public ResponseEntity<String> registerStudent(@RequestBody RegisterUserDto student){
        return userService.register(student, RoleName.STUDENT);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<String> registerTeacher(@RequestBody RegisterUserDto teacher){
        return userService.register(teacher, RoleName.TEACHER);
    }

    @PostMapping("/register/director")
    public ResponseEntity<String> registerDirector(@RequestBody RegisterUserDto director){
        return userService.register(director, RoleName.DIRECTOR);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }

    @GetMapping("/studentPage")
    public ResponseEntity<String> studentPage(){
        return new ResponseEntity<>("I am a student", HttpStatus.OK);
    }

    @GetMapping("/teacherPage")
    public ResponseEntity<String> teacherPage(){
        return new ResponseEntity<>("I am a teacher", HttpStatus.OK);
    }

    @GetMapping("/directorPage")
    public ResponseEntity<String> directorPage(){
        return new ResponseEntity<>("I am a director", HttpStatus.OK);
    }


}
