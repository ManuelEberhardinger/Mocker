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
public class InvocationAtLeast extends AbstractInvocationTimes {

    private final int _atLeast;
    
    public InvocationAtLeast(int number){
        if(number < 0)
            throw new IllegalArgumentException("number");
        
        _atLeast = number;
    }
    
    @Override
    public void verify(String name, HashMap<String, Integer> hashMap) {
        if(name == null)
            throw new NullPointerException("name");
        if(hashMap == null)
            throw new NullPointerException("hashMap");
        
        String comparedString = "greater than";
        
        // Failure if no key and times not zero
        if(!hashMap.containsKey(name))
            throw new AssertionError(verificationFailureString(comparedString, _atLeast, 0));
        // Pass if lesser than expected else failure
        if(_atLeast <= hashMap.get(name))
            return;
        else
            throw new AssertionError(verificationFailureString(comparedString, _atLeast, 0));
    }
    
}
