import { useEffect, useState } from 'react';
import { getMedicos, createMedico, updateMedico, deleteMedico } from '../services/api';
import type { Medico } from '../types';

const EMPTY: Medico = { id: 0, nome: '', crm: '', especialidade: '', disponivel: true };

export function Medicos() {
  const [lista, setLista] = useState<Medico[]>([]);
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState<'create' | 'edit' | null>(null);
  const [form, setForm] = useState<Medico>(EMPTY);
  const [erro, setErro] = useState('');
  const [saving, setSaving] = useState(false);

  const load = () => {
    setLoading(true);
    getMedicos().then(setLista).finally(() => setLoading(false));
  };

  useEffect(() => { load(); }, []);

  const openCreate = () => { setForm(EMPTY); setErro(''); setModal('create'); };
  const openEdit   = (m: Medico) => { setForm(m); setErro(''); setModal('edit'); };
  const close      = () => setModal(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    const checked = (e.target as HTMLInputElement).checked;
    setForm(f => ({
      ...f,
      [name]: type === 'checkbox' ? checked : (name === 'id' ? Number(value) : value),
    }));
  };

  const handleSave = async () => {
    setErro('');
    setSaving(true);
    try {
      if (modal === 'create') await createMedico(form);
      else await updateMedico(form.id, form);
      load();
      close();
    } catch (e: any) {
      setErro(e?.erro || 'Erro ao salvar. Verifique os dados.');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Remover este médico?')) return;
    await deleteMedico(id);
    load();
  };

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <h2>Médicos</h2>
          <p>Gerencie os profissionais de saúde cadastrados</p>
        </div>
        <button className="btn btn-primary" onClick={openCreate}>+ Novo médico</button>
      </div>

      <div className="card">
        {loading ? (
          <div className="loading">Carregando...</div>
        ) : lista.length === 0 ? (
          <div className="empty">
            <div className="empty-icon">👨‍⚕️</div>
            <p>Nenhum médico cadastrado. Clique em "Novo médico" para começar.</p>
          </div>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nome</th>
                  <th>CRM</th>
                  <th>Especialidade</th>
                  <th>Disponível</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {lista.map(m => (
                  <tr key={m.id}>
                    <td>#{m.id}</td>
                    <td><strong>{m.nome}</strong></td>
                    <td style={{ color: 'var(--text-2)' }}>{m.crm}</td>
                    <td>{m.especialidade}</td>
                    <td>
                      {m.disponivel
                        ? <span className="badge badge-success">Sim</span>
                        : <span className="badge badge-danger">Não</span>}
                    </td>
                    <td>
                      <div style={{ display: 'flex', gap: 6 }}>
                        <button className="btn btn-ghost btn-sm" onClick={() => openEdit(m)}>Editar</button>
                        <button className="btn btn-danger btn-sm" onClick={() => handleDelete(m.id)}>Remover</button>
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
            <h3>{modal === 'create' ? 'Novo Médico' : 'Editar Médico'}</h3>
            {erro && <div className="alert alert-error">{erro}</div>}
            <div className="form-row">
              <div className="form-group">
                <label>ID</label>
                <input name="id" type="number" value={form.id} onChange={handleChange} disabled={modal === 'edit'} />
              </div>
              <div className="form-group">
                <label>CRM</label>
                <input name="crm" value={form.crm} onChange={handleChange} placeholder="CRM-SP-123456" />
              </div>
            </div>
            <div className="form-group">
              <label>Nome completo</label>
              <input name="nome" value={form.nome} onChange={handleChange} placeholder="Ex: Dr. Paulo Saúde" />
            </div>
            <div className="form-group">
              <label>Especialidade</label>
              <input name="especialidade" value={form.especialidade} onChange={handleChange} placeholder="Ex: Clínica Geral" />
            </div>
            <div className="form-group" style={{ display: 'flex', alignItems: 'center', gap: 8, marginTop: 4 }}>
              <input type="checkbox" name="disponivel" id="disponivel"
                checked={form.disponivel} onChange={handleChange}
                style={{ width: 16, height: 16, cursor: 'pointer' }} />
              <label htmlFor="disponivel" style={{ margin: 0, cursor: 'pointer' }}>Disponível para consultas</label>
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
