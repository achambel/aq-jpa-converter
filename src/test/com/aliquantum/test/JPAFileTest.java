package com.aliquantum.test;

import org.junit.Test;
import static org.junit.Assert.*;

import com.aliquantum.util.JPAFile;

public class JPAFileTest {
	
	JPAFile jpaFile = new JPAFile();

	@Test
	public void testClassNameCapitalize() {
		
		jpaFile.setClassName("jPACLASS.java");
		assertEquals("Jpaclass", jpaFile.getClassName());
	}
	
	@Test
	public void testTableAnnotation() {
		
		jpaFile.setTable("tb_account");
		assertEquals(jpaFile.getTable(), "@Table(name=\"tb_account\")");
	}

}
