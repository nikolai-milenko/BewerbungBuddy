# BewerbungsBuddy

**BewerbungsBuddy** ist ein intelligenter Webservice, der Bewerber:innen dabei unterstützt, ihre Lebensläufe (CVs) und Bewerbungsschreiben gezielt auf Stellenanzeigen abzustimmen. Mithilfe moderner KI-Technologie analysiert der Dienst hochgeladene Dokumente und generiert individuelle Empfehlungen zur Verbesserung sowie automatisch erstellte Anschreiben.


## 🎯 Projektziel

Das Ziel von BewerbungsBuddy ist es, den Bewerbungsprozess zu vereinfachen, zu beschleunigen und qualitativ zu verbessern – insbesondere für Studierende, Berufseinsteiger:innen und Menschen, die sich unsicher beim Formulieren von Bewerbungsunterlagen fühlen.

Der Service hilft dabei:

- die Übereinstimmung zwischen CV und Stellenanzeige zu analysieren,
- Verbesserungspotenziale im Lebenslauf zu identifizieren,
- ein individuelles Anschreiben zu generieren,
- KI-basierte Empfehlungen zu erhalten.


## 🔍 Hauptfunktionen

- 📄 **Lebenslauf-Upload (PDF)**  
  Nutzer:innen können ihren Lebenslauf als PDF hochladen – dieser wird automatisch in Text umgewandelt und gespeichert.

- 💼 **Analyse von Stellenanzeigen**  
  Der Text einer Stellenanzeige kann eingefügt werden, um eine gezielte Analyse zu ermöglichen.

- 🤖 **GPT-Analyse**  
  Der Service verwendet ein GPT-Modell (z. B. gpt-4.1-mini), um die Passung zwischen CV und Anzeige zu bewerten.

- 📊 **Match Score**  
  Eine Prozentzahl zeigt, wie gut der Lebenslauf zur Stelle passt.

- 🧠 **Stärken & Schwächen**  
  Die KI erkennt und listet zentrale Stärken und Verbesserungspotenziale.

- ✍️ **Empfehlungen zur Optimierung**  
  Es werden konkrete Vorschläge gegeben (z. B. Schlüsselbegriffe ergänzen, Formulierungen anpassen, Layout verbessern).

- 📝 **Automatische Generierung von Anschreiben**  
  Basierend auf Analyseergebnissen wird ein personalisiertes Bewerbungsschreiben erzeugt.


## 🌐 Testumgebung

Der Service kann über den folgenden Link getestet werden:

**🔗 [https://bewerbungbuddy.de](https://bewerbungbuddy.de)**



## 🛠️ Technologie-Stack

- **Backend:** Java, Spring Boot, JPA, Maven  
- **Frontend:** HTML, CSS, Javascript
- **Datenbank:** PostgreSQL  
- **CI/CD:** GitLab CI  
- **KI-Integration:** OpenAI GPT API  


## 📁 Projektstruktur

```bash
backend/
├── controller/
├── dto/
├── entity/
├── mapper/
├── repository/
├── service/
frontend/
├── css/
├── js/
├── img/


