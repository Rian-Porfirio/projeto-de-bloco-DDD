import { Banknote, Gauge, Trophy, type LucideIcon } from 'lucide-react';
import { Card } from '@/shared/ui/card';
import { CarrierLogo } from './CarrierLogo';
import { formatCurrency, formatDeliveryEstimate } from '@/shared/lib/format';
import { modalityLabel } from '@/carrier/domain/carrier';
import { cn } from '@/shared/lib/utils';
import type { FreightOption } from '@/freight/domain/freight';

interface HighlightCardsProps {
  bestPrice: FreightOption | null;
  fastestDelivery: FreightOption | null;
  bestCostBenefit: FreightOption | null;
}

interface HighlightDef {
  key: string;
  label: string;
  hint: string;
  icon: LucideIcon;
  ring: string;
  iconWrap: string;
  option: FreightOption | null;
}

const HighlightCard = ({ def }: { def: HighlightDef }) => {
  const Icon = def.icon;
  return (
    <Card className={cn('p-5 lg:p-6', def.ring)}>
      <div className="flex items-start justify-between gap-3">
        <span className="text-xs font-semibold uppercase tracking-wide text-muted-foreground">
          {def.label}
        </span>
        <span className={cn('flex h-9 w-9 shrink-0 items-center justify-center rounded-full', def.iconWrap)}>
          <Icon className="h-5 w-5" strokeWidth={2.2} />
        </span>
      </div>

      {def.option ? (
        <div className="mt-5 space-y-4">
          <div className="flex items-center gap-3">
            <CarrierLogo name={def.option.carrierName} logoUrl={def.option.logoUrl} />
            <div className="min-w-0">
              <p className="truncate font-semibold leading-tight">{def.option.carrierName}</p>
              <p className="truncate text-sm text-muted-foreground">{def.option.serviceName}</p>
            </div>
          </div>
          <div className="flex items-end justify-between gap-3">
            <p className="whitespace-nowrap font-display text-2xl font-bold text-postal-ink">
              {formatCurrency(def.option.price, def.option.currency)}
            </p>
            <div className="text-right">
              <p className="whitespace-nowrap text-sm font-medium">{formatDeliveryEstimate(def.option.deliveryDays)}</p>
              <p className="text-xs text-muted-foreground">{modalityLabel(def.option.modality)}</p>
            </div>
          </div>
        </div>
      ) : (
        <p className="mt-6 text-sm text-muted-foreground">{def.hint}</p>
      )}
    </Card>
  );
};

export const HighlightCards = ({
  bestPrice,
  fastestDelivery,
  bestCostBenefit,
}: HighlightCardsProps) => {
  const defs: HighlightDef[] = [
    {
      key: 'best-price',
      label: 'Mais barato',
      hint: 'Sem opção disponível.',
      icon: Banknote,
      ring: 'ring-1 ring-emerald-200/70',
      iconWrap: 'bg-emerald-100 text-emerald-700',
      option: bestPrice,
    },
    {
      key: 'fastest',
      label: 'Mais rápido',
      hint: 'Sem opção disponível.',
      icon: Gauge,
      ring: 'ring-1 ring-sky-200/70',
      iconWrap: 'bg-sky-100 text-sky-700',
      option: fastestDelivery,
    },
    {
      key: 'best-value',
      label: 'Melhor custo-benefício',
      hint: 'Sem opção disponível.',
      icon: Trophy,
      ring: 'ring-2 ring-accent/60',
      iconWrap: 'bg-accent/20 text-postal-ink',
      option: bestCostBenefit,
    },
  ];

  return (
    <div className="grid gap-4 sm:grid-cols-2 lg:gap-5 xl:grid-cols-3">
      {defs.map((def) => (
        <HighlightCard key={def.key} def={def} />
      ))}
    </div>
  );
};
