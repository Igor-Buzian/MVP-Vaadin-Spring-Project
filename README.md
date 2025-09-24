# Project Overview

| Модуль      | Что хранить                             | Зависимости                     |
| ----------- | --------------------------------------- | ------------------------------- |
| core        | Entities, DTOs, base interfaces         | -                               |
| shared      | Common DTOs, utilities, interfaces      | core                            |
| backend     | Services, repositories, business logic  | core, shared                    |
| frontend    | UI components, entry point              | shared                          |
| application | Entry point, configuration, integration | backend                         |

## How to run

Open the project in your IDE.

Start the `application` module (Spring Boot).

Then start the `frontend` module (Spring Boot).

---

# Обзор проекта

| Module     | Description                             | Dependencies                     |
| ----------- | --------------------------------------- | ------------------------------- |
| core        | Entity, DTO, базовые интерфейсы         | -                               |
| shared      | Общие DTO, утилиты, интерфейсы          | core                            |
| backend     | Сервисы, репозитории, бизнес-логика     | core, shared                    |
| frontend    | UI-компоненты, Точка запуска            | shared                          |
| application | Точка запуска, конфигурация, интеграция | backend                         |

## Как запустить

Откройте проект в вашей IDE.

Сначала запустите модуль `application` (Spring Boot).

Затем запустите модуль `frontend` (Spring Boot).


---

