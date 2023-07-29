package com.swapnilxi.springbatch.config;

import org.springframework.batch.item.ItemProcessor;

import com.swapnilxi.springbatch.entity.Relation;
public class RelationItemProcessor implements ItemProcessor<Relation, Relation> {
    @Override
    public Relation process(Relation relation) throws Exception {
        return relation;
    }
}