import { describe, expect, it } from 'vitest';
import { freightSchema, toRequestPayload } from '@/freight/domain/freightSchema';

const validInput = {
  originZipCode: '01001-000',
  destinationZipCode: '30130-010',
  weight: '2,5',
  height: '20',
  width: '15',
  length: '30',
  declaredValue: '',
};

describe('freightSchema', () => {
  it('accepts a valid payload', () => {
    const result = freightSchema.safeParse(validInput);
    expect(result.success).toBe(true);
  });

  it('rejects an invalid origin zip code', () => {
    const result = freightSchema.safeParse({ ...validInput, originZipCode: '123' });
    expect(result.success).toBe(false);
  });

  it('rejects a non-positive weight', () => {
    const result = freightSchema.safeParse({ ...validInput, weight: '0' });
    expect(result.success).toBe(false);
  });

  it('requires the length field', () => {
    const result = freightSchema.safeParse({ ...validInput, length: '' });
    expect(result.success).toBe(false);
  });
});

describe('toRequestPayload', () => {
  it('converts string fields into numbers and omits an empty declared value', () => {
    const payload = toRequestPayload(validInput);
    expect(payload.weight).toBe(2.5);
    expect(payload.height).toBe(20);
    expect(payload.declaredValue).toBeUndefined();
  });

  it('parses the declared value when provided', () => {
    const payload = toRequestPayload({ ...validInput, declaredValue: '150,90' });
    expect(payload.declaredValue).toBeCloseTo(150.9);
  });
});
