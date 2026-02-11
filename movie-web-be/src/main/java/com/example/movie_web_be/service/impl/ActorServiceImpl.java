package com.example.movie_web_be.service.impl;

import com.example.movie_web_be.dto.request.ActorRequest;
import com.example.movie_web_be.dto.response.ActorResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.entity.Actor;
import com.example.movie_web_be.exception.DuplicateResourceException;
import com.example.movie_web_be.exception.ResourceNotFoundException;
import com.example.movie_web_be.repository.ActorRepository;
import com.example.movie_web_be.service.ActorService;
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
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Override
    public ActorResponse create(ActorRequest request) {
        if (actorRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Diễn viên", "name", request.getName());
        }

        Actor actor = Actor.builder()
                .name(request.getName())
                .build();

        return toResponse(actorRepository.save(actor));
    }

    @Override
    public ActorResponse update(Integer id, ActorRequest request) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diễn viên", "id", id));

        if (!actor.getName().equals(request.getName()) && actorRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Diễn viên", "name", request.getName());
        }

        actor.setName(request.getName());
        return toResponse(actorRepository.save(actor));
    }

    @Override
    public void delete(Integer id) {
        if (!actorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Diễn viên", "id", id);
        }
        actorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ActorResponse getById(Integer id) {
        return actorRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Diễn viên", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActorResponse> getAll() {
        return actorRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ActorResponse> getAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actor> actorPage = actorRepository.findAll(pageable);
        return toPageResponse(actorPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ActorResponse> search(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actor> actorPage = actorRepository.search(name, pageable);
        return toPageResponse(actorPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ActorResponse> getByCountry(Integer countryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actor> actorPage = actorRepository.findByCountryId(countryId, pageable);
        return toPageResponse(actorPage);
    }

    private ActorResponse toResponse(Actor actor) {
        return ActorResponse.builder()
                .id(actor.getId())
                .name(actor.getName())
                .build();
    }

    private PageResponse<ActorResponse> toPageResponse(Page<Actor> page) {
        return PageResponse.<ActorResponse>builder()
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
