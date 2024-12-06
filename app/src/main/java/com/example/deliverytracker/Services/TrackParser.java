package com.example.deliverytracker.Services;
import com.example.deliverytracker.models.Event;
import com.example.deliverytracker.models.TrackResponse;
import com.google.gson.Gson;

public class TrackParser {
    public TrackParser(){}

    static public  TrackResponse parse(String json){
        Gson gson = new Gson();
        TrackResponse response = gson.fromJson(json, TrackResponse.class);
        response.data.events.forEach(event -> event.DataId=response.data.trackCode);
        return response;
    }

    static public  TrackResponse parse() {
        String json = "{\n" +
                "    \"status\": \"ok\",\n" +
                "    \"data\": {\n" +
                "        \"trackCode\": \"RB287529899RU\",\n" +
                "        \"trackCreationDateTime\": \"06.12.2024 07:56:51\",\n" +
                "        \"trackUpdateDateTime\": \"06.12.2024 07:57:15\",\n" +
                "        \"trackUpdateDiffMinutes\": 17,\n" +
                "        \"trackAwaitingDateTime\": \"04.12.2024 11:44:00\",\n" +
                "        \"trackDeliveredDateTime\": \"\",\n" +
                "        \"fromCountryCode\": \"RU\",\n" +
                "        \"fromCountry\": \"Россия\",\n" +
                "        \"destinationCountryCode\": \"\",\n" +
                "        \"destinationCountry\": \"Киргизия\",\n" +
                "        \"fromName\": \"Новикова О. Г\",\n" +
                "        \"destinationName\": \"Daniyar**** *****a\",\n" +
                "        \"fromCity\": \"Екатеринбург\",\n" +
                "        \"destinationCity\": \"\",\n" +
                "        \"fromAddress\": \"\",\n" +
                "        \"destinationAddress\": \"\",\n" +
                "        \"destinationPostalCode\": \"104000\",\n" +
                "        \"collectOnDeliveryPrice\": \"\",\n" +
                "        \"declaredValue\": \"\",\n" +
                "        \"trackCodeModified\": \"\",\n" +
                "        \"deliveredStatus\": \"0\",\n" +
                "        \"awaitingStatus\": \"1\",\n" +
                "        \"awaiting\": true,\n" +
                "        \"events\": [\n" +
                "            {\n" +
                "                \"id\": \"304591918\",\n" +
                "                \"eventDateTime\": \"06.12.2024 07:56:51\",\n" +
                "                \"operationDateTime\": \"20.11.2024 15:07:55\",\n" +
                "                \"operationAttribute\": \"Прием\",\n" +
                "                \"operationType\": \"\",\n" +
                "                \"operationPlacePostalCode\": \"620023\",\n" +
                "                \"operationPlaceName\": \"Екатеринбург 23 [Свердловская область]\",\n" +
                "                \"itemWeight\": \"353\",\n" +
                "                \"source\": \"rupost\",\n" +
                "                \"serviceName\": \"Почта России\",\n" +
                "                \"serviceAllWayTracking\": true,\n" +
                "                \"operationAttributeInformation\": \"Означает, что зарубежный отправитель (продавец) принес Вашу посылку в местное почтовое отделение. При этом заполнил все необходимые документы, включая таможенную декларацию (формы CN 22 или CN 23). В это время отправлению присваивается уникальный почтовый идентификатор – специальный штриховой код (Трек-номер, Трек-код). Он находится в чеке (или квитанции), выдаваемом при приеме почтового отправления. Операция «Прием» показывает место, дату и страну приема отправления. После приема посылка движется на пути к месту международного обмена.\",\n" +
                "                \"operationAttributeOriginal\": \"Прием\",\n" +
                "                \"operationTypeOriginal\": \"\",\n" +
                "                \"operationPlaceNameOriginal\": \"Екатеринбург 23 [Свердловская область]\",\n" +
                "                \"operationAttributeTranslated\": \"Прием\",\n" +
                "                \"operationTypeTranslated\": \"\",\n" +
                "                \"operationPlaceNameTranslated\": \"Екатеринбург 23 [Свердловская область]\",\n" +
                "                \"icon\": \"accepted\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"304591919\",\n" +
                "                \"eventDateTime\": \"06.12.2024 07:56:51\",\n" +
                "                \"operationDateTime\": \"04.12.2024 11:44:00\",\n" +
                "                \"operationAttribute\": \"Прибыло в место вручения\",\n" +
                "                \"operationType\": \"\",\n" +
                "                \"operationPlacePostalCode\": \"KGFRUA\",\n" +
                "                \"operationPlaceName\": \"BICHKEK PI-1 [Kyrgyzstan]\",\n" +
                "                \"itemWeight\": \"353\",\n" +
                "                \"source\": \"rupost\",\n" +
                "                \"serviceName\": \"Почта России\",\n" +
                "                \"serviceAllWayTracking\": true,\n" +
                "                \"operationAttributeInformation\": \"Означает прибытие отправления в отделение почтовой связи (ОПС) получателя, которое должно произвести вручение отправления получателю. Как только отправление прибыло в отделение, сотрудники выписывают извещение (уведомление) о том, что отправление находится в отделении. Извещение отдают почтальону в доставку. Доставка осуществляется в день прибытия отправления в отделение или на следующий день (например, если отправления поступили в отделение вечером).&lt;br&gt;&lt;br&gt; Данный статус указывает на то, что получатель может самостоятельно обратиться в почтовое отделение связи для получения отправления не дожидаясь извещения. &lt;br&gt;&lt;br&gt; Если посылка была переупакованна в пакет с другими посылками, то для возможности получения Вам необходимо узнать трек-код общей посылки.\",\n" +
                "                \"operationAttributeOriginal\": \"Прибыло в место вручения\",\n" +
                "                \"operationTypeOriginal\": \"\",\n" +
                "                \"operationPlaceNameOriginal\": \"BICHKEK PI-1 [Kyrgyzstan]\",\n" +
                "                \"operationAttributeTranslated\": \"Прибыло в место вручения\",\n" +
                "                \"operationTypeTranslated\": \"\",\n" +
                "                \"operationPlaceNameTranslated\": \"БИЧКЕК ПИ-1 [Кыргызстан]\",\n" +
                "                \"icon\": \"awaiting\"\n" +
                "            }\n"+
                "        ],\n" +
                "        \"shippers\": [\n" +
                "            \"rupost\"\n" +
                "        ],\n" +
                "        \"itemWeight\": \"353\",\n" +
                "        \"trackFirstOperationDateTime\": \"20.11.2024 15:07:55\",\n" +
                "        \"daysInTransit\": 16,\n" +
                "        \"daysTracking\": 1,\n" +
                "        \"groupedCompanyNames\": [\n" +
                "            \"Почта России\"\n" +
                "        ],\n" +
                "        \"groupedEvents\": \"\",\n" +
                "        \"destinationPostalAddress\": \"Москва, Варшавское ш, 37\",\n" +
                "        \"destinationPostalPhones\": \"\",\n" +
                "        \"destinationPostalWorkTime\": \"<table> <tbody><tr class=><td>Понедельник</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>08:00 - 18:00</td></tr><tr class=><td>Вторник</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>08:00 - 18:00</td></tr><tr class=><td>Среда</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>08:00 - 18:00</td></tr><tr class=><td>Четверг</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>08:00 - 18:00</td></tr><tr class=rowWorkTimeActive><td>Пятница</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>08:00 - 18:00</td></tr><tr class=><td>Суббота</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>08:00 - 18:00</td></tr><tr class=><td>Воскресенье</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>08:00 - 18:00</td></tr> </tbody></table>\",\n" +
                "        \"destinationCompany\": \"rupost\",\n" +
                "        \"lastPoint\": {\n" +
                "            \"id\": \"304591919\",\n" +
                "            \"eventDateTime\": \"06.12.2024 07:56:51\",\n" +
                "            \"operationDateTime\": \"04.12.2024 11:44:00\",\n" +
                "            \"operationAttribute\": \"Прибыло в место вручения\",\n" +
                "            \"operationType\": \"\",\n" +
                "            \"operationPlacePostalCode\": \"KGFRUA\",\n" +
                "            \"operationPlaceName\": \"БИЧКЕК ПИ-1 [Кыргызстан]\",\n" +
                "            \"itemWeight\": \"353\",\n" +
                "            \"source\": \"rupost\",\n" +
                "            \"serviceName\": \"Почта России\",\n" +
                "            \"serviceAllWayTracking\": true,\n" +
                "            \"operationAttributeInformation\": \"Означает прибытие отправления в отделение почтовой связи (ОПС) получателя, которое должно произвести вручение отправления получателю. Как только отправление прибыло в отделение, сотрудники выписывают извещение (уведомление) о том, что отправление находится в отделении. Извещение отдают почтальону в доставку. Доставка осуществляется в день прибытия отправления в отделение или на следующий день (например, если отправления поступили в отделение вечером).&lt;br&gt;&lt;br&gt; Данный статус указывает на то, что получатель может самостоятельно обратиться в почтовое отделение связи для получения отправления не дожидаясь извещения. &lt;br&gt;&lt;br&gt; Если посылка была переупакованна в пакет с другими посылками, то для возможности получения Вам необходимо узнать трек-код общей посылки.\",\n" +
                "            \"operationAttributeOriginal\": \"Прибыло в место вручения\",\n" +
                "            \"operationTypeOriginal\": \"\",\n" +
                "            \"operationPlaceNameOriginal\": \"BICHKEK PI-1 [Kyrgyzstan]\",\n" +
                "            \"operationAttributeTranslated\": \"Прибыло в место вручения\",\n" +
                "            \"operationTypeTranslated\": \"\",\n" +
                "            \"operationPlaceNameTranslated\": \"БИЧКЕК ПИ-1 [Кыргызстан]\",\n" +
                "            \"icon\": \"awaiting\",\n" +
                "            \"trackUpdateDateTime\": \"06.12.2024 07:57:15\",\n" +
                "            \"operation\": \"Прибыло в место вручения\"\n" +
                "        },\n" +
                "        \"allWayTracking\": true,\n" +
                "        \"upu\": true\n" +
                "    },\n" +
                "    \"services\": [\n" +
                "        \"UPU\",\n" +
                "        \"RUPOST\",\n" +
                "        \"BEPOST\",\n" +
                "        \"ILPOST\",\n" +
                "        \"CAINIAO\",\n" +
                "        \"NPS\",\n" +
                "        \"OSMWW\",\n" +
                "        \"TFS\"\n" +
                "    ],\n" +
                "    \"deliveredStat\": null,\n" +
                "    \"id\": \"497c5fbe4be542a39f9bf69047dfae36\",\n" +
                "    \"rpm\": 0,\n" +
                "    \"limits\": {\n" +
                "        \"full\": false,\n" +
                "        \"updatesPeriod\": 240,\n" +
                "        \"minute\": 10,\n" +
                "        \"day\": 100,\n" +
                "        \"month\": 500\n" +
                "    },\n" +
                "    \"totalTime\": 0.4868\n" +
                "}"; // Ваш JSON строка
        Gson gson = new Gson();
        TrackResponse response = gson.fromJson(json, TrackResponse.class);
        response.data.events.forEach(event -> event.DataId=response.data.trackCode);

        // Вывод информации
//        System.out.println("Track Code: " + response.data.trackCode);
//        System.out.println("Weight: " + response.data.itemWeight + "g");
//        System.out.println("From: " + response.data.fromCountry + ", " + response.data.fromCity);
//        System.out.println("To: " + response.data.destinationCountry + ", " + response.data.destinationCity);
//        System.out.println("Events:");

        return response;
    }
}