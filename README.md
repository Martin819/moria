# MOIS – Moria – Analýza plateb

Server build: [![Build Status](http://194.182.88.14:8082/buildStatus/icon?job=build-server)](http://194.182.88.14:8082/job/build-server/)

Client build: [![Build Status](http://194.182.88.14:8082/buildStatus/icon?job=build-client)](http://194.182.88.14:8082/job/build-client/)



## IMPLEMENTOVANÉ USE CASY

V prototypu počítáme s jedním uživatelem a jeho jedním účtem.

1) Zobrazení výpisu plateb ([http://localhost:3000](http://localhost:3000))



*   fetchnutí z API
*   změna kategorie pro jednotlivou platbu (možnou přepsání automatického zařazení)
*   možnost filtrování 
    *   podle data
    *   příchozí/odchozí 

2) Správa pravidel ([http://localhost:3000/rules](http://localhost:3000/rules))



*   CRUD uživatelem vytvořených
*   v základu obsahuje několik předdefinovaných (ze kterých si může uživatel odvodit svoje)

3) Statistiky ([http://localhost:3000/stats](http://localhost:3000/stats))



*   grafické znázornění
*   negrafické znázornění (tabulky)
*   možnost filtrování


## ZDROJE DAT (API)


### API bankovního systému

[https://mois-banking.herokuapp.com/v1/api-docs](https://mois-banking.herokuapp.com/v1/api-docs)

Data pro tuto aplikaci jsou dostupná skrz AccountID = 6668


## DATABÁZE + DATA


#### Vzdálená DB (produkční)

Pro bezpečnost nedržíme údaje o připojení k DB v souboru application.properties, ale přímo v konfiguraci projektu v IDEI – před spuštěním projektu je potřeba otevřít _Run/Debug Configurations_ a zadat přihlašovací údaje.


#### Lokální DB (vývojová)

Pro vývoj je lepší vytvořit si v _Run/Debug Configurations _ještě druhou spouštěcí konfiguraci

A přepsat tyto parametry:

spring.datasource.url = jdbc:mysql://localhost:3306/moria?useTimezone=true&serverTimezone=GMT%2B8

spring.datasource.username = root

spring.datasource.password =		// nechat pole prázdné (pozor na mezery)

### Inicializace DB a mock data


#### Inicializace DB

V application.properties máme aktuálně nastaveno: 

spring.jpa.hibernate.ddl-auto = **create**

To funguje tak, že při každém spuštění se původní DB dropne a vytvoří znovu. Schéma vytvoří automaticky Hibernate a naplní se daty ze souboru import.sql (defaultní název požadovaný Springem).

_Ve Springu je možnost použít:_

*   buď soubory schema.sql + data.sql na vytvoření a naplnění DB 
*   nebo vytvoření nechat na Hibernate a použít jen import.sql na naplnění

_Všechny možnosti práce s DB:_

- **create** – Hibernate first drops existing tables, then creates new tables

- **update** – the object model created based on the mappings (annotations or XML) is compared with the existing schema, and then Hibernate updates the schema according to the diff. It never deletes the existing tables or columns even if they are no more required by the application

**create-drop** – similar to create, with the addition that Hibernate will drop the database after all operations are completed. Typically used for unit testing.

**validate** – Hibernate only validates whether the tables and columns exist, otherwise it throws an exception.

**none** – this value effectively turns off the DDL generation


#### Mock data

Mock data jsou přehledně definovaná a okomentovaná v excelovském souboru _20_radku_testovacich_transakci.xlsx_, ve kterém se daté generuje JSON pro posílání přes API a v případě nedostupnosti API jsou stejná data případně k dipozici i přes _import.sql._ 

**Aktuálně se v mock datech nalézá:**

21 zkušebních transakcí z toho jednu rozdělenou na další 4 dílčí transakce.



*   1 účet uživatele (ID = 6668)
*   2 platební karty
*   příjmy celkem 24 000, výdaje celkem 23 000
*   4 typy plateb 
    *   PAYMENT_HOME – převody mezi účty 
    *   CARD – platba kartou
    *   CASH – výběr hotovosti
    *   MORTGAGE – splátka hypotéky
*   některé platby kategorizované, jiné ne
    *   6x kategorizováno ručně, 15x nekategorizováno

5 zkušebních pravidel:



*   Každé by mělo zkategorizovat po jedné platbě, s vyjímkou pravidla na nákupy v Bille, které by mělo zkategorizovat platby dvě

_Jaký přesně je očekávaný průběh kategorizace?_


## KATEGORIZACE PLATEB


### Způsob implementace kategorií

Názvy kategorií neukládáme do DB, protože je to s ohledem na lokalizaci čistější řešení.

S kategoriemi pracujeme podle jejich ID:



*   Kategorie odchozích plateb….trojciferné číslo
*   Kategorie příchozích plateb….dvouciferné číslo

Všechny kategorie jsou napevno definované v enumu. Ani v prototypu, ani v plné verzi NEpočítáme s tím, že by si uživatelé tvořili vlastní. Výčet kategorií v prototypu není úplný, chybí například vše týkající se investiční sféry. V plné verzi by byly tyto a další kategorie doplněny. A taky pro jednoduchost NEřešíme podkategorie (např. u jízdného dělení na vlak / bus / letadlo).

Kvůli problémům s duplicitami mají kategorie příchozích plateb předponu I__nazevKategorie _(I podle slova incoming).


#### Implementované kategorie – outgoing


<table>
  <tr>
   <td><strong>CZ název</strong>
   </td>
   <td><strong>EN název</strong>
   </td>
   <td><strong>ID</strong>
   </td>
  </tr>
  <tr>
   <td>Jídlo
   </td>
   <td>Food
   </td>
   <td>111
   </td>
  </tr>
  <tr>
   <td>Alkohol
   </td>
   <td>Alcohol
   </td>
   <td>112
   </td>
  </tr>
  <tr>
   <td>Oděvy a móda
   </td>
   <td>Apparel & fashion
   </td>
   <td>113
   </td>
  </tr>
  <tr>
   <td>Nábytek a vybavení domácnosti
   </td>
   <td>Home equipment
   </td>
   <td>114
   </td>
  </tr>
  <tr>
   <td>Pohonné hmoty
   </td>
   <td>Fuel
   </td>
   <td>115
   </td>
  </tr>
  <tr>
   <td>Energie
   </td>
   <td>Utilities
   </td>
   <td>116
   </td>
  </tr>
  <tr>
   <td>Tabák a tisk
   </td>
   <td>Tobacco & press
   </td>
   <td>117
   </td>
  </tr>
  <tr>
   <td>Mobil, TV, internet apod.
   </td>
   <td>Phone / TV / Internet / Etc.
   </td>
   <td>118
   </td>
  </tr>
  <tr>
   <td>Jízdné
   </td>
   <td>Fare
   </td>
   <td>119
   </td>
  </tr>
  <tr>
   <td>Nájem
   </td>
   <td>Rent
   </td>
   <td>120
   </td>
  </tr>
  <tr>
   <td>Nemovitosti
   </td>
   <td>Real estate
   </td>
   <td>121
   </td>
  </tr>
  <tr>
   <td>Sport a volný čas
   </td>
   <td>Sport & leisure
   </td>
   <td>122
   </td>
  </tr>
  <tr>
   <td>Zdraví a krása
   </td>
   <td>Health & beauty
   </td>
   <td>123
   </td>
  </tr>
  <tr>
   <td>Zábava
   </td>
   <td>Entertainment
   </td>
   <td>124
   </td>
  </tr>
  <tr>
   <td>Cestování a ubytování
   </td>
   <td>Travelling & accommodation
   </td>
   <td>125
   </td>
  </tr>
  <tr>
   <td>Elektronika
   </td>
   <td>Electronics
   </td>
   <td>126
   </td>
  </tr>
  <tr>
   <td>Hazard a sázky (loterie, losy, dostihy)
   </td>
   <td>Gambling
   </td>
   <td>127
   </td>
  </tr>
  <tr>
   <td>Splátka půjčky/hypotéky
   </td>
   <td>Loans & mortgages
   </td>
   <td>128
   </td>
  </tr>
  <tr>
   <td>Pojištění
   </td>
   <td>Insurance
   </td>
   <td>129
   </td>
  </tr>
  <tr>
   <td>Dary a dárky
   </td>
   <td>Gifts
   </td>
   <td>130
   </td>
  </tr>
  <tr>
   <td>Jiné
   </td>
   <td>Other
   </td>
   <td>131
   </td>
  </tr>
</table>



#### Implementované kategorie – incoming


<table>
  <tr>
   <td>Plat / mzda
   </td>
   <td>Salary / wage
   </td>
   <td>11
   </td>
  </tr>
  <tr>
   <td>Důchod
   </td>
   <td>Pension
   </td>
   <td>12
   </td>
  </tr>
  <tr>
   <td>Sociální podpora
   </td>
   <td>Social assistance
   </td>
   <td>13
   </td>
  </tr>
  <tr>
   <td>Podnikání
   </td>
   <td>Business
   </td>
   <td>14
   </td>
  </tr>
  <tr>
   <td>Hazard
   </td>
   <td>Gambling
   </td>
   <td>15
   </td>
  </tr>
  <tr>
   <td>Nájem
   </td>
   <td>Rent
   </td>
   <td>16
   </td>
  </tr>
  <tr>
   <td>Splátka půjčky
   </td>
   <td>Loans
   </td>
   <td>17
   </td>
  </tr>
  <tr>
   <td>Kapesné
   </td>
   <td>Pocket money
   </td>
   <td>18
   </td>
  </tr>
  <tr>
   <td>Dary
   </td>
   <td>Gifts
   </td>
   <td>19
   </td>
  </tr>
  <tr>
   <td>Jiné
   </td>
   <td>Other
   </td>
   <td>20
   </td>
  </tr>
</table>



#### Bez přiřazené kategorie


<table>
  <tr>
   <td>Nekategorizováno
   </td>
   <td>Uncategorized
   </td>
   <td>0
   </td>
  </tr>
</table>


Bacha – je potřeba vnímat rozdíl mezi kategoriemi **Jiné **VS** Nekategorizováno**



*   **Jiné** = platby, co do ostatních kategorií nezapadají, nebo je tam uživatel nechtěl zahrnout.
*   **Nekategorizováno** = platby, které zatím nejsou nikam zařazeny. Typicky nové platby, které teprve čekají na zařazení nebo tvorbu pravidla.


### Zařazení platby do kategorie


#### Rozhodování o zařazení bere do úvahy (jednotlivě ale i libovolné kombinace)



*   číslo účtu odesílatele/příjemce (ve tvaru předčíslí, číslo účtu, kod banky)
*   směr platby (příchozí/odchozí)
*   typ platby (karetní, převod, splátka hypotéky, ...)
*   čas provedení platby (ne zaúčtování)
*   částku:
    *   částku pevnou/od
    *   částku do
*   zprávu (fuzzy způsob)
*   jméno protistrany
*   konst. symbol
*   var. symbol
*   specifický symbol
*   číslo karty


#### Princip zařazení platby do kategorie

**Myšlenka:**



*   Mám ruleset a v něm jednotlivá pravidla pro zařazení platby do kategorie
*   Mám transakci s nějakými parametry. Těch může být víc nebo míň.
*   Metoda pro každou transakci projde všechny rulesety a zvyšováním skóre vyjádří míru shody (vhodnosti použití) rulesetu a transakce.
*   Výstupy ukládá do TreeMapy ve tvaru <score, categoryID>
*   Nejvyšší skóre (tzn. nejvyšší podobnost pravidla s platbou) vyhrává a algoritmus vrátí ID odpovídající kategorie.

**Algoritmus:**

Pro každou nezařazenou transakci



*   Projdi všechny rulesety, který máme uložený 
    *   nejprve ošetři, že na PŘÍCHOZÍ transakci lze aplikovat jen pravidlo určené právě pro PŘÍCHOZÍ transakce, stejně tak se ošetří směr ODCHOZÍ
    *   pro každý ruleset projdi jednotlivý parametry pravidla a nastav skóre na nulu 
    *   porovnej hodnotu z pravidla s hodnotou z transakce a případně přičti určitou hodnotu<sup>*</sup> ke skóre
    *   _Jak se porovnávají jednotlivá pravidla?_
    *   _Před každým porovnáním se nejdříve zkontroluje, jestli je parametr v pravidle i platbě vyplněn (není null)_
        *   zpráva
            *   podle směru platby je ošetřeno zpracování buďto payee (přichozí platba) nebo payer (odchozí)
            *   porovnává se, jestli je celá zpráva v pravidlu obsažena ve zprávě transakce
            *   pokud není, tak ještě probíhá porovnání pomocí Fuzzy<sup>**</sup> knihovny
            *   pokud ani tento způsob nenajde shodu, skóre se nemění
            *   pokud je nalezena shoda, ke skóre se přičte 1
        *   číslo účtu
            *   porovná se, jestli prefix i číslo účtu z transakce obsahuje hodnoty z pravidla
            *   pokud ano, ke skóre se přičte 2, což nám zajistí, že transakce se kategorizuje výhradně podle tohoto pravidla
            *   pokud ne, tak se nastaví **isBankAccountFilledButDifferent **na true a celkové skóre se vynuluje.
        *   jméno protistrany
            *   podle typu platby je ošetřeno zpracování buďto MerchantName (platba kartou) nebo PartyDescription (ostatní typy plateb)
            *   porovnává, jestli je partyName pravidla obsaženo v parametrech transakce
            *   pokud ano, přičte se ke skóre 1
            *   pokud ne, skóre zůstává nezměněno
        *   čas zaúčtování transakce
            *   k porovnání je použita třída LocalTime
            *   porovnává, jestli je se čas zaúčtování transakce vyskytuje v rozmezí definovaném v pravidlu
            *   pokud ano, ke skóre se přičte 1
            *   pokud ne, skóre se nemění 
        *   částka transakce
            *   pokud v pravidle není vyplněno částka od a částka do, tak se skore nemění (není podle čeho porovnávat)
            *   pokud je v pravidle vyplněno pouze částka do a zároveň je transactionValue menší než částka do, tak se ke skóre přičte 1
            *   pokud je v pravidle vyplněno pouze částka od a zároveň je transactionValue větší než částka od, tak se ke skóre přičte 1
            *   pokud jsou v pravidle vyplněny částka od i částka do a zároveň se transactionValue vyskytuje v tomto rozmezí. přičte se ke skóre 1
            *   else skóre se nemění  
        *   číslo karty 
            *   porovnává, jestli číslo karty stejné jako číslo karty definované v pravidle
            *   pokud je stejné, ke skóre se přičte 2
            *   pokud není, skóre se nemění
        *   Konstantní, variabilní, specifický symbol
            *   u všech tří symbolů se používá stejný princip
            *   porovnává se, jestli je symbol z pravidla obsažen v symbolu transakce
            *   pokud ano, ke skóre se přičte 2
            *   pokud ne, skóre se nemění
        *   typ transakce 
            *   porovnává se, jestli je typ z pravidla obsažen v typu transakce
            *   pokud je typ jediným parametrem rulesetu
                *   pokud ano, ke skóre se přičte 1 (má větší váhu)
                *   pokud ne, ke skóre se přičte 0.5
    *   po vyhodnocení všech parametrů pravidla proběhne vyhodnocení podmínky** isBankAccountFilledButDifferent **
                *   pokud je true, tak se skore nastavi na 0
                *   pokud je false, neděje se nic a skore zůstává stejné
*   Ke každému rulesetu, jehož skóre je >=1 (našla se aspoň 1 shoda s nějakou transakcí) se uloží hodnota skóre do tree mapy ve tvaru <score, categoryID>
*   Vyber hodnotu nejvyššího skóre v mapě a podle ní ulož k transakci kategorii

_**  _FuzzySearch.partialRatio _nám vrátí hodnotu <0,100>, která definuje míru shody dvou Stringů. v algoritmu máme určený threshold 75. Pokud nám Fuzzy vrátí hodnotu větší než 75, považujeme Stringy za shodné._


#### Pipeline kategorizace plateb

Aktuálně se provede kategorizace po najetí na adresu [http://localhost:8080/plsCategorize](http://localhost:8080/plsCategorize), **kde se zkategorizují všechny transakce bez categoryID**. Další možností je [http://localhost:8080/categorize](http://localhost:8080/categorize) bez výpisu plateb.

Při vytvoření nového pravidla se všechny transakce překategorizují. Při smazání pravidla také.

Kategorie jdou přiřadit i ručně ve FE, takto přiřazené kategorie už nejdou žádným pravidlem přepsat.

**_Poznamka: Pri jakykoliv praci s cislem uctu, at uz ve smeru FE --> BE nebo BE --> FE, posilejte nejdriv cislo uctu pres jednu z getNormalizedAccountNumber() metodu. Je moznost tam poslat cely objekt TransactionPartyAccount nebo 3 stringy s prefixem, cislem uctu a kodem banky._**

_Tim zajistime, ze cisla uctu budou vzdycky ve stejnem standardizovanem formatu._


#### Jak ověřím, že kategorizace funguje (aspoň v principu) správně?

**Ve výpisu v konzoli (první dva sloupce by měly odpovídat výpisu)**


<table>
  <tr>
   <td><strong>Platba</strong>
   </td>
   <td><strong>Kategorie</strong>
   </td>
   <td><strong>Použité pravidlo</strong>
   </td>
  </tr>
  <tr>
   <td>ab02
   </td>
   <td>111
   </td>
   <td>Jméno protistrany
   </td>
  </tr>
  <tr>
   <td>ab04
   </td>
   <td>128
   </td>
   <td>Typ (hypotéka)
   </td>
  </tr>
  <tr>
   <td>ab07
   </td>
   <td>17
   </td>
   <td>Číslo účtu
   </td>
  </tr>
  <tr>
   <td>ab12
   </td>
   <td>11
   </td>
   <td>Číslo účtu + částka v rozmezí
   </td>
  </tr>
  <tr>
   <td>ab15
   </td>
   <td>111
   </td>
   <td>Jméno protistrany
   </td>
  </tr>
  <tr>
   <td>ab17
   </td>
   <td>124
   </td>
   <td>Jméno protistrany
   </td>
  </tr>
</table>


**Okometrickou metodou:**



*   [http://localhost:3000/stats](http://localhost:3000/stats)

    Grafy by měly vypadat takhle


    
TODO: obrazky grafu



**Na datech:**



*   kategorizují se pouze platby bez přiřazené kategorie (tzn. napoprvé celkem 15 plateb)
*   platbám s už přiřazenou kategorií se kategorie nezmění
*   5 plateb nově získá kategorii, z toho 2x příjmy a 3x výdaje
    *   jeden příjem spadne do kategorie 17 (Loans)
    *   jeden příjem spadne do kategorie 11 (Salary / wage)

        (zbývající tři příjmy zůstanou nekategorizovány)

    *   jeden výdaj spadne do kategorie 20 (Other)
    *   jeden výdaj spadne do kategorie 128 (Loans & mortgages)
    *   jeden výdaj spadne do kategorie 124 (Entertainment)

**Přes testy:**



*   Aktuálně test zrcadlí rulesety i transakce z import.sql a je tedy schopem ověřit to víceméně stejně.


## ROZDĚLOVÁNÍ TRANSAKCE NA DÍLČÍ TRANSAKCE


### Princip rozdělování 

spočívá v tom , že máme transakci, kterou chceme dále specifikovat (např. výběr z bankomatu). 

**Myšlenka**



*   Nerozdělená transakce má hodnotu v položce amount.  \
Pokud má dojít k rozdělení transakce je hodnota amount vynullovana a hodnota presunuta do polozky originValue
*   Dílčí transakce (ta která rozděluje parentí) vezme všechny parametry parent transakce, kromě id, category id, amountu. Zárověň je ji vyplněna položka parent_id, kterou převezme od parent transakce (id). 
*   Zároveň je pro FE vytvořena zbytková transakce (pro statistiky), která vezme rozdíl mezi parent original_value a zbylými dílčími transakcemi. Od ostatních transakci se liší tím, že má category_id 0 (Uncategorized).
*   V případě smazání dílčí transakce dojde k prepočítání zbytkové transakce (pokud již žádná dílčí transakce kromě zbytkové nezbývá, nastaví se hodnota zbytkové transakce na 0)

Na FE uživatel zadá rozdělení transakce a na na backend pošle id rodičovské transakce, částku dílčí transakce a její kategorii. V případě smazání přichází z FE pouze id transakce pro smazání.


### Implementace

_Pojmy (občas se vyskytují jiné výrazy pro stejnou věc, tak aby bylo jasno)_



*   _parentí transakce_
*   _dílčí transakce = child transakce, subtransakce_
*   _zbytková transakce = dopočítávací transakce_


#### Splitování transakce



*   Přijde nám objekt z FE (parent_id, amount, category_id)
*   Nalezeneme parenti transakci a vytvoříme její kopii
*   Pokud se jedná o první rozdělení transakce (poznáme podle toho, že má ještě vyplněné value a ne original value) přehodíme parentí transakci zmíněné value do original value
*   Nové (dílčí) transakci pak nasetuju hodnoty příchozí z FE, zbytek je stejný jako od parenta 
*   Vyhledám všechny dílčí transakce patřící parentí transakci a podivam se, jestli nemá nějaká stejné category_id, jako ten objekt, který přišel z FE
    *   Pokud ne, uložím novou transakci do databáze
    *   Pokud ano, updatuju amount dane dílčí transakce o součet jejiho amountu a amountu z FE
*   Zavolám metodu _updateRestOfAmountToOriginalValue_ 
    *   Pro zadanou dílčí transakci zjistí rozdíl mezi parent transakci (originalValue) a dílčími transakcemi (součet amountu)
    *   Pokud ještě neexistuje zbytková transakce, vytvoří kopii parent transakce a nasetuje ji předchozí vypočítaný rozdíl, jako value, category_id na 0 (protože je to ten neznámý zbytek - nutné pro statistiky na FE) a parent_id parent transakce (a uloží do db)
        *   pokud transakce už existuje, je ji pouze aktualizována amount částka
*   Na FE vratím parent transakci s listem dílčích transakcí (pro zobrazení na FE)


#### Smazání dílčí transakce



*   Z FE přijde id dílčí transakce pro smazání
*   Vytáhnu si z databáze dílčí transakci a smažu ji (servisa potřebuje celou transakci)
*   Najdu všechny zbývající dílčí transakce (podle parent_id) a spočítám, kolik jich zbylo
    *   Pokud je transakce poslední (tzn. mám tam jenom zbytkovou transakci), tak smažu transakci a parent transakci přehodím original_value zpátky do amountu
    *   V případě, že transakce poslední není, zavolám metodu _updateRestOfAmountToOriginalValue_, která aktualizuje hodnotu zbytkové transakce (viz. řádky výše)
*   Na FE vracíme opět parent transakci 


## BACKEND


### Controllery


#### RulesController


<table>
  <tr>
   <td>GET
   </td>
   <td>rules/getAll
   </td>
   <td>vytáhne všechna pravidla z databáze a pošle na FE
   </td>
  </tr>
  <tr>
   <td>POST
   </td>
   <td>rules/create
   </td>
   <td>z FE přijde pravidlo a to se uloží do databáze tak jak přijde
   </td>
  </tr>
  <tr>
   <td>POST
   </td>
   <td>rules/remove
   </td>
   <td>z FE přijde seznam IDček a podle nich se vymažou daná pravidla z databáze
   </td>
  </tr>
  <tr>
   <td>PUT
   </td>
   <td>rules/update
   </td>
   <td>z FE přijde upravené pravidlo a to se aktualizuje v databázi
   </td>
  </tr>
</table>


_CategorizedTransactionController_


<table>
  <tr>
   <td>GET
   </td>
   <td>/transaction
   </td>
   <td>vytáhne všechny transakce z DB, transformuje na dto a pošle na FE
   </td>
  </tr>
  <tr>
   <td>PUT
   </td>
   <td>/transaction/update
   </td>
   <td>z FE přijde id transakce a jmeno kategorie a podle toho se updatuje transakce v DB, navíc se daná transakce nastaví jako manullyUpdated
   </td>
  </tr>
  <tr>
   <td>POST
   </td>
   <td>/transaction/split
   </td>
   <td>z FE přijde id parent transakce, id kategorie a částka (BigDecimal), podle toho rozdělí transakci na dílčí transakce (pokud ještě nebyla vytvořena žádná dílčí transakce, vloží krom příchozí dílčí transakce ještě dopočítávací dílčí transakci (pro FE)
   </td>
  </tr>
  <tr>
   <td>POST
   </td>
   <td>/transaction/removeSplit
   </td>
   <td>Smaže dílčí transakci. Jako parametr bere celou transakci pro smazání
   </td>
  </tr>
</table>


TransactionsToDtoMapper (přesunuto do Utils) využitý v loadAllTransactions() použivá tuhle logiku:



*   Parametr partyDescription vzdycky obsahuje nazev obchodnika nebo název protistrany nebo cislo uctu protistrany. nemelo by se stat ze bude prazdny nebo null. 

    V pripade chybejicich informaci bude obsahovat “Unknown”.

*   Regex (regulární výraz) použitý pro získání jednotného tvaru čísla bankovního účtu vymaže nuly před samotným číslem účtu, resp. je nahradí “” (prázdným stringem)

	Vysvětlení zástupných znaků:

	`^ - začne na začátku stringu`


```
0 - hledaný řetězec
+ - 1 nebo více výskytů hledaného řetězce
(?!$) - "negative lookahead" (pokud nejsou na konci řádku)
```


_IncomingTransactionController_


<table>
  <tr>
   <td>GET
   </td>
   <td>
            /fetchTransactions
   </td>
   <td>stáhne všechny transakce z API podle konfigurace v BankingAPIConfiguration interfacu
   </td>
  </tr>
  <tr>
   <td>GET
   </td>
   <td>
            /saveTransactions
   </td>
   <td>stáhne vsechny transakce z API a nove (podle ID) uloží do DB
   </td>
  </tr>
  <tr>
   <td>GET
   </td>
   <td>
            /saveTransactions/<em>{fromDate}</em>/<em>{toDate}</em>
   </td>
   <td>stáhne transakce z API mezi danymi datumy a nove (podle ID) uloží do DB 
<p>
Zavolá se kategorizace na stáhnuté transakce
   </td>
  </tr>
</table>



### Testy


#### Integrační

Testuju více (provázaných) aplikačních vrstev, (často) potřebuju přístup k databázi, typicky testy servisních tříd

**RulesetServiceTest**



*   používá testovací databázi (H2, bylo potřeba definovat v pom.xml)
*   by default se databáze rollbackne po každém provedeném testu, takže by nemělo dojít ke konfliktům (pokud třeba v rámci více testů pracuju se stejnými daty)

#### Jednotkové

Testuju jen jednu “vrstvu” (logiku), pokud v průběhu testu potřebuju data, která reálně tahám z databáze, tak si je namockuju (prostě počítám s tím, že data z db mi přijdou, jak očekávám) - v unit testu tedy nepoužívám databázi

**CategoryScorerTest**



*   Princip: vytvořím testovací data, které podstrčím CategoryScoreru a následně assertuju, jestli se mi vrátí očekáváné categoryID
*   testovací data vytvořena přes TestUtils

**_Poznámka: když budete chtít spustit integrační test, nastavte _**spring.jpa.hibernate.ddl-auto=update_, kdyby tam bylo create, tak se hibernate snaží najebat import.sql do té testovací databáze a hází to chybové hlášky do konzole. Reálně to tomu testu (asi?) nevadí, ale dělá to akorát matoucí bordel v konzoli :)_


## FRONTEND


### Jaké informace o transakcích se zobrazují na FE?



*   Příchozí/odchozí tuzemsko (číslo a jmeno účtu, platební symboly, zpráva, částka, datum, čas)
*   Platba kartou? (název obchodníka, částka, datum, čas)
*   Výběry? (bankomat, částka, datum, čas)


## DEPLOYMENT


### Jenkins

Na adrese [http://194.182.88.14:8082/](http://194.182.88.14:8082/) běží Jenkins pro deployment

Joby:

*   build-server
    *   naklonuje moria repo z GH
    *   spusti maven build
    *   zpristupni .jar soubor
*   build-client
    *   naklonuje moria repo z GH
    *   nainstaluje npm
    *   spusti npm build
    *   zabali build adresar do tar.gz
    *   zpristupni archiv
*   [deploy-server](http://194.182.88.14:8082/job/deploy-server)
    *   Sestavi maven balik (build-server job)
    *   Posle balik pres SSH na server
    *   Spusti server jako systemd service
    *   Server je pak dostupny na [http://194.182.88.14:8083](http://194.182.88.14:8083/)
*   [deploy-client](http://194.182.88.14:8082/job/deploy-client)
    *   Sestavi npm balik (build-client job)
    *   Posle blaik pres SSH na server
    *   Spusti deployment
    *   Xicht je dostupny na [http://194.182.88.14:8080](http://194.182.88.14:8080/)


## Použité technologie a knihovny  \
TODO:


![alt text][Náš tým]

[Náš tým]: http://diymag.com/images/uploads/hobbitfull600.jpg

