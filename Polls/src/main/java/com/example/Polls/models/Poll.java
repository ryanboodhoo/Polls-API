package com.example.Polls.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

//POLL POJO Plain Old Java Object class

@Entity
public class Poll {

    @Id
    @GeneratedValue
    @Column(name="POLL_ID")
    private Long id;


    @Column(name="QUESTION")
    @NotEmpty
    private String question;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="POLL_ID")
    @OrderBy
    @Size(min=2, max = 6)

    @NotEmpty
    private Set<Options> options;
    // Getters and Setters removed for brevity


    public Poll() {
    }

    public Poll(Long id, String question, Set<Options> options) {
        this.id = id;
        this.question = question;
        this.options = options;
    }

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

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                '}';
    }
}