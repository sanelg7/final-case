package com.definex.practicum.finalcase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "credit_applications")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user"})
public class CreditLimitApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
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
