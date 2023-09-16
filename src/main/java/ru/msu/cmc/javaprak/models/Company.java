package ru.msu.cmc.javaprak.models;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "companies")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Company implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "company_id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(name = "specialization")
    private String spec;

    @Column(name = "phone_num")
    private String phone;

    @Column(name = "email")
    private String email;

}