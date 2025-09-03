import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import CVAnalysis from "./pages/CVAnalysis";
import Pricing from "./pages/Pricing";
import MainLayout from "./layout/MainLayout.tsx";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/cv-analysis" element={<CVAnalysis />} />
          <Route path="/pricing" element={<Pricing />} />
        </Route>
      </Routes>
    </Router>
  );
}

// <Route path="/our-team" element={<OurTeam />} />
// <Route path="/letter-generation" element={<LetterGeneration />} />