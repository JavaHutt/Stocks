package quotes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

/* 
   Данный класс, содержит методы которые считывают последнюю и первую строчку из
   из txt файлов в папке downloads и возвращает дату, считанной строки.
  
   This class contains methods that read the last and first line of
   from a txt file in the downloads folder and returns the date of string.
*/
public class LineReader {
    
    /* 
       Данный метод использует класс RandomAccessFile, который позволяет получить доступ
       к файлу с заданного указателя.
    
       This method uses a RandomAccessFile class that allows access
       to the file at the specified index.
    */
    
    /* 
       Нам необходимо считать последнюю строчку, следующий код работает следующим образом:
       1) Встаем в конец файла и считываем строку (для этого задаем указатель с которого
          начинаем чтение файла как startIdx = file.length())
       2) Если строка не считалась, то уменьшяем startIdx, и так до тех пор, пока не считаем
          последнюю строчку
    
       We need to count the last row, the following code works as follows:
       1) gets to the end of the file and reads the line (set the pointer from which
          start reading the file as startIdx = file.length())
       2) If the string was not considered to be, diminish startIdx, so until then, until you read
          last line
    */ 
    public static String LastLineReader(File file) {

        String result = null;
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long startIdx = file.length();
            while (startIdx >= 0 && (result == null || result.length() == 0)) {
                raf.seek(startIdx);
                if (startIdx > 0)
                    raf.readLine();
                result = raf.readLine();
                startIdx--;
            }
        }
        catch(IOException ie) {
            ie.printStackTrace();
        }
        return result;
    }

    /*
       Данный метод передает методу LastLineReader путь к тикеру, получает значения последней строки,
       и считывает дату
    
       This method passes the path to the symbol to the method LastLineReader, retrieves the values of the last row,
       and reads the date
    */
    
    public static String readLastLine(String symbol)  {
        
        File file = new File("downloads/"+symbol+".txt");
        String lastLine = LastLineReader(file);

        String year = lastLine.substring(0, 4);
        String month = lastLine.substring(4, 6);
        String day = lastLine.substring(6, 8);
        
        String date = day+"."+month+"."+year;
         
        return date;
    }
    
    /* 
       Данный метод считывает первую строчку из файла и берет из нее дату
    
       This method reads the first line from the file and takes out a date
    */
    public static String readFirstLine(String symbol) {
       
        String date = null;
          try {
              BufferedReader in = new BufferedReader(new FileReader("downloads/"+symbol+".txt"));
              String firstLine = in.readLine();
              in.close();
              
              String year = firstLine.substring(0, 4);
              String month = firstLine.substring(4, 6);
              String day = firstLine.substring(6, 8);
                
              date = day+"."+month+"."+year;
          }
          
        catch (Exception e) {
            e.printStackTrace();
        
        }   
          
          return date;
    }
    
}
