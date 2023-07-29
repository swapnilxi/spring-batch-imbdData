package com.swapnilxi.springbatch.config;



    
import org.springframework.batch.item.ItemProcessor;

import com.swapnilxi.springbatch.entity.Movie;

public class MovieItemProcessor implements ItemProcessor<Movie, Movie> {
    @Override
    public Movie process(Movie movie) throws Exception {
        return movie;
    }
}