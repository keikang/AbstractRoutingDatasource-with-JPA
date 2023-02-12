package com.example.abstractroutingdatasource.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_member")
public class Member {

    @Id
    String id;
    String addr;

    String userName;
}
