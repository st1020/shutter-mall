package com.st1020.shuttermall.aspect;


import com.st1020.shuttermall.annotation.Permission;
import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.enums.UserType;
import com.st1020.shuttermall.exception.PermissionException;
import com.st1020.shuttermall.exception.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Component
@Aspect
public class PermissionAspect {
    @Pointcut("@annotation(com.st1020.shuttermall.annotation.Permission)")
    public void permissionPointCut() {
    }

    @Around("permissionPointCut()")
    public Object tokenHandler(ProceedingJoinPoint point) throws Throwable {
        Permission annotation = getAnnotation(point);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (servletRequestAttributes != null) {
            request = servletRequestAttributes.getRequest();
            User user = (User) request.getAttribute("user");
            if (user == null) {
                throw new TokenException("Not Login");
            }
            if (annotation.value() == UserType.ADMIN && !user.isAdmin()) {
                throw new PermissionException("Not Admin");
            }
            if (annotation.value() == UserType.MANAGER && !(user.isAdmin() || user.isManager())) {
                throw new PermissionException("Not Manager");
            }
        }
        return point.proceed(point.getArgs());
    }

    private Permission getAnnotation(ProceedingJoinPoint point) throws NoSuchMethodException {
        Class<?> aClass = point.getTarget().getClass();
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = aClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        return method.getAnnotation(Permission.class);
    }
}
