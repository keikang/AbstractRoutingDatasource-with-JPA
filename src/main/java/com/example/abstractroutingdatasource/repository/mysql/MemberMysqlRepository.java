package com.example.abstractroutingdatasource.repository.mysql;

import com.example.abstractroutingdatasource.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;

@Repository
@PersistenceContext(unitName = "mysqlEntityManager")
public interface MemberMysqlRepository extends JpaRepository<Member, String> {
}
