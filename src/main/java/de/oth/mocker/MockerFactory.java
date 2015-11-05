
package de.oth.mocker;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MockerFactory extends AbstractMocker {
    
    private final Class<?> _clazz;
    private final Enhancer _e;
    private final HashMap<String, Integer> _hashMap;
    
    // Creates a MockerFactory object
    public MockerFactory(Class<?> clazz){
        if(clazz == null)
            throw new NullPointerException("clazz");
        
        _clazz = clazz;
        _e = new Enhancer();
        _hashMap = new HashMap<>();
    }
    
    // Creates the reflection of the class and returns it
    @Override
    public <T> T create() {

        _e.setSuperclass(_clazz);
        
        _e.setCallback((MethodInterceptor) (Object o, Method m, Object[] os, MethodProxy ms) -> {
            // if the verify flag is set the verify callback will be executed
            // otherwise the number of method calls will be increased
            if(_invTimes != null && _invTimes.isVerify())
                _invTimes.verify(m.getDeclaringClass() + m.getName() + Arrays.toString(os), _hashMap);
            else
                setOrIncreaseHashMap(m.getDeclaringClass() + m.getName() + Arrays.toString(os), _hashMap);
            
            return null;
        });
        
        return (T) _e.create();
    }
    
}
