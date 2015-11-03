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
public class Spyer {
    
    // Creates a spy of the given class.
    public static<T> T spy(T clazz){

        AbstractMocker spyer = new SpyerFactory(clazz);
        
        return spyer.create();
    }
    
}
