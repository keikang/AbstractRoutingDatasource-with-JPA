package com.example.abstractroutingdatasource.repository.mysql;

import com.example.abstractroutingdatasource.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberMysqlRepository extends JpaRepository<Member, String> {
}
