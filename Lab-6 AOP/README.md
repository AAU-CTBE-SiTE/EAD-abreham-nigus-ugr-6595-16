# Lab 6: Aspect Oriented Programming (AOP) with Spring

A lab project that shows Aspect Oriented Programming with Spring AOP and AspectJ annotations. `StudentService` contains only business logic (add, get, list and delete students). Cross-cutting concerns such as logging, validation, timing, caching and auditing live in separate **aspects**, and Spring runs them around the service methods without changing the service code.

## Tech stack

| Component | Version |
|---|---|
| Java | 21 |
| Spring Context (includes Spring AOP) | 6.2.7 |
| AspectJ Weaver | 1.9.24 |
| Lombok | 1.18.38 |
| Build tool | Maven |

## Project structure

```
Lab-6 AOP/
├── pom.xml
└── src/main/
    ├── java/com/craft/
    │   ├── App.java               # @Configuration, @EnableAspectJAutoProxy, runs the 10 demos
    │   ├── Student.java           # id, name, age
    │   ├── StudentService.java    # The target: business logic only
    │   ├── TrackTime.java         # Custom annotation used as a pointcut
    │   ├── LoggingAspect.java     # @Pointcut, @Before, @After, @AfterReturning, @AfterThrowing
    │   ├── ValidationAspect.java  # @Around that changes arguments, @Before that blocks a call
    │   ├── PerformanceAspect.java # @Around that times methods marked @TrackTime
    │   ├── CachingAspect.java     # @Around that skips the real method on a cache hit
    │   ├── AuditAspect.java       # Introduction with @DeclareParents
    │   ├── Auditable.java         # Interface added to StudentService by AuditAspect
    │   ├── AuditableImpl.java     # Implementation of the introduced interface
    │   └── XmlLoggingAspect.java  # Plain class used as an aspect from XML
    └── resources/
        └── aopConfig.xml          # XML-based AOP with <aop:config>
```

## AOP concepts and where they are

| Concept | Meaning | Where in the code |
|---|---|---|
| **Cross-cutting concern** | Code needed in many places that isn't business logic (logging, validation, timing, caching) | Moved out of `StudentService` into the aspect classes |
| **Aspect** | A class that holds one cross-cutting concern | `@Aspect` classes: `LoggingAspect`, `ValidationAspect`, `PerformanceAspect`, `CachingAspect`, `AuditAspect` |
| **Join point** | A point where an aspect can run. In Spring AOP it is always a method execution | `JoinPoint` / `ProceedingJoinPoint` parameters give the method name, arguments and target |
| **Pointcut** | An expression that selects join points | `@Pointcut` methods in `LoggingAspect`, and the expressions inside each advice annotation |
| **Advice** | The code that runs at the selected join points, and when it runs | `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing`, `@Around` |
| **Target object** | The object being advised | `StudentService` |
| **AOP proxy** | The object Spring creates around the target; calls go through it so the advice can run | Demo 1 prints `StudentService$$SpringCGLIB$$0` |
| **Weaving** | Linking aspects to targets. Spring AOP weaves at runtime by creating proxies | Turned on by `@EnableAspectJAutoProxy` on `App` |
| **Introduction** | Adding a new interface and its methods to an existing class | `AuditAspect` with `@DeclareParents` makes `StudentService` an `Auditable` |
| **Aspect order** | Which aspect runs first when several match the same method | `@Order(1)` to `@Order(4)`: lower number = higher priority (outermost) |

### The five advice types

| Advice | When it runs | Shown in |
|---|---|---|
| `@Before` | Before the method. It can stop the call by throwing an exception | `LoggingAspect.logBefore`, `ValidationAspect.validate` |
| `@AfterReturning` | After the method returns normally. It can read the return value (`returning = "result"`) | `LoggingAspect.logAfterReturning` |
| `@AfterThrowing` | After the method throws. It can read the exception (`throwing = "ex"`) | `LoggingAspect.logAfterThrowing` |
| `@After` | After the method, always, like a `finally` block | `LoggingAspect.logAfter` |
| `@Around` | Wraps the whole call. It decides if and when the method runs with `proceed()`, can change the arguments and can replace the return value | `ValidationAspect.normalizeName`, `PerformanceAspect.measureTime`, `CachingAspect.cacheStudent` |

### Pointcut designators

| Designator | Example in the code | Matches |
|---|---|---|
| `execution()` | `execution(* com.craft.StudentService.*(..))` | Every method of `StudentService` |
| `execution()` with a name pattern | `execution(* com.craft.StudentService.get*(..))` | Methods whose name starts with `get` |
| `within()` | `within(com.craft.StudentService)` | Every join point inside the class |
| `@annotation()` | `@annotation(trackTime)` | Methods marked with `@TrackTime` (and binds the annotation) |
| `bean()` | `bean(studentService)` | Only the bean named `studentService` |
| `args()` | `args(student)`, `args(id)` | Binds the method argument to an advice parameter |
| `&&`, `\|\|` | `getterMethods() \|\| addMethod()` | Combine pointcuts (`!` negates one) |

## What `App` runs

| Demo | What it shows |
|---|---|
| 1. Proxy | The bean from Spring is a CGLIB proxy around the real `StudentService` |
| 2. Advice and order | `ValidationAspect` (`@Order(1)`) runs before `LoggingAspect` (`@Order(2)`), then `@Before` → method → `@AfterReturning` → `@After` |
| 3. `@Around` changes arguments | The name `"  sARA  "` reaches the method as `"Sara"` |
| 4. `@Before` blocks a call | A blank name throws an exception, and `addStudent()` never runs |
| 5. `@AfterThrowing` | `deleteStudent(99)` throws; `@AfterThrowing` logs it and `@After` still runs |
| 6. `@Around` skips the method | The second `getStudent(1)` comes from the cache, and the real method doesn't run |
| 7. Custom annotation pointcut | `@TrackTime` on `getAllStudents()` makes `PerformanceAspect` time it |
| 8. Introduction | `service instanceof Auditable` is `true` although `StudentService` doesn't implement it |
| 9. Self-invocation | `registerAll()` calls `addStudent()` on `this`, which skips the proxy, so no advice runs for those inner calls |
| 10. XML-based AOP | `<aop:config>`, `<aop:pointcut>`, `<aop:aspect>`, `<aop:before>` and `<aop:after-returning>` do the same job as the annotations |

### Spring AOP limits shown by the demos

- **Only method executions** can be join points, and only on Spring beans.
- **Self-invocation isn't advised.** A call from one method to another inside the same object doesn't go through the proxy (demo 9).
- **Proxy type:** Spring uses a JDK dynamic proxy when the target implements an interface, and a CGLIB proxy (a generated subclass) otherwise. `StudentService` has no interface of its own, so it gets a CGLIB proxy (demo 1).

## Run it

Open the `Lab-6 AOP` folder in IntelliJ IDEA, let it import the Maven project, and run `com.craft.App`. Or from the command line:

```bash
mvn compile exec:java -Dexec.mainClass=com.craft.App
```

## Sample output

```
=== 1. Proxy (runtime weaving) ===
Class of the bean: com.craft.StudentService$$SpringCGLIB$$0
Is it an AOP proxy?   true
Is it a CGLIB proxy?  true
Real target class:    com.craft.StudentService

=== 2. @Before, @AfterReturning, @After and aspect order ===
[Validation] @Before      student 1 is valid
[Logging] @Before         addStudent() args=[Student(id=1, name=Abebe, age=20)]
    StudentService.addStudent() is running
[Logging] @AfterReturning addStudent() returned Student(id=1, name=Abebe, age=20)
[Logging] @After          addStudent() finished

=== 3. @Around changes the arguments ===
[Validation] @Around      changed name '  sARA  ' to 'Sara'
[Validation] @Before      student 2 is valid
[Logging] @Before         addStudent() args=[Student(id=2, name=Sara, age=22)]
    StudentService.addStudent() is running
[Logging] @AfterReturning addStudent() returned Student(id=2, name=Sara, age=22)
[Logging] @After          addStudent() finished

=== 4. @Before blocks an invalid call ===
[Validation] @Before      rejected student 3: empty name
App caught: Student name must not be empty

=== 5. @AfterThrowing ===
[Logging] @Before         deleteStudent() args=[99]
    StudentService.deleteStudent() is running
[Logging] @AfterThrowing  deleteStudent() threw IllegalArgumentException: Student 99 not found
[Logging] @After          deleteStudent() finished
App caught: Student 99 not found

=== 6. @Around skips the real method (cache) ===
-- first call
[Logging] @Before         getStudent() args=[1]
[Caching] @Around         cache MISS for id 1, calling the real method
    StudentService.getStudent() is running (reading from the store)
[Logging] @AfterReturning getStudent() returned Student(id=1, name=Abebe, age=20)
[Logging] @After          getStudent() finished
-- second call
[Logging] @Before         getStudent() args=[1]
[Caching] @Around         cache HIT for id 1, the real method is skipped
[Logging] @AfterReturning getStudent() returned Student(id=1, name=Abebe, age=20)
[Logging] @After          getStudent() finished

=== 7. Custom annotation pointcut (@TrackTime) ===
[Logging] @Before         getAllStudents() args=[]
    StudentService.getAllStudents() is running
[Performance] @Around     'load all students' (getAllStudents()) took 111 ms
[Logging] @AfterReturning getAllStudents() returned [Student(id=1, name=Abebe, age=20), Student(id=2, name=Sara, age=22)]
[Logging] @After          getAllStudents() finished
App got 2 students

=== 8. Introduction (@DeclareParents) ===
service instanceof Auditable: true
Audit: this object became Auditable through AuditAspect (@DeclareParents)

=== 9. Self-invocation (advice is NOT applied to inner calls) ===
[Logging] @Before         registerAll() args=[[Student(id=4, name=Kebede, age=21), Student(id=5, name=Hana, age=23)]]
    StudentService.registerAll() is running
    StudentService.addStudent() is running
    StudentService.addStudent() is running
[Logging] @After          registerAll() finished

=== 10. XML-based AOP (aopConfig.xml) ===
[XML aspect] <aop:before>          addStudent()
    StudentService.addStudent() is running
[XML aspect] <aop:after-returning> addStudent() returned Student(id=6, name=Liya, age=24)
[XML aspect] <aop:before>          getStudent()
    StudentService.getStudent() is running (reading from the store)
[XML aspect] <aop:after-returning> getStudent() returned Student(id=6, name=Liya, age=24)
```

The time in demo 7 changes from run to run.

## Summary

- **AOP** separates cross-cutting concerns from business logic. The business class stays clean, and each concern lives in one aspect instead of being copied into every method.
- An **aspect** holds **advice** (what to do and when), and a **pointcut** selects the **join points** (which methods) where the advice runs.
- **Spring AOP** weaves aspects at runtime with **proxies**: callers get a proxy, and the proxy runs the advice around the call to the real target.
