import { describe, expect, it, vi } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FreightForm } from '@/freight/presentation/FreightForm';

describe('FreightForm', () => {
  it('shows validation errors when submitting empty fields', async () => {
    const onSubmit = vi.fn();
    render(<FreightForm onSubmit={onSubmit} isPending={false} />);

    await userEvent.click(screen.getByRole('button', { name: /calcular frete/i }));

    expect(await screen.findByText(/CEP de origem é obrigatório/i)).toBeInTheDocument();
    expect(onSubmit).not.toHaveBeenCalled();
  });

  it('masks the zip code as the user types', async () => {
    const onSubmit = vi.fn();
    render(<FreightForm onSubmit={onSubmit} isPending={false} />);

    const originInput = screen.getByLabelText('CEP de origem') as HTMLInputElement;
    await userEvent.type(originInput, '01001000');

    expect(originInput.value).toBe('01001-000');
  });

  it('submits the form for valid data', async () => {
    const onSubmit = vi.fn();
    render(<FreightForm onSubmit={onSubmit} isPending={false} />);

    await userEvent.type(screen.getByLabelText('CEP de origem'), '01001000');
    await userEvent.type(screen.getByLabelText('CEP de destino'), '30130010');
    await userEvent.type(screen.getByLabelText('Peso (kg)'), '2,5');
    await userEvent.type(screen.getByLabelText('Altura (cm)'), '20');
    await userEvent.type(screen.getByLabelText('Largura (cm)'), '15');
    await userEvent.type(screen.getByLabelText('Comprimento (cm)'), '30');

    await userEvent.click(screen.getByRole('button', { name: /calcular frete/i }));

    await waitFor(() => expect(onSubmit).toHaveBeenCalledTimes(1));
    expect(onSubmit.mock.calls[0][0]).toMatchObject({
      originZipCode: '01001-000',
      destinationZipCode: '30130-010',
      weight: '2,5',
    });
  });
});
