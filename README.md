# Acquiring service
### Задание
Надо написать RESTful сервис, цель которого - операции со счетами пользователей.
Должно быть как минимум 3 метода - перевод денег с одного счёта на другой, положить деньги на счёт, снять деньги со счёта.
Отрицательный баланс счета недопустим.
В качестве хранилища можно использовать любую in-memory БД. Доступ к БД осуществить через JPA.
Исходный код должен собираться с помощью maven или gradle в исполняемый jar.
Решение должно быть на Spring (Java)
Если в инструкции не хватает каких-то данных, то дополнить задание можно на свое усмотрение и описать все иходные данные в README.
Проект разметсить на github

### Реализация
Реализовать механизм работы со счетом можно как минимум 4-5 разными вариантами, основными на мой взгляд являлись:
1) Таблица с очередью по обработке платежей (т.е. асинхронная обработка);
2) Синхронная обработка без очередей с помощью синхронизации запросов.

Первый вариант слишком скучный, по этому в рамках тестового проекта реализовал второй. В качестве механизма синхронизации использую reddis, что позволяет гарантировать последовательную обработку в рамках 1 счета не только в рамках разных реквестов от одного пользователя, но и в рамках горизонтального масштабирования (нескольких инстансов). Последовательная обработка необходима для гарантии отсутствия отрицательных балансов.

API docs: http://localhost:8080/acquiring-service/swagger-ui/index.html

Restrepository (просто что бы было, можно запрашивать транзакции без реализации цепочки контроллер-сервис-репозиторий): http://localhost:8080/acquiring-service/repository

Остановился на том что есть, не стал реализовывать:
* Кастомную обработку исключений для ошибок (exception mapper), в том числе для HttpMessageNotReadableException и NoSuchElementException, соответственно всегда будут Internal Server Error;
* Валидаторы для входящих обектов (будут падать NPE при отсутствии полей (!!!));
* Ограничений для счетов со стороны БД;
* Дженерики для процессоров;
* В качестве ИД использую просто Long (что явно слабое место, но не в рамках демо);
* Юнит тесты;
* Автоматический форматтер проекта;
* Настраивать логирование (формат, уровень);
* Донастроить gradle;
* Не поддерживает валюты (всё в условных единицах, пусть будут рубли);
* Точность вычислений (не знаю до какого сивола корректно было бы вычислять в банковсой сфере, предполагаю что гадать никакого смысла нет);
* Финальный рефакторинг и фиксы по анализаторам кода;
* и т.д. и т.п..

т.к. не думаю что это имеет большое значение.

### Инструкция по запуску сервиса:
1) `docker pull redis`;
2) `docker run --name acquiring-redis -p 6379:6379 -d redis`;
3) `gradle clean bootJar`;
4) `java -jar build/libs/acquiring-service-0.0.1.jar`;
5) go to http://localhost:8080/acquiring-service/swagger-ui/index.html

### Примеры запросов
Положить деньги на счет: `
{
    "operationType": "DEPOSIT",
    "operationData": {
        "operationHost": 1,
        "depositAmount": 10000
    }
}
`

Снять деньги со счета: `
{
    "operationType": "WITHDRAW",
    "operationData": {
        "operationHost": 1,
        "withdrawAmount": 10000
    }
}
`

Перевод денег с одного счета на другой: `
{
    "operationType": "SEND",
    "operationData": {
        "operationHost": 1,
        "operationReceiver": 2,
        "sendAmount": 10000
    }
}
`

operationType - определяет тип операции
operationHost - ИД счета над которых присходит операция (в нормальном workflow скорее всего был бы в каком ин будь токене)
operationReceiver - ИД счета кому производится отправка средств
