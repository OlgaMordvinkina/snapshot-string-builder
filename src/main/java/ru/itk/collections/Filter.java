package ru.itk.collections;

public interface Filter<T> {
  T apply(T o);
}
