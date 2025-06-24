# Projekt zaliczeniowy - symulacja pracy windy w Javie

Przy użyciu kolekcji TreeSet, ustala kolejność pięter, tak aby zasymulować zatrzymywanie się "po drodze" w czasie pracy windy.

Przy każdym piętrze widnieje `summoner`, użytkownik może nim wezwać windę na dane piętro, na każdym piętrze pasażerowie wsiądają automatycznie, wysiadanie jest obsługiwane przez użytkownika, należy kliknąć na ikonkę pasażera, z zamysłu pasażerowie po wyjściu z windy nie wracają do kolejki.
`summoner` wyświetla również aktualny kierunek windy.

Po lewo widoczne są przyciski windy, gdy winda stoi w miejscu, pasażerowie wybierają pietro na które ma pojechać winda, można wcisnąć kilka przycisków jeden po drugim, TreeSet ustali poprawną kolejność.

Symulacja kończy się 5 sekund po spełnieniu trzech warunków: winda jest zatrzymana, brak nowych wezwań windy, brak pasażerów w kabinie
## Preview
![preview](./preview.gif)