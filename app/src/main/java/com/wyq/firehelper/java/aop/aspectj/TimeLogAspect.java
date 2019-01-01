package com.wyq.firehelper.java.aop.aspectj;

import com.wyq.firehelper.utils.common.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TimeLogAspect {

    @Pointcut("execution(@com.wyq.firehelper.java.aop.aspectj.FireLogTime * *(..))")
    public void methodTimeLog(){}

    @Around("methodTimeLog()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getCanonicalName();
        String methodName = signature.getName();
//        LogUtils.i("Aspect", Arrays.toString(signature.getParameterNames()));//获取参数名

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        LogUtils.i("Aspect",className+" -> "+methodName+" [ cost: "+(end - start)+" ms ]");
        return result;
    }
}
