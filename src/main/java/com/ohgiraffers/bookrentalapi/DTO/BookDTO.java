package com.ohgiraffers.bookrentalapi.DTO;

import com.ohgiraffers.bookrentalapi.enumtype.BookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {

    @Schema(description = "도서 번호")
    private Integer bookNo;
    @NotNull(message = "도서번호는 필수입니다.")
    @NotBlank(message = "도서번호는 공백일 수 없습니다.")
    @Schema(description = "도서 제목")
    private String title;
    @NotNull(message = "작가명은 필수입니다.")
    @NotBlank(message = "작가명은 공백일 수 없습니다")
    @Schema(description = "글쓴이")
    private String author;
    @NotNull(message = "도서명은 필수입니다.")
    @NotBlank(message = "도서명은 공백일 수 없습니다")
    @Schema(description = "도서 식별 번호")
    private String isbn;
    @Schema(description = "도서 대여 상태")
    private BookStatus status;
    @Schema(description = "출간일")
    @PastOrPresent(message = "출간일은 오늘 또는 과거 날짜만 가능합니다.")
    private LocalDate publishedAt;

}
