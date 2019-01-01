package services;

import java.io.File;

/**
 * Interface denoting a lambda that requires a file to be invoked.
 * It also denotes that the method invoked has a return value.
 * 
 * @param <T> the return type to expect from the invocation of the lambda.
 */
public interface IRequiresFileAndHasReturn<T> {
    T invoke(File file);
}