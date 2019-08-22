package interfaces;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vult on 2018-06-18.
 *
 */
@FunctionalInterface
public interface ThrowConsumer<T extends File> {

    void accept(final T e) throws IOException, ClassNotFoundException;


}
