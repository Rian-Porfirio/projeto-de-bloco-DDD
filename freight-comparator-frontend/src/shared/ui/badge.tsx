import { type HTMLAttributes } from 'react';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '@/shared/lib/utils';

const badgeVariants = cva(
  'inline-flex items-center gap-1 rounded-full px-2.5 py-0.5 text-xs font-semibold',
  {
    variants: {
      variant: {
        neutral: 'bg-muted text-muted-foreground',
        price: 'bg-emerald-100 text-emerald-700',
        speed: 'bg-sky-100 text-sky-700',
        value: 'bg-accent/20 text-postal-ink',
        modality: 'border border-border bg-card text-foreground/80',
      },
    },
    defaultVariants: {
      variant: 'neutral',
    },
  }
);

export interface BadgeProps
  extends HTMLAttributes<HTMLSpanElement>,
    VariantProps<typeof badgeVariants> {}

export const Badge = ({ className, variant, ...props }: BadgeProps) => (
  <span className={cn(badgeVariants({ variant }), className)} {...props} />
);

export { badgeVariants };
