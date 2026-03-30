### Что мы сделали для DI с Hilt
## 1. Подключили Hilt
Добавили плагин и зависимости Hilt в build.gradle
## 2. Настроили Application
Создали NotesApp с аннотацией @HiltAndroidApp
Зарегистрировали его в AndroidManifest.xml
## 3. Создали Hilt-модули
DatabaseModule - для предоставления Room, DAO, репозитория
UseCaseModule - для предоставления UseCase
Использовали @Provides и @Singleton
## 4. Пометили Activity
Добавили @AndroidEntryPoint над MainActivity
## 5. Переделали ViewModel
Добавили @HiltViewModel и @Inject в конструктор
Внедрили все необходимые UseCase
## 6. Упростили получение ViewModel
Заменили ручное создание на hiltViewModel()
## 7. Удалили ручное создание зависимостей
Убрали из Activity код создания базы данных, репозитория и UseCase
Теперь всё создаётся автоматически через Hilt
### Результат: код стал слабо связанным, зависимости создаются централизованно, упростилось тестирование.
