package com.example.abstractroutingdatasource.repository;

import com.example.abstractroutingdatasource.Board;
import com.example.abstractroutingdatasource.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
}
