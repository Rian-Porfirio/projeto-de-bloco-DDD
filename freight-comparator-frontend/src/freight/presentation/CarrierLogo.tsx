import { useState } from 'react';
import { cn } from '@/shared/lib/utils';

interface CarrierLogoProps {
  name: string;
  logoUrl?: string;
  className?: string;
}

const initialsOf = (name: string): string =>
  name
    .split(/\s+/)
    .filter(Boolean)
    .slice(0, 2)
    .map((part) => part.charAt(0))
    .join('')
    .toUpperCase();

export const CarrierLogo = ({ name, logoUrl, className }: CarrierLogoProps) => {
  const [failed, setFailed] = useState(false);
  const showImage = Boolean(logoUrl) && !failed;

  return (
    <span
      className={cn(
        'flex h-10 w-10 shrink-0 items-center justify-center overflow-hidden rounded-md border border-border bg-white',
        className
      )}
    >
      {showImage ? (
        <img
          src={logoUrl}
          alt={name}
          loading="lazy"
          className="h-full w-full object-contain p-1"
          onError={() => setFailed(true)}
        />
      ) : (
        <span className="text-xs font-bold text-primary">{initialsOf(name)}</span>
      )}
    </span>
  );
};
