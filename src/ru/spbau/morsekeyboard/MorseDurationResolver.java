package ru.spbau.morsekeyboard;

/**
 * Created with IntelliJ IDEA.
 * User: DimaTWL
 * Date: 11/13/12
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class MorseDurationResolver {
    private Long dotDuration;

    public MorseDurationResolver(Long initialDotDuration){
        dotDuration = initialDotDuration;
    }

    public Character getSymbolFromDuration(Long duration) {
        if (duration <= dotDuration) {
            return '.';
        } else {
            return '-';
        }
    }

}
