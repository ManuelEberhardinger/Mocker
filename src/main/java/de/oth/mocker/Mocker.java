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
    
    private static boolean verifyCallback = false;
    private static int times;
    private static boolean isAtLeast = false;
    private static boolean isAtMost = false;
    private static boolean setTimesCorrectly = false;
    
    // <editor-fold defaultstate="collapsed" desc="All mocking classes">
    
    // Creates a mock of the given class.
    public static<T> T mock(Class<?> clazz){
        
        HashMap<String, Integer> mockMap = new HashMap<>();
        Enhancer e = new Enhancer();
        
        e.setSuperclass(clazz);
        
        setStandardCallback(e, mockMap);
        
        return (T) e.create();
    }
    
    // Sets the mocking Callback of the Enhancer
    private static void setStandardCallback(Enhancer e, final HashMap<String, Integer> mockMap){
        e.setCallback(new MethodInterceptor(){
            
        @Override
        public Object intercept(Object o, Method m, Object[] os, MethodProxy ms) throws Throwable {
            if(verifyCallback)
                verify(m, os, mockMap);
            else
                setOrIncreaseHashMap(m.getDeclaringClass() + m.getName() + Arrays.toString(os), mockMap);

            verifyCallback = isAtMost = isAtLeast = setTimesCorrectly = false;  
            return null;
        }
        
        });
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="All spying classes">
    
    // Creates a spy of the given class.
    public static<T> T spy(T clazz){
        
        HashMap<String, Integer> spyMap = new HashMap<>();
        Enhancer e = new Enhancer();
        
        e.setSuperclass(clazz.getClass());

        setSpyCallback(e, clazz, spyMap);
        
        return (T) e.create();
    }
    
    // Set the spying callback of the Enhancer
    private static<T> void setSpyCallback(Enhancer e, final T obj, final HashMap<String, Integer> spyMap){
        e.setCallback(new MethodInterceptor(){
            
        @Override
        public Object intercept(Object o, Method m, Object[] os, MethodProxy ms) throws Throwable {
            if(verifyCallback){
                verify(m, os, spyMap);
                verifyCallback = isAtMost = isAtLeast = setTimesCorrectly = false;  
                return null;
            }
            else{
                setOrIncreaseHashMap(m.getDeclaringClass() + m.getName() + Arrays.toString(os), spyMap);
                Object value = m.invoke(obj, os);
                return value;
            } 
        }
        
        });
    }
    
    // </editor-fold>
    
    // Sets or increases the value of the hash map.
    private static void setOrIncreaseHashMap(String key, HashMap<String, Integer> hashMap){
        if(hashMap.containsKey(key)){
            int value = hashMap.get(key);
            value++;
            hashMap.replace(key, value);
        }
        else
            hashMap.put(key, 1);
    }
    
    // verify if value of hash map and expected number are the same
    private static void verify(Method m, Object[] os, HashMap<String, Integer> hashMap){
        String name = m.getDeclaringClass() + m.getName() + Arrays.toString(os);
        
        // Failure if never and there is a key
        if(times == 0 && hashMap.containsKey(name))
                throw new AssertionError(verificationFailureString(times, hashMap.get(name)));
        
        // Failure if no key and times not zero
        else if(times != 0 && !hashMap.containsKey(name))
                throw new AssertionError(verificationFailureString(times, 0));
       
        // Pass if times=0 and there is no key
        else if(times == 0 && !hashMap.containsKey(name))
                return;      
        
        // Pass if greater than expected else failure
        else if(isAtLeast){
            if(times <= hashMap.get(name))
                return;
            else
                throw new AssertionError("Verification failure: Expected greater than " + times + " calls but is " + hashMap.get(name));
                      
        }
        
        // Pass if lesser than expected else failure
        else if(isAtMost){
            if(times >= hashMap.get(name))
                return;
            else
                throw new AssertionError("Verification failure: Expected lesser than " + times + " calls but is " + hashMap.get(name));
        }
        
        // Failure if expected not equals value
        else if(!(hashMap.get(name) == times))
            throw new AssertionError(verificationFailureString(times, hashMap.get(name)));
            
    }
       
    // The method you can call in your test files to verify the number of calls.
    public static<T>  T verify(T clazz, boolean hasNumber){
        
        if(setTimesCorrectly == false)
            throw new AssertionError("The expected number of calls was not set!");
        
        verifyCallback = true;
            
        return clazz;
    }
    
    public static<T> T verify(T clazz){
        return verify(clazz, times(1));
    }
    
    // Returns the failure string for the description of the AssertionError
    private static String verificationFailureString(int expected, int value){
        return "Verification failure: Expected number of calls was " + expected + " but is " + value;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Methods for specifying number of calls">
    
    // Methods for setting the expected number of calls.
    public static boolean times(int number){
        times = number;
        setTimesCorrectly = true;
        return true;
    }
    
    public static boolean never(){
        times = 0;
        setTimesCorrectly = true;
        return true;
    }
    
    public static boolean atLeast(int number){
        times = number;
        isAtLeast = true;
        setTimesCorrectly = true;
        return true;
    }
    
    public static boolean atMost(int number){
        times = number;
        isAtMost = true;
        setTimesCorrectly = true;
        return true;
    }

    // </editor-fold>
}