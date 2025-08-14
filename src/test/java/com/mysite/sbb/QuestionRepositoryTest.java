package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("findAll")
    void t1() {
        List<Question> questionList = questionRepository.findAll();

        assertThat(questionList).size().isEqualTo(2);
        assertEquals(2, questionList.size());

        Question q1 = questionList.get(0);
        assertThat(q1.getSubject()).isEqualTo("sbb가 무엇인가요?");
        assertEquals("sbb가 무엇인가요?", q1.getSubject());
    }

    @Test
    @DisplayName("findById")
    void t2() {
        Optional<Question> oq = questionRepository.findById(1);
        if(oq.isPresent()){
            Question question = oq.get();
            assertEquals("sbb가 무엇인가요?", question.getSubject());
        }
    }

    @Test
    @DisplayName("findBySubject")
    void t3() {
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?").get();
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectAndContent")
    void t4() {
        Question q = this.questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.").get();
        assertThat(q.getId()).isEqualTo(1);
    }
}
