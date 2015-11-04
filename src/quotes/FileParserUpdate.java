package quotes;

import java.io.*;
import java.util.GregorianCalendar;

// Данный класс испольуется для обновления txt файла из папки downloads

public class FileParserUpdate {

    // Метод download:
    // 1) получает название тикера
    // 2) cчитывает дату из последней строчки файла
    // 3) QuotesParser.makeQuoteArray , которому передает параметры:
    //       start - дата из последней строчки + 1 день
    //       end - сегодняшняя дата
    // 4) Записывает скачанные данные в txt файл, тем самым обновляя его
    
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



