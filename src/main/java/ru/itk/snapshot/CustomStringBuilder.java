package ru.itk.snapshot;

import java.util.Stack;

public class CustomStringBuilder {
  private final StringBuilder builder = new StringBuilder();
  private final Stack<Snapshot> history = new Stack<>();

/*
  public static void main(String[] args) {

    CustomStringBuilder sb = new CustomStringBuilder();

    sb.append("Hello");
    sb.append(" World");
    System.out.println(sb); // Hello World

    sb.delete(5, 11);
    System.out.println(sb); // Hello

    sb.undo();
    System.out.println(sb); // Hello World

    sb.undo();
    System.out.println(sb); // Hello
  }
*/

  /**
   * Сохраняет текущее состояние перед изменениями
   */
  private void saveSnapshot() {
    history.push(new Snapshot(builder.toString()));
  }

  /**
   * Добавляет текст в конец строки.
   * Перед выполнением сохраняет состояние для возможности отката.
   *
   * @param str текст для добавления
   * @return текущий объект
   */
  public CustomStringBuilder append(String str) {
    saveSnapshot();
    builder.append(str);
    return this;
  }

  /**
   * Вставляет текст в указанную позицию.
   * Перед выполнением сохраняет состояние.
   *
   * @param index позиция вставки
   * @param str   текст для вставки
   * @return текущий объект
   */
  public CustomStringBuilder insert(int index, String str) {
    saveSnapshot();
    builder.insert(index, str);
    return this;
  }

  /**
   * Удаляет часть строки в заданном диапазоне.
   * Перед удалением сохраняет состояние.
   *
   * @param start начало диапазона
   * @param end   конец диапазона (не включительно)
   * @return текущий объект
   */
  public CustomStringBuilder delete(int start, int end) {
    saveSnapshot();
    builder.delete(start, end);
    return this;
  }

  /**
   * Отменяет последнее изменение, восстанавливая состояние строки.
   * Если доступных снимков нет, метод ничего не делает.
   */
  public void undo() {
    if (!history.isEmpty()) {
      Snapshot snapshot = history.pop();
      builder.setLength(0);
      builder.append(snapshot.state);
    }
  }

  /**
   * Возвращает текущую строку.
   *
   * @return строковое представление данных
   */
  @Override
  public String toString() {
    return builder.toString();
  }

  /**
   * Снимок состояния.
   * Хранит неизменяемую строку, отражающую состояние на момент сохранения.
   *
   * @param state Сохранённая строка.
   */
  private record Snapshot(String state) {
  }
}
