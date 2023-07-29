package com.swapnilxi.springbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "actor")
public class Actor {
    @Id
    private String nconst;
    private String primaryname;
    private String birthyear;
    private String deathyear;
    private String primaryprofession;
    private String knownfortitles;
}
