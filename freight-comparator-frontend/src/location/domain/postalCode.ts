export type PostalCode = string;

export const formatCep = (value: string): PostalCode => {
  const digits = value.replace(/\D/g, '').slice(0, 8);
  if (digits.length <= 5) {
    return digits;
  }
  return `${digits.slice(0, 5)}-${digits.slice(5)}`;
};

export const isValidCep = (value: string): boolean => /^\d{5}-?\d{3}$/.test(value);
