package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author onnoA
 * @date 2021年05月24日 13:30
 */
@Data
public class PacketCheckDt implements Serializable {

    @NotBlank(message = "id不能为空")
    private String id;

    private String username;

    private String password;
}
