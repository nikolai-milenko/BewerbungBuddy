import Navbar from "../components/Navbar";
import { Outlet } from "react-router-dom";

export default function MainLayout() {
  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />
      <main className="flex-1 container mx-auto p-4">
        <Outlet />
      </main>
      <footer className="bg-gray-100 text-center p-4 text-sm text-gray-600">
        © 2025 BewerbungBuddy
      </footer>
    </div>
  );
}
