package ru.spbau.morsekeyboard;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: DimaTWL
 * Date: 11/13/12
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
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
}
