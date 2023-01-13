# Projekt Warcaby

Projekt zrealizowany w ramach przedmiotu Technologia Programowania na PWr, prowadzonego przez dr. Wojciecha Macyne.
Jest to nasza implementacja popularnej gry w warcaby, w wielu wariantach. Gra działa w modelu klient-serwer. Projekt powstał w języku JAVA z wykorzystaniem biblioteki JAVAFX.

## Wymagania
-Java </br>
-JavaFX

## Jak zagrać
<img src="https://user-images.githubusercontent.com/57231340/212222663-9e81ee63-0d64-40c7-88ba-5e0cee3597c7.jpg" alt="drawing" height="500"/> 


## Jak zagrać
- Pobierz kod z repozytorium
- Skompiluj z użyciem komend: (!!! Pamietaj aby zmienic nazwy directory na takie jakie masz u siebie)
   ```
  javac -d . --module-path /Users/denisstocki/Documents/javafx-sdk-18.0.1/lib --add-modules javafx.controls ChoiceGrid.java EndingStage.java EnglishBoard.java GameThread.java PolishBoard.java ClassicallyBoardable.java EndingThread.java Game.java ChoiceGrid.java OvertakingBoard.java board/BoardController.java board/BoardState.java board/Boardable.java board/ClassicBoard.java board/Field.java board/MouseState.java board/Pawn.java board/PawnLook.java board/PawnState.java
  ```
- Uruchom serwer a następnie dwóch klientów:
  ```
  java --module-path /Users/denisstocki/Documents/javafx-sdk-18.0.1/lib --add-modules javafx.controls tp.warcaby.klient.Game
  ```
- Wybierz jeden z trybów gry i to tyle

## Dokukemntacja i diagram Architektury: znajduje sie w folderze doc, wygenerowana w JAVADOC

<img src="https://user-images.githubusercontent.com/57231340/212220968-564b758b-7626-4f13-99f8-c58903bc7693.jpg" alt="drawing" width="500"/> <img src="https://user-images.githubusercontent.com/57231340/212221824-23fdf267-05f9-41e8-b596-59aa140684eb.jpg" alt="drawing" width="500"/>

## Autorzy: Denis Stocki, Jakub Dryka INA
