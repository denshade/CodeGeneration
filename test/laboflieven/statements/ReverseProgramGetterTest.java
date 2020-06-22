package laboflieven;

import junit.framework.TestCase;

/**
 * Created by Lieven on 6/07/2015.
 */
public class ReverseProgramIteratorTest extends TestCase {

    public void testIterate() throws Exception {
        ReverseProgramIterator ev = new ReverseProgramIterator(213);
        assertNotNull(ev.iterate(4,4));

        ev = new ReverseProgramIterator(213000);
        assertNull(ev.iterate(2, 2));


    }
}