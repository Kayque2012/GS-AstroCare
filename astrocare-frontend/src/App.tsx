import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Sidebar } from './components/Sidebar';
import { Dashboard } from './pages/Dashboard';
import { Pacientes } from './pages/Pacientes';
import { Medicos } from './pages/Medicos';
import { Consultas } from './pages/Consultas';

export default function App() {
  return (
    <BrowserRouter>
      <div className="layout">
        <Sidebar />
        <main className="main">
          <Routes>
            <Route path="/"          element={<Dashboard />} />
            <Route path="/pacientes" element={<Pacientes />} />
            <Route path="/medicos"   element={<Medicos />} />
            <Route path="/consultas" element={<Consultas />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}
