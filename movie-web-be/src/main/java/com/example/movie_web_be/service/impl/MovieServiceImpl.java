package com.example.movie_web_be.service.impl;

import com.example.movie_web_be.dto.request.MovieRequest;
import com.example.movie_web_be.dto.response.ActorResponse;
import com.example.movie_web_be.dto.response.EpisodeResponse;
import com.example.movie_web_be.dto.response.GenreResponse;
import com.example.movie_web_be.dto.response.MovieResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.entity.Actor;
import com.example.movie_web_be.entity.Genre;
import com.example.movie_web_be.entity.Movie;
import com.example.movie_web_be.exception.DuplicateResourceException;
import com.example.movie_web_be.exception.ResourceNotFoundException;
import com.example.movie_web_be.repository.ActorRepository;
import com.example.movie_web_be.repository.GenreRepository;
import com.example.movie_web_be.repository.MovieRepository;
import com.example.movie_web_be.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    @Override
    public MovieResponse create(MovieRequest request) {
        if (request.getSlug() != null && movieRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Phim", "slug", request.getSlug());
        }

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .year(request.getYear())
                .rating(request.getRating())
                .duration(request.getDuration())
                .poster(request.getPoster())
                .backdrop(request.getBackdrop())
                .director(request.getDirector())
                .trailer(request.getTrailer())
                .featured(request.getFeatured() != null ? request.getFeatured() : false)
                .isSeries(request.getIsSeries() != null ? request.getIsSeries() : false)
                .totalEpisodes(request.getTotalEpisodes() != null ? request.getTotalEpisodes() : 1)
                .slug(request.getSlug())
                .genres(getGenresByIds(request.getGenreIds()))
                .actors(getActorsByIds(request.getActorIds()))
                .build();

        return toResponse(movieRepository.save(movie));
    }

    @Override
    public MovieResponse update(Integer id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "id", id));

        if (request.getSlug() != null && !request.getSlug().equals(movie.getSlug())
                && movieRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Phim", "slug", request.getSlug());
        }

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setYear(request.getYear());
        movie.setRating(request.getRating());
        movie.setDuration(request.getDuration());
        movie.setPoster(request.getPoster());
        movie.setBackdrop(request.getBackdrop());
        movie.setDirector(request.getDirector());
        movie.setTrailer(request.getTrailer());
        movie.setFeatured(request.getFeatured());
        movie.setIsSeries(request.getIsSeries());
        movie.setTotalEpisodes(request.getTotalEpisodes());
        movie.setSlug(request.getSlug());
        movie.setGenres(getGenresByIds(request.getGenreIds()));
        movie.setActors(getActorsByIds(request.getActorIds()));

        return toResponse(movieRepository.save(movie));
    }

    @Override
    public void delete(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Phim", "id", id);
        }
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getById(Integer id) {
        return movieRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getBySlug(String slug) {
        return movieRepository.findBySlug(slug)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "slug", slug));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        return toPageResponse(moviePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> search(String title, Integer year, String director, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.search(title, year, director, pageable);
        return toPageResponse(moviePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getFeatured(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.findByFeaturedTrue(pageable);
        return toPageResponse(moviePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getByGenre(Integer genreId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.findByGenreId(genreId, pageable);
        return toPageResponse(moviePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<MovieResponse> getByActor(Integer actorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> moviePage = movieRepository.findByActorId(actorId, pageable);
        return toPageResponse(moviePage);
    }

    private Set<Genre> getGenresByIds(Set<Integer> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }

    private Set<Actor> getActorsByIds(Set<Integer> actorIds) {
        if (actorIds == null || actorIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(actorRepository.findAllById(actorIds));
    }

    private MovieResponse toResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .year(movie.getYear())
                .rating(movie.getRating())
                .duration(movie.getDuration())
                .poster(movie.getPoster())
                .backdrop(movie.getBackdrop())
                .director(movie.getDirector())
                .trailer(movie.getTrailer())
                .featured(movie.getFeatured())
                .isSeries(movie.getIsSeries())
                .totalEpisodes(movie.getTotalEpisodes())
                .slug(movie.getSlug())
                .createdAt(movie.getCreatedAt())
                .genres(movie.getGenres().stream()
                        .map(g -> GenreResponse.builder().id(g.getId()).name(g.getName()).build())
                        .collect(Collectors.toSet()))
                .actors(movie.getActors().stream()
                        .map(a -> ActorResponse.builder().id(a.getId()).name(a.getName()).build())
                        .collect(Collectors.toSet()))
                .episodes(movie.getEpisodes().stream()
                        .map(e -> EpisodeResponse.builder()
                                .id(e.getId())
                                .movieId(movie.getId())
                                .title(e.getTitle())
                                .episodeNumber(e.getEpisodeNumber())
                                .videoUrl(e.getVideoUrl())
                                .serverName(e.getServerName())
                                .quality(e.getQuality())
                                .createdAt(e.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private PageResponse<MovieResponse> toPageResponse(Page<Movie> page) {
        return PageResponse.<MovieResponse>builder()
                .content(page.getContent().stream().map(this::toResponse).collect(Collectors.toList()))
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
