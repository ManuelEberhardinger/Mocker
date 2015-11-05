
package de.oth.mocker;

import static de.oth.times.AbstractInvocationTimes.verify;
import static de.oth.times.InvocationTimes.never;
import static de.oth.times.InvocationTimes.times;
import static de.oth.mocker.Spyer.spy;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

public class SpyerTest {
    
    @Test
    public void testVerifySpying(){
        List<String> names = new ArrayList<>();
        List<String> spyList = spy(names);
        
        spyList.add("e");
        spyList.add("e");
        spyList.add("Hallo!");
        
        // Test the return values.
        int size = spyList.size();
        String testString = spyList.get(2);
                
        Assert.assertEquals(3, size);
        Assert.assertEquals("Hallo!", testString);
        
        verify(spyList, times(2)).add("e");
        verify(spyList).size();
        verify(spyList, never()).clear();
        verify(spyList).add("Hallo!");
        verify(spyList).get(2);
    }
    
}
