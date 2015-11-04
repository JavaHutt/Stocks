package quote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

// анный класс, содержит методы которые считывают последнюю и первую строчку из
// из txt файлов в папке downloads и возвращает дату, считанной строки.

public class LineReader {
    
    // Данный метод испольует класс RandomAccessFile, который позволяет получить доступ
    // к файлу с заданного указателя.
    
    // Нам необходимо считать последнюю строчку, следующий код работает следующим образом:
    // 1) Встаем в конец файла и считываем строку (для этого задаем указатель с которого
    //    начинаем чтение файла как startIdx = file.length())
    // 2) Если строка не считалась, то уменьшяем startIdx, и так до тех пор, пока не считаем
    //    последнюю строчку
       
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

    // Данный метод передает методу LastLineReader путь к тикеру, получает значения последней строки,
    // и считывает дату
    
    public static String readLastLine(String symbol)  {
        
        File file = new File("downloads/"+symbol+".txt");
        String lastLine = LastLineReader(file);

        String year = lastLine.substring(0, 4);
        String month = lastLine.substring(4, 6);
        String day = lastLine.substring(6, 8);
        
        String date = day+"."+month+"."+year;
         
        return date;
    }
    
    // Данный метод считывает первую строчку из файла и берет из нее дату
    
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
