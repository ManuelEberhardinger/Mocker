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
public class InvocationTimes extends AbstractInvocationTimes {

    private final int _times;
    
    public InvocationTimes(int number){
        if(number < 0)
            throw new IllegalArgumentException("number");
        
        _times= number;
    }
    
    @Override
    public void verify(String name, HashMap<String, Integer> hashMap) {
        if(name == null)
            throw new NullPointerException("name");
        if(hashMap == null)
            throw new NullPointerException("hashMap");
        
        String comparedString = "number of calls was";
                
        // Failure if no key and times not zero
        if(_times != 0 && !hashMap.containsKey(name))
            throw new AssertionError(verificationFailureString(comparedString, _times, 0));
                // Pass if times=0 and there is no key
        else if(_times == 0 && !hashMap.containsKey(name))
                return; 
        else if(_times == 0 && hashMap.containsKey(name))
            throw new AssertionError(verificationFailureString(comparedString, _times, hashMap.get(name)));
        // Failure if expected not equals value
        else if(!(hashMap.get(name) == _times))
            throw new AssertionError(verificationFailureString(comparedString, _times, hashMap.get(name)));
    }    
}
