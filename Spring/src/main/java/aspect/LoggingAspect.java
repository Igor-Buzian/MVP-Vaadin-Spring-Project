package aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* dao.*.*(..))")
    public void logBeforeDaoMethods() {
        System.out.println("Вызов метода DAO");
    }
}
