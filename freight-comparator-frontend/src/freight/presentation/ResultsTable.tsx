import { Card } from '@/shared/ui/card';
import { Badge } from '@/shared/ui/badge';
import { CarrierLogo } from './CarrierLogo';
import { formatCurrency, formatDeliveryEstimate } from '@/shared/lib/format';
import { modalityLabel } from '@/carrier/domain/carrier';
import { cn } from '@/shared/lib/utils';
import type { FreightOption } from '@/freight/domain/freight';

interface ResultsTableProps {
  options: FreightOption[];
}

const OptionTags = ({ option }: { option: FreightOption }) => {
  if (!option.bestPrice && !option.fastestDelivery && !option.bestCostBenefit) {
    return null;
  }
  return (
    <div className="mt-1 flex flex-wrap gap-1.5">
      {option.bestPrice && <Badge variant="price">Mais barato</Badge>}
      {option.fastestDelivery && <Badge variant="speed">Mais rápido</Badge>}
      {option.bestCostBenefit && <Badge variant="value">Custo-benefício</Badge>}
    </div>
  );
};

export const ResultsTable = ({ options }: ResultsTableProps) => (
  <Card className="overflow-hidden">
    <div className="hidden md:block">
      <table className="w-full border-collapse text-sm">
        <thead>
          <tr className="border-b bg-muted/40 text-left text-xs uppercase tracking-wide text-muted-foreground">
            <th className="px-6 py-4 font-semibold">Transportadora</th>
            <th className="px-6 py-4 font-semibold">Serviço</th>
            <th className="px-6 py-4 font-semibold">Modalidade</th>
            <th className="px-6 py-4 text-right font-semibold">Valor</th>
            <th className="px-6 py-4 text-right font-semibold">Prazo</th>
          </tr>
        </thead>
        <tbody>
          {options.map((option) => (
            <tr
              key={`${option.carrierCode}-${option.serviceName}`}
              className={cn(
                'border-b transition-colors last:border-0 hover:bg-muted/30',
                option.bestCostBenefit && 'bg-accent/5'
              )}
            >
              <td className="px-6 py-5">
                <div className="flex items-center gap-3">
                  <CarrierLogo name={option.carrierName} logoUrl={option.logoUrl} />
                  <div>
                    <p className="font-semibold">{option.carrierName}</p>
                    <OptionTags option={option} />
                  </div>
                </div>
              </td>
              <td className="px-6 py-5 text-foreground/80">{option.serviceName}</td>
              <td className="px-6 py-5">
                <Badge variant="modality">{modalityLabel(option.modality)}</Badge>
              </td>
              <td className="px-6 py-5 text-right font-display text-base font-bold text-postal-ink">
                {formatCurrency(option.price, option.currency)}
              </td>
              <td className="px-6 py-5 text-right font-medium">
                {formatDeliveryEstimate(option.deliveryDays)}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>

    <div className="divide-y md:hidden">
      {options.map((option) => (
        <div key={`${option.carrierCode}-${option.serviceName}`} className="space-y-3 p-4">
          <div className="flex items-center justify-between gap-3">
            <div className="flex items-center gap-3">
              <CarrierLogo name={option.carrierName} logoUrl={option.logoUrl} />
              <div>
                <p className="font-semibold leading-tight">{option.carrierName}</p>
                <p className="text-sm text-muted-foreground">{option.serviceName}</p>
              </div>
            </div>
            <Badge variant="modality">{modalityLabel(option.modality)}</Badge>
          </div>
          <div className="flex items-end justify-between">
            <p className="font-display text-xl font-bold text-postal-ink">
              {formatCurrency(option.price, option.currency)}
            </p>
            <p className="text-sm font-medium">{formatDeliveryEstimate(option.deliveryDays)}</p>
          </div>
          <OptionTags option={option} />
        </div>
      ))}
    </div>
  </Card>
);
