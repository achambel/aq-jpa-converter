package com.aliquantum.util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JPAConverter {
	
	private static List<String> comments;
	private static ArrayList<ArrayList<String>> OJBFields = new ArrayList<ArrayList<String>>();
	private static HashMap<String, String> JPAColumnAnnotation = new HashMap<>();
	private JPAFile jpaFile = new JPAFile();
	
	public JPAConverter(Path path, List<String> comments) {
		
		jpaFile.setClassName(path.getFileName().toString());
		this.comments = comments;
		this.setJPAColumnAnnotations();
		
	}
	
	
	private void setJPAColumnAnnotations() {
		
		this.JPAColumnAnnotation.put("column", "name=\"?\"");
		this.JPAColumnAnnotation.put("length", "length(?)");
	}

	public void generate() {
		
		searchForTable();
		searchForFields();
	}

	private void searchForTable() {
	
		Pattern pattern = Pattern.compile("table=\"(.*?)\"", Pattern.DOTALL);
		
		for (String comment : comments) {
			
			Matcher matcher = pattern.matcher(comment);
		
			if (matcher.find()) {
				// extract table name. A file should have only one table				
				jpaFile.setTable(matcher.group(1));
				break;
			}
		}
		
	}
	

	private void searchForFields() {
		
		// final String regex = "(\\/\\*\\*.+\\*\\/.+;)"; Use this to get javadoc sixtax and class member
		
		String fields = String.join("|"
				, "column=\"(.*?)\""
				, "length=\"(.*?)\""
				);
		
		Pattern pattern = Pattern.compile(fields, Pattern.DOTALL);
		ArrayList<String> listField = new ArrayList<String>();
		
		for (String comment : comments) {
			
			Matcher matcher = pattern.matcher(comment);
			
			while(matcher.find()) {

				listField.add(matcher.group());
			}
			
			if(listField.size() > 0) {
				
//				System.out.println(getJpaCollumnAnnotations(listField));
				
				HashMap annotation = new HashMap<>();
				annotation.put("annotation", getJpaCollumnAnnotations(listField));
				jpaFile.addAttribute(annotation);
				
				listField = new ArrayList<String>();
			}
			
		}
		
		System.out.println(jpaFile);
		
		
	}
	
	private String getJpaCollumnAnnotations(ArrayList<String> ojbList) {
		
		ArrayList<String> jpaAnnotationsList = new ArrayList<String>();
		
		for (String ojb : ojbList) {
			
			String[] keyValue = ojb.split("=");
			String jpaAnnotation = JPAColumnAnnotation.get(keyValue[0]);
			String value = keyValue[1].replaceAll("\"", "");
			
			if(jpaAnnotation != null) {
				String annotation = jpaAnnotation.replace("?", value);
				jpaAnnotationsList.add(annotation);
			}
			
		}
		
		String column = String.format("@Column(%s)", String.join("; ", jpaAnnotationsList));
		
		return column;
		
		
	}

}
