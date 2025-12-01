package ru.itk.collections.countOfElements;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection - count of elements
 * Напишите метод, который получает на вход массив элементов и возвращает
 * Map ключи в котором - элементы, а значения - сколько раз встретился этот элемент
 */
public class ElementCounter {
  public static void main(String[] args) {
    ElementCounter counter = new ElementCounter();

    Integer[] array = {1, 2, 22, 3, 4, 22, 1};

    Map<Integer, Integer> result = counter.countElements(array);

    System.out.println(result);
  }

  public <T> Map<T, Integer> countElements(T[] array) {
    HashMap<T, Integer> result = new HashMap<>();

    for (T item : array) {
      result.put(item, result.getOrDefault(item, 0) + 1);
    }

    return result;
  }

}
