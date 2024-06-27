package com.sanzuniao.member.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author yangguang
 */
@Data
public class MemberRegisterReq {
    @NotBlank(message = "【手机号】不能为空")
    private String mobile;
}
