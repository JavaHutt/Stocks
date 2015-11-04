package quote;

// Стешенко Максим
// https://vk.com/id257582788
// bigmax-91@mail.ru

// Данная программа скачивает котировки с yahoofinance.com и отображат их в виде графика
// Идея взята отсюда:
// http://www.long-short.ru/post/tyanem-dnevnye-dannye-s-yahoofinance-kod-na-java-536
// а также:
// http://www.cognitum-research.com/ru/cognitum-updater-description
 
// Программа использует библиотеку jfreechart (1.0.19) в классах QuitesViewer и QuotesParser
// Ссылка для скачивания: http://www.jfree.org/jfreechart/download.html
// Для работы необходимо подключить jcommon-1.0.23.jar и jfreechart-1.0.19.jar, находящиеся в директории \jfreechart-1.0.19\lib
// Также в классах AutoComplete и AutoCompleteSetup используется библиотека org.json для работы с форматом данных json
// Ссылка для скачивания: http://www.json.org



public class Main {
    public static void main(String[] args) {

        Gui.build();

   }
}
