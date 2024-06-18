package hexlet.code.controller.api;

import hexlet.code.DTO.AuthRequest;
import hexlet.code.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthenticationController {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "")
    public String create(@RequestBody AuthRequest auth) {
        var authentication = new UsernamePasswordAuthenticationToken(
                auth.getUsername(), auth.getPassword());

        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(auth.getUsername());
    }
}
