package development.twomicroservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role_id")
    private long role_id;

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserDto(long role_id, String password, String login, long id) {
        this.role_id = role_id;
        this.password = password;
        this.login = login;
        this.id = id;
    }
}