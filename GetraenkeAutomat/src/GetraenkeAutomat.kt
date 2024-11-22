/*
  Projekt:      GetraenkeAutomat
  Firma:        ABB Technikerschule
  Autor:        Marco Bontognali

  Beschreibung: GetraenkeAutomat prozedural implementiert.
  Hinweis:      Buchstaben werden bei der Eingabe nicht abgefangen.
 */

val MUENZEN = listOf(0.1, 0.2, 0.5, 1.0, 2.0, 5.0)
val MIN_WASSERSTAND_DL = 2
val BECHER_VOLUMEN_DL = 2

val KAFFEE_PREIS = 1.0
val TEE_PREIS = 1.1

var option = 0
var wasserstandDL = 10
var kredit = 0.0

fun main() {
    zeigeMenue()
    verarbeiten()
}

fun zeigeMenue() {
    println("------- MENUE -------")
    println("Muenze einwerfen....1")
    println("Kaffee (" + KAFFEE_PREIS + "0 CHF)...2")
    println("Tee    (" + TEE_PREIS + "0 CHF)...3")
    println("Retour-Geld.........4")
    println("Beenden.............0")
    println("---------------------")
}

fun verarbeiten() {
    do {
        print("Ihre Wahl: ")
        option = readln().toInt()
        when (option) {
            1 ->    muenzeEinwerfenMenuPunkt()
            2 ->    kaffeeMenuPunkt()
            3 ->    teeMenuPunkt()
            4 ->    rueckgabeMenuPunkt()
            0 ->    println("Bye bye!")
            else -> println("Ungueltige Eingabe!")
        }
    } while (option != 0)
}

fun muenzeEinwerfenMenuPunkt() {
    println("Geben Sie die Muenze an:")
    akzeptierteMuenzen()
    val muenze = readln().toDouble()
    einwerfen(muenze)
}

fun akzeptierteMuenzen() {
    for (muenze in MUENZEN) {
        print(muenze.toString() + "0 | ")
    }
    println("CHF")
}

fun einwerfen(muenze: Double) {
    if (MUENZEN.contains(muenze)) {
        kredit += muenze
        korrektur()
        println("Kredit: " + kredit + "0 CHF")
    }
    else {
        println("Ungueltige Muenze!")
    }
}

fun kaffeeMenuPunkt() {
    when {
        keinWasser()           -> println("Kein Wasser vorhanden!")
        keinGeld(KAFFEE_PREIS) -> println("Kein Geld vorhanden!")
        else ->  {
            belasten(KAFFEE_PREIS)
            macheKaffee()
        }
    }
}

fun teeMenuPunkt() {
    when {
        keinWasser()        -> println("Kein Wasser vorhanden!")
        keinGeld(TEE_PREIS) -> println("Kein Geld vorhanden!")
        else -> {
            belasten(TEE_PREIS)
            macheTee()
        }
    }
}

fun keinWasser() = wasserstandDL < MIN_WASSERSTAND_DL

fun keinGeld(betrag: Double) = kredit - betrag < 0

fun belasten(betrag: Double) {
    kredit -= betrag
    korrektur()
    println("Kredit: " + kredit + "0 CHF")
}

fun macheKaffee() {
    println("--------------------")
    println("Becher auswerfen...")
    println("Bohnen mahlen...")
    println("Wasser eingiessen...")
    wasserstandDL = wasserstandDL - BECHER_VOLUMEN_DL
    println("...Kaffee ist fertig")
    println("Wasserstand: $wasserstandDL Deziliter")
}

fun macheTee() {
    println("--------------------")
    println("Becher auswerfen...")
    println("Beutel einwerfen...")
    println("Wasser eingiessen...")
    wasserstandDL = wasserstandDL - BECHER_VOLUMEN_DL
    println("...Tee ist fertig")
    println("Wasserstand: $wasserstandDL Deziliter")
}

fun rueckgabeMenuPunkt() {
    if (restGeldVorhanden()) {
        println("Retourgeld")
        testeAlleMuenzen()
    }
}

fun restGeldVorhanden() = kredit > 0

fun testeAlleMuenzen() {
    for (muenze in MUENZEN.asReversed()) {
        abbuchen(muenze)
    }
}

fun abbuchen(muenze: Double) {
    while (passt(muenze)) {
        kredit -= muenze
        korrektur()
        println("Muenze: " + muenze + "0 CHF")
    }
}

fun passt(muenze: Double) = kredit - muenze >= 0

fun korrektur() {
    kredit = "%.2f".format(kredit).toDouble()      // Kunstgriff um Abweichungen des Double zu eliminieren.
}
