package quote;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

// Данный класс создает директорию downloads в папке с программой,
// обращается к QuotesParser и скачивает массив исторических данных в "тикер".txt файл

public class QuotesDownloader {

     public static void writeInFile (String symbol,GregorianCalendar start, GregorianCalendar end) {

             try {
                 QuotesParser.makeQuoteArray(symbol,start,end);

                 File dir = new File("downloads/");
                 File file = new File("downloads/"+symbol+".txt");

            // Если дирекотория и файл не созданы, то создаем их

            if (!file.exists()) {
                dir.mkdirs();
                file.createNewFile();
            }

            // Записываем исторические данные в файл .txt

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (QuotesParser.Quote b : QuotesParser.bars.values()) bw.write(b.toString() + "\n");
            bw.close();

                 

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

     
}
