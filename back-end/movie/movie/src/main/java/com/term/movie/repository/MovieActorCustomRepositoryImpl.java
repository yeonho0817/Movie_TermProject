package com.term.movie.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.term.movie.domain.QActor;
import com.term.movie.domain.QMovie;
import com.term.movie.domain.QMovieActor;
import com.term.movie.dto.ActorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieActorCustomRepositoryImpl implements MovieActorCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ActorDTO> readMovieActor(Long movieId) {
        QMovie qMovie = QMovie.movie;
        QMovieActor qMovieActor = QMovieActor.movieActor;
        QActor qActor = QActor.actor;

        return jpaQueryFactory.select(
                        Projections.fields(ActorDTO.class,
                                qActor.name)
                )
                .from(qMovieActor)
                .leftJoin(qMovieActor.movie(), qMovie)
                .on(qMovieActor.movie().id.eq(qMovie.id))

                .leftJoin(qMovieActor.actor(), qActor)
                .on(qMovieActor.actor().id.eq(qActor.id))

                .where(qMovie.id.eq(movieId))
                .fetch();
    }
}
