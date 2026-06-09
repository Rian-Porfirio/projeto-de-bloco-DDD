package com.freightcomparator.freight.application.port.in;

import com.freightcomparator.freight.application.dto.FreightCalculationResult;

import java.util.List;

public interface FreightHistoryUseCase {
    List<FreightCalculationResult> recent(int limit);
}
