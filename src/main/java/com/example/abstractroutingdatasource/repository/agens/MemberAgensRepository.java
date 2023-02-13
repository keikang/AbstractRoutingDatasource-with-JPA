package com.example.abstractroutingdatasource.repository.agens;

import com.example.abstractroutingdatasource.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@PersistenceContext(unitName = "agensEntityManager")
public interface MemberAgensRepository extends JpaRepository<Member, String> {

}
