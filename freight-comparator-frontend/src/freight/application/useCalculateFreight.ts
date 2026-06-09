import { useMutation } from '@tanstack/react-query';
import { freightApi } from '@/freight/infrastructure/freightApi';
import { toRequestPayload, type FreightFormValues } from '@/freight/domain/freightSchema';
import type { FreightCalculationResponse } from '@/freight/domain/freight';

export const useCalculateFreight = () =>
  useMutation<FreightCalculationResponse, Error, FreightFormValues>({
    mutationKey: ['freight', 'calculate'],
    mutationFn: (values) => freightApi.calculate(toRequestPayload(values)),
  });
