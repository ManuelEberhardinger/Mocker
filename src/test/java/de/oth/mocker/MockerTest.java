/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.mocker;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static de.oth.mocker.Mocker.mock;
import static de.oth.mocker.Mocker.never;
import static de.oth.mocker.Mocker.times;
import static de.oth.mocker.Mocker.verify;

/**
 *
 * @author manuel
 */

public class MockerTest {
    
    public MockerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testMock() {
        
    }

    @Test
    public void testVerify() {
        List<String> list = mock(ArrayList.class);
        list.add("e");
        list.add("w");
        list.add("e");
        list.clear();
        
        verify(list, times(3)).add("e");
        verify(list, never()).size();
        verify(list).clear();
    }
    
}
