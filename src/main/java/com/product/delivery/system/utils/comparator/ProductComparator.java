package com.product.delivery.system.utils.comparator;
import com.product.delivery.system.models.Product;
import java.util.Comparator;
public class ProductComparator implements Comparator<Product>
{
    @Override
    public int compare(Product p1, Product p2)
    {
        int weightCompare = p2.getWeight().compareTo(p1.getWeight());
        if (weightCompare != 0)
        {
            return weightCompare;
        }

        int distanceCompare = p2.getDistance().compareTo(p1.getDistance());
        if (distanceCompare != 0)
        {
            return distanceCompare;
        }
        return p1.getId().compareTo(p2.getId());
    }
}
