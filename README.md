# Utazó Ügynök Probléma - TSP (Traveling Salesman Problem)

Az utazó ügynök problémában adottak városok, amelyeket az ügynöknek meg kell látogatnia. A városok közötti távolságok ismeretében az a feladat, hogy megtaláljunk egy minimális hosszúságú útvonalat, amely mindegyik várost pontosan egyszer érinti (egyszer lép be, és egyszer lép ki onnan) illetve az út vége ugyanaz a város lesz, amelyből kiindultunk. 

Ez az Android alkalmazás a TSP problémát a felhasználó által megadott pontokra vizsgálja.

### Város elhelyezése
A képernyő tetszőleges pontját megérintve (amennyiben még nincs ott másik város) új várost adhatunk hozzá.
### Város mozgatása
Egy tetszőleges várost megérintve követi az ujjunk mozgását a kijelzőn.
### Városok törlése
A kijelző jobb felső sarkában található kuka ikon megérintésével törölhetjük az összes várost.
### Algoritmus választása
A bal felső sarokban található "hamburger" ikonra kattintva megjelenik egy menü, mely a választható algoritmusokat tartalmazza.
  - #### Nyers erő
  Az összes lehetséges útvonalat végignézi és kiválasztja a legrövidebbet. A lehetséges útvonalak száma n! (ahol n a városok száma), ezért több város (n>15) esetén hosszú futása időre lehet számítani.
  - #### Elágazás és korlátozás
  Rekurzióval elindul a lehetséges útvonalakon, de amint túl költségesnek talál egy útvonalat, nem folytatja azt.
  - #### Mohó
  Mindig az aktuálisan legközelebbi várost választja.

A kiválasztott algoritmus szerinti legrövidebb útvonal automatikusan kirajzolásra kerül.
