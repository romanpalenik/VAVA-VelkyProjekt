# Osobný plánovač DenFit

Jedná sa o osobný plánovač pre študentov s možnosťou kalendáru, poznámok, to-do listu a uloženia užitočných linkov.

Splnené požiadavky:
Použitie kolekcí
Celá tireda databáza je reprezentovaná pomocou ArrayListov a Hashmaps
https://github.com/romanpalenik/VAVA-VelkyProjekt/blob/a7b0a4a9fd024313c98487510c6276772ea5f15e/src/project/model/databases/CalendarDatabase.java#L14

Internacionalizácia:
Celá aplikácie je v 2 jazykoch: slovenčina a angličtina. Na internacionalizáciu používame resource budle.
https://github.com/romanpalenik/VAVA-VelkyProjekt/blob/a7b0a4a9fd024313c98487510c6276772ea5f15e/src/project/Bundle_en.properties#L1

Konkrétny preklad:
https://github.com/romanpalenik/VAVA-VelkyProjekt/blob/a7b0a4a9fd024313c98487510c6276772ea5f15e/src/project/controller/UseFullLinksController.java#L185

Logovanie:
(Lucia dopln, neviem kde)

XML:
Používame FXML súbory na GUI. Príklad celý balíč view. Napríklad kalendár:
https://github.com/romanpalenik/VAVA-VelkyProjekt/blob/a7b0a4a9fd024313c98487510c6276772ea5f15e/src/project/view/calendar/calendar.fxml#L1

I/O:
serializácia implementované pomocou FileInputStream, ObjectInputStream,FileOutputStream, ObjectOutputStream
https://github.com/romanpalenik/VAVA-VelkyProjekt/blob/a7b0a4a9fd024313c98487510c6276772ea5f15e/src/project/model/databases/CalendarDatabase.java#L20

Regulárne výrazy:
https://github.com/romanpalenik/VAVA-VelkyProjekt/blob/a7b0a4a9fd024313c98487510c6276772ea5f15e/src/project/controller/TodoController.java#L172
