import type { Paciente, Medico, Consulta } from '../types';

const BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080';

// ─── Pacientes ───────────────────────────────────────────────

export const getPacientes = (): Promise<Paciente[]> =>
  fetch(`${BASE_URL}/pacientes`).then(r => r.json());

export const getPaciente = (id: number): Promise<Paciente> =>
  fetch(`${BASE_URL}/pacientes/${id}`).then(r => r.json());

export const createPaciente = (p: Omit<Paciente, 'id'> & { id: number }): Promise<void> =>
  fetch(`${BASE_URL}/pacientes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(p),
  }).then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

export const updatePaciente = (id: number, p: Paciente): Promise<void> =>
  fetch(`${BASE_URL}/pacientes/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(p),
  }).then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

export const deletePaciente = (id: number): Promise<void> =>
  fetch(`${BASE_URL}/pacientes/${id}`, { method: 'DELETE' })
    .then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

// ─── Médicos ─────────────────────────────────────────────────

export const getMedicos = (): Promise<Medico[]> =>
  fetch(`${BASE_URL}/medicos`).then(r => r.json());

export const getMedico = (id: number): Promise<Medico> =>
  fetch(`${BASE_URL}/medicos/${id}`).then(r => r.json());

export const createMedico = (m: Medico): Promise<void> =>
  fetch(`${BASE_URL}/medicos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(m),
  }).then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

export const updateMedico = (id: number, m: Medico): Promise<void> =>
  fetch(`${BASE_URL}/medicos/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(m),
  }).then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

export const deleteMedico = (id: number): Promise<void> =>
  fetch(`${BASE_URL}/medicos/${id}`, { method: 'DELETE' })
    .then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

// ─── Consultas ───────────────────────────────────────────────

export const getConsultas = (): Promise<Consulta[]> =>
  fetch(`${BASE_URL}/consultas`).then(r => r.json());

export const getConsulta = (id: number): Promise<Consulta> =>
  fetch(`${BASE_URL}/consultas/${id}`).then(r => r.json());

export const getConsultasByPaciente = (idPaciente: number): Promise<Consulta[]> =>
  fetch(`${BASE_URL}/consultas/paciente/${idPaciente}`).then(r => r.json());

export const createConsulta = (c: Consulta): Promise<void> =>
  fetch(`${BASE_URL}/consultas`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(c),
  }).then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

export const updateConsulta = (id: number, c: Consulta): Promise<void> =>
  fetch(`${BASE_URL}/consultas/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(c),
  }).then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });

export const deleteConsulta = (id: number): Promise<void> =>
  fetch(`${BASE_URL}/consultas/${id}`, { method: 'DELETE' })
    .then(r => { if (!r.ok) return r.json().then(e => Promise.reject(e)); });
