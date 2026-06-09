export const formatCurrency = (value: number, currency = 'BRL'): string =>
  new Intl.NumberFormat('pt-BR', { style: 'currency', currency }).format(value);

export const formatDeliveryEstimate = (days: number): string =>
  days === 1 ? '1 dia útil' : `${days} dias úteis`;
