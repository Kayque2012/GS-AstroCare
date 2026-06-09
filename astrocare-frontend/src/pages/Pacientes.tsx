import { useEffect, useState } from 'react';
import { getPacientes, createPaciente, updatePaciente, deletePaciente } from '../services/api';
import type { Paciente } from '../types';

const EMPTY: Paciente = { id: 0, nome: '', cpf: '', localizacao: '', idade: 0, condicaoMedica: '' };

export function Pacientes() {
  const [lista, setLista] = useState<Paciente[]>([]);
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState<'create' | 'edit' | null>(null);
  const [form, setForm] = useState<Paciente>(EMPTY);
  const [erro, setErro] = useState('');
  const [saving, setSaving] = useState(false);

  const load = () => {
    setLoading(true);
    getPacientes().then(setLista).finally(() => setLoading(false));
  };

  useEffect(() => { load(); }, []);

  const openCreate = () => { setForm(EMPTY); setErro(''); setModal('create'); };
  const openEdit   = (p: Paciente) => { setForm(p); setErro(''); setModal('edit'); };
  const close      = () => setModal(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm(f => ({ ...f, [name]: name === 'idade' || name === 'id' ? Number(value) : value }));
  };

  const handleSave = async () => {
    setErro('');
    setSaving(true);
    try {
      if (modal === 'create') await createPaciente(form);
      else await updatePaciente(form.id, form);
      load();
      close();
    } catch (e: any) {
      setErro(e?.erro || 'Erro ao salvar. Verifique os dados.');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Remover este paciente?')) return;
    await deletePaciente(id);
    load();
  };

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <h2>Pacientes</h2>
          <p>Gerencie os pacientes cadastrados no sistema</p>
        </div>
        <button className="btn btn-primary" onClick={openCreate}>+ Novo paciente</button>
      </div>

      <div className="card">
        {loading ? (
          <div className="loading">Carregando...</div>
        ) : lista.length === 0 ? (
          <div className="empty">
            <div className="empty-icon">🧑‍⚕️</div>
            <p>Nenhum paciente cadastrado. Clique em "Novo paciente" para começar.</p>
          </div>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nome</th>
                  <th>CPF</th>
                  <th>Localização</th>
                  <th>Idade</th>
                  <th>Condição Médica</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {lista.map(p => (
                  <tr key={p.id}>
                    <td>#{p.id}</td>
                    <td><strong>{p.nome}</strong></td>
                    <td style={{ color: 'var(--text-2)' }}>{p.cpf}</td>
                    <td>{p.localizacao}</td>
                    <td>{p.idade} anos</td>
                    <td>{p.condicaoMedica || '—'}</td>
                    <td>
                      <div style={{ display: 'flex', gap: 6 }}>
                        <button className="btn btn-ghost btn-sm" onClick={() => openEdit(p)}>Editar</button>
                        <button className="btn btn-danger btn-sm" onClick={() => handleDelete(p.id)}>Remover</button>
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
            <h3>{modal === 'create' ? 'Novo Paciente' : 'Editar Paciente'}</h3>
            {erro && <div className="alert alert-error">{erro}</div>}
            <div className="form-row">
              <div className="form-group">
                <label>ID</label>
                <input name="id" type="number" value={form.id} onChange={handleChange} disabled={modal === 'edit'} />
              </div>
              <div className="form-group">
                <label>Idade</label>
                <input name="idade" type="number" value={form.idade} onChange={handleChange} />
              </div>
            </div>
            <div className="form-group">
              <label>Nome completo</label>
              <input name="nome" value={form.nome} onChange={handleChange} placeholder="Ex: Maria Silva" />
            </div>
            <div className="form-group">
              <label>CPF (somente números)</label>
              <input name="cpf" value={form.cpf} onChange={handleChange} placeholder="12345678901" maxLength={11} />
            </div>
            <div className="form-group">
              <label>Localização</label>
              <input name="localizacao" value={form.localizacao} onChange={handleChange} placeholder="Ex: Zona Rural - Limoeiro do Norte - CE" />
            </div>
            <div className="form-group">
              <label>Condição Médica</label>
              <input name="condicaoMedica" value={form.condicaoMedica} onChange={handleChange} placeholder="Ex: Hipertensão, Diabetes..." />
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
