package ru.spbau.morsekeyboard;

/**
 * Created with IntelliJ IDEA.
 * User: DimaTWL
 * Date: 11/12/12
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class BadCodeException extends Exception {
    public BadCodeException(String detailMessage) {
        super(detailMessage);
    }
}
