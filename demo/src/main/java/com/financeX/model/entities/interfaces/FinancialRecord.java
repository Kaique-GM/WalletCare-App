package com.financeX.model.entities.interfaces;

import java.math.BigDecimal;
import java.util.Date;

public interface FinancialRecord {
    Integer getId();

    String getDescription();

    BigDecimal getValue();

    Date getDate();
    
}
