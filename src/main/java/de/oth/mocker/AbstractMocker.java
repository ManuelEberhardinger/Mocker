/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.mocker;

import java.util.HashMap;

/**
 *
 * @author manuel
 */
public abstract class AbstractMocker {

    protected static AbstractInvocationTimes _invTimes;
    
    // Sets the InvocationTimes object
    public static void setAbstractInvocationTimes(AbstractInvocationTimes invTimes){
        if(invTimes == null)
            throw new NullPointerException("invTimes");
        
        _invTimes = invTimes;
    }
    
    // Sets or increases the value of the hash map.
    protected void setOrIncreaseHashMap(String key, HashMap<String, Integer> hashMap){
        if(hashMap.containsKey(key)){
            int value = hashMap.get(key);
            value++;
            hashMap.replace(key, value);
        }
        else
            hashMap.put(key, 1);
    }
    
    // Abstract Method to create different mocking types.
    public abstract<T> T create();
}
