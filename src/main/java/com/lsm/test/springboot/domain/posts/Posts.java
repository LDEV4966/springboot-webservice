package com.lsm.test.springboot.domain.posts;

import com.lsm.test.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //lombok , 모든 field 값의 getter 매소드를 자동 생성
@NoArgsConstructor // lombok , 기본 생성자 자동추가
@Entity //jpa , 테이블과 링크될 클래스임을 나타냄, 기본값으로 클래스의 카멜케이스 이름을 네이밍으로 테이블 이름을 매칭
public class Posts extends BaseTimeEntity {
    // Entity 클래스 내에서는 Setter method를 절대 만들지 않는다.
    // 대신 해당 필드 값의 변경이 필요하면 명확히 그 목적과 의도에 맞게 메소드를 추가한다.
    // 이렇게 함으로써 인스턴스의 값들의 변화를 명확히 구분할 수 있다.
    // 따라서 생성자를 통해 최종값을 채운 후 , 목적에 맞게 메소드를 호출하여 값을 변경하는 형식으로 DB에 삽입한다.

    @Id // table PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column(length = 500, nullable = false) // table column 선언하지 않아도 column 이지만 추가적으로 옵션 값 줄때 선언.
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder// lombok, 해당 클래스의 빌터 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    // Builder와 생성자의 역할과 호출 시점은 같다. 다만 차이점으로는 Builder는 어떤 필드에 어떤 값을 채울지 명확히 알 수 있다.
    public Posts(String title,String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
