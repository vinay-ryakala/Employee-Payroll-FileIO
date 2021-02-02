package com.bridgelabz;
// welcome to employee payroll
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollServiceTest
{
   private static String HOME = System.getProperty("user.home");
   private static String PLAY_WITH_NIO = "TempPlayGround";

   @Test
   public void givenPathWhenCheckedThenConfirm() throws IOException
   {
      //Checks file exists
      Path homePath = Paths.get(HOME);
      Assert.assertTrue(Files.exists(homePath));
      //Deletes files checks file not exists
      Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
      if (Files.exists(playPath)) FileUtils.deleteFiles(playPath.toFile());
      Assert.assertTrue(Files.notExists(playPath));
      //create Directory
      Files.createDirectories(playPath);
      Assert.assertTrue(Files.exists(playPath));
      //Creates file
      IntStream.range(1, 10).forEach(cntr -> {
         Path tempFile = Paths.get(playPath + "/temp" + cntr);
         Assert.assertTrue(Files.notExists(tempFile));
         try {
            Files.createFile(tempFile);
         } catch (IOException e) {
         }
         Assert.assertTrue(Files.exists(tempFile));
      });
      //lists directories and files with extension
      Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
      Files.newDirectoryStream(playPath).forEach(System.out::println);
      Files.newDirectoryStream(playPath, path -> path.toFile().isFile() &&
              path.toString().startsWith("temp")).forEach(System.out::println);
      ;
   }

   @Test
   public void givenADirectoryWhenWatchedListsAllTheActivities() throws IOException
   {
      Path dir = Paths.get(HOME + "/" + PLAY_WITH_NIO);
      Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
      new Java8WatchServiceExample(dir).processEvents();
   }

   @Test
   public void given3EmployeesWhenWrittenToFileShouldMatchNumberOfEmployeeEntries()
   {
      EmployeePayrollData[] arrayOfEmployees = {
              new EmployeePayrollData(1, "Vinay", 11000.0),
              new EmployeePayrollData(2, "Rya", 21100.0),
              new EmployeePayrollData(3, "kumar", 1500.0)};
      EmployeePayrollService empPayrollService;
      empPayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
      empPayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
      empPayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
      Assert.assertEquals(3, empPayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO));
   }
   @Test
   public void givenEmployeePayrollInDB_whenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
      EmployeePayrollService employeePayrollService = new EmployeePayrollService();
      List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollServiceData(EmployeePayrollService.IOService.DB_IO);
      Assert.assertEquals(3,employeePayrollData.size());
   }
   @Test
   public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws SQLException {
      EmployeePayrollService employeePayrollService = new EmployeePayrollService();
      List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollServiceData(EmployeePayrollService.IOService.DB_IO);
      employeePayrollService.updateEmployeeSalary("vinay", 3000000.00);
      boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("vinay");
      System.out.println(result);
      Assert.assertTrue(result);
   }

}
