package com.example.abstractroutingdatasource.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "tb_member")
public class Member {

    @Id
    String id;
    String addr;

    String userName;
}
