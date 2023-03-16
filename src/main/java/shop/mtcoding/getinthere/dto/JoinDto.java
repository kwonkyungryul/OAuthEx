package shop.mtcoding.getinthere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class JoinDto {
    private String username;
    private String pwd;
    private String email;
    private String provider;
}
