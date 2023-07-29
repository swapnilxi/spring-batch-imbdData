package com.swapnilxi.springbatch.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.swapnilxi.springbatch.entity.Actor;
import com.swapnilxi.springbatch.repository.ActorRepository;
import com.swapnilxi.springbatch.service.ActorService;




@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Actor> getActorByName(String primaryname) {
        return actorRepository.findByPrimaryname(primaryname);
    }

    @Override
    public Page<Actor> getAllActors(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 10);
        return actorRepository.findAll(pageable);
    }

    @Override
    public List<Actor> getActors() {
        return null;
    }

    @Override
    public List<Actor> searchActorsByName(String name) {
        return actorRepository.findByPrimarynameContainingIgnoreCase(name);
    }


}
