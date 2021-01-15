package com.bridgelabz;
// welcome to employee payroll
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollServiceTest
{
   private static String HOME = System.getProperty("user.home");
   private static String PLAY_WITH_NIO = "TempPlayGround";

   @Test
   public void givenPathWhenCheckedThenConfirm() throws IOException {
      //Checks file exists
      Path homePath = Paths.get(HOME);
      Assert.assertTrue(Files.exists(homePath));
      //Deletes files checks file not exists
      Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
      if(Files.exists(playPath)) FileUtils.deleteFiles(playPath.toFile());
      Assert.assertTrue(Files.notExists(playPath));
      //create Directory
      Files.createDirectories(playPath);
      Assert.assertTrue(Files.exists(playPath));
      //Creates file
      IntStream.range(1, 10).forEach(cntr -> {
         Path tempFile = Paths.get(playPath + "/temp"+cntr);
         Assert.assertTrue(Files.notExists(tempFile));
         try {Files.createFile(tempFile);}
         catch(IOException e) { }
         Assert.assertTrue(Files.exists(tempFile));
      });
      //lists directories and files with extension
      Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
      Files.newDirectoryStream(playPath).forEach(System.out::println);
      Files.newDirectoryStream(playPath, path -> path.toFile().isFile() &&
              path.toString().startsWith("temp")).forEach(System.out::println);;
   }
   @Test
   public void givenADirectoryWhenWatchedListsAllTheActivities() throws IOException {
      Path dir = Paths.get(HOME + "/" + PLAY_WITH_NIO);
      Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
      new Java8WatchServiceExample(dir).processEvents();
   }
}
