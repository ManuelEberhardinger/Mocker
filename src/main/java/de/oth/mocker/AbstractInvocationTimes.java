/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.mocker;

import java.util.*;

/**
 *
 * @author manuel
 */
public abstract class AbstractInvocationTimes {
    
    private static boolean _isVerify = false;
    
    // <editor-fold defaultstate="collapsed" desc="Static methods for specifying number of calls">
    
    public static AbstractInvocationTimes times(int number){
        return new InvocationTimes(number);
    }
    
    public static AbstractInvocationTimes never(){
        return new InvocationTimes(0);
    }
    
    public static AbstractInvocationTimes atLeast(int number){
        return new InvocationAtLeast(number);
    }
    
    public static AbstractInvocationTimes atMost(int number){
        return new InvocationAtMost(number);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Static methods for calling verify">
    
    // The method you can call in your test files
    public static<T>  T verify(T clazz, AbstractInvocationTimes invTimes){
        if(clazz == null)
            throw new NullPointerException("clazz");
        if(invTimes == null)
            throw new NullPointerException("invTimes");
        
        _isVerify = true;  
        AbstractMocker.setAbstractInvocationTimes(invTimes);
        
        return clazz;
    }
    
    // Same as above but with default times=1
    public static<T> T verify(T clazz){
        return verify(clazz, times(1));
    }
    
    // </editor-fold>
    
    // Returns a boolean if the verify callback has to be called
    public static boolean isVerify(){
        boolean toReturn = _isVerify;
        _isVerify = false;
        return toReturn;
    }
    
    // The abstract method for the different types of checking the amount of method calls
    public abstract void verify(String key, HashMap<String, Integer> hashMap);
    
    // Returns the failure string for the description of the AssertionError
    protected String verificationFailureString(String compared, int expected, int value){
        if(compared == null)
            throw new NullPointerException("compared");
        
        return "Verification failure: Expected  " + compared + " " + expected + " but is " + value;
    }
}
