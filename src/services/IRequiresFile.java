package services;

import java.io.File;

/**
 * Interface denoting a lambda that requires a file to be invoked.
 */
public interface IRequiresFile {
    void invoke(File file);
}