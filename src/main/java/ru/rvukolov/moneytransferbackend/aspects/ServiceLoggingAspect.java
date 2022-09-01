package ru.rvukolov.moneytransferbackend.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import ru.rvukolov.moneytransferbackend.exceptions.AApplicationException;
import ru.rvukolov.moneytransferbackend.model.Operation;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@Aspect
public class ServiceLoggingAspect {

    private ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();

    @Before("execution(public * ru.rvukolov.moneytransferbackend.service.CardsService.*(..))")
    public void beforeAddCardAdvice(JoinPoint joinPoint) {
        var method = joinPoint.getSignature().getName();
        try {
            Class<?> clazz = joinPoint.getTarget().getClass();
            Logger log = getLogger(clazz);
            log.debug("Calling method {}, request with args {}", method, mapper.writeValueAsString(joinPoint.getArgs()));
        } catch (JsonProcessingException e) {
            Logger log = getLogger(this.getClass());
            log.error("Exception while logging", e);
        }
    }

    @AfterReturning(pointcut = "execution(public * ru.rvukolov.moneytransferbackend.service.CardsService.*(..))",
            returning = "operation")
    public void afterAddCardAdvice(JoinPoint joinPoint, Operation operation) {
        var method = joinPoint.getSignature().getName();
        try {
            Class<?> clazz = joinPoint.getTarget().getClass();
            Logger log = getLogger(clazz);
            log.debug("Get information for {}: {}", method, mapper.writeValueAsString(operation));
        } catch (JsonProcessingException e) {
            Logger log = getLogger(this.getClass());
            log.error("Exception while logging", e);
        }
    }

    @AfterThrowing(pointcut = "execution(public * ru.rvukolov.moneytransferbackend.service.CardsService.*(..))",
            throwing = "throwable")
    public void afterThrowingCardServiceAdvice(JoinPoint joinPoint, AApplicationException throwable) {
        var method = joinPoint.getSignature().getName();
        try {
            Class<?> clazz = joinPoint.getTarget().getClass();
            Logger log = getLogger(clazz);
            log.error("Exception in method " + method + "\nResponse: " + mapper.writeValueAsString(throwable.getResponse()), throwable);
        } catch (JsonProcessingException e) {
            Logger log = getLogger(this.getClass());
            log.error("Exception while logging", e);
        }
    }
}
