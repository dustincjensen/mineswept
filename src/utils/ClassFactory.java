package utils;

import bootstrap.BootstrapModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ClassFactory {
    private static Injector injector;

    /**
     * Returns an instance of the class with it's dependencies configured.
     * 
     * @param c the class to create an instance of.
     * @return an instance of the class.
     */
    public static <T> T create(Class<T> c) {
        // We want to make sure we use the same injector when creating classes,
        // otherwise we may end up creating more instances of classes than we had hoped.
        if (injector == null) {
            injector = Guice.createInjector(new BootstrapModule());
        }
        return (T) injector.getInstance(c);
    }
}