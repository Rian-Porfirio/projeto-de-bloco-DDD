import { httpClient } from '@/shared/api/httpClient';
import type { CalculateFreightRequest, FreightCalculationResponse } from '@/freight/domain/freight';

export const freightApi = {
  async calculate(payload: CalculateFreightRequest): Promise<FreightCalculationResponse> {
    const { data } = await httpClient.post<FreightCalculationResponse>(
      '/v1/freights/calculate',
      payload
    );
    return data;
  },
};
