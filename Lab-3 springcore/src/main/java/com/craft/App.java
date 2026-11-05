package com.craft;

// Runs every step of the lab in order. Each step also has its own App with a main() you can run on its own.
public class App {
    public static void main(String[] args) {
        run("Step 1: Tight coupling", () -> com.craft.step01_tightcoupling.App.main(args));
        run("Step 2: Let Spring create the bean", () -> com.craft.step02_firstbean.App.main(args));
        run("Step 3: Bean from Spring, values set by hand", () -> com.craft.step03_manualsetters.App.main(args));
        run("Step 4: Setter-based injection", () -> com.craft.step04_setterinjection.App.main(args));
        run("Step 5: Constructor-based injection", () -> com.craft.step05_constructorinjection.App.main(args));
        run("Step 6: Literal dependency injection", () -> com.craft.step06_literalinjection.App.main(args));
        run("Step 7: Object dependency injection (setter)", () -> com.craft.step07_objectinjection.App.main(args));
        run("Step 8: Object dependency injection (constructor)", () -> com.craft.step08_constructorobjectinjection.App.main(args));
        run("Step 9: Collections dependency injection", () -> com.craft.step09_collectioninjection.App.main(args));
        run("Step 10: Singleton scope", () -> com.craft.step10_singletonscope.App.main(args));
        run("Step 11: Prototype scope", () -> com.craft.step11_prototypescope.App.main(args));
    }

    private static void run(String title, Runnable step) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        step.run();
    }
}
