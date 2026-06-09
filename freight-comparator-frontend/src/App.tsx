import { useState } from 'react';
import { PackageCheck } from 'lucide-react';
import { FreightForm } from '@/freight/presentation/FreightForm';
import { FreightResults } from '@/freight/presentation/FreightResults';
import { useCalculateFreight } from '@/freight/application/useCalculateFreight';
import { resolveErrorMessage } from '@/shared/lib/errors';
import type { FreightFormValues } from '@/freight/domain/freightSchema';

const App = () => {
  const [hasSubmitted, setHasSubmitted] = useState(false);
  const mutation = useCalculateFreight();

  const handleSubmit = (values: FreightFormValues) => {
    setHasSubmitted(true);
    mutation.mutate(values);
  };

  return (
    <div className="flex min-h-screen flex-col">
      <header className="border-b border-border/70 bg-card/80 backdrop-blur">
        <div className="mx-auto flex h-16 w-full max-w-7xl items-center gap-3 px-4 sm:px-6 lg:px-8">
          <span className="flex h-10 w-10 items-center justify-center rounded-xl bg-primary text-primary-foreground shadow-card">
            <PackageCheck className="h-5 w-5" />
          </span>
          <div className="leading-tight">
            <p className="font-display text-lg font-bold text-postal-ink">Comparador de Fretes</p>
            <p className="text-xs text-muted-foreground">
              Compare prazos e preços de transportadoras
            </p>
          </div>
        </div>
      </header>

      <main className="flex-1 py-10 lg:py-12">
        <div className="mx-auto w-full max-w-7xl space-y-10 px-4 sm:px-6 lg:px-8">
          <section className="animate-fade-up space-y-6 text-center">
            <div className="mx-auto inline-flex items-center gap-2 rounded-full border border-border bg-card px-3 py-1 text-xs font-medium text-muted-foreground">
              <span className="h-1.5 w-1.5 rounded-full bg-accent" />
              SEDEX, PAC, Jadlog, Braspress e mais
            </div>
            <div className="space-y-3">
              <h1 className="text-balance text-3xl font-bold leading-tight text-postal-ink sm:text-4xl">
                Calcule e compare o frete em segundos
              </h1>
              <p className="mx-auto max-w-xl text-muted-foreground">
                Informe origem, destino e as dimensões da encomenda para encontrar a opção mais
                barata, mais rápida e com melhor custo-benefício.
              </p>
            </div>
          </section>

          <div className="grid items-start gap-8 lg:grid-cols-[minmax(0,380px)_minmax(0,1fr)] lg:gap-10">
            <div className="animate-fade-up rounded-lg border bg-card p-6 shadow-float lg:sticky lg:top-8">
              <FreightForm onSubmit={handleSubmit} isPending={mutation.isPending} />
            </div>
            <div className="animate-fade-up">
              <FreightResults
                data={mutation.data}
                isPending={mutation.isPending}
                isError={mutation.isError}
                errorMessage={mutation.error ? resolveErrorMessage(mutation.error) : undefined}
                hasSubmitted={hasSubmitted}
              />
            </div>
          </div>
        </div>
      </main>

      <footer className="border-t border-border/70 py-6">
        <div className="mx-auto w-full max-w-7xl px-4 text-center text-xs text-muted-foreground sm:px-6 lg:px-8">
          Projeto demonstrativo · Valores calculados por provedor mock para fins de avaliação.
        </div>
      </footer>
    </div>
  );
};

export default App;
