/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.mocker;

import static de.oth.mocker.Mocker.atLeast;
import static de.oth.mocker.Mocker.atMost;
import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static de.oth.mocker.Mocker.mock;
import static de.oth.mocker.Mocker.never;
import static de.oth.mocker.Mocker.times;
import static de.oth.mocker.Mocker.verify;

/**
 *
 * @author manuel
 */

public class MockerTest {
    
    @Test
    public void testMock() {
        
    }

    @Test
    public void testVerify() {
        List<String> list = mock(LinkedList.class);
        list.add("e");
        list.add("w");
        list.add("e");
        list.clear();
        
        List<String> list2 = mock(ArrayList.class);
        list2.add("e");
        
        verify(list2).add("e");
        verify(list2, never()).clear();
        
        verify(list, times(2)).add("e");
        verify(list, never()).size();
        verify(list).clear();
        verify(list, atLeast(2)).add("e");
        verify(list, atMost(4)).add("w");
        verify(list).add("w");
    }
    
}
