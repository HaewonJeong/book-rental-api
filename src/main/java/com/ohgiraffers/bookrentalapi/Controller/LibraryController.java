package com.ohgiraffers.bookrentalapi.Controller;

import com.ohgiraffers.bookrentalapi.DTO.*;
import com.ohgiraffers.bookrentalapi.Exceiption.BookAlreadyRentedException;
import com.ohgiraffers.bookrentalapi.Exceiption.BookNotFoundException;
import com.ohgiraffers.bookrentalapi.Exceiption.MemberNotFoundException;
import com.ohgiraffers.bookrentalapi.Exceiption.RentalNotFoundException;
import com.ohgiraffers.bookrentalapi.enumtype.BookStatus;
import com.ohgiraffers.bookrentalapi.enumtype.RentalStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/library") //이 Controller안의 모든 메서드에 붙는 URL Prefix
public class LibraryController {

    private final List<MemberDTO> memberList;
    private final List<BookDTO> bookList;
    private final List<RentalDTO> rentalList;

    public LibraryController() {
        memberList = new ArrayList<>();
        memberList.add(new MemberDTO(1, "reader01", "죠르디", "reader01@test.co", LocalDate.of(2025, 12, 25)));
        memberList.add(new MemberDTO(2, "reader02", "스카피", "reader02@test.co", LocalDate.of(2025, 12, 26)));
        memberList.add(new MemberDTO(3, "reader03", "팬다 주니어", "reader03@test.co", LocalDate.of(2025, 12, 27)));
        memberList.add(new MemberDTO(4, "reader04", "케로&베로니", "reader04@test.co", LocalDate.of(2025, 12, 28)));

        bookList = new ArrayList<>();
        bookList.add(new BookDTO(1, "제로베이스에서 취업까지 백엔드 개발자편 with 스프링부트", "김송아", "979-11-7579-016-2", BookStatus.AVAILABLE, LocalDate.of(2026, 01, 19)));
        bookList.add(new BookDTO(2, "백엔드 개발자 온보딩 가이드", "이준형,김석현", "979-11-7579-028-5", BookStatus.AVAILABLE, LocalDate.of(2026, 04, 25)));
        bookList.add(new BookDTO(3, "ETS 토익 정기시험 기출문제집 5 1000 RC", "YBM", "", BookStatus.AVAILABLE, LocalDate.of(2026, 05, 01)));
        bookList.add(new BookDTO(4, "해커스 토익 기출 보카", "David Cho", "979-89-6542-278-5", BookStatus.RENTED, LocalDate.of(2026, 03, 02)));

        rentalList = new ArrayList<>();
        rentalList.add(new RentalDTO(1,1,4, LocalDate.of(2026,06,07), LocalDate.of(2026,06,21), LocalDate.of(2026,06,14), RentalStatus.RENTED));


    }

    /*회원 API*/
    //1. 회원 목록 조회
    @GetMapping("/members")
        @Operation(summary = "회원 목록 조회/검색",  description = "전체 회원을 조회하거나 name으로 회원을 검색한다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공")
        })
    public ResponseEntity<ResponseMessage> findMembers(@RequestParam(required = false) String name) {

        if (name == null) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("members", memberList);
            return ResponseEntity.ok(new ResponseMessage(200, "회원 목록 조회(전체)", response));
        } else {
            List<MemberDTO> foundMembers = memberList.stream()
                    .filter(member -> member.getName().contains(name))
                    .toList();

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("members", foundMembers);

            ResponseMessage responseMessage = new ResponseMessage(200, "회원 목록 조회", response);
            return ResponseEntity.ok(responseMessage);
        }
    }

    //2. 회원 단건 조회
    @GetMapping("/members/{memberNo}")
        @Operation(summary = "회원 번호로 조회",  description = "회원 번호를 통해 해당하는 회원 정보를 조회합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "회원 단건 조회 성공"),
                @ApiResponse(responseCode = "404", description = "회원 정보 없음")
        })
    public ResponseEntity<ResponseMessage> findMember(@PathVariable int memberNo) {

        MemberDTO foundMember = memberList.stream()
                .filter(member -> member.getMemberNo() == memberNo)
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException(memberNo + "번 회원을 찾을 수 없습니다."));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("member", foundMember);

        ResponseMessage responseMessage = new ResponseMessage(200, "회원 단건 조회", response);
        return ResponseEntity.ok(responseMessage);
    }

    //3. 회원 등록
    @PostMapping("/members")
    @Operation(summary = "신규 회원 등록",  description = "신규 회원 등록")
    @ApiResponses({
                @ApiResponse(responseCode = "201", description = "회원 등록 성공")
        })
    public ResponseEntity<Void> registMember(@Valid @RequestBody MemberDTO member) {
        int nextNo = memberList.size() + 1;
        member.setMemberNo(nextNo);
        member.setJoinedAt(LocalDate.now());
        memberList.add(member);

        return ResponseEntity
                .created(URI.create("/api/v1/library/members/" + nextNo))
                .build();

    }

    /*도서 API*/
    //1. 도서 목록 조회
    @GetMapping("/books")
        @Operation(summary = "도서 목록 조회/검색",  description = "전체 도서을 조회하거나 name, status query string으로 도서를 검색한다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "도서 목록 조회 성공")
        })
    public ResponseEntity<ResponseMessage> findBooks(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) BookStatus status) {

        if (title == null && status == null) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("books", bookList);
            return ResponseEntity.ok(new ResponseMessage(200, "도서 목록 조회(전체)", response));
        } else {
            List<BookDTO> foundBooks = bookList.stream()
                    .filter(book -> title == null || book.getTitle().contains(title))
                    .filter(book -> status == null || book.getStatus() == BookStatus.AVAILABLE)
                    .toList();

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("books", foundBooks);

            ResponseMessage responseMessage = new ResponseMessage(200, "도서 목록 조회", response);
            return ResponseEntity.ok(responseMessage);
        }
    }

    //2. 도서 단권 조회
    @GetMapping("/books/{bookNo}")
        @Operation(summary = "도서 번호로 조회",  description = "도서 번호를 통해 해당하는 도서 정보를 조회합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "도서 단건 조회 성공"),
                @ApiResponse(responseCode = "404", description = "도서 정보 없음")
        })
    public ResponseEntity<ResponseMessage> findBook(@PathVariable int bookNo) {

        BookDTO foundBook = bookList.stream()
                .filter(book -> book.getBookNo() == bookNo)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(bookNo + "번 도서를 찾을 수 없습니다."));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("book", foundBook);

        ResponseMessage responseMessage = new ResponseMessage(200, "도서 단권 조회", response);
        return ResponseEntity.ok(responseMessage);
    }

    //3. 도서 등록
    @PostMapping("/books")
        @Operation(summary = "신규 도서 등록",  description = "신규 도서 등록")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "도서 등록 성공")
        })
    public ResponseEntity<Void> registbook(@Valid @RequestBody BookDTO book) {
        int nextNo = bookList.size() + 1;
        book.setBookNo(nextNo);
        book.setStatus(BookStatus.AVAILABLE);
        bookList.add(book);

        return ResponseEntity
                .created(URI.create("/api/v1/library/books/" + nextNo))
                .build();

    }

    /*대여 API*/
    //1. 도서 대여
    @PostMapping("/rentals")
        @Operation(summary = "신규 도서 대여",  description = "신규 도서 대여")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "도서 대여 성공")
        })
    public ResponseEntity<Void> registrental(@Valid @RequestBody RentalRequest rental) {

        memberList.stream()
               .filter(member -> member.getMemberNo() == rental.getMemberNo())
               .findFirst()
               .orElseThrow(() -> new MemberNotFoundException(rental.getMemberNo() + "번 회원을 찾을 수 없습니다."));

        BookDTO foundBook = bookList.stream()
                .filter(book -> book.getBookNo() == rental.getBookNo())
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(rental.getBookNo() + "번 도서를 찾을 수 없습니다."));

        if(foundBook.getStatus()  == BookStatus.RENTED){
            throw new BookAlreadyRentedException(foundBook.getBookNo() + "번 도서는 이미 대여 중입니다.");
        }

        RentalDTO rentalBook = new RentalDTO(rentalList.size() + 1,
                rental.getMemberNo(),
                rental.getBookNo(),
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                null, RentalStatus.RENTED);

        //도서 상태 변경
        foundBook.setStatus(BookStatus.RENTED);

        rentalList.add(rentalBook);

        return ResponseEntity
                .created(URI.create("/api/v1/library/rentals/" + rentalBook.getRentalNo()))
                .build();

    }

    //2. 대여 단건 조회
    @GetMapping("/rentals/{rentalNo}")
        @Operation(summary = "대여 번호로 조회",  description = "대여 번호를 통해 해당하는 대여 정보를 조회합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "대여 단건 조회 성공"),
                @ApiResponse(responseCode = "404", description = "대여 정보 없음")
        })
    public ResponseEntity<ResponseMessage> findRental(@PathVariable int rentalNo) {

        RentalDTO foundRental = rentalList.stream()
                .filter(rental -> rental.getRentalNo() == rentalNo)
                .findFirst()
                .orElseThrow(() -> new RentalNotFoundException(rentalNo + "번 대여 건을 찾을 수 없습니다."));

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("rental", foundRental);

        ResponseMessage responseMessage = new ResponseMessage(200, "대여 단권 조회", response);
        return ResponseEntity.ok(responseMessage);
    }

    //3. 도서 반납
    @PatchMapping("/rentals/{rentalNo}/return")
        @Operation(summary = "도서 반납",  description = "대여 번호를 통해 해당하는 대여 도서를 반납 합니다.")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "도서 반납 성공"),
                @ApiResponse(responseCode = "404", description = "회원/도서 정보 없음"),
                @ApiResponse(responseCode = "400", description = "이미 반납된 대여")
        })
    public ResponseEntity<Void> registrental(@PathVariable int rentalNo) {

        RentalDTO  foundRental = rentalList.stream()
                .filter(rental -> rental.getRentalNo() == rentalNo)
                .findFirst()
                .orElseThrow(() -> new RentalNotFoundException(rentalNo+ "번 대여 건을 찾을 수 없습니다."));

        if(foundRental.getStatus()  == RentalStatus.RETURNED){
            throw new BookAlreadyRentedException(rentalNo+ "번 대여 건은 이미 반납 되었습니다.");
        }

        foundRental.setStatus(RentalStatus.RETURNED);
        foundRental.setReturnedAt(LocalDate.now());

        BookDTO foundBook = bookList.stream()
                .filter(book -> book.getBookNo() == foundRental.getBookNo())
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException(foundRental.getBookNo() + "번 도서를 찾을 수 없습니다."));

        //도서 상태 변경
        foundBook.setStatus(BookStatus.AVAILABLE);

        return ResponseEntity.noContent().build();

    }

    //4. 대여 목록 조회 (테스트)
    @GetMapping("/rentals")
        @Operation(summary = "대여 목록 조회/검색(테스트)",  description = "전체 대여를 조회하거나 rentalNo query string으로 대여 건을 검색한다.")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "대여 목록 조회 성공")
        })
    public ResponseEntity<ResponseMessage> findrentals(@RequestParam(required = false) String rentalNo) {

        if (rentalNo == null) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("rentals", rentalList);
            return ResponseEntity.ok(new ResponseMessage(200, "대여 목록 조회(전체)", response));
        } else {
            List<RentalDTO> foundRentals = rentalList.stream()
                    .filter(rental -> rental.getRentalNo() == Integer.parseInt(rentalNo))
                    .toList();

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("rentals", foundRentals);

            ResponseMessage responseMessage = new ResponseMessage(200, "대여 목록 조회", response);
            return ResponseEntity.ok(responseMessage);
        }
    }

}
