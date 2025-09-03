import { Link } from "react-router-dom";

export default function Footer() {
    return (
        <footer className="mb-2 px-4 py-8 text-[0.9rem] text-text-light opacity-85">
            <div className="container mx-auto text-center">
                <h4 className="mb-3 text-lg font-semibold">BewerbungBuddy</h4>
                <div className="flex justify-center gap-6 mb-3">
                    <Link
                        to="/"
                        className="text-text-light font-medium transition-colors duration-200 hover:text-accent-blue"
                    >
                        Startseite
                    </Link>
                    <Link
                        to="/pricing"
                        className="text-text-light font-medium transition-colors duration-200 hover:text-accent-blue"
                    >
                        Preise
                    </Link>
                    <a
                        href="#"
                        className="text-text-light font-medium transition-colors duration-200 hover:text-accent-blue"
                    >
                        Kontakt
                    </a>
                </div>
                <div className="text-sm text-text-light opacity-60">
                    &copy; 2025 BewerbungBuddy. Alle Rechte vorbehalten.
                </div>
            </div>
        </footer>
    );
}
