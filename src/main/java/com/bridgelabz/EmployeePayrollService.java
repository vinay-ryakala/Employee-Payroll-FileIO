package com.bridgelabz;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

// welcome to employee payroll
public class EmployeePayrollService
{
   public enum IOService {CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
   private List<EmployeePayrollData> employeePayrollList;
   private EmployeePayrollDBService employeePayrollDBService;

   public EmployeePayrollService(){
   employeePayrollDBService  = EmployeePayrollDBService.getInstance();
   }

   public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList)
   {
      this();
      this.employeePayrollList = employeePayrollList;
   }
   public List<EmployeePayrollData> readEmployeePayrollServiceData(IOService ioService) throws  SQLException {
      if(ioService.equals(IOService.DB_IO))
         this.employeePayrollList = employeePayrollDBService.readData();
      return this.employeePayrollList;
   }
   public boolean checkEmployeePayrollInSyncWithDB(String name) {
      List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
      return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
   }

   public void updateEmployeeSalary(String name, double salary) {
      int result = employeePayrollDBService.updateEmployeeData(name,salary);
      if(result == 0) return;
      EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
      if(employeePayrollData != null) employeePayrollData.salary = salary;
   }

   private EmployeePayrollData getEmployeePayrollData(String name) {
      return this.employeePayrollList.stream()
              .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
              .findFirst()
              .orElse(null);
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

   public void writeEmployeePayrollData(IOService ioService)
   {
      if (ioService.equals(IOService.CONSOLE_IO))
         System.out.println("\nWriting Payroll to Console\n" + employeePayrollList);
      else if (ioService.equals(IOService.FILE_IO))
         new EmployeePayrollFileIOService().writeData(employeePayrollList);

   }
   public long readEmployeePayrollData(IOService ioService) {
      if(ioService.equals(IOService.FILE_IO))
         this.employeePayrollList = new EmployeePayrollFileIOService().readData();
      return employeePayrollList.size();
   }
   public void printData(IOService ioService) {
      if(ioService.equals(IOService.FILE_IO))
         new EmployeePayrollFileIOService().printData();
   }
   public long countEntries(IOService ioService)
   {
      if (ioService.equals(IOService.FILE_IO))
         return new EmployeePayrollFileIOService().countEntries();
      return 0;
   }

}


