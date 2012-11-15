package ru.spbau.morsekeyboard;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: DimaTWL
 * Date: 11/13/12
 * Time: 3:47 PM
 * Test class for MorseDurationResolver
 */
public class MorseDurationResolverTest {

    @Test
    public void smokeTest() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
    }

    @Test
    public void getterTest() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        assertEquals(r.getDotDuration(), new Long(300));
    }

    @Test
    public void resolvingTest() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)301), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void noAdjustmentTest0() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //No adjustment:
        assertEquals(r.onRelease((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)299), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)301), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void noAdjustmentTest1() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //No adjustment:
        assertEquals(r.onRelease((long)30), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)299), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)301), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void noAdjustmentTest2() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //No adjustment:
        assertEquals(r.onRelease((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)299), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)301), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseLargeDotAdjustmentTest0() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from large dot duration:
        assertEquals(r.onRelease((long)280), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)301), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseLargeDotAdjustmentTest1() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from large dot duration:
        assertEquals(r.onRelease((long)271), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)301), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseLargeDotAdjustmentTest2() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from large dot duration:
        assertEquals(r.onRelease((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)300), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)301), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseSmallDotAdjustmentTest0() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from small dot duration:
        assertEquals(r.onRelease((long)10), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)271), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseSmallDotAdjustmentTest1() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from small dot duration:
        assertEquals(r.onRelease(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)271), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseSmallDotAdjustmentTest2() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from small dot duration:
        assertEquals(r.onRelease((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)271), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseSmallDotAdjustmentTest3() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from small dot duration:
        assertEquals(r.onRelease((long)29), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)271), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseSmallPointAdjustmentTest0() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from small point duration:
        assertEquals(r.onRelease((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)271), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseSmallPointAdjustmentTest1() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from small point duration:
        assertEquals(r.onRelease((long)301), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)271), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }

    @Test
    public void increaseSmallPointAdjustmentTest2() {
        MorseDurationResolver r = new MorseDurationResolver((long)300);
        //Adjusment to increase dotDuration from small point duration:
        assertEquals(r.onRelease((long)329), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)100), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)1), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)0), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)-1), new Character('.'));
        assertEquals(r.getSymbolFromDuration(Long.MIN_VALUE), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)270), new Character('.'));
        assertEquals(r.getSymbolFromDuration((long)271), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)310), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)330), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)331), new Character('-'));
        assertEquals(r.getSymbolFromDuration((long)500), new Character('-'));
        assertEquals(r.getSymbolFromDuration(Long.MAX_VALUE), new Character('-'));
    }
}
