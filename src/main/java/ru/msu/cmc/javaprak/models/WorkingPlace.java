package ru.msu.cmc.javaprak.models;

import lombok.*;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "working_places")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class WorkingPlace implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "place_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "person_id")
    @NonNull
    private Person person;

    @Column(nullable = false, name = "company_name")
    @NonNull
    private String company_name;

    @Column(nullable = false, name = "startofperiod")
    @NonNull
    private Date start;

    @Column(nullable = false, name = "endofperiod")
    @NonNull
    private Date end;

    @NonNull
    @Column(nullable = false, name = "position")
    private String position;
}