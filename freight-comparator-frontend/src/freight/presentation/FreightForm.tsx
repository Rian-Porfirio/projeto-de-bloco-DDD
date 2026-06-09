import { type ChangeEvent, type ReactNode } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Loader2, PackageSearch } from 'lucide-react';
import { Button } from '@/shared/ui/button';
import { Input } from '@/shared/ui/input';
import { Label } from '@/shared/ui/label';
import { freightSchema, type FreightFormValues } from '@/freight/domain/freightSchema';
import { formatCep } from '@/location/domain/postalCode';

interface FreightFormProps {
  onSubmit: (values: FreightFormValues) => void;
  isPending: boolean;
}

const defaultValues: FreightFormValues = {
  originZipCode: '',
  destinationZipCode: '',
  weight: '',
  height: '',
  width: '',
  length: '',
  declaredValue: '',
};

interface FieldProps {
  id: string;
  label: string;
  error?: string;
  children: ReactNode;
}

const Field = ({ id, label, error, children }: FieldProps) => (
  <div className="space-y-1.5">
    <Label htmlFor={id}>{label}</Label>
    {children}
    {error && <p className="text-xs font-medium text-destructive">{error}</p>}
  </div>
);

export const FreightForm = ({ onSubmit, isPending }: FreightFormProps) => {
  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<FreightFormValues>({
    resolver: zodResolver(freightSchema),
    defaultValues,
    mode: 'onBlur',
  });

  const registerCep = (id: 'originZipCode' | 'destinationZipCode') => {
    const field = register(id);
    return {
      ...field,
      onChange: (event: ChangeEvent<HTMLInputElement>) => {
        setValue(id, formatCep(event.target.value), { shouldValidate: false });
      },
    };
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-6" noValidate>
      <div className="grid gap-4 sm:grid-cols-2">
        <Field id="originZipCode" label="CEP de origem" error={errors.originZipCode?.message}>
          <Input
            id="originZipCode"
            inputMode="numeric"
            autoComplete="postal-code"
            placeholder="01001-000"
            {...registerCep('originZipCode')}
          />
        </Field>
        <Field
          id="destinationZipCode"
          label="CEP de destino"
          error={errors.destinationZipCode?.message}
        >
          <Input
            id="destinationZipCode"
            inputMode="numeric"
            autoComplete="postal-code"
            placeholder="30130-010"
            {...registerCep('destinationZipCode')}
          />
        </Field>
      </div>

      <div className="space-y-2">
        <Label>Dimensões e peso</Label>
        <div className="grid grid-cols-2 gap-3 sm:grid-cols-4 lg:grid-cols-2">
          <Field id="weight" label="Peso (kg)" error={errors.weight?.message}>
            <Input id="weight" inputMode="decimal" placeholder="2,5" {...register('weight')} />
          </Field>
          <Field id="height" label="Altura (cm)" error={errors.height?.message}>
            <Input id="height" inputMode="decimal" placeholder="20" {...register('height')} />
          </Field>
          <Field id="width" label="Largura (cm)" error={errors.width?.message}>
            <Input id="width" inputMode="decimal" placeholder="15" {...register('width')} />
          </Field>
          <Field id="length" label="Comprimento (cm)" error={errors.length?.message}>
            <Input id="length" inputMode="decimal" placeholder="30" {...register('length')} />
          </Field>
        </div>
      </div>

      <Field
        id="declaredValue"
        label="Valor declarado (opcional)"
        error={errors.declaredValue?.message}
      >
        <Input
          id="declaredValue"
          inputMode="decimal"
          placeholder="0,00"
          {...register('declaredValue')}
        />
      </Field>

      <Button type="submit" size="lg" className="w-full" disabled={isPending}>
        {isPending ? (
          <>
            <Loader2 className="h-5 w-5 animate-spin" />
            Calculando...
          </>
        ) : (
          <>
            <PackageSearch className="h-5 w-5" />
            Calcular Frete
          </>
        )}
      </Button>
    </form>
  );
};
