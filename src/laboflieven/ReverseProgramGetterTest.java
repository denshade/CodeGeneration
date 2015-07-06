package laboflieven;

import junit.framework.TestCase;

import java.util.Collections;

/**
 * Created by Lieven on 6/07/2015.
 */
public class ReverseProgramGetterTest extends TestCase {

    public void testIterate() throws Exception {
        ReverseProgramGetter ev = new ReverseProgramGetter(213);
        assertNotNull(ev.iterate(4,4));

        ev = new ReverseProgramGetter(213000);
        assertNull(ev.iterate(2, 2));


    }
}