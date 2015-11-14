package fr.inria.gforge.spoon.processors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimitiveTypeTest {

    @Test
    public void testGetTypeDefaultValue() {
        String longString = "long";
        String intString = "iNt";
        String voidString = "VOID";

        assertEquals("0L", PrimitiveType.getTypeDefaultValue(longString));
        assertEquals("0", PrimitiveType.getTypeDefaultValue(intString));
        assertEquals("void", PrimitiveType.getTypeDefaultValue(voidString));

    }
}
