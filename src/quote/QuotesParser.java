package quote;

import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// Данный класс переходит на http://ichart.finance.yahoo.com, выбирает нужную котировку, считывает ее исторические данные
// и заносит их в Map bars, а также возвращает в формате OHLCDataset, который испольует библиотека jfreecharts

public class QuotesParser {

    // Создаем класс Quote, в котором обьявляем переменные и создаем конструктор

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

    // Создаем коллекцию bars класса TreeMap, в которую будут добавляться объекты класса Quote и сортироваться  по ключу (по дате, которую считаем с потока данных)
    // К ней будем обращатья из классов FileParserUpdate и QuotesDonwloader
    
    public static Map<Date, Quote> bars = new TreeMap<>();
    
    // makeQuoteArray - этот метод считывает данные тикера с заданного url

    public static OHLCDataset makeQuoteArray (String symbol, GregorianCalendar start, GregorianCalendar end) {
             
        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        try {
            
            // Для начала очистим нашу коллекцию bars, это сделано для того, чтобы
            // в ней не оставались значения от предыдущих вызовов этого метода
            
            bars.clear();
                               
            // Задаем нужный url адресс

            String strUrl = "http://ichart.finance.yahoo.com/table.csv?s=" + symbol +
                    "&a=" + start.get(Calendar.MONTH) +
                    "&b=" + start.get(Calendar.DAY_OF_MONTH) +
                    "&c=" + start.get(Calendar.YEAR) +
                    "&d=" + end.get(Calendar.MONTH) +
                    "&e=" + end.get(Calendar.DAY_OF_MONTH) +
                    "&f=" + end.get(Calendar.YEAR) +
                    "&g=d&ignore=.csv";
            URL url = new URL(strUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            DateFormat df = new SimpleDateFormat("y-M-d");

            // Считываем построчно csv, заданный в url. Нужные нам данные разделены зяпятой

            String inputLine;
            in.readLine();
            while ((inputLine = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inputLine, ",");

                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                double close = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());
                double adjClose = Double.parseDouble(st.nextToken());

                bars.put(date, new Quote(date, open, high, low, close, volume, adjClose));

                open = open * adjClose / close;
                high = high * adjClose / close;
                low = low * adjClose / close;

                // Создаем объект item класса OHLCDataItem, состоящий из считанных из строки данных
                // Добавляем item в ArrayList dataItems
                // Эта процедура повторяется, пока не считаются все необходимые нам строки

                OHLCDataItem item = new OHLCDataItem(date, open, high, low, adjClose, volume);
                dataItems.add(item);

            }
            
            in.close();
        } catch (Exception e) {
            
        }
        

        // Сортируем dataItems в обратном порядке
        // Конвентируем ArrayList dataItems в массив data
        // Создаем dataset, данные которого используются для построения графика

        Collections.reverse(dataItems);
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        OHLCDataset dataset = new DefaultOHLCDataset(symbol, data);


        return dataset;


    }

    // makeQuoteArrayOffline - этот метод считывает данные тикера из папки downloads
    
    public static OHLCDataset makeQuoteArrayOffline (String symbol) {

        List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
        try {

            // Задаем нужный url адресс

            BufferedReader in = new BufferedReader(new FileReader("downloads/"+symbol+".txt"));
            DateFormat df = new SimpleDateFormat("yyyyMMdd");

            // Считываем построчно csv, заданный в url. Нужные нам данные разделены зяпятой

            String inputLine;
         
            while ((inputLine = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inputLine, ",");

                Date date = df.parse(st.nextToken());
                double open = Double.parseDouble(st.nextToken());
                double high = Double.parseDouble(st.nextToken());
                double low = Double.parseDouble(st.nextToken());
                double close = Double.parseDouble(st.nextToken());
                double volume = Double.parseDouble(st.nextToken());
                double adjClose = Double.parseDouble(st.nextToken());

                

                open = open * adjClose / close;
                high = high * adjClose / close;
                low = low * adjClose / close;

                // Создаем объект item класса OHLCDataItem, состоящий из считанных из строки данных
                // Добавляем item в ArrayList dataItems
                // Эта процедура повторяется, пока не считаются все необходимые нам строки

                OHLCDataItem item = new OHLCDataItem(date, open, high, low, adjClose, volume);
                dataItems.add(item);


            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            
        }

        // Сортируем dataItems в обратном порядке
        // Конвентируем ArrayList dataItems в массив data
        // Создаем dataset, данные которого используются для построения графика

        Collections.reverse(dataItems);
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);
        OHLCDataset dataset = new DefaultOHLCDataset(symbol, data);


        return dataset;


    }


}
