package ru.msu.cmc.javaprak.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vacancies")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Vacancy implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "vacancy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "company_id")
    @NonNull
    private Company company;

    @Column(nullable = false, name = "position")
    @NonNull
    private String position;

    @Column(nullable = false, name = "salary")
    @NonNull
    private Long salary;

    @Column(nullable = false, name = "education")
    @NonNull
    private String education;

    @NonNull
    @Column(nullable = false, name = "experience")
    private Long experience;

    @Column(name = "other")
    private String other;
}