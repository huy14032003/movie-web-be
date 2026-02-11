package com.example.movie_web_be.service.impl;

import com.example.movie_web_be.dto.request.CountryRequest;
import com.example.movie_web_be.dto.response.CountryResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.entity.Country;
import com.example.movie_web_be.exception.DuplicateResourceException;
import com.example.movie_web_be.exception.ResourceNotFoundException;
import com.example.movie_web_be.repository.CountryRepository;
import com.example.movie_web_be.service.CountryService;
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
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public CountryResponse create(CountryRequest request) {
        if (countryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Quốc gia", "name", request.getName());
        }

        Country country = Country.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .build();

        return toResponse(countryRepository.save(country));
    }

    @Override
    public CountryResponse update(Integer id, CountryRequest request) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quốc gia", "id", id));

        if (!country.getName().equals(request.getName()) && countryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Quốc gia", "name", request.getName());
        }

        country.setName(request.getName());
        country.setSlug(request.getSlug());
        return toResponse(countryRepository.save(country));
    }

    @Override
    public void delete(Integer id) {
        if (!countryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Quốc gia", "id", id);
        }
        countryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CountryResponse getById(Integer id) {
        return countryRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Quốc gia", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryResponse> getAll() {
        return countryRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CountryResponse> getAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Country> countryPage = countryRepository.findAll(pageable);
        return toPageResponse(countryPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CountryResponse> search(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Country> countryPage = countryRepository.search(name, pageable);
        return toPageResponse(countryPage);
    }

    private CountryResponse toResponse(Country country) {
        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .slug(country.getSlug())
                .build();
    }

    private PageResponse<CountryResponse> toPageResponse(Page<Country> page) {
        return PageResponse.<CountryResponse>builder()
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
