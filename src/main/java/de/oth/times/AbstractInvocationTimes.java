
package de.oth.times;

import de.oth.mocker.AbstractMocker;
import java.util.*;
import static de.oth.times.InvocationTimes.*;

public abstract class AbstractInvocationTimes {
    
    private static boolean _isVerify = false;
    
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
    public boolean isVerify(){
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
