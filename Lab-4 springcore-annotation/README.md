# springcore

A lab project that shows the basics of the Spring Framework's IoC container and dependency injection. It starts with a tightly coupled `App` that creates its own `Student` with `new`, then hands object creation and wiring to Spring. The beans are configured with **annotations on the classes themselves** (`@Component`, `@Value`, `@Autowired`, `@Scope`) and found with `@ComponentScan`. There are no XML files.

## Tech stack

| Component | Version |
|---|---|
| Java | 21 |
| Spring Context | 6.2.7 |
| Lombok | 1.18.38 |
| Build tool | Maven |

## Project structure

```
springcore/
├── pom.xml
└── src/main/java/com/craft/
    ├── App.java      # @Configuration + @ComponentScan, the two Subject beans, and the demos
    ├── Student.java  # @Component, @Scope("singleton"), @Autowired constructor and setter
    ├── Address.java  # @Component, @Scope("prototype"), @Value fields
    └── Subject.java  # subjectName
```

## The annotations

| Annotation | Where | What it does |
|---|---|---|
| `@Configuration` | `App` | Marks `App` as a source of bean definitions (replaces the XML file) |
| `@ComponentScan("com.craft")` | `App` | Tells Spring to find every `@Component` class in `com.craft` |
| `@Component` | `Student`, `Address` | Spring creates a bean of this class (bean id = class name in lower camel case: `student`, `address`) |
| `@Value("...")` | `Student` constructor, `Address` fields | Injects a literal value (replaces `value="..."`) |
| `@Autowired` on a constructor | `Student` | **Constructor injection**: Spring calls this constructor and passes the `@Value` literals and the `Address` bean |
| `@Autowired` on a setter | `Student.setSubjects` | **Setter injection**: Spring calls the setter and passes every `Subject` bean as a `List` |
| `@Bean` | `App.math()`, `App.java()` | Defines the two `Subject` beans. `@Component` makes only one bean per class, so two subjects need `@Bean` methods |
| `@Scope("singleton")` | `Student` | One shared instance (the default) |
| `@Scope("prototype")` | `Address` | A new instance every time one is asked for |

`App` starts the container with:

```java
ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
```

## What the demo shows

| Demo | What it shows |
|---|---|
| Tight coupling | `App` creates `Student` with `new Student()` and calls `getHello()` |
| Spring creates the beans | When the container starts, Spring creates `Address`, then calls the `@Autowired` `Student` constructor |
| Dependency injection | The `Student` bean has literal values (`@Value`), an `Address` object (constructor injection) and a `List<Subject>` (setter injection) |
| Singleton scope | `getBean("student")` twice returns the same `Student`; a change through one reference shows in the other |
| Prototype scope | `getBean("address")` twice creates two different `Address` objects; changing one doesn't change the other |

## Run it

Open the `Lab-4 springcore-annotation` folder in IntelliJ IDEA, let it import the Maven project, and run `com.craft.App`. Or from the command line:

```bash
mvn compile exec:java -Dexec.mainClass=com.craft.App
```

## Sample output

```
=== Tight coupling ===
Empty Constructor...
Hello Springers

=== Spring creates the beans ===
Address Constructor Called
Student Parameterized Constructor Called

=== Dependency injection ===
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=[Subject(subjectName=Maths 101), Subject(subjectName=Java EE)])
Student Name: John Doe
Subject 1: Maths 101
Subject 2: Java EE

=== Singleton scope (Student) ===
true
Abebe

=== Prototype scope (Address) ===
Address Constructor Called
Address Constructor Called
false
Addis
```

## Summary

- **IoC (Inversion of Control):** the control of creating and wiring objects moves out of your code and into a framework. It's often summed up as the Hollywood principle: "Don't call us, we'll call you."
- **DI (Dependency Injection):** how Spring does IoC. The container creates an object's dependencies and hands them in through setters or constructors, instead of the object creating them with `new`.
- **IoC container and beans:** the part of Spring that does this work is the IoC container, and every object it manages is called a bean.
