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
        dateOfCreation = rs.getTimestamp("dateOfCreation").toLocalDateTime();
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
    public boolean dailyProduction (){
        if (citizens <= 0) return true;
        if (food >= citizens){
            gold += 2 * citizens;
            food -= citizens;
        } else {
            gold += 2 * food;
            citizens -= citizens - food;
            food = 0;
        }
        return citizens == 0;
    }
    public void investInFood(int goldToInvest) {
        if (gold <= goldToInvest) {
            throw new NotAcceptableException("Not enough gold to invest in food");
        }
        food +=  goldToInvest *2;
        gold -= goldToInvest;
    }
    public void investInCitizens(int goldToInvest) {
        if (gold <= goldToInvest) {
            throw new NotAcceptableException("Not enough gold to invest in citizens");
        }
        citizens += goldToInvest;
        gold -= goldToInvest;
    }



    public void updateKingdom(KingdomDTO kingdom) {
        if (kingdom == null) {
            throw new InvalidDataException("Kingdom cannot be null");
        }
        setGold(kingdom.gold());
        setPopulation(kingdom.citizens());
        setFood(kingdom.food());
    }

    public KingdomDTO toDTO() {
        return new KingdomDTO(kingdomId, gold, citizens, food, dateOfCreation);
    }



}
