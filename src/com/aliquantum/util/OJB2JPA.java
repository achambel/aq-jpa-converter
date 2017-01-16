package com.aliquantum.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OJB2JPA {
	
	String sourceFolder;
	String destinationFolder;
	
	public OJB2JPA(String sourceFolder, String destinationFolder) {
		
		this.sourceFolder = sourceFolder;
		this.destinationFolder = destinationFolder;
	}
	
	private void proccessFile() {
		
		try {
			
			DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get(sourceFolder), path -> path.toString().toLowerCase().endsWith(".java"));
			
			for (Path path : files) {
				
				new JPAConverter(path, extractCommentsFromFile(path)).generate();
					
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private List<String> extractCommentsFromFile(Path path) throws IOException {
		
		List<String> list = new ArrayList<>();
		List<String> comments = new ArrayList<>();
		
		for (String line : Files.readAllLines(path)) {
			
			if (line.trim().startsWith("/**") || line.trim().startsWith("*")) {
				list.add(line);
			}
			
			if(line.trim().trim().endsWith("*/")) {
								
				comments.add(String.join(System.getProperty("line.separator"), list));
				
				list = new ArrayList<>();
			}
		}
		
		return comments;
	}
	
	public void start() {
		
		System.out.println("Starting...");
		proccessFile();
	}
}
