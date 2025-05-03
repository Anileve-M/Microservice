package development.twomicroservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;
}