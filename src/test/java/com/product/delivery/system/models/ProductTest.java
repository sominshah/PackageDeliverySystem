package com.product.delivery.system.models;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest
{
    @Test
    public void testToString()
    {
        Product p = new Product("PKG1", new BigDecimal("10"), new BigDecimal("20"));
        String str = p.toString();
        Assert.assertTrue(str.contains("PKG1"));
        Assert.assertTrue(str.contains("10"));
        Assert.assertTrue(str.contains("20"));
    }
}
