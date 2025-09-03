import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav className="nav">
      <div className="container d-flex align-items-center justify-content-between flex-wrap gap-3">
        <Link to="/" className="logo d-flex align-items-center text-decoration-none">
          <h4 className="logo-text">
            <span className="logo-gray">bewerbung</span>
            <span className="logo-highlight">buddy</span>
          </h4>
        </Link>

        <button className="burger" aria-label="Menü öffnen">
          <span></span>
          <span></span>
          <span></span>
        </button>

        <div className="links">
          <div className="dropdown-custom">
            <a href="#" className="link active">
              <span>Tools ✨</span>
            </a>
            <div className="dropdown-menu-custom">
              <Link to="/cv-analysis" className="dropdown-item-custom">
                CV analysieren
              </Link>
              <Link to="/letter-generation" className="dropdown-item-custom">
                Motivationsschreiben generieren
              </Link>
            </div>
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
