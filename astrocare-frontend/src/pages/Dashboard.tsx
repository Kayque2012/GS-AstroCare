import { useEffect, useState } from 'react';
import { getPacientes, getMedicos, getConsultas } from '../services/api';
import type { Consulta } from '../types';

export function Dashboard() {
  const [counts, setCounts] = useState({ pacientes: 0, medicos: 0, consultas: 0 });
  const [consultas, setConsultas] = useState<Consulta[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([getPacientes(), getMedicos(), getConsultas()])
      .then(([p, m, c]) => {
        setCounts({ pacientes: p.length, medicos: m.length, consultas: c.length });
        setConsultas(c.slice(0, 5));
      })
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  const statusBadge = (s: string) => {
    if (s === 'AGENDADA')  return <span className="badge badge-blue">Agendada</span>;
    if (s === 'REALIZADA') return <span className="badge badge-success">Realizada</span>;
    return <span className="badge badge-danger">Cancelada</span>;
  };

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <h2>Dashboard</h2>
          <p>Visão geral do sistema de telemedicina</p>
        </div>
      </div>

      {loading ? (
        <div className="loading">Carregando...</div>
      ) : (
        <>
          <div className="stats-row">
            <div className="stat-card">
              <div className="stat-value">{counts.pacientes}</div>
              <div className="stat-label">Pacientes cadastrados</div>
            </div>
            <div className="stat-card">
              <div className="stat-value">{counts.medicos}</div>
              <div className="stat-label">Médicos cadastrados</div>
            </div>
            <div className="stat-card">
              <div className="stat-value">{counts.consultas}</div>
              <div className="stat-label">Consultas registradas</div>
            </div>
          </div>

          <div className="card">
            <div style={{ padding: '16px 20px', borderBottom: '1px solid var(--border)', fontWeight: 600, fontSize: 14 }}>
              Consultas recentes
            </div>
            {consultas.length === 0 ? (
              <div className="empty">
                <div className="empty-icon">📋</div>
                <p>Nenhuma consulta registrada ainda.</p>
              </div>
            ) : (
              <div className="table-wrap">
                <table>
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Paciente (ID)</th>
                      <th>Médico (ID)</th>
                      <th>Data / Hora</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {consultas.map(c => (
                      <tr key={c.id}>
                        <td>#{c.id}</td>
                        <td>Paciente {c.idPaciente}</td>
                        <td>Médico {c.idMedico}</td>
                        <td>{c.dataHora}</td>
                        <td>{statusBadge(c.status)}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
}
