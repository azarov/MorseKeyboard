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

    public Character onRelease(Long duration) {
        if (duration <= dotDuration) {
            if (duration > (dotDuration - dotDuration/10)) {
                dotDuration += dotDuration/10;
            } else if (duration < dotDuration/10) {
                dotDuration -= dotDuration/10;
            }
            return '.';
        } else {
            if (duration < (dotDuration + dotDuration/10)) {
                dotDuration -= dotDuration/10;
            }
            return '-';
        }
    }

}
