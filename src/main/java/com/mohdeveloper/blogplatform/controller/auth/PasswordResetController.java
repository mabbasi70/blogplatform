package com.mohdeveloper.blogplatform.controller.auth;

import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.UserService;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PasswordResetController {

    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/password-reset/{email}")
    public ResponseEntity<?> isUserExistWithEmail(@PathVariable(value = "email",required = true) String email){
        Optional<User> optionalUser = userService.findByEmail(email);
        if(optionalUser.isPresent()){
            System.out.println(optionalUser.get());
            return ResponseEntity.ok(optionalUser.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no Account with this email.");
        }
    }

    @PostMapping ("/password-reset/{email}/send")
    public ResponseEntity<?> sendTokenToEmail(@PathVariable(value = "email") String email){
        try{
            Boolean isSend = userService.send6CharToken(email);
            return ResponseEntity.ok(isSend);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }

    }

    @PostMapping("/password-reset/{email}/send/check")
    public ResponseEntity<?> sendTokenToEmail(@PathVariable(value = "email") String email,
                                              @RequestBody Map<String, String> payload){

        try{
            Boolean isMatch = userService.checkToken(email,payload.get("token"));
            if(!isMatch){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
            System.out.println("mached");
            return ResponseEntity.ok(isMatch);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }

    }

    @PostMapping("/password-reset/{email}/send/check/change")
    public ResponseEntity<?> changePass(@PathVariable(value = "email") String email,
                                                          RedirectAttributes redirectAttributes,
                                                          @RequestBody Map<String, String> payload) {
        if (!Objects.equals(payload.get("password"), payload.get("confirmPassword")) || payload.get("password").isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("messageType", "error", "message", "Passwords do not match!"));
        }
        try {
            userServiceImpl.changePass(email, payload.get("password"));
            return ResponseEntity.ok(Map.of("messageType", "success", "message", "Password changed successfully", "redirectUrl", "/login"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("messageType", "error", "message", "Failed to update password"));
        }
    }
}
