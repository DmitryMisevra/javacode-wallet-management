#  Wallet service

---

## Описание

Приложение для пополнения и снятия денег со своего кошелька

---

## Функциональность

В приложении реализованы два эндпойнта:

* **Пополнение/снятие со счета**
* **Просмотр информации о кошельке** 

### Фичи и просто факты:

- На входе каждый json проходит валидацию UUID и корректность написания операции DEPOSIT/WITHDRAW
- Транзакция блокируется на время операций по пополнению/снятию
- Приложение прошло нагрузочное тестирование (1000 RPS) в Jmeter

---


## Технологический стек

Проект использует следующий набор технологий, библиотек и инструментов:

- **[Spring Boot](https://spring.io/projects/spring-boot)**

- **[Docker](https://www.docker.com/)**

- **[Lombok](https://projectlombok.org/)**

- **[PostgreSQL](https://www.postgresql.org/)**

- **[Spring JPA](https://spring.io/projects/spring-data-jpa)**

- **[Hibernate](https://hibernate.org/)**

---

## Установка и запуск

Для того чтобы запустить проект у себя локально, необходимо выполнить следующие шаги:

### Предварительные требования

Убедитесь, что на вашем компьютере установлены следующие инструменты:

- Java JDK 11 или выше: [Скачать](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- Docker: [Инструкция по установке](https://docs.docker.com/get-docker/)
- IntelliJ IDEA (для разработки): [Скачать](https://www.jetbrains.com/idea/download/)

### Установка проекта

1. Клонируйте репозиторий проекта:

```
git clone https://https://github.com/DmitryMisevra/javacode-wallet-management.git
cd javacode-wallet-management
```

2. Запустите все сервисы одной командой с использованием Docker Compose:

```
docker-compose up --build
```

Эта команда сначала соберет образы для сервисов, указанных в файле docker-compose.yml, если они еще не собраны,
и затем запустит контейнеры. Флаг --build гарантирует, что будут использованы самые последние версии образов.

После успешного запуска контейнеров приложение будет доступно по следующим адресу: http://localhost:8080
База данных доступна на порту 6541.
