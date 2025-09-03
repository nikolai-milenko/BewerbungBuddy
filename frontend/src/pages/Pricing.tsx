export default function Pricing() {
  return (
    <>
    <header className="w-full min-h-[90vh] text-center py-24 px-4 bg-[url('/img/landing_bg.jpg')] bg-no-repeat bg-center bg-cover relative">
        <div className="container mx-auto px-4">
            <h1>Ein Plan. Ein Schritt näher zum Job.</h1>
        </div>
    </header>
    <section id="pricing" className="container text-center py-5">
        <h1 className="fw-bold mb-5" style={{ opacity: 0.75 }}>Alle Abos im Überblick</h1>
        <div className="pricing-row fade-in">
            <div className="pricing-card">
                <div className="title">Basic</div>
                <div className="price">0€ <span className="period">/ Monat</span></div>
                <ul>
                    <li>Einmalige Lebenslaufanalyse</li>
                    <li>
                        Standard-Anschreiben
                        <small>Basierend auf Stellenanzeige</small>
                    </li>
                    <li>
                        E-Mail-Support
                        <small>Antwort innerhalb 48h</small>
                    </li>
                </ul>
                <a href="signup.html" className="btn btn-outline">Kostenlos starten</a>
            </div>

            <div className="pricing-card highlight">
                <div className="title">Pro</div>
                <div className="price">9,99€ <span className="period">/ Monat</span></div>
                <ul>
                    <li>Unbegrenzte CV-Analysen</li>
                    <li>
                        Premium-Anschreiben
                        <small>KI-Optimierung</small>
                        <small>Individuelle Einleitung</small>
                    </li>
                    <li>
                        Job-Matching-Insights
                        <small>GPT-gestützte Bewertung</small>
                        <small>Visualisierung der Passgenauigkeit</small>
                    </li>
                    <li>
                        Priorisierter Support
                        <small>Antwort innerhalb 24h</small>
                    </li>
                </ul>
                <a href="checkout.html?plan=pro" className="btn btn-primary">Jetzt upgraden</a>
            </div>

            <div className="pricing-card">
                <div className="title">Premium</div>
                <div className="price">19,99€ <span className="period">/ Monat</span></div>
                <ul>
                    <li>Alles aus Pro</li>
                    <li>
                        1:1 Coaching
                        <small>Persönliche Beratung</small>
                        <small>Vorbereitungsgespräch</small>
                    </li>
                    <li>
                        Lebenslauf-Designvorlagen
                        <small>Über 50 professionelle Templates</small>
                    </li>
                    <li>
                        Telefon-Support
                        <small>Mo–Fr, 10–18 Uhr</small>
                    </li>
                </ul>
                <a href="checkout.html?plan=premium" className="btn btn-primary">Vollversion sichern</a>
            </div>
        </div>
        <h2 className="fw-semibold display-6 mt-5 mb-4 fade-in" style={{ opacity: 0.7 }}>
        Für alle, die es ganz genau wissen wollen.
        </h2>

        <div className="pricing-table-wrapper table-responsive">
            <table className="pricing-table">
                <thead>
                    <tr>
                        <th>Funktionen</th>
                        <th>Kostenlos</th>
                        <th>Pro</th>
                        <th>Premium</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Lebenslauf-Upload <small>Formate: PDF, DOCX</small></td>
                        <td className="text-success">✅</td>
                        <td className="text-success">✅</td>
                        <td className="text-success">✅</td>
                    </tr>
                    <tr>
                        <td>CV-Analysen</td>
                        <td>2 / Monat</td>
                        <td>Unbegrenzt</td>
                        <td>Unbegrenzt</td>
                    </tr>
                    <tr>
                        <td>Motivationsschreiben <small>Generierung</small></td>
                        <td>–</td>
                        <td className="text-success">✅</td>
                        <td className="text-success">✅</td>
                    </tr>
                    <tr>
                        <td>Vorlagenauswahl <small>Designs</small></td>
                        <td>–</td>
                        <td>10</td>
                        <td>50+</td>
                    </tr>
                    <tr>
                        <td>PDF-Export <small>Wasserzeichen</small></td>
                        <td>–</td>
                        <td className="text-success">✅</td>
                        <td className="text-success">✅</td>
                    </tr>
                    <tr>
                        <td>Team-Konten <small>Benutzer</small></td>
                        <td>–</td>
                        <td>–</td>
                        <td className="text-success">✅</td>
                    </tr>
                </tbody>
            </table>
        </div>    

        <div className="pricing-tiers-mobile d-md-none" id="mobilePlanSelector">
            <div className="tier active" data-plan="0">Basic</div>
            <div className="tier" data-plan="1">Pro</div>
            <div className="tier" data-plan="2">Premium</div>
        </div>

        <div className="pricing-cards-mobile d-md-none">
            <div className="pricing-feature">
                <h3>Lebenslauf-Upload</h3>
                <small>Formate: PDF, DOCX</small>
                <div className="pricing-options">
                    <div className="text-success option" data-index="0">✅</div>
                    <div className="text-success option" data-index="1">✅</div>
                    <div className="text-success option" data-index="2">✅</div>
                </div>
            </div>

            <div className="pricing-feature">
                <h3 className="mb-4">CV-Analysen</h3>
                <div className="pricing-options">
                    <div className="option" data-index="0">2 / Monat</div>
                    <div className="option" data-index="1">Unbegrenzt</div>
                    <div className="option" data-index="2">Unbegrenzt</div>
                </div>    
            </div>

            <div className="pricing-feature">
                <h3 className="mb-4">Motivationsschreiben generieren</h3>
                <div className="pricing-options">
                    <div className="option" data-index="0">–</div>
                    <div className="text-success option" data-index="1">✅</div>
                    <div className="text-success option" data-index="2">✅</div>
                </div>    
            </div>

            <div className="pricing-feature">
                <h3>Vorlagenauswahl</h3>
                <small>Designs</small>
                <div className="pricing-options">
                    <div className="option" data-index="0">–</div>
                    <div className="option" data-index="1">10</div>
                    <div className="option" data-index="2">50+</div>
                </div>
            </div>

            <div className="pricing-feature">
                <h3>PDF-Export</h3>
                <small>Wasserzeichen</small>
                <div className="pricing-options">
                    <div className="text-success option" data-index="0">✅</div>
                    <div className="option" data-index="1">-</div>
                    <div className="option" data-index="2">-</div>
                </div>
            </div>

            <div className="pricing-feature">
                <h3 className="mb-4">Team-Konten</h3>
                <div className="pricing-options">
                    <div className="option" data-index="0">–</div>
                    <div className="option" data-index="1">–</div>
                    <div className="text-success option" data-index="2">✅</div>
                </div>
            </div>
        </div>
    </section>
    </>
    );
}