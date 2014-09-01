package com.gogo.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

import com.gogo.domain.User;

public class Test {

public static void main(String[] args) {
		ArrayList<User> list=new ArrayList<User>();
		User s1=new User();
		s1.setUserName("蕾蕾");
		User s2=new User();
		s2.setUserName("leilei");
		list.add(s1);
		list.add(s2);
		
		StringWriter str=new StringWriter();
		
		
		try {
			OutputStream os = new FileOutputStream(new File("d:/1.txt"));
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.writeValue(os,list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(str);
	}
}
