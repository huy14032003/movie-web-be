package com.example.movie_web_be.service.impl;

import com.example.movie_web_be.dto.request.GenreRequest;
import com.example.movie_web_be.dto.response.GenreResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.entity.Genre;
import com.example.movie_web_be.exception.DuplicateResourceException;
import com.example.movie_web_be.exception.ResourceNotFoundException;
import com.example.movie_web_be.repository.GenreRepository;
import com.example.movie_web_be.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreResponse create(GenreRequest request) {
        if (genreRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Thể loại", "name", request.getName());
        }

        Genre genre = Genre.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .build();

        return toResponse(genreRepository.save(genre));
    }

    @Override
    public GenreResponse update(Integer id, GenreRequest request) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Thể loại", "id", id));

        if (!genre.getName().equals(request.getName()) && genreRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Thể loại", "name", request.getName());
        }

        genre.setName(request.getName());
        genre.setSlug(request.getSlug());
        return toResponse(genreRepository.save(genre));
    }

    @Override
    public void delete(Integer id) {
        if (!genreRepository.existsById(id)) {
            throw new ResourceNotFoundException("Thể loại", "id", id);
        }
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public GenreResponse getById(Integer id) {
        return genreRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Thể loại", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreResponse> getAll() {
        return genreRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<GenreResponse> getAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Genre> genrePage = genreRepository.findAll(pageable);
        return toPageResponse(genrePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<GenreResponse> search(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Genre> genrePage = genreRepository.search(name, pageable);
        return toPageResponse(genrePage);
    }

    private GenreResponse toResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .slug(genre.getSlug())
                .build();
    }

    private PageResponse<GenreResponse> toPageResponse(Page<Genre> page) {
        return PageResponse.<GenreResponse>builder()
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
