package ru.itk.stream.generatingNumbers;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Order {
  private String product;
  private double cost;

  public Order(String product, double cost) {
    this.product = product;
    this.cost = cost;
  }

  public String getProduct() {
    return product;
  }

  public double getCost() {
    return cost;
  }
}

public class StreamCollectorsExample {
  public static void main(String[] args) {
    List<Order> orders = List.of(
      new Order("Laptop", 1200.0),
      new Order("Smartphone", 800.0),
      new Order("Laptop", 1500.0),
      new Order("Tablet", 500.0),
      new Order("Smartphone", 900.0)
    );

    Map<String, Double> totalCostByProduct = orders.stream()
      .collect(Collectors.groupingBy(
        Order::getProduct,
        Collectors.summingDouble(Order::getCost)
      ));

    List<Map.Entry<String, Double>> expensiveProducts = totalCostByProduct.entrySet().stream()
      .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
      .limit(3)
      .toList();

    System.out.println("Три самых дорогих продукта:");
    expensiveProducts.forEach(entry ->
      System.out.printf("%s - Общая стоимость: %s%n", entry.getKey(), entry.getValue())
    );

  }
}
