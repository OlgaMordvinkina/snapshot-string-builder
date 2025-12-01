package ru.itk.collections.filter;

public interface Filter<T> {
  T apply(T o);
}
