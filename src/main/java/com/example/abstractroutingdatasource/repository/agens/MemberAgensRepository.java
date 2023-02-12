package com.example.abstractroutingdatasource.repository.agens;

import com.example.abstractroutingdatasource.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAgensRepository extends JpaRepository<Member, String> {
}
