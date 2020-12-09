package etu.univlille.fr.projetmodei3.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser {

	public static Model3D parse(File file) throws Exception {
		List<List<String>> headerList = parseHeader(file);
		List<List<String>> list = new ArrayList<>();
		boolean body = false;
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), " ");
				int idx = 0;
				String key = null;
				List<String> elements = new ArrayList<>();
				while(tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					if(token.length() > 0) {
						if(idx == 0) {
							key = token;
						}
						elements.add(token);
						idx++;
					}
				}
				if(body && key != null && !key.equalsIgnoreCase("comment"))list.add(elements);
				if(!body && key != null && key.equalsIgnoreCase("end_header"))body = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(scanner != null)scanner.close();
		}
		
		List<Element> elements = new ArrayList<>();
		for(List<String> headline:headerList) {
			if(headline.get(0).equalsIgnoreCase("element")) {
				if(headline.size() != 3)throw new NoSuchElementException("an element must have at least and only 2 arguments");
				int count = -1;
				try {
					count = Integer.parseInt(headline.get(2));
				}catch(Exception e) {
					throw new IllegalArgumentException("the element's count must be a positive Integer");
				}
				if(count < 0)throw new IllegalArgumentException("the element's count must be a positive Integer");
				elements.add(new Element(headline.get(1), count));
			}else if(headline.get(0).equalsIgnoreCase("property")) {
				if(elements.size() == 0)throw new NullPointerException("you must define an element before a property");
				if(headline.size() < 3)throw new NoSuchElementException("a property must have at least 2 arguments");
				if(headline.get(1).equalsIgnoreCase("list") && headline.size() < 5)throw new NoSuchElementException("list property must have at least 4 arguments");
				Element element = elements.get(elements.size()-1);
				int fe = 1;
				if(headline.get(1).equalsIgnoreCase("list"))fe++;
				if(!element.addProp(headline.get(headline.size()-1), headline.subList(fe, headline.size()-1))) {
					throw new IllegalArgumentException(headline.get(headline.size()-1)+" already exist in the properties");
				}
			}
		}
		
		Model3D model = new Model3D();
		List<Point> vertexes = new ArrayList<>();
		
		int intidx = 0;
		for(Element el:elements) {
			System.out.println("parsing: "+el.name+" ["+el.count+"]");
			List<Entry<String, List<String>>> propList = new ArrayList<>();
			for(Entry<String, List<String>> prop:el.properties.entrySet()) {
				propList.add(prop);
			}
			if(list.size() < intidx+el.count) throw new NoSuchElementException("missing arguments for element "+el.name);
			for(int i = intidx; i < intidx+el.count; i++) {
				List<String> line = list.get(i);
				Point point = new Point();
				Face face = new Face();
				if(line.size() < el.properties.size()) throw new NoSuchElementException("missing elements on declared value for element "+el.name);
				if(el.name.equalsIgnoreCase("face")) {
					int lstsz = (int) getDoubleValueFromArg(propList.get(0).getValue().get(0).toLowerCase(), line.get(0));
					if(lstsz != line.size()-1) throw new NoSuchElementException("arguments given for the list didn't correspond to the declared size");
				}
				for(int e = 0; e < line.size(); e++) {
					if(el.name.equalsIgnoreCase("vertex")) {
						String datatype = propList.get(e).getValue().get(0).toLowerCase();
						String dataname = propList.get(e).getKey().toLowerCase();
						if(dataname.equals("x") || dataname.equals("y") || dataname.equals("z") || dataname.equals("nx") || dataname.equals("ny") || dataname.equals("nz")) {
							double value = getDoubleValueFromArg(datatype, line.get(e));
							switch(dataname) {
								case "x":
									point.setX(value);
									break;
								case "y":
									point.setY(value);
									break;
								case "z":
									point.setZ(value);
									break;
								default:
									break;
							}
						}
					}else if(el.name.equalsIgnoreCase("face")) {
						if(e != 0) {
							int ptidx = (int) getDoubleValueFromArg(propList.get(0).getValue().get(1).toLowerCase(), line.get(e));
							if(ptidx >= vertexes.size())throw new NullPointerException("argument \""+line.get(e)+"\" pointing to an unexisting vertex");
							face.addPoints(vertexes.get(ptidx));
						}
					}
				}
				if(el.name.equalsIgnoreCase("vertex")) {
					vertexes.add(point);
				}else if(el.name.equalsIgnoreCase("face")) {
					model.addFaces(face);
				}
			}
			intidx = intidx+el.count;
		}
		System.out.println("parsing completed!");
		
		return model;
	}
	
	public static double getDoubleValueFromArg(String type, String arg) {
		double value = 0;
		if(type.startsWith("short") || type.startsWith("ushort")) {
			try {
				value = Short.parseShort(arg);
			} catch (Exception ex) {
				throw new IllegalArgumentException("non matching arg declared type \""+type+"\" and arg value \""+arg+"\"");
			}
		}else if(type.startsWith("char") || type.startsWith("int")) {
			try {
				value = Integer.parseInt(arg);
			} catch (Exception ex) {
				throw new IllegalArgumentException("non matching arg declared type \""+type+"\" and arg value \""+arg+"\"");
			}
		}else if(type.startsWith("uchar") || type.startsWith("uint")) {
			try {
				value = Integer.parseUnsignedInt(arg);
			} catch (Exception ex) {
				throw new IllegalArgumentException("non matching arg declared type \""+type+"\" and arg value \""+arg+"\"");
			}
		}else if(type.startsWith("float")) {
			try {
				Float f = Float.parseFloat(arg);
				value = f.doubleValue();
			} catch (Exception ex) {
				throw new IllegalArgumentException("non matching arg declared type \""+type+"\" and arg value \""+arg+"\"");
			}
		}else if(type.startsWith("double")) {
			try {
				value = Double.parseDouble(arg);
			} catch (Exception ex) {
				throw new IllegalArgumentException("non matching arg declared type \""+type+"\" and arg value \""+arg+"\"");
			}
		}
		return value;
	}
	
	public static List<List<String>> parseHeader(File file){
		List<List<String>> list = new ArrayList<>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				StringTokenizer tokenizer = new StringTokenizer(scanner.nextLine(), " ");
				int idx = 0;
				String key = null;
				List<String> elements = new ArrayList<>();
				while(tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					if(token.length() > 0) {
						if(idx == 0) {
							key = token;
						}
						elements.add(token);
						idx++;
					}
				}
				if(key != null && key.equalsIgnoreCase("end_header"))break;
				if(key != null)list.add(elements);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(scanner != null)scanner.close();
		}
		
		return list;
	}
	
	public static class Element {
		
		public String name;
		public int count;
		
		public LinkedHashMap<String, List<String>> properties = new LinkedHashMap<>();
		
		public Element(String name, int count) {
			this.name = name;
			this.count = count;
		}
		
		public boolean addProp(String name, List<String> args) {
			if(name == null || args == null || args.size() == 0 || properties.containsKey(name))return false;
			properties.put(name, args);
			return true;
		}
		
	}
	
	public static int parseNb(File file, String element) {
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			StringTokenizer st;
			while(sc.hasNextLine()) {
				st = new StringTokenizer(sc.nextLine());
				if( (st.hasMoreTokens() && st.nextToken().equals("element") )&& (st.hasMoreTokens() &&st.nextToken().equals(element) )) {
					if(st.hasMoreTokens()) {
						int nb = Integer.parseInt(st.nextToken());
						System.out.println("On a "+nb+ " "+element +" pour le fichier" +file.getName());
						return nb;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(sc != null) sc.close();
		}
		return -1;
	}
	
}
