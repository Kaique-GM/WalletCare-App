package com.financeX.model.entities;

import java.math.BigDecimal;

public class Month {
    private Integer id;
    private String monthName;
    private Integer year;
    private Integer user_id;
    private BigDecimal totalBalance;


    public Month(){

    }


    public Month(Integer id, String monthName, Integer year, Integer user_id, BigDecimal totalBalance) {
        this.id = id;
        this.monthName = monthName;
        this.year = year;
        this.user_id = user_id;
        this.totalBalance = totalBalance;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getMonthName() {
        return monthName;
    }


    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }


    public Integer getYear() {
        return year;
    }


    public void setYear(Integer year) {
        this.year = year;
    }


    public Integer getUser_id() {
        return user_id;
    }


    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public BigDecimal getTotalBalance() {
        return totalBalance;
    }


    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }


}
