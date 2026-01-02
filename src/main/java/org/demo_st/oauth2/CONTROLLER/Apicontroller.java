package org.demo_st.oauth2.CONTROLLER;

import org.demo_st.oauth2.ENTITY.User;
import org.demo_st.oauth2.JWTTOKENS.Validatetoken;
import org.demo_st.oauth2.REPO.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class Apicontroller {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private Validatetoken validatetoken;

    @GetMapping("/getuserinfo")
    @PreAuthorize("hasAuthority('WEATHER_READ')")
    public ResponseEntity<?> getuserinfo(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(userrepo.findByEmail(email));
    }

    @PutMapping("/updatedetails")
    public ResponseEntity<?>  updatedetails(Authentication auth , @RequestBody User user) {
        String email = auth.getName();
        Optional<User> userOptional = userrepo.findByEmail(email);
        if(userOptional.isPresent()) {
            User newUser = userOptional.get();
            newUser.setEmail(user.getEmail());
            newUser.setLogintype("google -> manual");
            newUser.setPassword(user.getPassword());
            newUser.setUsername(user.getUsername());
            userrepo.save(newUser);
            return ResponseEntity.ok("user pwd updated successfully");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/hi")
//    //@PreAuthorize("hasRole('ADMIN')")
//    //@PreAuthorize("hasAnyRole('ADMIN' , 'USER)")
//    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    //WE CAN ADD ANY ONE IN ABOVE 3
//    public ResponseEntity<?>  hi(Authentication auth) {
//        String email = auth.getName();
//        return ResponseEntity.ok(userrepo.findByEmail(email));
//    }
//
//    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('WEATHER_DELETE')")
//    //@PreAuthorize("hasRole('ADMIN')")
//    //@PreAuthorize("hasAnyRole('ADMIN' , 'USER)")
//    public ResponseEntity<?>  hello(Authentication auth) {
//        String email = auth.getName();
//        return ResponseEntity.ok(userrepo.findByEmail(email));
//    }



    //WHILE DEPLOYING USER ROLE CREATE AND DELETE USERS FROM DB
    //BY DEFAULT ROLE IS USER NOT DB
    //IF WE SET USER AD ADMIN . THERE IS ONE ADMIN WHO HAVE ACCESS TO DB
    //HE CAN UPDATE USING SQL
}

//@PreAuthorize("hasRole('ADMIN')")
//@GetMapping("/getuserinfo")
//public ResponseEntity<?> getuserinfo(Authentication auth) {
//    String email = auth.getName();
//    return ResponseEntity.ok(userrepo.findByEmail(email));
//}
//YES — no need to validate again at controller level. ✅
//Each HTTP request is handled by one thread
//SecurityContextHolder uses ThreadLocal
//So each thread has its own SecurityContext
//JWT is validated once per request in the filter, and the controller trusts the SecurityContext.
