package quotes;

import java.io.*;
import java.util.GregorianCalendar;

/* 
   Данный класс используется для обновления txt файла из папки downloads

   This class is used to update the txt file from folder downloads
*/
public class FileParserUpdate {

    /* 
       Метод download:
       1) получает название тикера
       2) cчитывает дату из последней строчки файла
       3) QuotesParser.makeQuoteArray , которому передает параметры:
             start - дата из последней строчки + 1 день
             end - сегодняшняя дата
       4) Записывает скачанные данные в txt файл, тем самым обновляя его
    
       Method download:
       1) gets the name of the Ticker
       2) reads the date from the last line of the file
       3) QuotesParser.makeQuoteArray , which passes parameters:
             start - date from the last line + 1 day
             end - today's date
       4) Record the downloaded data in the txt file, updating it
    */
    
    public static void download(String symbol)  {
       
        try {
            
        File file = new File("downloads/"+symbol+".txt");
        String result = LineReader.LastLineReader(file);

        int a = Integer.parseInt(result.substring(0, 4));
        int b = Integer.parseInt(result.substring(4, 6));
        int c = Integer.parseInt(result.substring(6, 8));
        
        GregorianCalendar start = new GregorianCalendar(a,b-1,c+1);
        GregorianCalendar end = new GregorianCalendar(Gui.getCurrentYear(), Gui.getCurrentMonth(), Gui.getCurrentDay());

        QuotesParser.makeQuoteArray(symbol,start,end);
                
        FileWriter sw = new FileWriter(file, true);
        for (QuotesParser.Quote g : QuotesParser.bars.values()) sw.write(g.toString() + "\n");
        sw.close();
        
        }
        
        catch(IOException ie) {
            ie.printStackTrace();
        }
    }


    }



