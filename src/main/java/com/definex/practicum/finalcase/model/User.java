package com.definex.practicum.finalcase.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tckn", unique = true, nullable = false, length = 11)
    private String tckn;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gsm_number", length = 10)
    private String gsmNumber;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public User(String tckn, String firstName, String lastName, String gsmNumber, Date dateOfBirth, Role role) {
        this.tckn = tckn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gsmNumber = gsmNumber;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

}
