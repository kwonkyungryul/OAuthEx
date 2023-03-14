package shop.mtcoding.getinthere.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenProperties {
    private String accessToken;
    private String tokenType;
    private String idToken;
    private String refreshToken;
    private Long expiresIn;
    private String scope;
    private Long refreshTokenExpiresIn;
}