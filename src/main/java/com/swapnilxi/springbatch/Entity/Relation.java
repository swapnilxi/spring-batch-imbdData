package com.swapnilxi.springbatch.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="relation")
public class Relation {
    @Id
    private String tconst;
    private String nconst;
}
