package com.example.javafxapps;

import java.util.LinkedList;
import java.util.Scanner;

public class PL {
    
    static Scanner sc;
    static LinkedList<Employee> empll;
    
    public static void main(String[] args) {
        int yn = 1;
        
        sc = new Scanner(System.in);
        empll = new LinkedList<Employee>();
        
        while(yn == 1){
            //E1 = new Employee();
            empin(new Employee());
            System.out.println("¿Desea ingresar un nuevo empleado? (0/1g)");
            yn = Integer.parseInt(sc.nextLine());
        }
        
        for (int i = 0; i < empll.size(); i++){
            empll.get(i).printemp(empll.get(i));
        }
        
        
        for (int i = 0; i < 7; i++){
            //System.out.println("");
        }
    }
    
    static void empin(Employee in){
        System.out.println("Ingresar nombre: ");
        String UN = sc.nextLine();
        System.out.println("Ingresar dirección: ");
        String UD = sc.nextLine();
        System.out.println("Ingresar edad: ");
        String UE = sc.nextLine();
        System.out.println("Ingresar sexo: ");
        String SN = sc.nextLine();
        System.out.println("Ingresar cédula: ");
        String SC = sc.nextLine();
        System.out.println("Ingresar código: ");
        String SCOD = sc.nextLine();
        System.out.println("Ingresar Salario: ");
        String SSAL = sc.nextLine();
        in.empin(UN, UD, UE, SN, SC, SCOD, SSAL, empll);
    }
    
}
