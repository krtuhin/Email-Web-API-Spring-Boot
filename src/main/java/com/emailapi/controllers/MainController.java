package com.emailapi.controllers;

import com.emailapi.models.EmailRequest;
import com.emailapi.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private EmailService emailService;

    //handler for welcome page
    @GetMapping("/welcome")
    public String welcome() {
        return "This is welcome page";
    }

    //handler for send email api
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request) {

        //sending email
        boolean result = this.emailService.sendEmail(
                request.getSubject(),
                request.getMessage(),
                request.getReceiver());

        System.out.println(request);

        //sending http response based on condition
        if (result) {
            return ResponseEntity.ok("Email sent successfully..!!");
        } else {
            return ResponseEntity.internalServerError().body("Something went wrong, Email not sent..!");
        }
    }
}