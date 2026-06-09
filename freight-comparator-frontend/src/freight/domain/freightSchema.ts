import { z } from 'zod';
import type { CalculateFreightRequest } from '@/freight/domain/freight';

const toNumber = (value: string): number => Number(value.trim().replace(',', '.'));

const zipCode = (field: string) =>
  z
    .string()
    .trim()
    .min(1, `${field} é obrigatório.`)
    .regex(/^\d{5}-?\d{3}$/, `${field} inválido. Use o formato 00000-000.`);

const measure = (field: string) =>
  z
    .string()
    .trim()
    .min(1, `${field} é obrigatório.`)
    .refine((value) => !Number.isNaN(toNumber(value)), `${field} deve ser um número.`)
    .refine((value) => toNumber(value) > 0, `${field} deve ser maior que zero.`);

const declaredValue = z
  .string()
  .trim()
  .optional()
  .refine(
    (value) => value === undefined || value === '' || !Number.isNaN(toNumber(value)),
    'Valor declarado deve ser um número.'
  )
  .refine(
    (value) => value === undefined || value === '' || toNumber(value) >= 0,
    'Valor declarado não pode ser negativo.'
  );

export const freightSchema = z.object({
  originZipCode: zipCode('CEP de origem'),
  destinationZipCode: zipCode('CEP de destino'),
  weight: measure('Peso'),
  height: measure('Altura'),
  width: measure('Largura'),
  length: measure('Comprimento'),
  declaredValue,
});

export type FreightFormValues = z.infer<typeof freightSchema>;

export const toRequestPayload = (values: FreightFormValues): CalculateFreightRequest => ({
  originZipCode: values.originZipCode,
  destinationZipCode: values.destinationZipCode,
  weight: toNumber(values.weight),
  height: toNumber(values.height),
  width: toNumber(values.width),
  length: toNumber(values.length),
  declaredValue:
    values.declaredValue && values.declaredValue.trim() !== ''
      ? toNumber(values.declaredValue)
      : undefined,
});
