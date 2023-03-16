package shop.mtcoding.getinthere.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
public class KakaoAccountDto {
    private Long id;
    private String connectedAt;
    private KakaoAccount kakaoAccount;

    @Getter @Setter @NoArgsConstructor
    public class KakaoAccount {
        private Boolean hasEmail;
        private Boolean emailNeedsAgreement;
        private Boolean isEmailValid;
        private Boolean isEmailVerified;
        private String email;
    }
}
