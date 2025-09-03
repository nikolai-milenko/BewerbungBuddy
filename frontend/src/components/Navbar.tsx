import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav className="w-full bg-bg-dark flex items-center justify-between px-8 py-[1.2rem] flex-nowrap gap-4 shadow-[0_2px_6px_rgba(0,0,0,0.2)] sticky top-0 z-[1000]">
    <div className="container mx-auto px-4 flex items-center justify-between flex-wrap gap-3">
      <Link to="/" className="flex items-center no-underline">
        <h4 className="
          m-0 p-0 text-[1.8rem] font-bold flex items-center leading-[1.2]
          bg-gradient-to-r from-blue-500 to-blue-700 bg-[length:200%_100%]
          bg-left bg-clip-text text-transparent transition-all duration-300
          hover:bg-right
        ">
          <span className="
              font-bold bg-gradient-to-r from-[#868686] to-white bg-[length:200%_100%]
              bg-left bg-clip-text text-transparent
            "
          >
            bewerbung
          </span>
          <span
            className="
              font-bold bg-[linear-gradient(90deg,#0a84ff,#ff4f81,#0a84ff)]
              bg-[length:200%_100%] bg-left bg-clip-text text-transparent
              transition-all duration-[800ms] ease
              group-hover:bg-right
            "
          >
            buddy
          </span>
        </h4>
      </Link>

      <button
        id="burgerBtn"
        aria-label="Menü öffnen"
        className="
          hidden flex-col gap-[5px] bg-none border-none cursor-pointer p-2 z-[2000]
        "
      >
        <span className="w-6 h-[3px] bg-[var(--text-light)] rounded transition-all duration-300"></span>
        <span className="w-6 h-[3px] bg-[var(--text-light)] rounded transition-all duration-300"></span>
        <span className="w-6 h-[3px] bg-[var(--text-light)] rounded transition-all duration-300"></span>
      </button>

      <div
        id="navLinks"
        className="flex gap-4 items-center"
      >
        <div className="relative">
          <a
            href="#"
            id="toolsLink"
            className="
              text-[var(--text-light)] text-[17px] font-medium inline-block
              py-[0.4rem] px-[0.6rem] rounded-lg
              transition-colors duration-200
              [&.active]:bg-[var(--highlight-gray)]
            "
          >
            <span className="w-6 h-[3px] bg-[var(--text-light)] rounded transition-all duration-300 ease-linear">Tools ✨</span>
          </a>
          <div
            id="toolsMenu"
            className="
              absolute top-[calc(100%+8px)] left-0
              bg-[var(--highlight-gray)] border border-[var(--border-muted)]
              rounded-xl py-2
              shadow-[0_6px_18px_rgba(0,0,0,0.25)]
              min-w-[220px] z-[100]
              flex flex-col
              opacity-0 -translate-y-[10px] pointer-events-none
              transition-opacity transition-transform duration-300 ease
              [&.show]:opacity-100 [&.show]:translate-y-0 [&.show]:pointer-events-auto
            "
          >
            <a
              href="cv-analysis.html"
              className="
                py-[0.7rem] px-[1.2rem]
                text-[var(--text-light)] no-underline font-medium rounded-md
                transition-all duration-200
                hover:bg-[var(--accent-blue)] hover:text-white hover:pl-6
              "
            >
              CV analysieren
            </a>
            <a
              href="letter-generation.html"
              className="
                py-[0.7rem] px-[1.2rem]
                text-[var(--text-light)] no-underline font-medium rounded-md
                transition-all duration-200
                hover:bg-[var(--accent-blue)] hover:text-white hover:pl-6
              "
            >
              Motivationsschreiben generieren
            </a>
          </div>
        </div>
        <a
          href="pricing.html"
          className="
            text-[var(--text-light)] text-[17px] font-medium inline-block
            py-[0.4rem] px-[0.6rem] rounded-lg
            transition-colors duration-200
            [&.active]:bg-[var(--highlight-gray)]
          "
        >
          Preise
        </a>

        <a
          href="our-team.html"
          className="
            text-[var(--text-light)] text-[17px] font-medium inline-block
            py-[0.4rem] px-[0.6rem] rounded-lg
            transition-colors duration-200
            [&.active]:bg-[var(--highlight-gray)]
          "
        >
          Unser Team
        </a>
      </div>
    </div>
  </nav>
  );
}
