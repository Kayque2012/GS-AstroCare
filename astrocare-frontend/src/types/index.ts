export interface Paciente {
  id: number;
  nome: string;
  cpf: string;
  localizacao: string;
  idade: number;
  condicaoMedica: string;
}

export interface Medico {
  id: number;
  nome: string;
  crm: string;
  especialidade: string;
  disponivel: boolean;
}

export interface Consulta {
  id: number;
  idPaciente: number;
  idMedico: number;
  dataHora: string;
  status: 'AGENDADA' | 'REALIZADA' | 'CANCELADA';
  descricao: string;
}
