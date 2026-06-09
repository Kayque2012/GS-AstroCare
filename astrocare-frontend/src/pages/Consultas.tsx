import { useEffect, useState } from 'react';
import { getConsultas, createConsulta, updateConsulta, deleteConsulta } from '../services/api';
import type { Consulta } from '../types';

const EMPTY: Consulta = {
  id: 0, idPaciente: 0, idMedico: 0,
  dataHora: '', status: 'AGENDADA', descricao: '',
};

const statusBadge = (s: string) => {
  if (s === 'AGENDADA')  return <span className="badge badge-blue">Agendada</span>;
  if (s === 'REALIZADA') return <span className="badge badge-success">Realizada</span>;
  return <span className="badge badge-danger">Cancelada</span>;
};

export function Consultas() {
  const [lista, setLista] = useState<Consulta[]>([]);
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState<'create' | 'edit' | null>(null);
  const [form, setForm] = useState<Consulta>(EMPTY);
  const [erro, setErro] = useState('');
  const [saving, setSaving] = useState(false);

  const load = () => {
    setLoading(true);
    getConsultas().then(setLista).finally(() => setLoading(false));
  };

  useEffect(() => { load(); }, []);

  const openCreate = () => { setForm(EMPTY); setErro(''); setModal('create'); };
  const openEdit   = (c: Consulta) => { setForm(c); setErro(''); setModal('edit'); };
  const close      = () => setModal(null);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = e.target;
    setForm(f => ({
      ...f,
      [name]: (name === 'id' || name === 'idPaciente' || name === 'idMedico')
        ? Number(value) : value,
    }));
  };

  const handleSave = async () => {
    setErro('');
    setSaving(true);
    try {
      if (modal === 'create') await createConsulta(form);
      else await updateConsulta(form.id, form);
      load();
      close();
    } catch (e: any) {
      setErro(e?.erro || 'Erro ao salvar. Verifique os dados.');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Remover esta consulta?')) return;
    await deleteConsulta(id);
    load();
  };

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <h2>Consultas</h2>
          <p>Agendamento e gestão de teleconsultas remotas</p>
        </div>
        <button className="btn btn-primary" onClick={openCreate}>+ Nova consulta</button>
      </div>

      <div className="card">
        {loading ? (
          <div className="loading">Carregando...</div>
        ) : lista.length === 0 ? (
          <div className="empty">
            <div className="empty-icon">📋</div>
            <p>Nenhuma consulta registrada. Clique em "Nova consulta" para agendar.</p>
          </div>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Paciente</th>
                  <th>Médico</th>
                  <th>Data / Hora</th>
                  <th>Status</th>
                  <th>Descrição</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {lista.map(c => (
                  <tr key={c.id}>
                    <td>#{c.id}</td>
                    <td>Pac. {c.idPaciente}</td>
                    <td>Med. {c.idMedico}</td>
                    <td>{c.dataHora}</td>
                    <td>{statusBadge(c.status)}</td>
                    <td style={{ maxWidth: 200, color: 'var(--text-2)', fontSize: 12 }}>
                      {c.descricao || '—'}
                    </td>
                    <td>
                      <div style={{ display: 'flex', gap: 6 }}>
                        <button className="btn btn-ghost btn-sm" onClick={() => openEdit(c)}>Editar</button>
                        <button className="btn btn-danger btn-sm" onClick={() => handleDelete(c.id)}>Remover</button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {modal && (
        <div className="modal-overlay" onClick={close}>
          <div className="modal" onClick={e => e.stopPropagation()}>
            <h3>{modal === 'create' ? 'Nova Consulta' : 'Editar Consulta'}</h3>
            {erro && <div className="alert alert-error">{erro}</div>}
            <div className="form-row">
              <div className="form-group">
                <label>ID da Consulta</label>
                <input name="id" type="number" value={form.id} onChange={handleChange} disabled={modal === 'edit'} />
              </div>
              <div className="form-group">
                <label>Status</label>
                <select name="status" value={form.status} onChange={handleChange}>
                  <option value="AGENDADA">Agendada</option>
                  <option value="REALIZADA">Realizada</option>
                  <option value="CANCELADA">Cancelada</option>
                </select>
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>ID do Paciente</label>
                <input name="idPaciente" type="number" value={form.idPaciente} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>ID do Médico</label>
                <input name="idMedico" type="number" value={form.idMedico} onChange={handleChange} />
              </div>
            </div>
            <div className="form-group">
              <label>Data e Hora (YYYY-MM-DD HH:MM)</label>
              <input name="dataHora" value={form.dataHora} onChange={handleChange} placeholder="2025-07-10 09:00" />
            </div>
            <div className="form-group">
              <label>Descrição</label>
              <textarea name="descricao" value={form.descricao} onChange={handleChange}
                placeholder="Descreva o motivo ou observações da consulta..." />
            </div>
            <div className="modal-actions">
              <button className="btn btn-ghost" onClick={close}>Cancelar</button>
              <button className="btn btn-primary" onClick={handleSave} disabled={saving}>
                {saving ? 'Salvando...' : 'Salvar'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
