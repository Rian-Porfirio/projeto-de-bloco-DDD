import type { Modality } from '@/freight/domain/freight';

export interface Carrier {
  code: string;
  name: string;
  logoUrl: string;
}

export const MODALITY_LABELS: Record<Modality, string> = {
  EXPRESS: 'Expresso',
  ECONOMY: 'Econômico',
  ROAD: 'Rodoviário',
  AIR: 'Aéreo',
};

export const modalityLabel = (modality: Modality): string => MODALITY_LABELS[modality] ?? modality;
