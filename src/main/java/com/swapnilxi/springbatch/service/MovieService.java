package com.swapnilxi.springbatch.service;


import java.util.List;

public interface MovieService {
    List<String> findMovieNamesByActorName(String primaryname);
}
