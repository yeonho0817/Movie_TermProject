package com.term.movie.repository;

import com.term.movie.dto.ActorDTO;

import java.util.List;

public interface MovieActorCustomRepository {

    public List<ActorDTO> readMovieActor(Long movieId);
}
