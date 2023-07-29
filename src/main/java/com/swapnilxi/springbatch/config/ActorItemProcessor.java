package com.swapnilxi.springbatch.config;

import org.springframework.batch.item.ItemProcessor;

import com.swapnilxi.springbatch.entity.Actor;

public class ActorItemProcessor implements ItemProcessor<Actor, Actor>{
    @Override
    public Actor process(Actor actor) throws Exception {
        return actor;
    }
}