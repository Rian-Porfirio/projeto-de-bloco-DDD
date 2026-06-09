import axios from 'axios';

interface ProblemDetail {
  detail?: string;
  title?: string;
}

export const resolveErrorMessage = (error: unknown): string => {
  if (axios.isAxiosError(error)) {
    const data = error.response?.data as ProblemDetail | undefined;
    if (data?.detail) {
      return data.detail;
    }
    if (data?.title) {
      return data.title;
    }
    if (error.response?.status === 404) {
      return 'Não encontramos endereço para o CEP informado.';
    }
    return 'Não foi possível calcular o frete. Tente novamente.';
  }
  return 'Ocorreu um erro inesperado. Tente novamente.';
};
