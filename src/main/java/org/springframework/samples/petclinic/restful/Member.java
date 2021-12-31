package org.springframework.samples.petclinic.restful;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * com.asianaidt.springrestful.step01.entity
 * L Member.java
 * JPA Entity
 * spring-boot-starter-data-jpa 의존성을 추가하고 @Entity를 붙이면 테이블과 자바 클래스가 매핑이 된다.
 * @Id 테이블의 식별자. primary key
 * @GeneratedValue : 전략을 strategy = GenerationType.AUTO 하면 데이터베이스에 넣을 때 자동으로 관리해준다.
 *
 * @date : 2021-05-21 오전 10:34
 * @author : YHKIM
 **/

@Setter
@Getter
@NoArgsConstructor
@ToString(of = {"id", "name", "email"})
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "사용자이름은 필수 입력 사항입니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, max = 12, message = "비밀번호는 8~12 로 입력해주세요.")
    private String password;
    @NotNull
    @Email
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @JsonIgnore
    Department department;

    @Builder
    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
