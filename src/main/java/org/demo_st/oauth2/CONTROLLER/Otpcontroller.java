package org.demo_st.oauth2.CONTROLLER;

import org.demo_st.oauth2.ENTITY.Otp;
import org.demo_st.oauth2.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class Otpcontroller {

    @Autowired
    private OtpService otpService;

    @PostMapping("/signup-otp-req")
    public ResponseEntity<?> signupOtpReq(@RequestBody Otp otp) {
        return otpService.signupOtpReq(otp);
    }

    @PostMapping("/signup-otp-validation")
    ResponseEntity<?> signupOtpValidation(@RequestBody Otp otp) {
        return otpService.signupOtpValidation(otp);
    }

    @PostMapping("/pwd-reset-otp-req")
    public ResponseEntity<?> pwdResetOtpReq(@RequestBody Otp otp) {
        return otpService.pwdResetOtpReq(otp);
    }

    @PostMapping("/pwd-otp-req-validation")
    ResponseEntity<?> pwdResetOtpValidation(@RequestBody Otp otp) {
        return otpService.pwdResetOtpValidation(otp);
    }

    @PostMapping("/pwd-reset")
    ResponseEntity<?> pwdReset(@RequestBody Otp otp) {
        return otpService.pwdReset(otp);
    }
}
