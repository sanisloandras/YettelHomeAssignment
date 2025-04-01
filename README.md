Rövid videó bemutató:
https://drive.google.com/file/d/1dxTF4g7xnNlFssb5py5U57gAOMVQPr53/view?usp=sharing

Telepítési dokumentáció:
Előfeltétel: Android Studio Meerkat
- válaszd ki a "development" branch-et,
- győződj meg, hogy a HighwayApi.BASE_URL értéke a környezetednek megfelelő

Javaslatok (bónusz feladat):
API:
1) Amennyiben helyes értelmezem az API-t, ez a két property 
"category": "CAR", "vignetteCategory": "D1", 
összevonható
2) nem teljesen értem, hogy az éver vármegye matricák id-ja miért YEAR_11, YEAR_12, stb, ez elég furcsa
3) Az "order" végpontnak "cost" paramétert adtam át(nem a "sum"-ot), remélem ez helyes, de ez így kicsit félrevezető.
4) Az "order" végpont visszatéríti a megvásárolt matricákat, de a mobil UI ezt nem használja

UI: 
1) A Figma tervek és az API alapján arra a következtetésre jutottam, hogy különböző vármegyékben a matrica ára eltérhet. A kódot is ennek megfelelően írtam. Viszont más webes felületeken azt tapasztaltam, hogy egységes a vármegye matricák ára is, ezen lehetne egyszerűsíteni. 
Amenniben igen, úgy a highway/info API válaszból kivehető a county(vármegyék) property, mivel hogy a vármegyék száma és neve nagyon ritkán változik, ezért megkockáztatom, hogy bele lehetne égetni az alkalmazás kódjába (ennek hátránya - hogyha tényleg változik egy vármegye neve, akkor frissítést kell kiadni, előnye - hogy kevesebb a traffic)
3) A technikai leírásból:
Készíts egy ... alkalmazást, amelyben a felhasználó egy egyszerűsített autópálya matrica vásárlást tud végrehajtani. Az adott felhasználóhoz tartoznak autó információk is (rendszám, autó típusát jelző ikon, autóhoz köthető név), amelyek megjelenítésre kerülnek a kezdőoldalon.
Itt többes számban - megjelenítésre kerülnek. Ennek megfelelően a  "highway/info" válaszban van egy "vehicleCategories" egy tömb:
"vehicleCategories": [ { "category": "CAR", "vignetteCategory": "D1", "name": { "hu": "Személygépjármű", "en": "Car" } } ],  Viszont ez a figma terveken sajnos nincs bemutatva, ott mindig csak 1 gépjármű információja van megjelenítve.
Továbbá, ez azt is jelenti, vagy jelentheti, hogy matrica vásárlás előtt a felhasználónak autót is kell választania?. Különböző gépjármű típusokhoz különböző típusú matricák különböző összegért vásárolhatóak. Ezeket az esetek nincsenek lefedve sem a figmában, sem az api válaszokban. Ez azért lenne, mert "egyszerűsített" autópálya matrica vásárlást kell lefejleszteni?

Amire nem maradt időm:
- térképes nézet. Valamiért csak .png formátúmban sikerült exportálnom a képes resourcokat (például a "sikeres rendelés" képernyőn a képeket). Valamennyit kutattam, és legtöbbször a geoJSON jött elő mint megoldási javaslat.
https://github.com/wuerdo/geoHungary
- teljes test lefedettség
- a process death-et egyenlőre nem kezeli teljes mértékben az alkalmazás

Megjegyzések:
- egyéb 3rd party libek amiket nem használtam, hogy minimalista maradjon a kód: https://arrow-kt.io/
https://github.com/MobileNativeFoundation/Store
