
package de.oth.times;

import java.util.HashMap;

// The only way to create an object is with the static methods to verify
// the number of calls 
public class InvocationAtLeast extends AbstractInvocationTimes {

    private final int _atLeast;
    
    // Creates a new InvocationAtLeast object
    private InvocationAtLeast(int number){
        if(number < 0)
            throw new IllegalArgumentException("number");
        
        _atLeast = number;
    }
    
    // Method to set the expected number of method calls
    // Returns a new InvocationTimes object    
    public static AbstractInvocationTimes atLeast(int number){
        return new InvocationAtLeast(number);
    }
    
    // Verify if it's the right amount of method calls
    // If not an AssertionError is thrown -> Unit tests fail    
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
