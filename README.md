## 1 Практическое задание - StringBuilder
Изучите внутреннюю реализацию класса **StringBuilder** и 
напишите свою с добавлением дополнительного метода - **undo()**.

Прежде чем приступать - прочитайте про паттерн **snapshot** и 
примените его в своей реализации.

**примечание:** полностью переписывать все методы которые есть в **StringBuilder** не нужно, 
в задании важно именно понимание сути паттерна. В случае, 
если задание остаётся непонятным, задайте вопрос ментору

## 2 Collection filter
Напишите метод **filter**, который принимает на вход массив любого типа,
вторым арументом метод должен принимать класс, реализующий интерфейс **Filter**,
в котором один метод - `T apply(T o)` (параметризованный).
<p>
Метод должен быть реализован так чтобы возвращать новый массив,
к каждому элементу которого была применена функция **apply**

## 3 Collection - count of elements
Напишите метод, который получает на вход массив элементов и возвращает
**Map** ключи в котором - элементы, а значения - сколько раз встретился этот элемент

## 4 Concurrency - блокирующая очередь
Предположим, у вас есть пул потоков, и вы хотите реализовать блокирующую очередь 
для передачи задач между потоками. Создайте класс BlockingQueue, который будет 
обеспечивать безопасное добавление и извлечение элементов между производителями 
и потребителями в контексте пула потоков.

Класс BlockingQueue должен содержать методы enqueue() для добавления элемента 
в очередь и dequeue() для извлечения элемента. Если очередь пуста, dequeue() 
должен блокировать вызывающий поток до появления нового элемента.

очередь должна иметь фиксированный размер.

Используйте механизмы wait() и notify() для координации между производителями 
и потребителями. Реализуйте метод size(), который возвращает текущий размер очереди.

## 5 Concurrency - синхронизаторы
Синхронизация потоков с использованием CyclicBarrier и ExecutorService

В этой задаче мы будем использовать CyclicBarrier и ExecutorService для синхронизации нескольких потоков, выполняющих сложную задачу, и затем ожидающих, пока все потоки завершат выполнение, чтобы объединить результаты.

Создайте класс ComplexTask, представляющий сложную задачу, которую несколько потоков будут выполнять. В каждой задаче реализуйте метод execute(), который выполняет часть сложной задачи.

Создайте класс ComplexTaskExecutor, в котором будет использоваться CyclicBarrier и ExecutorService для синхронизации выполнения задач. Реализуйте метод executeTasks(int numberOfTasks), который создает пул потоков и назначает каждому потоку экземпляр сложной задачи для выполнения. Затем используйте CyclicBarrier для ожидания завершения всех потоков и объединения результатов их работы. В методе main создайте экземпляр ComplexTaskExecutor и вызовите метод executeTasks с несколькими задачами для выполнения.

Код для тестирования

```
public class TestComplexTaskExecutor {

    public static void main(String[] args) {
        ComplexTaskExecutor taskExecutor = new ComplexTaskExecutor(5); // Количество задач для выполнения

        Runnable testRunnable = () -> {
            System.out.println(Thread.currentThread().getName() + " started the test.");

            // Выполнение задач
            taskExecutor.executeTasks(5);

            System.out.println(Thread.currentThread().getName() + " completed the test.");
        };

        Thread thread1 = new Thread(testRunnable, "TestThread-1");
        Thread thread2 = new Thread(testRunnable, "TestThread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
```

## 6 Concurrency - многопоточный банковский счет
В виртуальном банке "ConcurrentBank" решено внедрить многопоточность для обработки операций по счетам клиентов. Система должна поддерживать возможность одновременного пополнения (deposit), снятия (withdraw), а также переводов (transfer) между счетами. Каждый счет имеет свой уникальный номер.

Реализуйте класс BankAccount с методами deposit, withdraw и getBalance, поддерживающими многопоточное взаимодействие.

Реализуйте класс ConcurrentBank для управления счетами и выполнения переводов между ними. Класс должен предоставлять методы createAccount для создания нового счета и transfer для выполнения переводов между счетами.

Переводы между счетами должны быть атомарными, чтобы избежать ситуаций, когда одна часть транзакции выполняется успешно, а другая нет.

Реализуйте метод getTotalBalance, который возвращает общий баланс всех счетов в банке.

```
public class ConcurrentBankExample {
    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBank();

        // Создание счетов
        BankAccount account1 = bank.createAccount(1000);
        BankAccount account2 = bank.createAccount(500);

        // Перевод между счетами
        Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, 200));
        Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, 100));

        transferThread1.start();
        transferThread2.start();

        try {
            transferThread1.join();
            transferThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод общего баланса
        System.out.println("Total balance: " + bank.getTotalBalance());
    }
}
```

## 7 Stream API - генерация чисел
Предположим, у нас есть список заказов, и каждый заказ представляет собой продукт и его стоимость. Задача состоит в использовании Stream API и коллекторов для решения следующих задач:

Создайте список заказов с разными продуктами и их стоимостями.
Группируйте заказы по продуктам.
Для каждого продукта найдите общую стоимость всех заказов.
Отсортируйте продукты по убыванию общей стоимости.
Выберите три самых дорогих продукта.
Выведите результат: список трех самых дорогих продуктов и их общая стоимость.
Исходный код

```
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
}
```

## 8 Stream API - агрегация и объединение результатов
1. Создайте коллекцию студентов, где каждый студент содержит информацию о предметах, 
   которые он изучает, и его оценках по этим предметам.
2. Используйте Parallel Stream для обработки данных и создания Map,
   где ключ - предмет, а значение - средняя оценка по всем студентам.
3. Выведите результат: общую Map с средними оценками по всем предметам.

```
class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }
}

public class ParallelStreamCollectMapAdvancedExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );
}
```

## 9 Stream API - ForkJoinPool: Рекурсивное вычисление факториала
Рассмотрим задачу вычисления факториала числа с использованием ForkJoinPool. Факториал числа n обозначается как n! и вычисляется как произведение всех положительных целых чисел от 1 до n.

1. Реализуйте класс FactorialTask, который расширяет RecursiveTask. Этот класс будет выполнять рекурсивное вычисление факториала числа.
2. В конструкторе FactorialTask передайте число n, факториал которого нужно вычислить.
3. В методе compute() разбейте задачу на подзадачи и используйте fork() для их асинхронного выполнения.
4. Используйте join() для получения результатов подзадач и комбинирования их для получения общего результата.
5. В основном методе создайте экземпляр FactorialTask с числом, для которого нужно вычислить факториал, и запустите его в ForkJoinPool.
6. Выведите результат вычисления факториала.
В итоге должно получиться так

```
public class ForkJoinPoolExample {
    public static void main(String[] args) {
        int n = 10; // Вычисление факториала для числа 10

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(n);

        long result = forkJoinPool.invoke(factorialTask);

        System.out.println("Факториал " + n + "! = " + result);
    }
}
```