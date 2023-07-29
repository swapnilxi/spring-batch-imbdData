package com.swapnilxi.springbatch.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swapnilxi.springbatch.repository.MovieRepository;
import com.swapnilxi.springbatch.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public List<String> findMovieNamesByActorName(String primaryname) {
        return movieRepository.findMovieNamesByActorName(primaryname);
    }
}
