package com.product.delivery.system.features.pricing;

import com.product.delivery.system.models.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class DistanceAndWeightCriteriaTest
{
    private DistanceAndWeightCriteria criteria;

    @Before
    public void setUp()
    {
        // 50 <= distance <= 150, 10 <= weight <= 100
        criteria = new DistanceAndWeightCriteria(
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(150),
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(100)
        );
    }

    @Test
    public void testApplicableWithinRange()
    {
        Product product = new Product();
        product.setDistance(BigDecimal.valueOf(100));
        product.setWeight(BigDecimal.valueOf(50));
        Assert.assertTrue(criteria.isApplicable(product));
    }

    @Test
    public void testNotApplicableBelowMinDistance()
    {
        Product product = new Product();
        product.setDistance(BigDecimal.valueOf(40));
        product.setWeight(BigDecimal.valueOf(50));
        Assert.assertFalse(criteria.isApplicable(product));
    }

    @Test
    public void testNotApplicableAboveMaxDistance()
    {
        Product product = new Product();
        product.setDistance(BigDecimal.valueOf(200));
        product.setWeight(BigDecimal.valueOf(50));
        Assert.assertFalse(criteria.isApplicable(product));
    }

    @Test
    public void testNotApplicableBelowMinWeight()
    {
        Product product = new Product();
        product.setDistance(BigDecimal.valueOf(100));
        product.setWeight(BigDecimal.valueOf(5));
        Assert.assertFalse(criteria.isApplicable(product));
    }

    @Test
    public void testNotApplicableAboveMaxWeight()
    {
        Product product = new Product();
        product.setDistance(BigDecimal.valueOf(100));
        product.setWeight(BigDecimal.valueOf(150));
        Assert.assertFalse(criteria.isApplicable(product));
    }

    @Test
    public void testBoundaryValuesInclusive()
    {
        Product product = new Product();
        product.setDistance(BigDecimal.valueOf(50));
        product.setWeight(BigDecimal.valueOf(10));
        Assert.assertTrue(criteria.isApplicable(product));
        product.setDistance(BigDecimal.valueOf(150));
        product.setWeight(BigDecimal.valueOf(100));
        Assert.assertTrue(criteria.isApplicable(product));
    }

    @Test
    public void testNullProductOrFields()
    {
        Assert.assertFalse(criteria.isApplicable(null));
        Product product = new Product();
        product.setDistance(null);
        product.setWeight(BigDecimal.valueOf(50));
        Assert.assertFalse(criteria.isApplicable(product));
        product.setDistance(BigDecimal.valueOf(100));
        product.setWeight(null);
        Assert.assertFalse(criteria.isApplicable(product));
    }

}
