# ST-7 Работа с web-приложениями с использованием Java и фрейморка Selenium


![GitHub pull requests](https://img.shields.io/github/issues-pr/UNN-CS/ST-7)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/UNN-CS/ST-7)

Срок выполнения задания:

**по 17.05.2026** ![Relative date](https://img.shields.io/date/1779051600)

## Подготовка к выполнению работы

- установить браузер Google Chrome последних версий
- скачать со страницы [Chrome for Testing availability](https://googlechromelabs.github.io/chrome-for-testing/) версию драйвера, соответствующую установленной версии браузера

### Настройка ChromeDriver (обязательно для ST-7)

В программе **обязательно** должен существовать файл драйвера на диске. В `App.java` по умолчанию ожидается путь  
`C:\chromedriver\chromedriver.exe` (константа **`CHROME_DRIVER_PATH_DEFAULT`**).

#### Windows — пошагово

1. **Установите Google Chrome** (если ещё не стоит), обычная установка с сайта Google.

2. **Узнайте версию Chrome**
   - Откройте Chrome.
   - В адресной строке введите: `chrome://version` и нажмите Enter.
   - Найдите строку **«Версия»** (например `131.0.6778.85`). Важна **первая цифра до точки** — *major* (здесь **131**).

3. **Скачайте подходящий Chromedriver**
   - Откройте в браузере: [Chrome for Testing — availability](https://googlechromelabs.github.io/chrome-for-testing/).
   - В таблице **Stable** (или в списке версий) найдите строку, где **версия Chrome совпадает по major** с вашей (например у вас Chrome 131.x — ищите вариант **131.x**).
   - В этой же строке для **chromedriver** скачайте архив для Windows 64-bit (часто называется **`chromedriver-win64.zip`**).

4. **Распакуйте и положите `chromedriver.exe`**
   - Распакуйте ZIP (ПКМ → «Извлечь всё…»).
   - Внутри будет папка с файлом **`chromedriver.exe`**.
   - Создайте на диске папку `C:\chromedriver\` (если её нет).
   - **Скопируйте** `chromedriver.exe` **в** `C:\chromedriver\`  
     → итоговый путь должен быть ровно: **`C:\chromedriver\chromedriver.exe`**.  
     Тогда **ничего в коде менять не нужно** — он совпадает с константой по умолчанию.

5. **Если положили драйвер в другое место** (например `D:\tools\chromedriver.exe`):
   - Либо откройте `src/main/java/com/mycompany/app/App.java` и в константе **`CHROME_DRIVER_PATH_DEFAULT`** укажите свой путь в кавычках, в Java используют **двойной обратный слэш**:  
     `"D:\\tools\\chromedriver.exe"`.
   - Либо **не трогая код**, задайте переменную окружения на одну сессию в PowerShell (подставьте свой путь):  
     `$env:CHROME_DRIVER_PATH = "D:\tools\chromedriver.exe"`  
     затем в **том же** окне PowerShell запускайте Maven (см. ниже).

6. **Запуск программы**
   - Установите **JDK 11+** и **Maven**, если ещё не установлены.
   - Откройте **PowerShell** (или «Терминал» в Cursor).
   - Перейдите в корень проекта (где лежит `pom.xml`), например:  
     `cd "C:\Users\user\source\repos\Projects_VS_Code\ST_0^0\ST_7\ST-7"`
   - Выполните:  
     `mvn compile exec:java`  
     (или сначала `mvn compile`, потом `mvn exec:java`).

7. **Что должно произойти**
   - Откроется **окно Chrome**, загрузятся страницы заданий.
   - В консоли появятся строки: пароль с calculator.net, IP с ipify, таблица погоды.
   - Файл **`result/forecast.txt`** в проекте **перезапишется** актуальной таблицей прогноза.

Если файла драйвера по указанному пути нет, программа **сразу завершится** и в консоли напечатает, что не найден `chromedriver`, и ссылку на Chrome for Testing.

#### Linux / macOS / CI

Задайте переменную окружения **`CHROME_DRIVER_PATH`** — полный путь к исполняемому файлу `chromedriver` (в GitHub Actions для этого репозитория используется `/usr/bin/chromedriver`).

#### Если что-то не работает

- **Версии не совпадают** — снова откройте `chrome://version` и скачайте **именно тот** chromedriver, что соответствует вашей ветке Chrome (часто помогает взять **Stable** целиком с той же major-версией).
- **Антивирус блокирует** `chromedriver.exe` — добавьте исключение для папки с драйвером или временно проверьте отключением сканирования для этого файла.
- **Maven не находится** — установите Maven и убедитесь, что `mvn` вызывается из той же консоли (`mvn -v`).

С помощью утилиты **Maven** создать проект по шаблону **quickstart** (см. описание ST-6). Убедиться в появлении веток `java/main` и `java/test` в разделе `src` проекта, а также файла с конфигурацией `pom.xml` в корне проекта.

В **pom.xml** поместить зависимость **Selenium-Java**:

```xml
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>4.15.0</version>
    </dependency>

```

**Внимание!** Версия пакета в зависимости может отличаться от приведенной (определяется опытным путем)!

## Задание №1

В файл **App.java** поместить код для проверки работы Selenium, заменив предложение в кавычках на реальный путь в операционной системе.

```java
        System.setProperty("webdriver.chrome.driver", "путь к драйверу в файловой системе");
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://www.calculator.net/password-generator.html");
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
```

> **В этом репозитории:** строка пути к драйверу задаётся константой `CHROME_DRIVER_PATH_DEFAULT` в `App.java` (либо переменной окружения `CHROME_DRIVER_PATH`); перед созданием `ChromeDriver` выполняется эквивалентный вызов `System.setProperty("webdriver.chrome.driver", ...)`. См. раздел «Настройка ChromeDriver» выше.

В начало файла поместить строки импорта для доступа к классам:

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
```

С помощью выражений Selenium сформировать запрос к ресурсу *https://www.calculator.net/password-generator.html* и получить от него вариант пароля, который необходимо вывести на экран.

Построить и запустить проект. В результате должно автоматически открыться окно браузера и в него загрузится страница, чей URL указан в коде программы.


## Пример парсинга ответа сервера в формате JSON

В данном примере программа обращается к online-сервису, который выдает список космонавтов на орбите Земли в формате JSON

```java
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App 
{
    public static void main( String[] args )
    {
        System.setProperty("webdriver.chrome.driver", "путь к веб-драйверу");
        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("http://api.open-notify.org/astros.json");
            System.out.println(webDriver.getPageSource());
            WebElement elem = webDriver.findElement(By.tagName("pre"));

            String json_str = elem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json_str);
            JSONArray people = (JSONArray) obj.get("people");
            System.out.println("Список космонавтов на орбите");
            System.out.println("Количество: " + people.size());
            for (int i = 0; i < people.size(); ++i) {
                JSONObject person = (JSONObject) people.get(i);
                System.out.println((i + 1) + ") " + person.get("craft") + " " + person.get("name"));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        }
    }
}
```

## Задание №2

Обратиться через браузер на адрес **https://api.ipify.org/?format=json** и получить json-ответ, содержащий IP4-адрес клиента. Извлечь адрес и вывести на экран в виде строки.

## Задание №3

Через сайт **https://open-meteo.com** получить прогноз погоды на сутки. Использовать в качестве местоположения координаты Нижнего Новгорода (56, 44).

Пример запроса:

```
https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms
```

Расшифровка:

- `latitude=56&longitude=44` - координаты
- `hourly=temperature_2m,rain` - ежечасный прогноз температуры и осадков
- `timezone=Europe%2FMoscow` - Московское время
- `forecast_days=1` - прогноз на 1 день
- `ind_speed_unit=ms` - скорость ветра в м/с

Пример ответа сервера:

```
{"latitude":56.0,"longitude":44.0,"generationtime_ms":0.041961669921875,"utc_offset_seconds":10800,"timezone":"Europe/Moscow","timezone_abbreviation":"GMT+3","elevation":169.0,"current_units":{"time":"iso8601","interval":"seconds","cloud_cover":"%"},"current":{"time":"2025-05-03T10:30","interval":900,"cloud_cover":54},"hourly_units":{"time":"iso8601","temperature_2m":"°C","rain":"mm"},"hourly":{"time":["2025-05-03T00:00","2025-05-03T01:00","2025-05-03T02:00","2025-05-03T03:00","2025-05-03T04:00","2025-05-03T05:00","2025-05-03T06:00","2025-05-03T07:00","2025-05-03T08:00","2025-05-03T09:00","2025-05-03T10:00","2025-05-03T11:00","2025-05-03T12:00","2025-05-03T13:00","2025-05-03T14:00","2025-05-03T15:00","2025-05-03T16:00","2025-05-03T17:00","2025-05-03T18:00","2025-05-03T19:00","2025-05-03T20:00","2025-05-03T21:00","2025-05-03T22:00","2025-05-03T23:00"],"temperature_2m":[3.6,3.5,3.3,3.1,2.6,2.2,1.8,1.8,1.8,3.0,4.9,6.8,8.6,9.8,11.0,11.5,11.8,11.5,10.7,9.8,9.0,8.7,8.4,8.3],"rain":[0.00,0.00,0.00,0.10,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.10,0.10,0.00]}}
```

Необходимо извлечь из json-ответа данные и распечатать на экране в виде таблицы

|№   |  Дата/время   | Температура | Осадки (мм)  |
| -- | ------------- | ----------- | ------------ |


## Состав проекта

- файл **src/../Task2.java** - класс с текстом программы задания №2
- файл **src/../Task3.java** - класс с текстом программы задания №3
- файл **src/../App.java** - класс App с решением задания №1 и вызовом методов из Task2 и Task3
- файл **result/forecast.txt** - файл с таблицей (прогноз погоды)
