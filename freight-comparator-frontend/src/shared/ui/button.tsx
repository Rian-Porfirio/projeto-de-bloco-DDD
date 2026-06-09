import { forwardRef, type ButtonHTMLAttributes } from 'react';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '@/shared/lib/utils';

const buttonVariants = cva(
  'inline-flex items-center justify-center gap-2 whitespace-nowrap rounded-md text-sm font-semibold transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 focus-visible:ring-offset-background disabled:pointer-events-none disabled:opacity-60',
  {
    variants: {
      variant: {
        default: 'bg-primary text-primary-foreground shadow-card hover:bg-primary/92 active:translate-y-px',
        accent: 'bg-accent text-accent-foreground shadow-card hover:brightness-95 active:translate-y-px',
        outline: 'border border-input bg-card hover:bg-muted',
        ghost: 'hover:bg-muted',
      },
      size: {
        default: 'h-11 px-5',
        lg: 'h-12 px-7 text-base',
        sm: 'h-9 px-3',
        icon: 'h-10 w-10',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  }
);

export interface ButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {}

export const Button = forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, size, ...props }, ref) => (
    <button ref={ref} className={cn(buttonVariants({ variant, size }), className)} {...props} />
  )
);

Button.displayName = 'Button';

export { buttonVariants };
