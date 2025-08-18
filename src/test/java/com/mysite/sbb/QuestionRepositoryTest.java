package com.mysite.sbb;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

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

    @Test
    @DisplayName("findBySubjectLike ")
    void t5() {
        List<Question> qList = this.questionRepository.findBySubjectLike ("%sbb%");
        Question q = qList.get(0);
        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    @Test
    @DisplayName("update")
    void t6() { // 가장 먼저 실행시키기 위해서 t0로 메소드 이름 변경해도 정상동작
        Question q = questionRepository.findById(1).get();
        assertThat(q).isNotNull();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);

        Question q2 = questionRepository.findBySubject("수정된 제목").get();
        assertThat(q2).isNotNull();
    }

    @Test
    @DisplayName("삭제")
    void t7() {
        assertThat(questionRepository.count()).isEqualTo(2);

        Question q = questionRepository.findById(1).get();
        questionRepository.delete(q);

        assertThat(questionRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("답변 생성")
    void t8() {
        Question q = questionRepository.findById(2).get();
        String content = "네 자동으로 생성됩니다.";

        Answer a = new Answer();
        a.setContent(content);
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());

        answerRepository.save(a);
        Answer a2 = answerRepository.findById(1).get();
        assertThat(a2.getContent()).isEqualTo(content);

        assertThat(a.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("답변 생성 by oneToMany")
    void t9() {
        Question q = questionRepository.findById(2).get();
        String content = "네 자동으로 생성됩니다.";

        int beforeCount = q.getAnswerList().size();

        Answer a = new Answer();
        a.setContent(content);
        q.getAnswerList().add(a);

        int afterCount = q.getAnswerList().size();
        assertThat(a.getId()).isEqualTo(null);
        assertThat(afterCount).isEqualTo(beforeCount+1);
    }

    @Test
    @DisplayName("답변 생성 by oneToMany 2")
    void t10() {
        Question q = questionRepository.findById(2).get();
        String content = "네 자동으로 생성됩니다.";

        int beforeCount = q.getAnswerList().size();

        Answer a = q.addAnswer(content);
        assertThat(a.getContent()).isEqualTo(content);
        answerRepository.saveAndFlush(a);;

        int afterCount = q.getAnswerList().size();
        assertThat(a.getId()).isNotNull();
        assertThat(afterCount).isEqualTo(beforeCount+1);
    }

    @Test
    @DisplayName("답변 조회")
    void t11() {
        Question question = questionRepository.findById(2).get();

        List<Answer> answerList = question.getAnswerList();
        Answer a = answerList.get(0);
        assertThat(a.getContent()).isEqualTo("네 자동으로 생성됩니다.");
    }

    @Test
    @DisplayName("답변 조회")
    void t12 () {
        Answer answer = answerRepository.findById(1).get();

        assertThat(answer.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("답변 조회 by oneToMany")
    void t13 () {
        Question question = questionRepository.findById(2).get();

        List<Answer> answers = question.getAnswerList();
        assertThat(answers.size()).isEqualTo(1);

        Answer answer = answers.get(0);
        assertThat(answer.getContent()).isEqualTo("네 자동으로 생성됩니다.");
    }

    @Test
    @DisplayName("findAnswer by question")
    void t14 () {
        Question question = questionRepository.findById(2).get();

        Answer answer = question.getAnswerList().get(0);
        assertThat(answer.getId()).isEqualTo(1);
    }
}
