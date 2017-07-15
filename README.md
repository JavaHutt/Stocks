# StockDownloader
Данная программа скачивает с finance.yahoo.com котировки акций и отображает их график. Также возможно обновлять загруженные данные. Присутствует всплывающее окно с автозаполнением. Более подробную информацию вы можете прочитать здесь: http://www.long-short.ru/post/kachalka-dannyh-dlya-financeyahoocom-903

Используемые библиотеки:  
1) jfreecharts – для отображения графиков  
2) org.json – для работы с форматом данных JSON

Подробное описание:  

StockDownloader скачивает котировки ценных бумаг. Для этого посылается запрос на url: yahoo.finance.com и считывается файл в формате csv. Данные записываются в коллекцию treemap, которая используется для построения графика при помощи библиотеки jfreecharts. Также программа поддерживает автозаполнение при вводе символа и отображает список подходящих вариантов. Для этого посылаются запросы на другой url, где данные содержатся в формате JSON, которые считываются при помощи библиотеки org.json. Программа также поддерживает выбор даты и обновление всех котировок. Сами котировки сохранятся на компьютер в виде txt файла.

Изменения:
v1.1 - для выгрузки данных из yahoofinance использовано следующее решение:
http://blog.bradlucas.com/posts/2017-06-04-yahoo-finance-quote-download-java/

![stocksgui](https://cloud.githubusercontent.com/assets/13558216/10944297/fc3830ae-8331-11e5-92b5-d30793b6348c.JPG)
![stockschart](https://cloud.githubusercontent.com/assets/13558216/10944296/fc310f5e-8331-11e5-9f85-8675409ac883.JPG)

This program downloads and displays stock quotes from finance.yahoo.com. It also can update downloaded data.
There is a pop-up window with auto-complete. For more information you can read here (Russian only): http://www.long-short.ru/post/kachalka-dannyh-dlya-financeyahoocom-903

Used libraries:  
1) jfreecharts to display graphs  
2) org.json – for JSON data format  

Detailed description: 

StockDownloader downloads stock quotes. To do this, it sends a request to the url: yahoo.finance.com and reads a file in csv format. Data are recorded in a treemap, which is used to build a graph that is displayed by using the jfreecharts library. The program also supports autocomplete, when you type in a character, it displays a list of matching options. To do this another requests are send to url where data is contained in the JSON format, which is read by org.json library. The program also supports a date picker and updates all the quotes. Stock quotes will be saved to your computer as a txt file.

Changelog:
v1.1 - for downloading data from yahoofinance used the following solution:
http://blog.bradlucas.com/posts/2017-06-04-yahoo-finance-quote-download-java/


