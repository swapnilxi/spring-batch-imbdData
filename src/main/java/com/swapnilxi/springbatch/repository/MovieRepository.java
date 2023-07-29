package com.swapnilxi.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swapnilxi.springbatch.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query("SELECT m.primarytitle FROM Movie m JOIN Relation r ON m.tconst = r.tconst JOIN Actor a ON r.nconst = a.nconst WHERE a.primaryname = :primaryname")
    List<String> findMovieNamesByActorName(@Param("primaryname") String primaryname);

}
