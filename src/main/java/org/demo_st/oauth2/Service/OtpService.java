package org.demo_st.oauth2.Service;

import org.demo_st.oauth2.ENTITY.Otp;
import org.demo_st.oauth2.ENTITY.Role;
import org.demo_st.oauth2.ENTITY.User;
import org.demo_st.oauth2.JWTTOKENS.TokenGeneration;
import org.demo_st.oauth2.MAIL.EmailService;
import org.demo_st.oauth2.MAIL.EmailService;
import org.demo_st.oauth2.REPO.Otprepo;
import org.demo_st.oauth2.REPO.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class OtpService {

    //STILL THINGS CAN BE DONE ARE
    //EXPIRATION TIME
    //IF WE HAVE ONE OR MORE OTP WITH the SAME TYPE AND EMAIL SORT THEN BASED ON CREATION TIME IN DESC
    //ADD CORN JOB TO DELETE AFTER EXPIRATION TIME
    //IN DB WE CAN ADD CREATION TIME ALONG WITH CREATION TIME + 5 MIN IS EXPIRE TIME

    @Autowired
    private Otprepo otprepo;

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private EmailService mailSender;

    @Autowired
    private TokenGeneration tokenGeneration;

    private long generateOtp() {
        return 100000L + new java.util.Random().nextInt(900000);
    }

    public ResponseEntity<?> signupOtpReq(Otp otp) {
        if (userrepo.existsByEmail(otp.getEmail())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body("email already registered");
        }

        long otpValue = generateOtp();
        if (!mailSender.send(otp.getEmail(), "signup", otpValue)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("failed to send otp");
        }

        otp.setOtp(otpValue);
        otp.setType("signup");
        LocalDateTime now = LocalDateTime.now();
        otp.setCreatedAt(now);
        otp.setExpiredAt(now.plusSeconds(180));
        otprepo.save(otp);
        return ResponseEntity.ok("otp sent successfully");
    }

    public ResponseEntity<?> signupOtpValidation(Otp otp) {
        Optional<Otp> savedOtp = otprepo.findTopByEmailAndTypeOrderByCreatedAtDesc(otp.getEmail(), "signup");

        if (savedOtp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("email not found");
        }
        Otp dbOtp = savedOtp.get();
        if (!Objects.equals(dbOtp.getOtp(), otp.getOtp())) {
            return ResponseEntity.badRequest().body("enter correct otp");
        }
        if (LocalDateTime.now().isAfter(dbOtp.getExpiredAt())) {
            return ResponseEntity.badRequest().body("otp expired");
        }
        User user = new User();
        user.setEmail(otp.getEmail());
        user.setPassword(dbOtp.getPassword());
        user.setUsername(dbOtp.getUsername());
        user.setRole(Role.USER);
        user.setLogintype("manual");

        userrepo.save(user);

        otprepo.delete(dbOtp);
        String token = tokenGeneration.generateToken(user.getEmail());
        String refresh_token = tokenGeneration.generaterefreshtoken(user.getEmail());

        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("refreshtoken", refresh_token);
        map.put("msg", "user registered successfully");

        return ResponseEntity.ok(map);

    }

        public ResponseEntity<?> pwdResetOtpReq(Otp otp) {
        Optional<User> user = userrepo.findByEmail(otp.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("email not found");
        }

        long otpValue = generateOtp();
        if (!mailSender.send(otp.getEmail(), "pwdreset", otpValue)) {
            return ResponseEntity.badRequest().body("failed to send otp");
        }

        otp.setOtp(otpValue);
        otp.setType("pwdreset");
        LocalDateTime now = LocalDateTime.now();
        otp.setCreatedAt(now);
        otp.setExpiredAt(now.plusSeconds(180));
        otprepo.save(otp);

        return ResponseEntity.ok("password reset otp sent successfully");
    }

    public ResponseEntity<?> pwdResetOtpValidation(Otp otp) {
        Optional<Otp> savedOtp = otprepo.findTopByEmailAndTypeOrderByCreatedAtDesc(otp.getEmail(),"pwdreset");
        Optional<User> user = userrepo.findByEmail(otp.getEmail());

        if (savedOtp.isEmpty() || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("email not found");
        }
        Otp dbOtp = savedOtp.get();
        if (!Objects.equals(dbOtp.getOtp(), otp.getOtp())) {
            return ResponseEntity.badRequest().body("enter correct otp");
        }
        if (LocalDateTime.now().isAfter(dbOtp.getExpiredAt())) {
            return ResponseEntity.badRequest().body("otp expired");
        }

        return ResponseEntity.ok("otp validation done");
    }

    public ResponseEntity<?> pwdReset(Otp otp) {
        Optional<User> user = userrepo.findByEmail(otp.getEmail());
        if(user.isPresent()){
            user.get().setPassword(otp.getPassword());
            userrepo.save(user.get());
            return ResponseEntity.ok("password reset done successfully");
        }
        return  ResponseEntity.badRequest().body("email not found");
    }
}
