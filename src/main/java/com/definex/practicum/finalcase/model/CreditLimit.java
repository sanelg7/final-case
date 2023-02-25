package com.definex.practicum.finalcase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "credit_limits")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user"})
public class CreditLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public CreditLimit(Double amount, User user) {
        this.amount = amount;
        this.user = user;
    }
}
