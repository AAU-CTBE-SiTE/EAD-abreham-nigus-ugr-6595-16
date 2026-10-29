# springcore

A lab project that shows the basics of the Spring Framework's IoC container. Instead of creating objects with `new`, the app asks a `ClassPathXmlApplicationContext` for beans that Spring creates and wires from XML configuration files.

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
└── src/main/
    ├── java/com/craft/
    │   ├── App.java       # Loads each config file and runs the demos
    │   ├── Student.java   # id, name, age, address, subjects
    │   ├── Address.java   # city, country
    │   └── Subject.java   # subjectName
    └── resources/
        ├── beansConfig.xml        # Setter injection: literals, ref, <list>
        ├── constructorConfig.xml  # Constructor injection: literals, ref
        └── scopeConfig.xml        # singleton vs prototype beans
```

## What the lab covers

| Topic | Where | How |
|---|---|---|
| Setter-based injection | `beansConfig.xml` | `<property name="..." value="..."/>` calls the matching setter |
| Object injection | `beansConfig.xml`, `constructorConfig.xml` | `ref="address"` injects the `address` bean into `Student` |
| Collection injection | `beansConfig.xml` | `<list>` of `<ref bean="..."/>` fills `List<Subject>` |
| Constructor-based injection | `constructorConfig.xml` | `<constructor-arg index="..."/>` calls `Student(int, String, int, Address)` |
| Singleton scope (default) | `scopeConfig.xml` | `getBean()` returns the same instance every time |
| Prototype scope | `scopeConfig.xml` | `getBean()` returns a new instance every time |

## Run it

Open the `Lab-3 springcore` folder in IntelliJ IDEA, let it import the Maven project, and run `com.craft.App`. Or from the command line:

```bash
mvn compile exec:java -Dexec.mainClass=com.craft.App
```

## Sample output

```
=== Setter injection (beansConfig.xml) ===
Empty Constructor...
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=[Subject(subjectName=Maths 101), Subject(subjectName=Java EE)])
Student Name: John Doe
Subject 1: Maths 101
Subject 2: Java EE

=== Constructor injection (constructorConfig.xml) ===
Student Parameterized Constructor Called
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=null)

=== Bean scopes (scopeConfig.xml) ===
Empty Constructor...
singleton: student1 == student2 -> true
singleton: student2.getName() -> John Doe
Empty Constructor...
Empty Constructor...
prototype: student3 == student4 -> false
prototype: student4.getName() -> null
```

The singleton `Student` is created once when the context starts. The prototype `Student` is created on each `getBean()` call, so changing one instance doesn't affect the other.
