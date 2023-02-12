package com.definex.practicum.finalcase.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "credit_limits")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {})
public class CreditLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @OneToOne
    @JoinColumn(name = "user_tckn")
    private User user;
}
