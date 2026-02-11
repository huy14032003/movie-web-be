package com.example.movie_web_be.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer year;

    @Column(precision = 3, scale = 1)
    private BigDecimal rating;

    @Column(length = 50)
    private String duration;

    @Column(length = 1000)
    private String poster;

    @Column(length = 1000)
    private String backdrop;

    private String director;

    @Column(length = 500)
    private String trailer;

    @Builder.Default
    private Boolean featured = false;

    @Column(name = "is_series")
    @Builder.Default
    private Boolean isSeries = false;

    @Column(name = "total_episodes")
    @Builder.Default
    private Integer totalEpisodes = 1;

    @Column(unique = true)
    private String slug;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @Builder.Default
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_cast", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    @Builder.Default
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_countries", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "country_id"))
    @Builder.Default
    private Set<Country> countries = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Episode> episodes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
