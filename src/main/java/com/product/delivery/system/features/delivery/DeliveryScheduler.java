package com.product.delivery.system.features.delivery;
import com.product.delivery.system.models.Product;
import com.product.delivery.system.models.Vehicle;
import com.product.delivery.system.services.ProductService;
import com.product.delivery.system.services.VehicleService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
public class DeliveryScheduler
{

    public void scheduleDeliveries(ProductService productService, VehicleService vehicleService)
    {
        List<Product> remainingProducts = new ArrayList<>(productService.getProducts());
        PriorityQueue<Vehicle> vehicleQueue = new PriorityQueue<>(Comparator.comparing(Vehicle::getAvailableAt));
        vehicleQueue.addAll(vehicleService.getVehicles());
        if (vehicleQueue.isEmpty()) return;


        int tripNo=0;
        BigDecimal tripStart=null;
        List<Product> deliveries=null;
        while(!remainingProducts.isEmpty())
        {
            tripNo++;
            Vehicle vehicle = vehicleQueue.poll();
            if(vehicle == null) break;
            tripStart=vehicle.getAvailableAt();
            deliveries = getBestProducts(remainingProducts,vehicle.getPayloadCapacity());
            if (deliveries.isEmpty())
            {
                Product fallback = null;
                for (Product p : remainingProducts)
                {
                    if (p.getWeight().compareTo(vehicle.getPayloadCapacity()) <= 0)
                    {
                        if (fallback == null || p.getWeight().compareTo(fallback.getWeight()) > 0) fallback = p;
                    }
                }
                if (fallback != null) deliveries.add(fallback);
                else
                {
                    vehicleQueue.offer(vehicle); break;
                }
            }

            BigDecimal farthestDistance = BigDecimal.ZERO;
            for(Product p : deliveries) if(p.getDistance().compareTo(farthestDistance)>0)farthestDistance=p.getDistance();
            BigDecimal speed= vehicle.calculateSpeed();
            //System.out.println("Trip items: "+deliveries.size()+" Farthest Distance: "+farthestDistance.toPlainString()+" Speed: "+speed.toPlainString());
            for (Product p : deliveries)
            {
               BigDecimal travelTimePrecise = p.getDistance().divide(speed,2 ,RoundingMode.DOWN);
               BigDecimal deliveryTimePrecise = tripStart.add(travelTimePrecise);
               BigDecimal deliveryTimeDisplay = deliveryTimePrecise.setScale(2, RoundingMode.DOWN);
               p.setDeliveryTimePrecise(deliveryTimePrecise);
               p.setDeliveryTimeDisplay(deliveryTimeDisplay);
               p.setScheduled(true);
//                //System.out.println("  Product="+p.getPackageLabel()+" dist=" +p.getDistance().toPlainString()
//                        +" travelPrecise="+travelTimePrecise.toPlainString()
//                        +" deliveryPrecise="+deliveryTimePrecise.toPlainString()
//                        +" deliveryDisplay="+deliveryTimeDisplay.toPlainString());
            }

            BigDecimal roundTripPrecise = farthestDistance.multiply(BigDecimal.valueOf(2)).divide(speed, 2, RoundingMode.DOWN);
            BigDecimal newAvailableAtPrecise = tripStart.add(roundTripPrecise);
            vehicle.setAvailableAt(newAvailableAtPrecise);

            vehicleQueue.offer(vehicle);
            remainingProducts.removeAll(deliveries);
        }
    }
    public  List<Product> getBestProducts(List<Product> remainingProducts, BigDecimal payloadCapacity)
    {
        // scaleFactor: multiply kg by 100 to avoid fractional kg issues (adjust as needed)
        // 1 unit = 0.01 kg (10 grams)
        final int scaleFactor = 100;
        final int DP_CAP_LIMIT = 20000;
        int n = remainingProducts.size();
        if (n == 0) return new ArrayList<>();
        BigDecimal capScaledBD = payloadCapacity.multiply(BigDecimal.valueOf(scaleFactor)).setScale(0,RoundingMode.DOWN);
        int capScaled;
        try
        {
            capScaled = capScaledBD.intValueExact();
        }catch (ArithmeticException e)
        {
            capScaled = DP_CAP_LIMIT+1;
        }
        if (capScaled<=0) return new ArrayList<>();
        int []w = new int[n];
        BigDecimal ws = null;
        for(int i = 0; i<n; i++)
        {
            ws = remainingProducts.get(i).getWeight().multiply(BigDecimal.valueOf(scaleFactor)).setScale(0,RoundingMode.DOWN);
            int wi = ws.intValue();
            w[i] = Math.max(0,wi);
        }

        if (capScaled > DP_CAP_LIMIT)
        {
            List<Integer> idx = new ArrayList<>();
            for (int i = 0; i < n; ++i) idx.add(i);
            idx.sort(Comparator.comparingInt(a -> w[a])); // ascending weight
            List<Product> chosen = new ArrayList<>();
            int used = 0;
            for (int id : idx) {
                if (used+w[id]<=capScaled) {
                    chosen.add(remainingProducts.get(id));
                    used+=w[id];
                }
            }
            return chosen;
        }

        int [][] dpCount =new int[n+1][capScaled+1];
        int [][] dpWeight =new int[n+1][capScaled+1];
        boolean [][] take =new boolean[n+1][capScaled+1];
        for (int i =1; i<=n; i++)
        {
            int wi = w[i-1];
            for (int j=0; j<=capScaled;j++)
            {
                int notCount = dpCount[i-1][j];
                int notWeight = dpWeight[i-1][j];
                int takeCount = -1, takeWeight=-1;
                if (wi<=j)
                {
                    takeCount = dpCount[i-1][j-wi]+1;
                    takeWeight = dpWeight[i-1][j-wi]+wi;
                }
                if (takeCount>notCount || (takeCount==notCount && takeWeight>notWeight))
                {
                    dpCount[i][j]= takeCount;
                    dpWeight[i][j]= takeWeight;
                    take[i][j] =true;
                } else {
                    dpCount[i][j] = notCount;
                    dpWeight[i][j] = notWeight;
                    take[i][j] = false;
                }
            }
        }

        List<Product> chosen = new ArrayList<>();
        int j = capScaled;
        for (int i=n; i>=1; i--)
        {
            if (take[i][j]) {
                chosen.add(remainingProducts.get(i-1));
                j-= w[i-1];
            }
        }
        return chosen;
    }

}
