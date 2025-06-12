package com.tecnocampus.examsimulation.domain;

import com.tecnocampus.examsimulation.application.dto.KingdomDTO;
import com.tecnocampus.examsimulation.utilities.InvalidDataException;
import com.tecnocampus.examsimulation.utilities.NotAcceptableException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Kingdom {
    private String kingdomId = UUID.randomUUID().toString();
    private Integer gold;
    private Integer citizens;
    private Integer food;
    private LocalDateTime dateOfCreation = LocalDateTime.now();

    public Kingdom (){

    }
    public Kingdom(KingdomDTO kingdomDTO) {
        if (kingdomDTO.gold() < 0 || kingdomDTO.gold() > 60) {
            throw new NotAcceptableException("Gold must be between 0 and 60");
        }
        this.gold = kingdomDTO.gold();
        if (kingdomDTO.citizens() < 0 || kingdomDTO.citizens() > 60) {
            throw new NotAcceptableException("Population must be between 0 and 100");
        }
        this.citizens = kingdomDTO.citizens();
        if (kingdomDTO.food() < 0 || kingdomDTO.food() > 60) {
            throw new NotAcceptableException("Food must be between 0 and 60");
        }
        this.food = kingdomDTO.food();
    }

    public Kingdom (ResultSet rs) throws SQLException {
        kingdomId = rs.getString("kingdom_id");
        gold = rs.getInt("gold");
        citizens = rs.getInt("citizens");
        food = rs.getInt("food");
        dateOfCreation = rs.getTimestamp("created_at").toLocalDateTime();
    }




    public String getKingdomId() {
        return kingdomId;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getPopulation() {
        return citizens;
    }

    public void setPopulation(Integer population) {
        this.citizens = population;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }



    public KingdomDTO toDTO() {
        return new KingdomDTO(kingdomId, gold, citizens, food, dateOfCreation);
    }
}
