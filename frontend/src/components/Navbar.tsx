import { Link } from "react-router-dom";
import { useState } from "react";

export default function Navbar() {
  const [isToolsOpen, setIsToolsOpen] = useState(false);
  const [isBurgerOpen, setIsBurgerOpen] = useState(false);

  return (
    <nav className="nav">
      <div className="container d-flex align-items-center justify-content-between flex-wrap gap-3">
        <Link to="/" className="logo d-flex align-items-center text-decoration-none">
          <h4 className="logo-text">
            <span className="logo-gray">bewerbung</span>
            <span className="logo-highlight">buddy</span>
          </h4>
        </Link>

        {/* Burger */}
        <button
          className="burger"
          aria-label="Menü öffnen"
          onClick={() => setIsBurgerOpen(!isBurgerOpen)}
        >
          <span></span>
          <span></span>
          <span></span>
        </button>

        {/* Links */}
        <div className={`links ${isBurgerOpen ? "show" : ""}`}>
          <div className="dropdown-custom">
            <button
              className={`link ${isToolsOpen ? "active" : ""}`}
              onClick={() => setIsToolsOpen(!isToolsOpen)}
            >
              Tools ✨
            </button>

            {isToolsOpen && (
              <div className="dropdown-menu-custom">
                <Link to="/cv-analysis" className="dropdown-item-custom">
                  CV analysieren
                </Link>
                <Link to="/letter-generation" className="dropdown-item-custom">
                  Motivationsschreiben generieren
                </Link>
              </div>
            )}
          </div>

          <Link to="/pricing" className="link">
            Preise
          </Link>
          <Link to="/our-team" className="link">
            Unser Team
          </Link>
        </div>
      </div>
    </nav>
  );
}
