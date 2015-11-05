
package de.oth.mocker;

public class Spyer {
    
    // Creates a spy of the given class.
    public static<T> T spy(T clazz){

        AbstractMocker spyer = new SpyerFactory(clazz);
        
        return spyer.create();
    }
    
}
