import { PackageX, ServerCrash, type LucideIcon } from 'lucide-react';
import { Card } from '@/shared/ui/card';
import { Skeleton } from '@/shared/ui/skeleton';
import { HighlightCards } from './HighlightCards';
import { ResultsTable } from './ResultsTable';
import type { FreightCalculationResponse } from '@/freight/domain/freight';

interface FreightResultsProps {
  data?: FreightCalculationResponse;
  isPending: boolean;
  isError: boolean;
  errorMessage?: string;
  hasSubmitted: boolean;
}

const LoadingState = () => (
  <div className="space-y-4">
    <div className="grid gap-4 sm:grid-cols-2 lg:gap-5 xl:grid-cols-3">
      {[0, 1, 2].map((key) => (
        <Card key={key} className="p-5">
          <Skeleton className="h-4 w-24" />
          <div className="mt-4 flex items-center gap-3">
            <Skeleton className="h-10 w-10 rounded-md" />
            <div className="flex-1 space-y-2">
              <Skeleton className="h-4 w-2/3" />
              <Skeleton className="h-3 w-1/2" />
            </div>
          </div>
          <Skeleton className="mt-4 h-7 w-28" />
        </Card>
      ))}
    </div>
    <Card className="space-y-3 p-5">
      {[0, 1, 2, 3].map((key) => (
        <Skeleton key={key} className="h-12 w-full" />
      ))}
    </Card>
  </div>
);

interface MessageStateProps {
  icon: LucideIcon;
  title: string;
  description: string;
  tone: 'muted' | 'error';
}

const MessageState = ({ icon: Icon, title, description, tone }: MessageStateProps) => (
  <Card className="flex flex-col items-center gap-3 px-6 py-14 text-center">
    <span
      className={
        tone === 'error'
          ? 'flex h-12 w-12 items-center justify-center rounded-full bg-destructive/10 text-destructive'
          : 'flex h-12 w-12 items-center justify-center rounded-full bg-muted text-muted-foreground'
      }
    >
      <Icon className="h-6 w-6" />
    </span>
    <div className="space-y-1">
      <h3 className="text-lg font-semibold">{title}</h3>
      <p className="mx-auto max-w-sm text-sm text-muted-foreground">{description}</p>
    </div>
  </Card>
);

export const FreightResults = ({
  data,
  isPending,
  isError,
  errorMessage,
  hasSubmitted,
}: FreightResultsProps) => {
  if (isPending) {
    return <LoadingState />;
  }

  if (isError) {
    return (
      <MessageState
        icon={ServerCrash}
        tone="error"
        title="Não foi possível calcular"
        description={errorMessage ?? 'Tente novamente em instantes.'}
      />
    );
  }

  if (!data) {
    return (
      <MessageState
        icon={PackageX}
        tone="muted"
        title={hasSubmitted ? 'Nenhuma opção encontrada' : 'Pronto para comparar'}
        description={
          hasSubmitted
            ? 'Não encontramos opções de frete para os dados informados.'
            : 'Preencha os dados da encomenda e clique em Calcular Frete para ver as melhores opções.'
        }
      />
    );
  }

  return (
    <section className="space-y-5 lg:space-y-6">
      <div className="flex flex-wrap items-baseline justify-between gap-2">
        <h2 className="text-xl font-semibold">Opções de frete</h2>
        <p className="text-sm text-muted-foreground">
          {data.originZipCode} para {data.destinationZipCode} · {data.options.length} opções
        </p>
      </div>
      <HighlightCards
        bestPrice={data.bestPrice}
        fastestDelivery={data.fastestDelivery}
        bestCostBenefit={data.bestCostBenefit}
      />
      <ResultsTable options={data.options} />
    </section>
  );
};
