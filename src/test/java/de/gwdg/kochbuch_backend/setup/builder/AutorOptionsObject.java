package de.gwdg.kochbuch_backend.setup.builder;

import de.gwdg.kochbuch_backend.model.dto.Autor;

public class AutorOptionsObject {

    private Integer ID = 0; // Default value, can be overridden
    private String autorName = ""; // also default value can be overridden


    // Builds configured User instance
    public Autor build(){
        return new Autor(ID, autorName);
    }

    /**
     * Overrides pre-definded ID with specified value
     * @param ID
     * @return Current UserOptionsObject instance
     * */
    public AutorOptionsObject withId(Integer ID) {
        this.ID = ID;
        return this;
    }

    /**
     * Overrides pre-definded autorName with specified value
     * @param autorName
     * @return Current autorOptions instance
     */
    public AutorOptionsObject withAutorName(String autorName) {
        this.autorName = autorName;
        return this;
    }

    /**
     * static factory to create a new UserOptionsObject instance
     * @return a new AutorOptionsObject instance with default values
     */
    public static AutorOptionsObject autor(){
        return new AutorOptionsObject();
    }

}
