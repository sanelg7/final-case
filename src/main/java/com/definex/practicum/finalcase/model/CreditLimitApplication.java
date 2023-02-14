package com.definex.practicum.finalcase.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "credit_applications")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {})
public class CreditLimitApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_tckn")
    private User user;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "monthly_income")
    private Double monthlyIncome;

    @Column(name = "guarantee")
    private Double guarantee;

    public CreditLimitApplication(User user, Boolean approved, Double monthlyIncome, Double guarantee){
        this.user = user;
        this.approved = approved;
        this.monthlyIncome = monthlyIncome;
        this.guarantee = guarantee;
    }
    public CreditLimitApplication(User user, Boolean approved, Double monthlyIncome){
        this.user = user;
        this.approved = approved;
        this.monthlyIncome = monthlyIncome;
    }

}
