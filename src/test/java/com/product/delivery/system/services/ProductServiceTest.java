package com.product.delivery.system.services;

import com.product.delivery.system.models.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceTest
{
    private ProductService productService;
    @Before
    public void setUp()
    {
        productService = new ProductService();
    }
    @Test
    public void testAddAndGetProduct()
    {
        Product product = new Product();
        product.setPackageLabel("PKG1");
        product.setWeight(BigDecimal.valueOf(50));
        product.setDistance(BigDecimal.valueOf(30));
        productService.add(product);
        List<Product> products = productService.getProducts();
        Assert.assertEquals(1,products.size());
        Assert.assertEquals(product,products.get(0));
        Assert.assertEquals("PKG1", products.get(0).getPackageLabel());
        Assert.assertEquals(BigDecimal.valueOf(50), products.get(0).getWeight());
        Assert.assertEquals(BigDecimal.valueOf(30), products.get(0).getDistance());
    }
    @Test
    public void testGetProductsReturnsCopy()
    {
        Product product = new Product();
        product.setPackageLabel("PKG2");
        productService.add(product);
        List<Product> products = productService.getProducts();
        products.clear();
        Assert.assertEquals(1, productService.getProducts().size());
    }

}
