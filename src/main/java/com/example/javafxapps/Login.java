package com.example.javafxapps;

import java.util.ArrayList;

public class Login implements Cloneable {
	private int uid;
	private String password;
	private ArrayList<Integer> list;
	
	public Login(int id, String pass) {
		uid = id;
		password = pass;
		list = new ArrayList<>();
	}
	
	//if not overridden, we use Object's toString() which
	//simply returns the type of the object (variable) along with
	//a unique hexadecimal number for that object
	@Override
	public String toString() {
		return uid+" "+password+" "+list;
//		return id+" "+pass;
	}
	
	//customize cloning process
	public Object clone() {
		try {
			// start w/ Java's shallow-copy clone
			// object class has the clone() method
			Login copy = (Login) super.clone();

			// customization
			// create a new String with the same contents
			// as in _my_ (this) password
			copy.password = new String(password);
			// copy.setPassword(...);

			// clone will start w/ an empty list
			// based on application and context
			copy.list = new ArrayList<Integer>();
			
			//e.g course list
			//clone should have a new list w/ all
			//values from original's list
			
			return copy;
			
		} catch (CloneNotSupportedException e) {
			System.err.println("Login cloning was unsuccessful");
			return null;
		}
	}
	
	public void addToList(int i) {
		list.add(i);
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		Login login1 = new Login(1111, "pass1");
		login1.addToList(2);
		login1.addToList(3);
		System.out.println(login1);
		
		Login login1copy = (Login) login1.clone();;
		System.out.println(login1copy);
		
		login1copy.addToList(10);
		
		System.out.println("original: "+login1);
		System.out.println("clone: "+login1copy);
		
		Login loginalias = login1;
		System.out.println(loginalias);
	}
}
