package com.example.backend.service.auth;

import com.example.backend.service.UserPresenter;
import com.example.backend.utils.JwtTokenUtils;
import com.example.core.entity.User;
import com.example.share.interfaces.dto.JwtRequest;
import com.example.share.interfaces.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final  AccountSecurityService accountSecurityService;
    private  final  LoginAttemptService loginAttemptService;
    private  final CaptchaService captchaService;
    private final LoginAttemptsCountService loginAttemptsCountService;
    private final UserPresenter userPresenter;

    public ResponseEntity<?>logout(HttpServletResponse response)
    {
        Cookie cookie = new Cookie("Auth_cookie", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/login");
        return  new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    /**
     *  gg wp
     * @param jwtRequest
     * @param request
     * @param response
     * @return
     */
    public ResponseEntity<?> authUser(@RequestBody JwtRequest jwtRequest, HttpServletRequest request, HttpServletResponse response) {

        //if login not exist
        ResponseEntity<Object> SEE_OTHER =
                BruteForceAttackProtection(jwtRequest, request);
        if (SEE_OTHER != null) return SEE_OTHER;

        User user = userPresenter.getUserByEmail(jwtRequest.getEmail());

     /*   if(!user.isEnabled()){
            //return new ResponseEntity<>(new InfoExeption(HttpStatus.FORBIDDEN.value(), "This account was Baned"),HttpStatus.FORBIDDEN);
            return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location","/login?error=account_baned").build();
        }*/

        String ip = request.getRemoteAddr();

        //if(loginAttemptService.isBloked(ip)) return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location","/login?error=ip_banned").build();

        if(!loginAttemptService.validateCaptcha(ip, jwtRequest.getCaptchaResponse()))   return  ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location","http://localhost:8080/login?error=ip_banned").build();

        if(!passwordEncoder.matches(jwtRequest.getPassword(), user.getPassword())){
            if(accountSecurityService.isAccountLocked(user)){
                return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location","http://localhost:8080/login?error=account_banned").build();
            }
            else {
                accountSecurityService.IncrementFailedAttempts(user, captchaService.isCaptchaValid(jwtRequest.getCaptchaResponse()));
                int attemptsLeft = accountSecurityService.getMax_failed_attempts() - user.getFailedAttempts();
                return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location","http://localhost:8080/login?error=wrong&attempt="+attemptsLeft).build();
            }
        }

        loginSusses(user,ip);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                jwtRequest.getEmail(), jwtRequest.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtTokenUtils.generateToken(user);

        Cookie cookie = new Cookie("Auth_cookie",token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        HttpHeaders headers =new HttpHeaders();
        headers.add("Location", "http://localhost:8080");
        return  new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);

    }

    private ResponseEntity<Object> BruteForceAttackProtection(JwtRequest jwtRequest, HttpServletRequest request) {
        if(!userPresenter.existsByEmail(jwtRequest.getEmail())){

            String ip = request.getRemoteAddr();
            loginAttemptService.loginFailed(ip);
            if (loginAttemptService.isBloked(ip)) {
                return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "http://localhost:8080/login?errpr=ip_banned").build();
            }
            int attemptsLeft = loginAttemptService.getCount_attempts() - loginAttemptService.AttemptsCount(ip);
            return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "http://localhost:8080/login?error=incorrect&attempt=" + attemptsLeft).build();
        }
        return null;
    }

    private void loginSusses(User user, String ip)
    {
        loginAttemptsCountService.loginAttemptsCount = 0;
        accountSecurityService.resetFailedAttempts(user);
        loginAttemptService.loginSuccess(ip);
    }


    public ResponseEntity<?> getLoginAttempts()
    {
        return ResponseEntity.ok(Map.of("attempts", loginAttemptsCountService.loginAttemptsCount++));
    }
}
