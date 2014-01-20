package goupe_a1.mavenproject7;

import junit.framework.TestCase;

public class SomeClassTest extends TestCase
{
    public void testDoSomething()
    {
        SomeClass sc = new SomeClass();
        assertEquals("Hello World!", sc.saySomething());
    }
}
