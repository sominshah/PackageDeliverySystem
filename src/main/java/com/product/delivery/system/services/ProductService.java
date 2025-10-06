package com.product.delivery.system.services;
import com.product.delivery.system.features.pricing.DeliveryCostCalculator;
import com.product.delivery.system.models.Product;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class ProductService
{
    private final List<Product> products;
    public ProductService()
    {
        this.products = new LinkedList<>();
    }
    public void add(Product product)
    {
        this.products.add(product);
    }
    public List<Product> getProducts()
    {
        return new ArrayList<>(this.products);
    }
    public void printProducts(DeliveryCostCalculator deliveryCostCalculator)
    {
        for(Product product: products)
        {
            deliveryCostCalculator.calculate(product);
            System.out.println(product.getPackageLabel()+" "+product.getDiscount()+" "+product.getTotalDeliveryCost());
        }
    }

    public void printProductsWithEstimatedTime(DeliveryCostCalculator deliveryCostCalculator)
    {
        for(Product product: products)
        {
            deliveryCostCalculator.calculate(product);
            System.out.println(product.getPackageLabel()+" "+product.getDiscount()+" "+product.getTotalDeliveryCost()+" "+product.getDeliveryTimeDisplay());
        }
    }

}
