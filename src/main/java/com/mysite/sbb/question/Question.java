package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // 기본적으로 LAZY 모드, EAGER 환경 사용 시 fetch = FetchType.EAGER 추가
    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Answer> answerList = new ArrayList<>();

    public Answer addAnswer(String content) {
        Answer a = new Answer();
        a.setContent(content);
        a.setQuestion(this);
        a.setCreateDate(LocalDateTime.now());
        answerList.add(a);

        return a;
    }
}
