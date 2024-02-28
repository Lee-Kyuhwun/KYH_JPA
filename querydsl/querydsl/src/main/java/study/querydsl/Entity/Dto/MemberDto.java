package study.querydsl.Entity.Dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;


    @QueryProjection // Querydsl을 사용하는 쿼리 메서드에서 사용할 생성자에 추가
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }




}
