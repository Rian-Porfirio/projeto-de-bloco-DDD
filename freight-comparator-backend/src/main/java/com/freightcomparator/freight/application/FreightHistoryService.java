package com.freightcomparator.freight.application;

import com.freightcomparator.freight.application.dto.FreightCalculationResult;
import com.freightcomparator.freight.application.port.in.FreightHistoryUseCase;
import com.freightcomparator.freight.domain.port.FreightCalculationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FreightHistoryService implements FreightHistoryUseCase {

    private static final int MAX_LIMIT = 50;
    private static final int DEFAULT_LIMIT = 10;

    private final FreightCalculationRepository freightCalculationRepository;

    public FreightHistoryService(FreightCalculationRepository freightCalculationRepository) {
        this.freightCalculationRepository = freightCalculationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FreightCalculationResult> recent(int limit) {
        return freightCalculationRepository.findRecent(normalize(limit)).stream()
                .map(FreightResultAssembler::toResult)
                .toList();
    }

    private int normalize(int limit) {
        if (limit <= 0) {
            return DEFAULT_LIMIT;
        }
        return Math.min(limit, MAX_LIMIT);
    }
}
