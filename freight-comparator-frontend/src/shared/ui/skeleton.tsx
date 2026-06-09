import { type HTMLAttributes } from 'react';
import { cn } from '@/shared/lib/utils';

export const Skeleton = ({ className, ...props }: HTMLAttributes<HTMLDivElement>) => (
  <div className={cn('skeleton-shimmer rounded-md bg-muted', className)} {...props} />
);
