package com.swapnilxi.springbatch.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.swapnilxi.springbatch.entity.Actor;

@Service
public interface ActorService {
    public List<Actor> getActorByName(String name);
    Page<Actor> getAllActors(Pageable pageable);
    public List<Actor> getActors();

    List<Actor> searchActorsByName(String name);
}
