# springcore

A lab project that shows the basics of the Spring Framework's IoC container and dependency injection. It starts with a tightly coupled `App` that creates its own `Student` with `new`, then hands object creation and wiring to Spring step by step, using XML configuration files read by `ClassPathXmlApplicationContext`.

## Tech stack

| Component | Version |
|---|---|
| Java | 21 |
| Spring Context | 6.2.7 |
| Lombok | 1.18.38 |
| Build tool | Maven |

## Project structure

Each step of the lab is its own package under `com.craft`, with its own `Student` (and `Address`/`Subject` where needed), its own `App` with a `main()`, and its own config folder under `src/main/resources`. Because the steps don't share classes, every version of `Student` from the lab stays exactly as the lab wrote it.

```
springcore/
├── pom.xml
└── src/main/
    ├── java/com/craft/
    │   ├── App.java                          # Runs every step in order
    │   ├── step01_tightcoupling/
    │   ├── step02_firstbean/
    │   ├── step03_manualsetters/
    │   ├── step04_setterinjection/
    │   ├── step05_constructorinjection/
    │   ├── step06_literalinjection/
    │   ├── step07_objectinjection/
    │   ├── step08_constructorobjectinjection/
    │   ├── step09_collectioninjection/
    │   ├── step10_singletonscope/
    │   └── step11_prototypescope/
    └── resources/
        └── stepNN_.../beansConfig*.xml       # One folder per step
```

## The steps

| Step | Topic | What it shows |
|---|---|---|
| 1 | Tight coupling | `App` creates `Student` with `new Student()`, so `App` is directly coupled to `Student` |
| 2 | Let Spring create the bean | `<bean id="student" class="...">` and `context.getBean("student", Student.class)` |
| 3 | Values set by hand | Spring creates the bean, `App` calls `setId`, `setName`, `setAge` itself |
| 4 | Setter-based injection | `<property name="..." value="..."/>` makes Spring call the setters |
| 5 | Constructor-based injection | `<constructor-arg>` matched three ways: by `name`, by order (no attribute) and by `index` |
| 6 | Literal dependency injection | Strings and numbers (`city`, `country`) injected through setters |
| 7 | Object dependency injection (setter) | `ref="address"` injects the `Address` bean into `Student` |
| 8 | Object dependency injection (constructor) | `<constructor-arg name="address" ref="address"/>` |
| 9 | Collections dependency injection | `List<Subject>` filled with `<list>`, `<array>` and `<set>` |
| 10 | Singleton scope (default) | `getBean()` returns the same instance; a change through one reference shows in the other |
| 11 | Prototype scope | `getBean()` returns a new instance every time |

Step 5's match-by-`name` version needs the compiler to keep constructor parameter names, so `pom.xml` sets `<parameters>true</parameters>` on the compiler plugin.

## Run it

Open the `Lab-3 springcore` folder in IntelliJ IDEA and let it import the Maven project. Run `com.craft.App` to see every step, or run the `App` inside any step package to see only that step.

From the command line:

```bash
mvn compile exec:java -Dexec.mainClass=com.craft.App
```

## Sample output

```
=== Step 1: Tight coupling ===
Hello Springers

=== Step 2: Let Spring create the bean ===
Hello Springers

=== Step 3: Bean from Spring, values set by hand ===
Student Details Student(id=101, name=John, age=34)

=== Step 4: Setter-based injection ===
Student Details Student(id=101, name=John Doe, age=20)
Student Name: John Doe
Student(id=101, name=John Doe, age=20)

=== Step 5: Constructor-based injection ===
-- step05_constructorinjection/beansConfig-name.xml
Student Parameterized Constructor Called
Student(id=101, name=John Doe, age=20)
-- step05_constructorinjection/beansConfig-order.xml
Student Parameterized Constructor Called
Student(id=101, name=John Doe, age=20)
-- step05_constructorinjection/beansConfig-index.xml
Student Parameterized Constructor Called
Student(id=101, name=John Doe, age=20)

=== Step 6: Literal dependency injection ===
Student(id=101, name=John Doe, age=20, city=Addis, country=Ethiopia)
John Doe

=== Step 7: Object dependency injection (setter) ===
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia))

=== Step 8: Object dependency injection (constructor) ===
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia))

=== Step 9: Collections dependency injection ===
-- step09_collectioninjection/beansConfig.xml
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=[Subject(subjectName=Maths 101), Subject(subjectName=Java EE)])
Student Name: John Doe
Subject 1: Maths 101
Subject 2: Java EE
-- step09_collectioninjection/beansConfig-array.xml
...same as <list>...
-- step09_collectioninjection/beansConfig-set.xml
...same as <list>...

=== Step 10: Singleton scope ===
Empty Constructor...
true
John Doe

=== Step 11: Prototype scope ===
Empty Constructor...
Empty Constructor...
false
null
```

In step 10, `Empty Constructor...` prints once because the singleton is created when the context starts. In step 11 it prints twice, once for each `getBean()` call, and changing `student1`'s name leaves `student2`'s name as `null`.

## Summary

- **IoC (Inversion of Control):** the control of creating and wiring objects moves out of your code and into a framework. It's often summed up as the Hollywood principle: "Don't call us, we'll call you."
- **DI (Dependency Injection):** how Spring does IoC. The container creates an object's dependencies and hands them in through setters or constructors, instead of the object creating them with `new`.
- **IoC container and beans:** the part of Spring that does this work is the IoC container, and every object it manages is called a bean.
