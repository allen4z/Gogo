package com.gogo.advice;


import java.io.OutputStream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.gogo.annotation.GoJsonFilter;
import com.gogo.annotation.GoJsonFilters;
import com.gogo.helper.WebContext;


@Component
@Aspect
public class GoJsonFilterAdvice {

	@Pointcut("execution(* com.gogo.ctrl..*(..))")
	public void pointCut(){
		
	}
	
	@Around("pointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
		
		MethodSignature msig = (MethodSignature) pjp.getSignature(); 
		
		GoJsonFilter annotation = msig.getMethod().getAnnotation(GoJsonFilter.class);
		
		GoJsonFilters annotations =  msig.getMethod().getAnnotation(GoJsonFilters.class);
		
		if(annotation == null && annotations == null){
			return pjp.proceed();
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		if (annotation != null) {  
            Class<?> mixin = annotation.mixin();  
            Class<?> target = annotation.target();  
              
            if (target != null) {  
                mapper.getSerializationConfig().addMixInAnnotations(target,  
                        mixin);  
            } else {  
                mapper.getSerializationConfig().addMixInAnnotations(  
                        msig.getMethod().getReturnType(), mixin);  
            }  
        }  
          
        if (annotations != null) {  
        	GoJsonFilter[] filters= annotations.values();  
            for(GoJsonFilter filter :filters){  
                Class<?> mixin = filter.mixin();  
                Class<?> target = filter.target();  
                  
                if (target != null) {  
                    mapper.getSerializationConfig().addMixInAnnotations(target,  
                            mixin);  
                } else {  
                    mapper.getSerializationConfig().addMixInAnnotations(  
                            msig.getMethod().getReturnType(), mixin);  
                }  
            }  
        }  
       
        try {  
        	OutputStream os = WebContext.getInstance().getResponse().getOutputStream();
            mapper.writeValue(os, pjp.proceed());  
            
        } catch (Exception ex) {  
            throw new RuntimeException(ex);  
        }  
        return null;  
		
	}
	
}
