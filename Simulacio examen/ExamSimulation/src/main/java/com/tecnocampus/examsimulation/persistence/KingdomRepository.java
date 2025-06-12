package com.tecnocampus.examsimulation.persistence;

import com.tecnocampus.examsimulation.domain.Kingdom;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.zip.Checksum;

@Repository
@Profile("db-h2")
public class KingdomRepository {
    private final JdbcClient jdbcClient;

    public KingdomRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void addKingdom ( Kingdom kingdom){
        var params = Map.<String, Object>of(
                "kingdom_id", kingdom.getKingdomId(),
                "gold", kingdom.getGold(),
                "citizens", kingdom.getPopulation(),
                "food", kingdom.getFood(),
                "dateOfCreation", kingdom.getDateOfCreation()
        );
        jdbcClient.sql ("INSERT INTO KINGDOMS (kingdom_id, gold, citizens, food, dateOfCreation) " +
                        "VALUES (:kingdom_id, :gold, :citizens, :food, :dateOfCreation)")
                .params(params)
                .update();
    }


}
