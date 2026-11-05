# springcore

A lab project that shows the basics of the Spring Framework's IoC container and dependency injection. It starts with a tightly coupled `App` that creates its own `Student` with `new`, then hands object creation and wiring to Spring through XML configuration files read by `ClassPathXmlApplicationContext`.

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
    │   ├── App.java       # Runs one demo per topic of the lab
    │   ├── Student.java   # id, name, age, address, subjects + 3 constructors
    │   ├── Address.java   # city, country
    │   └── Subject.java   # subjectName
    └── resources/
        ├── basicConfig.xml        # First bean: <bean id="student" .../>
        ├── beansConfig.xml        # Setter injection: literals, ref, <list>/<array>/<set>
        ├── constructorConfig.xml  # Constructor injection: by name, order, index, and with ref
        └── scopeConfig.xml        # singleton vs prototype
```

`Student` has three constructors so every part of the lab can use the same class:

| Constructor | Prints | Used by |
|---|---|---|
| `Student()` | `Empty Constructor...` | `new Student()`, setter injection, scopes |
| `Student(int id, String name, int age)` | `Student Parameterized Constructor Called` | constructor injection |
| `Student(int id, String name, int age, Address address)` | | constructor object injection |

## What `App` runs

| Demo | Config | What it shows |
|---|---|---|
| Tight coupling | none | `App` creates `Student` with `new Student()` and calls `getHello()` |
| Let Spring create the bean | `basicConfig.xml` | `getBean("student", Student.class)`, then values set by hand with `setId`, `setName`, `setAge` |
| Setter injection | `beansConfig.xml` | `<property name="..." value="..."/>` makes Spring call the setters (literal values) |
| Constructor injection | `constructorConfig.xml` | `<constructor-arg>` matched by `name`, by order (no attribute) and by `index` |
| Object injection | `beansConfig.xml`, `constructorConfig.xml` | `ref="address"` injects the `Address` bean, through a setter and through a constructor |
| Collection injection | `beansConfig.xml` | `List<Subject>` filled with `<list>`, `<array>` and `<set>` |
| Bean scopes | `scopeConfig.xml` | singleton: `getBean()` returns the same instance; prototype: a new instance every time |

`beansConfig.xml` and `constructorConfig.xml` set `default-lazy-init="true"`, so each bean is created only when `getBean()` asks for it and its constructor message prints next to the demo that uses it.

The by-`name` constructor arguments need the compiler to keep parameter names, so `pom.xml` sets `<parameters>true</parameters>` on the compiler plugin.

## Run it

Open the `Lab-3 springcore` folder in IntelliJ IDEA, let it import the Maven project, and run `com.craft.App`. Or from the command line:

```bash
mvn compile exec:java -Dexec.mainClass=com.craft.App
```

## Sample output

```
=== Tight coupling ===
Empty Constructor...
Hello Springers

=== Let Spring create the bean (basicConfig.xml) ===
Empty Constructor...
Hello Springers
Student Details Student(id=101, name=John, age=34, address=null, subjects=null)

=== Setter injection (beansConfig.xml) ===
Empty Constructor...
Student Details Student(id=101, name=John Doe, age=20, address=null, subjects=null)
Student Name: John Doe
Student(id=101, name=John Doe, age=20, address=null, subjects=null)

=== Constructor injection (constructorConfig.xml) ===
Student Parameterized Constructor Called
studentByName: Student(id=101, name=John Doe, age=20, address=null, subjects=null)
Student Parameterized Constructor Called
studentByOrder: Student(id=101, name=John Doe, age=20, address=null, subjects=null)
Student Parameterized Constructor Called
studentByIndex: Student(id=101, name=John Doe, age=20, address=null, subjects=null)

=== Object injection ===
Empty Constructor...
setter:      Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=null)
constructor: Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=null)

=== Collection injection (beansConfig.xml) ===
-- student
Empty Constructor...
Student(id=101, name=John Doe, age=20, address=Address(city=Addis, country=Ethiopia), subjects=[Subject(subjectName=Maths 101), Subject(subjectName=Java EE)])
Student Name: John Doe
Subject 1: Maths 101
Subject 2: Java EE
-- arrayStudent
...same as student...
-- setStudent
...same as student...

=== Bean scopes (scopeConfig.xml) ===
Empty Constructor...
-- singleton
true
John Doe
-- prototype
Empty Constructor...
Empty Constructor...
false
null
```

The singleton `Student` is created once, when the context starts. The prototype `Student` is created on each `getBean()` call, so changing `student1`'s name leaves `student2`'s name as `null`.

## Summary

- **IoC (Inversion of Control):** the control of creating and wiring objects moves out of your code and into a framework. It's often summed up as the Hollywood principle: "Don't call us, we'll call you."
- **DI (Dependency Injection):** how Spring does IoC. The container creates an object's dependencies and hands them in through setters or constructors, instead of the object creating them with `new`.
- **IoC container and beans:** the part of Spring that does this work is the IoC container, and every object it manages is called a bean.
