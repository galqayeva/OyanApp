/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Exception;

/**
 *
 * @author Eldar Hasanov
 */
public class DatabaseException extends Exception {

    public DatabaseException() {

    }

    public DatabaseException(String message) {
        super(message);
    }

}
