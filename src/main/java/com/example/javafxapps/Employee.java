package com.example.javafxapps;//package com.mycocos.pl;
//name, address, age, sex, ID, code, salary

import java.util.LinkedList;

public class Employee {
    private String name, address, id, code;
    private byte age;
    private double salary;
    private char sex;
    
    public void setName(String sn){
        name = sn; 
    }
    public String getName(){
        return name;
    }
    
    public void setAddress(String sa){
        address = sa;
    }
    public String getAddress(){
        return address;
    }
    
    public void setAge(String sage){
        age = Byte.parseByte(sage);
    }
    public byte getAge(){
        return age;
    }
    
    public void setSex(String ss){
        sex = ss.charAt(0);
    }
    public char getSex(){
        return sex;
    }
    
    public void setID(String si){
        id = si;
    }
    public String getID(){
        return id;
    }
    
    public void setCode(String sc){
        code = sc;
    }
    public String getCode(){
        return code;
    }
    
    public void setSalary(String ssal){
        salary = Double.parseDouble(ssal);
    }
    public double getSalary(){
        return salary;
    }
    
    
    /**
     * Setter method for employee data
     * @param nam Name
     * @param add Address
     * @param ag Age
     * @param se Sex
     * @param i ID
     * @param cod Code
     * @param Salar Salary
     * @param lin LinkedList <Employee>
     */
    public void empin(String nam, String add, String ag, String se, String i, String cod, String Salar, LinkedList<Employee> lin){
        setName(nam);
        setAddress(add);
        setAge(ag);
        setSex(se);
        setID(i);
        setCode(cod);
        setSalary(Salar);
        lin.addFirst(this);
    }
    
    public void printemp(Employee empin){
        System.out.println("Nombre: " + empin.getName() + " " + 
                           "Dirección: " + empin.getAddress() + " " + 
                           "Edad: " + empin.getAge() + " " + 
                           "Sexo: " + empin.getSex() + " " + 
                           "ID: " + empin.getID() + " " + 
                           "Código: " + empin.getCode() + " " + 
                           "Salario: " + empin.getSalary());
        
    }
    
}
