package com.lsm.test.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// ibatis나 MyBatis 같은 경우 DAO 라고 불리는 DB 접근자이며 JPA에서는 Repository라고 부르는 인터페이스로 관리한다.
//Entity와 Entity repo 는 함께 위치해야 한다.
public interface PostsRepository extends JpaRepository<Posts,Long> {

    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();
}
