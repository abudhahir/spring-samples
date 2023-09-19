# spring-samples
To create a custom annotation that calls a method when an exception is thrown in the annotated method, you can follow these steps:

1. Define the custom annotation:

```java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnException {
    String value() default "";
}
```

This annotation `@OnException` can be applied to methods, and it has an optional `value` attribute where you can specify the name of the method to be called if an exception is thrown in the annotated method.

2. Create an aspect to handle the annotation:

```java
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    @AfterThrowing(value = "@annotation(onExceptionAnnotation)", throwing = "ex")
    public void handleException(JoinPoint joinPoint, OnException onExceptionAnnotation, Exception ex) {
        String methodName = onExceptionAnnotation.value();
        if (!methodName.isEmpty()) {
            try {
                Object target = joinPoint.getTarget();
                target.getClass().getMethod(methodName).invoke(target);
            } catch (Exception e) {
                // Handle any exceptions thrown while invoking the method
                e.printStackTrace();
            }
        }
    }
}
```

In this aspect, we use the `@AfterThrowing` advice to intercept methods annotated with `@OnException` when an exception is thrown. We then use reflection to invoke the specified method (provided in the `value` attribute of the annotation).

3. Use the custom annotation in your code:

```java
@Service
public class MyService {

    @OnException("handleException")
    public void methodThatMightThrowException() {
        // Your business logic here
        if (someCondition) {
            throw new RuntimeException("An exception occurred.");
        }
    }

    public void handleException() {
        // This method will be called if an exception is thrown in methodThatMightThrowException
        // Add your exception handling logic here
        System.out.println("Handling the exception...");
    }
}
```

In this example, we've annotated the `methodThatMightThrowException` method with `@OnException("handleException")`, specifying that if an exception is thrown in this method, the `handleException` method will be called.

Make sure you have Spring AOP properly configured in your project for this aspect to work. This setup allows you to define custom exception handling logic for specific methods using annotations.
