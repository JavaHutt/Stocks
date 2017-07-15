package quotes;

import java.io.BufferedInputStream;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/*
   Данный класс переходит на http://ichart.finance.yahoo.com, выбирает нужную котировку, считывает ее исторические данные
   и заносит их в Map bars, а также возвращает в формате OHLCDataset, который испольует библиотека jfreecharts

   This class goes to http://ichart.finance.yahoo.com selects the desired quote, reads the historical data
   and puts it in bars Map, returns OHLCDataset format that jfreecharts library uses
*/

public class QuotesParser {

    /* 
       Создаем класс Quote, в котором обьявляем переменные и создаем конструктор
    
       Create a Quote class, in which we declare variables and create the constructor
    */

    public static class Quote {
        public final Date date;
        public final double open;
        public final double high;
        public final double low;
        public final double close;
        public final double volume;
        public final double adjusted;

        public Quote(Date date, double open, double high, double low, double close, double volume, double adjusted) {
            this.date = date;
            this.open = open;
            this.high = high;
            this.low = low;
            this.close = close;
            this.volume = volume;
            this.adjusted = adjusted;
        }

        @Override
        public String toString() {
            return String.format(Locale.US, "%s,%f,%f,%f,%f,%f,%f",
                    dateFormat.format(date), open, high, low, close, volume, adjusted);
        }
    }
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    /*
       Создаем коллекцию bars класса TreeMap, в которую будут добавляться объекты класса Quote и сортироваться  по ключу (по дате, которую считаем с потока данных)
       К ней будем обращатьcя из классов FileParserUpdate и QuotesDonwloader
    
       Create a collection class TreeMap bars, in which objects of the class Quote will be added and sorted by the key (by date, which we read from the data stream)
       We will access to it from classes FileParserUpdate and QuotesDonwloader
    */
    
    public static Map<Date, Quote> bars = new TreeMap<>();
    
    /* 
       makeQuoteArray - этот метод считывает данные тикера с заданного url
    
       makeQuoteArray - this method reads the data of the Ticker from the given url
    */

    public static OHLCDataset makeQuoteArray (String symbol, GregorianCalendar start, GregorianCalendar end) {
             
        List<String> lines = new LinkedList<String>();
        
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        try {
            
            /* 
               Для начала очистим нашу коллекцию bars, это сделано для того, чтобы
               в ней не оставались значения от предыдущих вызовов этого метода
                  
               First we need to clear our collection of bars, this is done in order
               it was left with the values from previous calls to this method
            */       
 
                String crumb = GetYahooQuotes.getInstance().getCrumb(symbol);
                if (crumb != null && !crumb.isEmpty()) {
        //            System.out.println(String.format("Downloading data to %s", symbol));
       //             System.out.println("Crumb: " + crumb);
                   long s = start.getTimeInMillis() / 1000;
                   long e = end.getTimeInMillis() / 1000;
                   
           
                   
                     lines = GetYahooQuotes.getInstance().getData(symbol, s, e, crumb);
           
                   
                  
                
                    
 //                               BufferedReader r = new BufferedReader(
  //      new InputStreamReader(bis));
  //       String line = r.readLine();
  //       System.out.println("Жоп");
  //       System.out.println(line);
                    
               
                } else {
                    System.out.println(String.format("Error retreiving data for %s", symbol));
                }
            
            
            
            
            
            
            bars.clear();
                               
            /*
               Задаем нужный url адрес
            
               Set the desired url
            */

      
        
            DateFormat df = new SimpleDateFormat("y-M-d");

            /* 
               Считываем построчно csv, заданный в url. Нужные нам данные разделены зяпятой
            
               Read csv line by line, specified in the url. Date we need is separated by a comma
            */
            
           
  
            for (String inputLine : lines) {
                
               
                
                StringTokenizer st = new StringTokenizer(inputLine, ",");

                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                double close = Double.parseDouble(st.nextToken());
                double adjClose = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());
              

                bars.put(date, new Quote(date, open, high, low, close, adjClose, volume));

          //     open = open * adjClose / close;
          //     high = high * adjClose / close;
          //     low = low * adjClose / close;

                /*
                   Создаем объект item класса OHLCDataItem, состоящий из считанных из строки данных
                   Добавляем item в ArrayList dataItems
                   Эта процедура повторяется, пока не считаются все необходимые нам строки
                
                   Create an object of class OHLCDataItem, consisting of a few rows of data
                   Add item to ArrayList dataItems
                   This procedure is repeated until we read all needed rows
                */ 
                
                OHLCDataItem item = new OHLCDataItem(date, open, high, low, adjClose, volume);
                
            //     System.out.println(inputLine);
                dataItems.add(item);

                
            //    System.out.println(date +" "+ open +" "+ high +" "+ low +" "+ adjClose +" "+ volume);
            }
            
            
        } catch (Exception e) {
            
        }
        

        /* 
           Сортируем dataItems в обратном порядке
           Конвенртируем ArrayList dataItems в массив data
           Создаем dataset, данные которого используются для построения графика
        
           Sort DataItems in reverse order
           Convert ArrayList dataItems in the data array
           Create dataset, whose data is used for graphing
        */
        
        Collections.reverse(dataItems);
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        OHLCDataset dataset = new DefaultOHLCDataset(symbol, data);

        return dataset;


    }

    /* 
       makeQuoteArrayOffline - этот метод считывает данные тикера из папки downloads
    
       makeQuoteArrayOffline - this method reads the data of the Ticker from the downloads folder
    */
    public static OHLCDataset makeQuoteArrayOffline (String symbol) {

        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        try {

            /* 
               Задаем нужный url адрес
            
               Set the desired url
            */
            BufferedReader in = new BufferedReader(new FileReader("downloads/"+symbol+".txt"));
            DateFormat df = new SimpleDateFormat("yyyyMMdd");

            /*
               Считываем построчно csv, заданный в url. Нужные нам данные разделены зяпятой
               Read csv line by line, specified in the url. The data we need is separated by a comma
            */

            String inputLine;
         
            while ((inputLine = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inputLine, ",");

                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                double close = Double.parseDouble(st.nextToken());
                double adjClose = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());

              //  open = open * adjClose / close;
             //   high = high * adjClose / close;
             //   low = low * adjClose / close;

                /*
                   Создаем объект item класса OHLCDataItem, состоящий из считанных из строки данных
                   Добавляем item в ArrayList dataItems
                   Эта процедура повторяется, пока не считаются все необходимые нам строки
                
                   Create an object of class OHLCDataItem, consisting of a few rows of data
                   Add item to ArrayList dataItems
                   This procedure is repeated until we read all needed rows
                */
                
                OHLCDataItem item = new OHLCDataItem(date, open, high, low, adjClose, volume);
                dataItems.add(item);


            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            
        }

        /* 
           Сортируем dataItems в обратном порядке
           Конвенртируем ArrayList dataItems в массив data
           Создаем dataset, данные которого используются для построения графика
        
           Sort DataItems in reverse order
           Convert ArrayList dataItems in the data array
           Create dataset, whose data is used for graphing
        */

        Collections.reverse(dataItems);
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        OHLCDataset dataset = new DefaultOHLCDataset(symbol, data);


        return dataset;


    }


}
