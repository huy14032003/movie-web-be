package com.example.movie_web_be.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(unique = true, length = 100)
    private String slug;

    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Movie> movies = new HashSet<>();
}
