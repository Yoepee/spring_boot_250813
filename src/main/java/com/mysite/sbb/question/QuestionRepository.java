package com.mysite.sbb.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findBySubject(String s);

    Optional<Question> findBySubjectAndContent(String s, String s1);

    List<Question> findBySubjectLike(String s);
}
