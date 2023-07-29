package com.swapnilxi.springbatch.repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swapnilxi.springbatch.Entity.Actor;

import java.util.List;
@Repository
public interface ActorRepository extends JpaRepository<Actor, String> {
    List<Actor> findByPrimaryname(String primaryname);
    @Query("SELECT SUBSTRING(a.primaryname, 1, 200) FROM Actor a")
    List<String> findActorNames();

    List<Actor> findByPrimarynameContainingIgnoreCase(String name);
}
