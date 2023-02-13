package com.example.abstractroutingdatasource;

import com.example.abstractroutingdatasource.config.ClientDatabase;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ClientDataSourceRouter extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("ClientDataSourceRouter determineCurrentLookupKey 호출");
        return ClientDatabaseContextHolder.getClientDatabase();
    }

    public void initDatasource(DataSource agens,
                               DataSource mysql){
        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put(ClientDatabase.AGENS, agens);
        targetDataSources.put(ClientDatabase.MYSQL, mysql);
        this.setTargetDataSources(targetDataSources);
        this.setDefaultTargetDataSource(agens);
    }
}
