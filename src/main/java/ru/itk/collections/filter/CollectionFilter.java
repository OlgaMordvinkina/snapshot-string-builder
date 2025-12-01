package ru.itk.collections.filter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Напишите метод filter, который принимает на вход массив любого типа,
 * вторым арументом метод должен принимать класс, реализующий интерфейс Filter,
 * в котором один метод - T apply(T o) (параметризованный).
 * <p>
 * Метод должен быть реализован так чтобы возвращать новый массив,
 * к каждому элементу которого была применена функция apply
 */
public class CollectionFilter {
  public static void main(String[] args) {
    CollectionFilter filter = new CollectionFilter();

    Integer[] array = {1, 2, 3, 4};

    Integer[] doubledArray = filter.filter(array, n -> n * 2);

    System.out.println(Arrays.toString(doubledArray));
  }

  public <T> T[] filter(T[] array, Filter<T> filter) {
    if (Objects.isNull(array) || Objects.isNull(filter)) {
      throw new IllegalArgumentException("Array and filter must not be null");
    }

    T[] result = Arrays.copyOf(array, array.length);

    for (int i = 0; i < array.length; i++) {
      result[i] = filter.apply(array[i]);
    }

    return result;
  }

}
