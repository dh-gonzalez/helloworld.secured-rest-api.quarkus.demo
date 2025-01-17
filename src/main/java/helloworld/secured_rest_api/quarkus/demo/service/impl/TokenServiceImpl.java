package helloworld.secured_rest_api.quarkus.demo.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import helloworld.secured_rest_api.quarkus.demo.service.TokenService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

/**
 * Token service implementation
 */
@ApplicationScoped
public class TokenServiceImpl implements TokenService {

    /** Logger for this class */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ConfigProperty(name = "jwt.secret")
    private String _jwtSecret;

    @ConfigProperty(name = "jwt.algorithm", defaultValue = "HmacSHA512")
    private String _jwtAlgorithm;

    @ConfigProperty(name = "jwt.expiration", defaultValue = "3600")
    private long _jwtExpiration;

    @Inject
    private JWTParser _jwtParser;

    /** Secret key */
    private SecretKey _secretKey;

    @PostConstruct
    public void init() {
        // Initialization of secret key in order to encode and decode token
        byte[] keyBytes = _jwtSecret.getBytes(StandardCharsets.UTF_8);
        _secretKey = new SecretKeySpec(keyBytes, _jwtAlgorithm); // ou "HmacSHA512" selon ton algo
    }

    @Override
    public String generateToken(SecurityIdentity securityIdentity) {

        // Retrieve username and authorities
        String username = securityIdentity.getPrincipal().getName();
        Set<String> authorities = securityIdentity.getRoles();

        LOGGER.debug("Token generation has been requested for user {} with authorities {}",
                username, authorities);

        String token = Jwt
                //
                .subject(username)
                //
                .groups(authorities)
                //
                .expiresAt(Instant.now().plus(Duration.ofSeconds(_jwtExpiration)))
                //
                .sign(_secretKey);

        return token;
    }

    @Override
    public SecurityIdentity getAuthentication(String token) {

        try {
            JsonWebToken jsonWebToken = _jwtParser.verify(token, _secretKey);
            String username = jsonWebToken.getSubject();
            Set<String> authorities = jsonWebToken.getGroups();
            LOGGER.debug("Token is valid - expiration date: {}",
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(jsonWebToken.getExpirationTime()),
                            ZoneId.systemDefault()));

            return QuarkusSecurityIdentity.builder()
                    //
                    .setPrincipal(new QuarkusPrincipal(username))
                    //
                    .addRoles(authorities)
                    //
                    .build();

        } catch (ParseException e) {
            throw new NotAuthorizedException("Invalid or expired token : {}", e.getMessage());
        }
    }
}
