package com.example.abstractroutingdatasource.controller;

import com.example.abstractroutingdatasource.BoardService;
import com.example.abstractroutingdatasource.ClientDatabaseContextHolder;
import com.example.abstractroutingdatasource.ClientDatasource;
import com.example.abstractroutingdatasource.config.ClientDatabase;
import com.example.abstractroutingdatasource.entity.Member;
import com.example.abstractroutingdatasource.repository.agens.MemberAgensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MainController {

    private final BoardService boardService;

    private final MemberAgensRepository memberAgensRepository;

    //private final MemberMysqlRepository memberMysqlRepository;

    @GetMapping("/datasource/{dbName}")
    public ResponseEntity<?> getData(@PathVariable String dbName) throws SQLException {
        Object result = null;
        DataSource dataSource = null;
        if("agens".equals(dbName)){
            //System.out.println("MainController getData dbName : "+dbName);
            dataSource = ClientDatasource.getDatasource(ClientDatabase.AGENS);
            //System.out.println("MainController getData dataSource : "+dataSource.getConnection().getClientInfo().keys());
            result = boardService.getData(dataSource);

        }else{
            dataSource = ClientDatasource.getDatasource(ClientDatabase.MYSQL);
            //System.out.println("MainController getData dataSource : "+dataSource.getConnection().toString());
            result = boardService.getData(dataSource);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/jpa/{dbName}")
    public ResponseEntity<?> creatMember(@PathVariable String dbName) throws SQLException {

        Member member = new Member();
        member.setId("bitnine");
        member.setUserName("비트나인");
        member.setAddr("서울특별시 강남구 테헤란로 516 정헌빌딩 4층");

        if("agens".equals(dbName)){
            //System.out.println("MainController getData dbName : "+dbName);
            ClientDatabaseContextHolder.set(ClientDatabase.AGENS);


        }/*else{
            ClientDatabaseContextHolder.set(ClientDatabase.MYSQL);
            return ResponseEntity.ok(memberMysqlRepository.save(member));
        }*/

        return ResponseEntity.ok(memberAgensRepository.save(member));
    }
}
