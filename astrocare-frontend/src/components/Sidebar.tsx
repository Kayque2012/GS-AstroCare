import { NavLink } from 'react-router-dom';

export function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-brand">
        <h1>🛰 AstroCare</h1>
        <span>Remote Health System</span>
      </div>

      <nav className="sidebar-nav">
        <NavLink to="/" end className={({ isActive }) => isActive ? 'active' : ''}>
          <span className="nav-icon">📊</span> Dashboard
        </NavLink>
        <NavLink to="/pacientes" className={({ isActive }) => isActive ? 'active' : ''}>
          <span className="nav-icon">🧑‍⚕️</span> Pacientes
        </NavLink>
        <NavLink to="/medicos" className={({ isActive }) => isActive ? 'active' : ''}>
          <span className="nav-icon">👨‍⚕️</span> Médicos
        </NavLink>
        <NavLink to="/consultas" className={({ isActive }) => isActive ? 'active' : ''}>
          <span className="nav-icon">📋</span> Consultas
        </NavLink>
      </nav>

      <div className="sidebar-footer">
        FIAP – ADS 1TDSPB<br />RM567980 · RM567398 · RM567903
      </div>
    </aside>
  );
}
