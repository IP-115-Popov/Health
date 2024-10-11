# Task and Project Management App with Gamification

## Описание

Данное приложение предназначено для управления задачами и проектами с элементами геймификации. Оно позволяет пользователям устанавливать и отслеживать цели, анализировать данные и получать вознаграждения за выполнения задач. Приложение реализует контроль различных аспектов жизни, включая:

- Длительность сна
- Оценка прошедшего дня
- Время работы
- Посещения тренажерного зала
- Пройденные шаги
- Настраиваемые элементы отслеживания активности (например, чтение книг, время, проведенное на определенных занятиях и т. д.)

## Технологии

В приложении используются следующие технологии и библиотеки:

- **Dagger Hilt**: для внедрения зависимостей.
- **ViewModel**: для управления жизненным циклом пользовательского интерфейса.
- **Jetpack Compose**: для разработки современного интерфейса пользователя.
- **Clean Architecture**: структура проекта включает три слоя:
  - **App**: слой пользовательского интерфейса и взаимодействия с пользователем.
  - **Domain**: бизнес-логика приложения.
  - **Data**: взаимодействие с данными (API, базы данных).

## Структура проекта

```plaintext
- app
- domain
- data
```

## Функционал

- **Планирование задач**: возможность создавать временные рамки для задач и отслеживать их выполнение.
- **Добавление задач**: легкий и интуитивно понятный интерфейс для добавления новых задач.
- **Удаление задач**: возможность удаления задач как завершенных или отмененных.
- **Выполнение целей**: пользователи могут устанавливать цели и получать бонусы за их выполнение.
- **Анализ данных и построение графиков**: отображение прогресса и продуктивности с помощью визуальных графиков.
- **Геймификация**: начисление уровней за выполнение задач, с возможностью получения очков даже за частично выполненные задачи.
- **Запись рекордов и достижений пользователей**: пользователи могут отслеживать свои достижения и рекорды.

## Скриншоты
![Alt text](https://github.com/user-attachments/assets/b8eb9258-856e-4138-8885-8015b7c34006?raw=true)




## Диаграмма Use Case

![Alt text](https://github.com/user-attachments/assets/44fe7477-e025-4a67-9c62-216c36bd8daa?raw=true)

![Alt text](https://github.com/user-attachments/assets/c831d3fa-72d1-4ce0-b810-58ff6e0293c3?raw=true)

## Установка и настройка

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/IP-115-Popov/Health.git
   ```
2. Откройте проект в Android Studio.
3. Синхронизируйте проект с Gradle.
4. Запустите приложение на вашем устройстве или эмуляторе.

## Вклад

Если вы хотите внести свой вклад в проект, пожалуйста, создайте Pull Request с описанием ваших изменений.

### Контакты

Для вопросов или предложений обращайтесь по адресу: serzh.serp123.popov@mail.ru
