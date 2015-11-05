
package de.oth.mocker;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class SpyerFactory<T> extends AbstractMocker {

    private final T _object;
    private final Enhancer _e;
    private final HashMap<String, Integer> _hashMap;
    
    // Creates a SpyerFactory object
    public SpyerFactory(T clazz){
        if(clazz == null)
            throw new NullPointerException("clazz");
        
        _object = clazz;
        _e = new Enhancer();
        _hashMap = new HashMap<>();
    }
    
    // Creates the reflection of the class and returns it
    @Override
    public<T> T create() {
        
        _e.setSuperclass(_object.getClass());

        _e.setCallback((MethodInterceptor) (Object o, Method m, Object[] os, MethodProxy ms) -> {
            
            // If the verify flag is set the verify callback will be executed and null will be returned
            // Otherwise the method will be invoked and the return value of the method will be returned
            if(_invTimes != null && _invTimes.isVerify()){
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
