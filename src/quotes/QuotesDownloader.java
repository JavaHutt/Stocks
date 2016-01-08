package quotes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

/* 
   Данный класс создает директорию downloads в папке с программой,
   обращается к QuotesParser и скачивает массив исторических данных в "тикер".txt файл

   This class creates a directory in the downloads folder with the program and
   accesses to QuotesParser, downloads an array of historical data in "Ticker".txt file
*/

public class QuotesDownloader {

     public static void writeInFile (String symbol,GregorianCalendar start, GregorianCalendar end) {

             try {
                 QuotesParser.makeQuoteArray(symbol,start,end);

                 File dir = new File("downloads/");
                 File file = new File("downloads/"+symbol+".txt");

            /* 
               Если директория и файл не созданы, то создаем их
               If the directory and the file is not created, create them
            */

            if (!file.exists()) {
                dir.mkdirs();
                file.createNewFile();
            }

            /* 
               Записываем исторические данные в файл .txt
            
               Record in the historical data .txt file
            */
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (QuotesParser.Quote b : QuotesParser.bars.values()) bw.write(b.toString() + "\n");
            bw.close();

                 

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

     
}
