package com.bridgelabz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// welcome to employee payroll
public class EmployeePayrollService
{
   public enum IOService {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
   private List<EmployeePayrollData> employeePayrollList;

   public EmployeePayrollService() {}

   public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList)
   {
      this.employeePayrollList = employeePayrollList;
   }

   private void readEmployeePayrollData(Scanner consoleInputReader)
   {
      System.out.print("Enter Employee ID: ");
      int id = consoleInputReader.nextInt();
      System.out.print("Enter Employee Name: ");
      String name = consoleInputReader.next();
      System.out.print("Enter Employee Salary: ");
      double salary = consoleInputReader.nextDouble();
      employeePayrollList.add(new EmployeePayrollData(id, name, salary));
   }

   private void writeEmployeePayrollData()
   {
      System.out.println("\nWriting Employee Payroll Data to Console\n" + employeePayrollList);
   }

   public static void main(String[] args)
   {
      ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
      EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
      Scanner consoleInputReader = new Scanner(System.in);
      employeePayrollService.readEmployeePayrollData(consoleInputReader);
      employeePayrollService.writeEmployeePayrollData();
   }
}


