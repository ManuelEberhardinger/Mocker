/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.mocker;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * @author manuel
 */
public class SpyerFactory<T> extends AbstractMocker {

    private final T _object;
    private final Enhancer _e;
    private final HashMap<String, Integer> _hashMap;
    
    public SpyerFactory(T clazz){
        if(clazz == null)
            throw new NullPointerException("clazz");
        
        _object = clazz;
        _e = new Enhancer();
        _hashMap = new HashMap<>();
    }
    
    @Override
    public<T> T create() {
        
        _e.setSuperclass(_object.getClass());

        _e.setCallback((MethodInterceptor) (Object o, Method m, Object[] os, MethodProxy ms) -> {
            if(AbstractInvocationTimes.isVerify()){
                _invTimes.verify(m.getDeclaringClass() + m.getName() + Arrays.toString(os), _hashMap);
                return null;
            }
            else{
                setOrIncreaseHashMap(m.getDeclaringClass() + m.getName() + Arrays.toString(os), _hashMap);
                Object value = m.invoke(_object, os);
                return value;
            }
        });
        
        return (T) _e.create();
    }
    
}
