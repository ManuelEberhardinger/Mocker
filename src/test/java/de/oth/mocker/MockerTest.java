
package de.oth.mocker;

import java.util.*;
import org.junit.Test;
import static de.oth.mocker.Mocker.mock;
import static de.oth.times.AbstractInvocationTimes.*;
import static de.oth.times.InvocationAtLeast.atLeast;
import static de.oth.times.InvocationAtMost.atMost;
import static de.oth.times.InvocationTimes.never;
import static de.oth.times.InvocationTimes.times;
import junit.framework.Assert;

public class MockerTest {

    @Test
    public void testVerifyMocking() {
        List<String> list = mock(LinkedList.class);
        list.add("e");
        list.add("w");
        list.add("e");
        list.clear();

        verify(list, times(2)).add("e");
        verify(list, never()).size();
        verify(list).clear();
        verify(list, atLeast(2)).add("e");
        verify(list, atMost(4)).add("w");
        verify(list).add("w");
        
        List<String> list2 = mock(ArrayList.class);
        list2.add("e");

        verify(list2).add("e");
        verify(list2, never()).clear();
        
        List<String> list3 = mock(LinkedList.class);
        list3.add("e");
        list3.add("2");
        list3.contains("e");
        
        // Test the return value.
        int size = list3.size();
        String testString = list3.get(0);
        
        Assert.assertEquals(0, size);
        Assert.assertNull(testString);
        
        verify(list3).add("e");
        verify(list3, never()).clear();
        verify(list3).contains("e");
        verify(list3).size();
    }    
}
