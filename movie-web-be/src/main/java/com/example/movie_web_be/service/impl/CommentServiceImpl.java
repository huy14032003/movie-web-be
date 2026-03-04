package com.example.movie_web_be.service.impl;

import com.example.movie_web_be.dto.request.CommentRequest;
import com.example.movie_web_be.dto.response.CommentResponse;
import com.example.movie_web_be.dto.response.PageResponse;
import com.example.movie_web_be.entity.Comment;
import com.example.movie_web_be.entity.Movie;
import com.example.movie_web_be.entity.User;
import com.example.movie_web_be.exception.ResourceNotFoundException;
import com.example.movie_web_be.repository.CommentRepository;
import com.example.movie_web_be.repository.MovieRepository;
import com.example.movie_web_be.repository.UserRepository;
import com.example.movie_web_be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    public CommentResponse create(CommentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng", "id", request.getUserId()));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "id", request.getMovieId()));

        Comment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", request.getParentId()));
        }

        Comment comment = Comment.builder()
                .user(user)
                .movie(movie)
                .content(request.getContent())
                .parent(parent)
                .build();

        return toResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CommentResponse> getByMovieId(Integer movieId, int page, int size) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Phim", "id", movieId);
        }

        Page<Comment> pageResult = commentRepository
                .findByMovieIdAndParentIsNullOrderByCreatedAtDesc(movieId, PageRequest.of(page, size));

        return PageResponse.<CommentResponse>builder()
                .content(pageResult.getContent().stream().map(this::toResponse).collect(Collectors.toList()))
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .first(pageResult.isFirst())
                .last(pageResult.isLast())
                .build();
    }

    @Override
    public CommentResponse update(Integer id, String content) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        comment.setContent(content);
        return toResponse(commentRepository.save(comment));
    }

    @Override
    public void delete(Integer id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", "id", id);
        }
        commentRepository.deleteById(id);
    }

    private CommentResponse toResponse(Comment comment) {
        List<CommentResponse> replies = comment.getReplies().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .userAvatar(comment.getUser().getAvatar())
                .movieId(comment.getMovie().getId())
                .content(comment.getContent())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .replies(replies.isEmpty() ? null : replies)
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
