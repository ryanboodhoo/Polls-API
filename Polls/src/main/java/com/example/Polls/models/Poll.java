package com.example.Polls.models;

import jakarta.persistence.*;

import java.util.Set;
@Entity
public class Poll {
    @Id
    @Column(name = "POLL_ID")    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column (name = "QUESTION")
    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "POLL_ID")
    @OrderBy
    private Set<Options> options;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Options> getOptions() {
        return options;
    }

    public void setOptions(Set<Options> options) {
        this.options = options;
    }
}
