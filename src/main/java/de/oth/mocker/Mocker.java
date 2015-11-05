
package de.oth.mocker;

public class Mocker {
    
    // Creates a mock of the given class.
    public static<T> T mock(Class<?> clazz){
        
        AbstractMocker mocker = new MockerFactory(clazz);
        
        return mocker.create();
    }
}