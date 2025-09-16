**Zadanie 1** Docker

:white_check_mark: 3.0 obraz ubuntu z Pythonem w wersji 3.10 [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/2fae912d6ac1d4fd385e5100859af2ca5a477952)

:white_check_mark: 3.5 obraz ubuntu:24.04 z Javą w wersji 8 oraz Kotlinem [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/985c200f369ae30d2d95c60d0b8579fb77433450)

:white_check_mark: 4.0 do powyższego należy dodać najnowszego Gradle’a oraz paczkę JDBC SQLite w ramach projektu na Gradle (build.gradle) [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/f5c061d992502005c374f093a5a518214f50875a)

:white_check_mark: 4.5 stworzyć przykład typu HelloWorld oraz uruchomienie aplikacji przez CMD oraz gradle [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/f5c061d992502005c374f093a5a518214f50875a)

:x: 5.0 dodać konfigurację docker-compose


Kod: folder "Zadanie1"

Dockerhub: https://hub.docker.com/repository/docker/wbzdeb/zad1/general

**Zadanie 2** Scala

:white_check_mark: 3.0 Należy stworzyć kontroler do Produktów [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/0742ec268d0abbc2b1e700edca9411af84624bba)

:white_check_mark: 3.5 Do kontrolera należy stworzyć endpointy zgodnie z CRUD - dane pobierane z listy [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/d98fd5184e12788efe41567c1454f1afdfb6972d)

:x: 4.0 Należy stworzyć kontrolery do Kategorii oraz Koszyka + endpointy zgodnie z CRUD 

:x: 4.5 Należy aplikację uruchomić na dockerze (stworzyć obraz) oraz dodać skrypt uruchamiający aplikację via ngrok (nie podawać tokena ngroka w gotowym rozwiązaniu)
:x: 5.0 Należy dodać konfigurację CORS dla dwóch hostów dla metod CRUD
Kontrolery mogą bazować na listach zamiast baz danych. CRUD: show all, show by id (get), update (put), delete (delete), add (post).


Kod: folder "Zadanie2"


**Zadanie 3** Kotlin

:white_check_mark: 3.0 Należy stworzyć aplikację kliencką w Kotlinie we frameworku Ktor, która pozwala na przesyłanie wiadomości na platformę Discord [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/315a957ae0913556489c4dc95ac0c53a8abe1412)

:white_check_mark: 3.5 Aplikacja jest w stanie odbierać wiadomości użytkowników z platformy Discord skierowane do aplikacji (bota) [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/315a957ae0913556489c4dc95ac0c53a8abe1412)

:white_check_mark: 4.0 Zwróci listę kategorii na określone żądanie użytkownika [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/315a957ae0913556489c4dc95ac0c53a8abe1412)

:white_check_mark: 4.5 Zwróci listę produktów wg żądanej kategorii [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/315a957ae0913556489c4dc95ac0c53a8abe1412)

:x: 5.0 Należy dodać konfigurację CORS dla dwóch hostów dla metod CRUD
Kontrolery mogą bazować na listach zamiast baz danych. CRUD: show all, show by id (get), update (put), delete (delete), add (post).


Kod: folder "Zadanie3"


**Zadanie 4** Go

:white_check_mark: 3.0 Należy stworzyć aplikację we frameworki echo w j. Go, która będzie miała kontroler Produktów zgodny z CRUD [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:white_check_mark: 3.5 Należy stworzyć model Produktów wykorzystując gorm oraz wykorzystać model do obsługi produktów (CRUD) w kontrolerze (zamiast listy) [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:white_check_mark: 4.0 Należy dodać model Koszyka oraz dodać odpowiedni endpoint [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:white_check_mark: 4.5 Należy stworzyć model kategorii i dodać relację między kategorią, a produktem [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:white_check_mark: 5.0 pogrupować zapytania w gorm’owe scope'y [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)


Kod: folder "Zadanie4"


**Zadanie 5** Frontend

:white_check_mark: 3.0 W ramach projektu należy stworzyć dwa komponenty: Produkty oraz Płatności; Płatności powinny wysyłać do aplikacji serwerowej dane, a w Produktach powinniśmy pobierać dane o produktach z aplikacji serwerowej; [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:white_check_mark: 3.5 Należy dodać Koszyk wraz z widokiem; należy wykorzystać routing [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:white_check_mark: 4.0 Dane pomiędzy wszystkimi komponentami powinny być przesyłane za pomocą React hooks [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:x: 4.5 Należy dodać skrypt uruchamiający aplikację serwerową oraz kliencką na dockerze via docker-compose

:x: 5.0 Należy wykorzystać axios’a oraz dodać nagłówki pod CORS


Kod: folder "Zadanie5"



**Zadanie 6** Testy

:white_check_mark: Należy stworzyć 20 przypadków testowych w CypressJS lub Selenium (Kotlin, Python, Java, JS, Go, Scala) [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:white_check_mark: 3.5 Należy rozszerzyć testy funkcjonalne, aby zawierały minimum 50 asercji [Link do commita](https://github.com/WBZdeb/E-Biznes-Zadania/commit/ee734019061cb542414aa3b009fe9d3e5ae3dae8)

:x: 4.0 Należy stworzyć testy jednostkowe do wybranego wcześniejszego projektu z minimum 50 asercjami

:x: 4.5 Należy dodać testy API, należy pokryć wszystkie endpointy z minimum jednym scenariuszem negatywnym per endpoint

:x: 5.0 Należy uruchomić testy funkcjonalne na Browserstacku


Kod: folder "Zadanie6"



Kod nalezy umieścić w w folderach lub na branchach, ale nalezy pod kazdym zadaniem wskazac link do brancha lub nazwę katalogu na repozytorium. 

Do kazdego zadania nalezy przygotowac demo za pomocą https://obsproject.com/, a film (skompresowany) nalezy umiescic w katalogu demos.
