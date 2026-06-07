package com.ohgiraffers.bookrentalapi.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

    @Schema(description = "회원 번호")
    private Integer memberNo;
    @NotBlank(message = "아이디는 반드시 입력되어야 합니다.")
    @Size(min= 4, max = 20, message = "아이디는 길이 4~20자여야 합니다.")
    @Schema(description = "회원 ID")
    private String id;
    @NotNull(message = "이름은 반드시 입력되어야 합니다.")
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Schema(description = "회원 이름")
    private String name;
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotNull(message = "이메일은 반드시 입력되어야 합니다.")
    @Schema(description = "회원 이메일")
    private String email;
    @Schema(description = "가입 일자")
    private LocalDate joinedAt;
}
