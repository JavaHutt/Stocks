package quotes;

/*
   Стешенко Максим (Steshenko Maxim)
   https://vk.com/id257582788
   bigmax-91@mail.ru
*/

/* 
   Данная программа скачивает котировки с yahoofinance.com и отображат их в виде графика
   Идея взята отсюда:
   http://www.long-short.ru/post/tyanem-dnevnye-dannye-s-yahoofinance-kod-na-java-536
   а также:
   http://www.cognitum-research.com/ru/cognitum-updater-description

   Программа использует библиотеку jfreechart (1.0.19) в классах QuitesViewer и QuotesParser
   Ссылка для скачивания: http://www.jfree.org/jfreechart/download.html
   Для работы необходимо подключить jcommon-1.0.23.jar и jfreechart-1.0.19.jar, находящиеся в директории \jfreechart-1.0.19\lib
   Также в классах AutoComplete и AutoCompleteSetup используется библиотека org.json для работы с форматом данных json
   Ссылка для скачивания: http://www.json.org
*/

/*
   This program downloads quotes from yahoofinance.com and displays them in a graph
   Idea taken from here:
   http://www.long-short.ru/post/tyanem-dnevnye-dannye-s-yahoofinance-kod-na-java-536
   and also:
   http://www.cognitum-research.com/ru/cognitum-updater-description
 
   The program uses the jfreechart library (1.0.19) in classes QuitesViewer and QuotesParser
   Download link: http://www.jfree.org/jfreechart/download.html
   To work you need to connect jcommon-1.0.23.jar and jfreechart-1.0.19.jar located in the directory \jfreechart-1.0.19\lib
   In AutoComplete classes and AutoCompleteSetup it uses the library org.json for working with json data format
   Download link: http://www.json.org
*/

public class Main {
    public static void main(String[] args) {

        Gui.build();

   }
}
