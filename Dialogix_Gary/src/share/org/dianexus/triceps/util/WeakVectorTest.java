package org.dianexus.triceps.util;

import junit.framework.TestCase;

public class WeakVectorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testWeakVector(){
		WeakVector wv = new WeakVector();
		String str = new String("test");
		
		wv.add(str);
		assertEquals(str,wv.get(0));
		wv.clear();
		assertEquals(str,wv.get(0));
	}
}
