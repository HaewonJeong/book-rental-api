package com.ohgiraffers.bookrentalapi.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RentalRequest {

    @Schema(description = "회원 번호")
    @NotNull(message = "회원 번호는 필수 입력 값입니다.")
    private Integer memberNo;

    @Schema(description = "도서 번호")
    @NotNull(message = "도서 번호는 필수 입력 값입니다.")
    private Integer bookNo;

}
