package ru.msu.cmc.javaprak.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "people")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Person implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "person_id")
    private Long id;


    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(name = "phone_num")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "education")
    private String edu;

    @Column(name = "experience")
    private Long experience;

    @NonNull
    @Column(nullable = false, name = "status")
    private Boolean status;

    @Column(name = "wanted_position")
    private String wanted_position;

    @Column(name = "wanted_salary")
    private Long wanted_salary;
}