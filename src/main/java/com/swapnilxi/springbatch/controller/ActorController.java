package com.swapnilxi.springbatch.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swapnilxi.springbatch.entity.Actor;
import com.swapnilxi.springbatch.repository.ActorRepository;
import com.swapnilxi.springbatch.service.ActorService;

import java.util.List;



@RestController
@RequestMapping("/actors")
class ActorController {
    @Autowired
    ActorService actorService;
    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/")
    public  String getActor(){
        return "actors";
    }
    @GetMapping("/name/{name}")
    public List<Actor> getActorsByName(@PathVariable String name) {
        return actorService.getActorByName(name);
    }

    @GetMapping("/search")
    public List<Actor> searchActorsByName(@RequestParam("name") String name) {
        return  actorService.searchActorsByName(name);

    }


    @GetMapping("/all")
    public ResponseEntity<Page<Actor>> getAllActors(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 100);
        Page<Actor> actorsPage = actorService.getAllActors(pageable);
        return ResponseEntity.ok(actorsPage);
    }

}
