package com.freightcomparator.config;

import com.freightcomparator.freight.domain.exception.FreightCalculationException;
import com.freightcomparator.freight.domain.exception.NoQuotesAvailableException;
import com.freightcomparator.freight.domain.exception.UnresolvableLocationException;
import com.freightcomparator.location.domain.exception.AddressNotFoundException;
import com.freightcomparator.location.domain.exception.InvalidZipCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidZipCodeException.class)
    public ProblemDetail handleInvalidZipCode(InvalidZipCodeException exception) {
        return problem(HttpStatus.BAD_REQUEST, "Invalid zip code", exception.getMessage(), "invalid-zip-code");
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ProblemDetail handleAddressNotFound(AddressNotFoundException exception) {
        return problem(HttpStatus.NOT_FOUND, "Address not found", exception.getMessage(), "address-not-found");
    }

    @ExceptionHandler(UnresolvableLocationException.class)
    public ProblemDetail handleUnresolvableLocation(UnresolvableLocationException exception) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, "Unresolvable location", exception.getMessage(), "unresolvable-location");
    }

    @ExceptionHandler(NoQuotesAvailableException.class)
    public ProblemDetail handleNoQuotes(NoQuotesAvailableException exception) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, "No freight quotes available", exception.getMessage(), "no-quotes-available");
    }

    @ExceptionHandler(FreightCalculationException.class)
    public ProblemDetail handleFreightCalculation(FreightCalculationException exception) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, "Freight calculation failed", exception.getMessage(), "freight-calculation-failed");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException exception) {
        return problem(HttpStatus.BAD_REQUEST, "Invalid request", exception.getMessage(), "invalid-request");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = problem(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "One or more fields are invalid",
                "validation-error");

        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception exception) {
        return problem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error",
                "An unexpected error occurred while processing the request",
                "internal-error");
    }

    private ProblemDetail problem(HttpStatus status, String title, String detail, String type) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create("https://freight-comparator/problems/" + type));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
