import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import CVAnalysis from "./pages/CVAnalysis";
import MainLayout from "./layout/MainLayout.tsx";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route element={<MainLayout />}>
          <Route path="/cv-analysis" element={<CVAnalysis />} />
        </Route>
      </Routes>
    </Router>
  );
}
