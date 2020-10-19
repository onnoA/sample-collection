package entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private String username;

    @NotBlank(message = "电话号码不能为空。")
    private String phoneNumber;

    // 验证码
    private String code;

}
