package etu.univlille.fr.projetmodei3.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class FolderParser {

	public static Map<File, List<String>> getCompatibleFiles(File folder){
		Map<File, List<String>> fileMap = new HashMap<>();
		
		if(folder == null || !folder.exists() || !folder.canRead() || !folder.isDirectory())return null;
		
		File[] subFiles = folder.listFiles();
		
		for(File fle:subFiles) {
			List<String> lst = new ArrayList<>();
			if(fle.canRead() && fle.isFile()) {
				List<String> infos = getFileInfos(fle);
				if(infos == null) {continue;}
				lst.addAll(infos);
			}
			lst.add("size: "+fle.length());
			fileMap.put(fle, lst);
		}
		
		return fileMap;
	}
	
	public static List<String> getFileInfos(File file){
		List<String> lst = new ArrayList<>();
		if(file == null || !file.exists() || !file.canRead() || !file.isFile())return null;
		
		BufferedReader br;
		String fType = null;
		try {
			br = new BufferedReader(new FileReader(file));
			fType = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(fType == null || !fType.equalsIgnoreCase("ply"))return null;
		
		List<List<String>> header = Parser.parseHeader(file);
		for(List<String> line:header) {
			StringJoiner sj = new StringJoiner(" ");
			if(line.size() > 1 && line.get(0).equalsIgnoreCase("comment")) {
				if(line.size() > 4 && line.get(1).equalsIgnoreCase("created") && line.get(2).equalsIgnoreCase("by")) {
					for(int i = 1; i < line.size(); i++) {
						sj.add(line.get(i));
					}
					lst.add(sj.toString());
				}
			}else if(line.size() == 3 && line.get(0).equalsIgnoreCase("element")) {
				for(int i = 1; i < line.size(); i++) {
					sj.add(line.get(i));
				}
				lst.add(sj.toString());
			}
		}
		
		Collections.sort(lst);
		
		return lst;
	}
	
	public static List<String> getFileInfos(File file, FileDescriptor fd){
		List<String> lst = new ArrayList<>();
		if(file == null || !file.exists() || !file.canRead() || !file.isFile())return null;
		
		BufferedReader br;
		String fType = null;
		try {
			br = new BufferedReader(new FileReader(file));
			fType = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(fType == null || !fType.equalsIgnoreCase("ply"))return null;
		
		List<List<String>> header = Parser.parseHeader(file);
		for(List<String> line:header) {
			StringJoiner sj = new StringJoiner(" ");
			if(line.size() > 1 && line.get(0).equalsIgnoreCase("comment")) {
				if(line.size() > 4 && line.get(1).equalsIgnoreCase("created") && line.get(2).equalsIgnoreCase("by")) {
					for(int i = 1; i < line.size(); i++) {
						sj.add(line.get(i));
						
					}
					

					fd.setCreator(sj.toString().substring(10));
					lst.add(sj.toString());
				}	
			}else if(line.size() == 3 && line.get(0).equalsIgnoreCase("element")) {
				for(int i = 1; i < line.size(); i++) {
					
					sj.add(line.get(i));
				}
				
				if(line.get(1).equalsIgnoreCase("vertex")) {
					fd.setVertex(sj.toString().substring(6));
				}else if(line.get(1).equalsIgnoreCase("face")) {
					fd.setFace(sj.toString().substring(4));
				}
				
				lst.add(sj.toString());
			}
		}
		
		Collections.sort(lst);
		
		return lst;
	}
	
}
