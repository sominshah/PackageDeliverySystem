package com.product.delivery.system.models;
import com.product.delivery.system.interfaces.Rate;
import java.math.BigDecimal;

public class BaseRate implements Rate
{
    BigDecimal baseRate;
    public BaseRate(BigDecimal baseRate)
    {
        this.baseRate = baseRate;
    }
    public BigDecimal getRate()
    {
        return this.baseRate;
    }
}
