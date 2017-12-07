package com.manulaiko.tabitha.exceptions;

/**
 * NotFound exception.
 *
 * This exception is thrown when you try to use
 * something that wasn't find.
 *
 * The constructor accepts 2 parameters:
 * * name: The name of the thing that wasn't find
 * * value: The value of name
 *
 * Example:
 *
 * try {
 * String database_name = settings.get("database.name");
 * } catch(NotFound e) {
 * Console.println("The "+ e.name +" "+ e.value +" was not found!");
 * }
 *
 * @author Manulaiko <manulaiko@gmail.com>
 */
public class NotFound extends Exception {
    /**
     * Thing's name
     */
    public String name = "";

    /**
     * Thing's value
     */
    public String value = "";

    /**
     * Constructor
     *
     * @param name  Thing's name
     * @param value Thing's value
     */
    public NotFound(String name, String value) {
        super("The " + name + " " + value + " was not found!");

        this.name = name;
        this.value = value;
    }
}
