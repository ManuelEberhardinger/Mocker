
package de.oth.times;

import java.util.HashMap;

// The only way to create an object is with the static methods to verify
// the number of calls 
public class InvocationTimes extends AbstractInvocationTimes {

    private final int _times;
    
    // Creates a new InvocationTimes object
    private InvocationTimes(int number){
        if(number < 0)
            throw new IllegalArgumentException("number");
        
        _times= number;
    }
    
    // Method to set the expected number of method calls
    // Returns a new InvocationTimes object
    public static AbstractInvocationTimes times(int number){
        return new InvocationTimes(number);
    }
     
    // Method to set expected number of method calls to zero
    // Returns a new InvocationTimes object
    public static AbstractInvocationTimes never(){
        return new InvocationTimes(0);
    }
    
    // Verify if it's the right amount of method calls
    // If not an AssertionError is thrown -> Unit tests fail
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
