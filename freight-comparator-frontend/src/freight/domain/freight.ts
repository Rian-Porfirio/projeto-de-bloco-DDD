export type Modality = 'EXPRESS' | 'ECONOMY' | 'ROAD' | 'AIR';

export interface FreightOption {
  carrierCode: string;
  carrierName: string;
  logoUrl: string;
  serviceName: string;
  modality: Modality;
  price: number;
  currency: string;
  deliveryDays: number;
  bestPrice: boolean;
  fastestDelivery: boolean;
  bestCostBenefit: boolean;
}

export interface FreightCalculationResponse {
  id: string;
  originZipCode: string;
  destinationZipCode: string;
  bestPrice: FreightOption | null;
  fastestDelivery: FreightOption | null;
  bestCostBenefit: FreightOption | null;
  options: FreightOption[];
  calculatedAt: string;
}

export interface CalculateFreightRequest {
  originZipCode: string;
  destinationZipCode: string;
  weight: number;
  height: number;
  width: number;
  length: number;
  declaredValue?: number;
}
