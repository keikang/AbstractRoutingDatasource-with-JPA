package com.example.abstractroutingdatasource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tb_board")
public class Board {

    @Id
    Integer id;
    String title;

    String content;
}
