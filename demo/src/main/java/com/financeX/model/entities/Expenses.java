package com.financeX.model.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.financeX.model.entities.interfaces.FinancialRecord;

public class Expenses implements FinancialRecord {
   private Integer id;
   private String description;
   private BigDecimal value;
   private Date date;
   private Integer id_month;
   private Integer id_category;
   private Integer id_user;

   public Expenses() {

   }

   public Expenses(Integer id, String description, BigDecimal value, Date date, Integer id_month, Integer id_category,
         Integer id_user) {
      this.id = id;
      this.description = description;
      this.value = value;
      this.date = date;
      this.id_month = id_month;
      this.id_category = id_category;
      this.id_user = id_user;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public BigDecimal getValue() {
      return value;
   }

   public void setValue(BigDecimal value) {
      this.value = value;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public Integer getId_month() {
      return id_month;
   }

   public void setId_month(Integer id_month) {
      this.id_month = id_month;
   }

   public Integer getId_category() {
      return id_category;
   }

   public void setId_category(Integer id_category) {
      this.id_category = id_category;
   }

   public Integer getId_user() {
      return id_user;
   }

   public void setId_user(Integer id_user) {
      this.id_user = id_user;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Expenses other = (Expenses) obj;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      return true;
   }

}
