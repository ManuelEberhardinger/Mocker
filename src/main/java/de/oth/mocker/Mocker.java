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
    
    private static final HashMap<String, Integer> hashMap= new HashMap<>();
    private static int value;
    private static boolean verifyCallback = false;
    private static int times;
    private static boolean isAtLeast = false;
    private static boolean isAtMost = false;
    private static boolean setTimesCorrectly = false;
    
    // Creates a mock of the given class.
    public static<T> T mock(Class<?> clazz){
        
        Enhancer e = new Enhancer();
        
        e.setSuperclass(clazz);
        
        setStandardCallback(e);
        
        return (T) e.create();
    }
    
    // Sets or increases the value of the hash map.
    private static void setOrIncreaseHashMap(String key){
        if(hashMap.containsKey(key)){
            value = hashMap.get(key);
            value++;
            hashMap.replace(key, value);
        }
        else
            hashMap.put(key, 1);
    }
    
    // verify if value of hash map and expected number are the same
    private static void verify(Method m, Object[] os){
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
                throw new AssertionError("Verification failure: Expected greater than " + times + " calls but was " + hashMap.get(name));
                      
        }
        
        // Pass if lesser than expected else failure
        else if(isAtMost){
            if(times >= hashMap.get(name))
                return;
            else
                throw new AssertionError("Verification failure: Expected lesser than " + times + " calls but was " + hashMap.get(name));
        }
        
        // Failure if expected not equals value
        else if(!(hashMap.get(name) == times))
            throw new AssertionError(verificationFailureString(times, hashMap.get(name)));
            
    }
    
    // Returns the failure string for the description of the AssertionError
    private static String verificationFailureString(int expected, int value){
        return "Verification failure: Expected number of calls was " + expected + " but is " + value;
    }
    
    // Sets the standard Callback of the Enhancer
    private static void setStandardCallback(Enhancer e){
        e.setCallback(new MethodInterceptor(){
            
        @Override
        public Object intercept(Object o, Method m, Object[] os, MethodProxy ms) throws Throwable {
            if(verifyCallback)
                verify(m, os);
            else
                setOrIncreaseHashMap(m.getDeclaringClass() + m.getName() + Arrays.toString(os));

            verifyCallback = isAtMost = isAtLeast = setTimesCorrectly = false;  
            return null;
        }
        
        });
    }
    
    // The method you can call in your test files to verify the number of calls.
    public static<T>  T verify(T clazz, boolean... hasNumber){

        if(hasNumber.length == 0)
        {
            times = 1;
            setTimesCorrectly = true;
        }
        else if(hasNumber.length > 1)
            throw new AssertionError("Too many arguments for times!");
        
        if(setTimesCorrectly == false)
            throw new AssertionError("The expected number of calls was not set!");
        
        verifyCallback = true;
            
        return clazz;
    }
    
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

}
