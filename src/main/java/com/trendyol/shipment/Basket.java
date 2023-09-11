package com.trendyol.shipment;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Basket {

    private List<Product> products;

    public ShipmentSize getShipmentSize() {

        int REPEAT_THRESHOLD = 3;

        Map<ShipmentSize, Integer> repeatCount = getMap();

        ShipmentSize[] all_sizes = ShipmentSize.values();
        ShipmentSize maxRepeatedSize = all_sizes[0];
        int maxRepeat = 0;

        for (Map.Entry<ShipmentSize, Integer> entry : repeatCount.entrySet()) {
            ShipmentSize size = entry.getKey();
            int repeat = entry.getValue();

            if (repeat > maxRepeat || repeat == maxRepeat && size.ordinal() > maxRepeatedSize.ordinal()) {
                maxRepeatedSize = size;
                maxRepeat = repeat;
            }
        }

        if (maxRepeat < REPEAT_THRESHOLD) {
            ShipmentSize basketSize = all_sizes[0];

            for (Product product : products) {
                if (product.getSize().ordinal() > basketSize.ordinal()) {
                    basketSize = product.getSize();
                }
            }
            return basketSize;
        }

        else {
            ShipmentSize maxPossibleSize = all_sizes[all_sizes.length - 1];
            return maxRepeatedSize == maxPossibleSize ?
                    maxPossibleSize:
                    all_sizes[(maxRepeatedSize.ordinal() + 1)];


        }
    }

    private Map<ShipmentSize, Integer> getMap() {
        Map<ShipmentSize, Integer> repeatCount = new HashMap<>();

        for (Product product : products) {
            ShipmentSize size = product.getSize();
            if (repeatCount.containsKey(size)) {
                repeatCount.put(product.getSize(), repeatCount.get(size) + 1);
            }
            else {
                repeatCount.put(size, 1);
            }
        }
        return repeatCount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
