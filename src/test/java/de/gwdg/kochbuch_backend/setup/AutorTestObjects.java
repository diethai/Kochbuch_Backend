package de.gwdg.kochbuch_backend.setup;

import de.gwdg.kochbuch_backend.model.dto.Autor;
import de.gwdg.kochbuch_backend.setup.builder.AutorOptionsObject;

import java.util.List;
import java.util.stream.IntStream;

public class AutorTestObjects {

    /**
     * Returns user with randomized attributes
     * @return
     * */
    public static Autor any(){
        return new AutorOptionsObject().build();
    }

    /**
     *Returns builder instance to be used for specific test instance
     *
     *
     * */
    public static AutorOptionsObject builder(){
        return new AutorOptionsObject();
    }

    /**
     * Returns list of all available Autor configured for testing
     *
     * @return
     * */
    public static List<Autor> all(final int size){
        return IntStream.range(0,size)
                .mapToObj(i -> any())
                .toList();
    }


}
