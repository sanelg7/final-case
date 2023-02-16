package com.definex.practicum.finalcase.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "credit_scores")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {})
public class CreditScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "credit_score")
    private Double creditScoreValue;

}
