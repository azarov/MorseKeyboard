package ru.spbau.morsekeyboard;

/**
 * Created with IntelliJ IDEA.
 * User: DimaTWL
 * Date: 11/13/12
 * Time: 11:38 AM
 * This class should be used to resolve input symbol using press duration.
 */
public class MorseDurationResolver {
    private Long dotDuration;


    /**
     * Should be used to save state of MorseDurationResolver.
     * @return dotDuration.
     */
    public Long getDotDuration() {
        return dotDuration;
    }

    /**
     * Creates MorseDurationResolver with specified duration for dot.
     * @param initialDotDuration - duration for dot.
     */
    public MorseDurationResolver(Long initialDotDuration){
        dotDuration = initialDotDuration;
    }

    /**
     * Resolves input symbol using press duration without adjustment of dotDuration.
     * Should be used for fast resolve.
     * @param duration - press duration.
     * @return  '.' if duration <= dotDuration
     *          '-' otherwise
     */
    public Character getSymbolFromDuration(Long duration) {
        if (duration <= dotDuration) {
            return '.';
        } else {
            return '-';
        }
    }

    /**
     * Resolves input symbol using press duration with adjustment of dotDuration.
     * Should be used on button release ONLY.
     * @param duration - press duration.
     * @return  '.' if duration <= dotDuration
     *          '-' otherwise
     */
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
