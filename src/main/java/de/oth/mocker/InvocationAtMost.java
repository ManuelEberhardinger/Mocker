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
public class InvocationAtMost extends AbstractInvocationTimes{
    
    private final int _atMost;
    
    public InvocationAtMost(int number){
        if(number < 0)
            throw new IllegalArgumentException("number");
        
        _atMost = number;
    }
    
    @Override
    public void verify(String name, HashMap<String, Integer> hashMap) {
        if(name == null)
            throw new NullPointerException("name");
        if(hashMap == null)
            throw new NullPointerException("hashMap");
        
        String comparedString = "lesser than";
        
        // Failure if no key and times not zero
        if(!hashMap.containsKey(name))
            throw new AssertionError(verificationFailureString(comparedString, _atMost, 0));
        // Pass if lesser than expected else failure
        if(_atMost >= hashMap.get(name))
            return;
        else
            throw new AssertionError(verificationFailureString(comparedString, _atMost, 0));
    }    
}
