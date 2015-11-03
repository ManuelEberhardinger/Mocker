/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.mocker;

import java.lang.reflect.Method;
import java.util.*;
import net.sf.cglib.proxy.*;

/**
 *
 * @author manuel
 */
public class Mocker {
    
    // Creates a mock of the given class.
    public static<T> T mock(Class<?> clazz){
        
        AbstractMocker mocker = new MockerFactory(clazz);
        
        return mocker.create();
    }
}