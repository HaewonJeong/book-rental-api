package com.ohgiraffers.bookrentalapi.DTO;

import com.ohgiraffers.bookrentalapi.enumtype.RentalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RentalDTO {

    @Schema(description = "대여 번호")
    private Integer rentalNo;
    @NotNull (message = "회원 번호는 필수 입니다.")
    @Schema(description = "회원 번호")
    private Integer memberNo;
    @NotNull (message = "도서 번호는 필수 입니다.")
    @Schema(description = "도서 번호")
    private Integer bookNo;

    @Schema(description = "대여 날짜")
    private LocalDate rentedAt;
    @Schema(description = "반납 날짜")
    private LocalDate dueDate;
    @Schema(description = "반납 예정 날짜")
    private LocalDate returnedAt;

    @Schema(description = "대여 상태")
    private RentalStatus status;

}
