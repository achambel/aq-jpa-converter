package com.aliquantum.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.aliquantum.util.OJB2JPA;

public class OJB2JPATest {

	@Test
	public void testProccessFile() {
		
		OJB2JPA converter = new OJB2JPA("/home/achambel/labs/java/ojb/",
				"/home/achambel/labs/java/aq-jpa-converter/src/com/aliquantum/jpa/");
		
		
		converter.start();
	}

}
