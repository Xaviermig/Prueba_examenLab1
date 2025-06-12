package com.tecnocampus.examsimulation.persistence;

import com.tecnocampus.examsimulation.domain.Kingdom;
import com.tecnocampus.examsimulation.utilities.NotFoundException;
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


    public Kingdom getKingdomById(String id) {
        String sql = "SELECT * FROM KINGDOMS WHERE kingdom_id = :kingdomId";

        return jdbcClient.sql(sql)
                .param("kingdomId", id)
                .query((rs,rowNum) -> new Kingdom(rs))
                .optional()
                .orElseThrow(() -> new RuntimeException("Kingdom with ID " + id + " not found"));
    }

    public void updateKingdom(Kingdom kingdom) {
        int rows = jdbcClient
                .sql("""
                     UPDATE KINGDOMS
                         SET gold = :gold,
                             citizens = :citizens,
                             food = :food
                     WHERE kingdom_id = :kingdomId
                """)
                .param("kingdomId", kingdom.getKingdomId())
                .param("gold", kingdom.getGold())
                .param("citizens", kingdom.getPopulation())
                .param("food", kingdom.getFood())
                .update();
        if (rows == 0) {
            throw new NotFoundException("Kingdom with ID " + kingdom.getKingdomId() + " not found");
        }
    }

    public void removeKingdomById(String id) {
        int rows = jdbcClient.sql("DELETE FROM KINGDOMS WHERE kingdom_id = :kingdomId")
                .param("kingdomId", id)
                .update();
        if(rows == 0){
            throw new NotFoundException("Customer with id " + id + " not found");
        }
    }
}
