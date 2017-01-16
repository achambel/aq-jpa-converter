package com.aliquantum.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JPAFile {
	
	private final String packageName = "com.aliquantum.objects.jpa";
	private String className;
	private String table;
	private List<HashMap<String, String>> attributes = new ArrayList<>();
	
	public void setClassName(String className) {
		this.className = className.replaceAll(".java$", "");
	}
	
	public String getClassName() {
		return this.capitalize(this.className);
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getTable() {
		return String.format("@Table(name=\"%s\")", this.table.replaceAll("\"", ""));
	}
	
	private String capitalize(String str) {
		
		 return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	public void addAttribute(HashMap<String, String> attr) {
		
		attributes.add(attr);
		
	}
	
	private String generateMethods() {
		
		StringBuilder method = new StringBuilder();
		
		for (HashMap<String, String> attr : attributes) {
			
			method.append("\t" + generateGettersAndSetters(attr.get("annotation")) + "\n");
		}
		
		return method.toString();
	}
	
	private String generateGettersAndSetters(String annotation) {
		
		StringBuilder str = new StringBuilder();
		
		if(annotation.startsWith("@Column")) {
			
		}
		
		return str.toString();
	}
	
	public String toString() {
		
		StringBuilder strClass = new StringBuilder();
		
		
		strClass.append(String.format("package %s;%n%n", packageName));
		strClass.append("@Entity");
		strClass.append(String.format("%n%s%n", getTable()));
		strClass.append(String.format("public class %s {%n%n", getClassName()));
		strClass.append(generateMethods());
		strClass.append(String.format("%n}"));
		
		return strClass.toString();
	}
}
