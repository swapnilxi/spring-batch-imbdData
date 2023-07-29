package com.swapnilxi.springbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    private String tconst;
    private String titletype;
    private String primarytitle;
    private String originaltitle;
    private String issdult;
    private String startyear;
    private String endyear;
    private String runtimeminutes;
    private String genres;
    }
