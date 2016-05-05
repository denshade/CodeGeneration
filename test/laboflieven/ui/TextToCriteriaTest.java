package laboflieven.ui;

import laboflieven.InOutParameters;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Lieven on 5-5-2016.
 */
public class TextToCriteriaTest {

    @Test
    public void testParseMultipleStrings() throws Exception {
        TextToCriteria crit = new TextToCriteria();
        List<InOutParameters> param = crit.parseMultipleStrings("1,2;1\n1,2;3");
        assertEquals(2, param.size());
    }

    @Test
    public void testCreateFromString() throws Exception {
        TextToCriteria crit = new TextToCriteria();
        InOutParameters param = crit.createFromString("1,2;1");
        assertTrue(param.expectedOutput.containsKey("r0"));
        assertEquals((Double)1.0, param.input.get("r0"));
        assertEquals((Double)2.0, param.input.get("r1"));
        assertTrue(param.input.containsKey("r0"));
        assertEquals((Double)1.0, param.expectedOutput.get("r0"));

    }
}