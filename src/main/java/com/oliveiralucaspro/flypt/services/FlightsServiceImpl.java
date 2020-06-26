package com.oliveiralucaspro.flypt.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;

import com.oliveiralucaspro.flypt.dto.ExternalRequestDTO;
import com.oliveiralucaspro.flypt.dto.FlightDetailDTO;
import com.oliveiralucaspro.flypt.enums.Destination;
import com.oliveiralucaspro.flypt.exceptions.BusinessException;
import com.oliveiralucaspro.flypt.exceptions.OutboundServiceException;
import com.oliveiralucaspro.flypt.utils.Constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FlightsServiceImpl implements FlightsService{
	
	private final ProcessorRequest processorRequest;

    public List<FlightDetailDTO> getFlights(List<String> destinations, String dateFrom, String dateTo) throws BusinessException, OutboundServiceException {
        LocalDate dateFromFormatted = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

        if (isDateFormatValid(dateFrom)) {
            dateFromFormatted = LocalDate.parse(dateFrom, formatter);
            dateFromTodayOrAfterValidator(dateFromFormatted);
        }

        LocalDate dateToFormatted = dateFromFormatted;

        if (isDateFormatValid(dateTo)) {
            dateToFormatted = LocalDate.parse(dateTo, formatter);
        }

       departureDateBeforeOrEqualArrivalDateValidator(dateFromFormatted, dateToFormatted);

        List<Destination> destinationEnumList = filterDestinations(destinations);

        ExternalRequestDTO externalRequestDTO = ExternalRequestDTO.builder()
                .dateFrom(dateFromFormatted)
                .dateTo(dateToFormatted)
                .destinations(destinationEnumList)
                .build();

        return processorRequest.getFlightDetailResponse(externalRequestDTO);
    }

    private List<Destination> filterDestinations(List<String> destinations) throws BusinessException {
        Set<String> destinationSet = new HashSet<>();
        for (String destination : destinations) {
            destinationSet.add(getDestinationByKey(destination).getDestinationCode());
        }

        List<Destination> destinationFilteredList = new ArrayList<>();

        destinationSet.forEach(destination -> destinationFilteredList.add(Destination.findByValue(destination)));

        return destinationFilteredList;
    }

    private Destination getDestinationByKey(String destinationKey) throws BusinessException {
        Destination destination = Destination.findByKey(destinationKey);
        if (destination == null) {
            throw new BusinessException(Constants.INVALID_DESTINATION_ERROR_MESSAGE);
        }

        return destination;
    }

    private boolean isDateFormatValid(String date) throws BusinessException {
        if (date == null || date.isEmpty()) {
            return false;
        } else if (!GenericValidator.isDate(date, Constants.DATE_FORMAT, true)) {
            throw new BusinessException(Constants.INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }

        return true;
    }

    private void dateFromTodayOrAfterValidator(LocalDate date) throws BusinessException {
        if (date.isBefore(LocalDate.now())) {
            throw new BusinessException(Constants.PASSED_DATE_ERROR_MESSAGE);
        }
    }

    private void departureDateBeforeOrEqualArrivalDateValidator(LocalDate dateDeparture, LocalDate dateArrival) throws BusinessException {
        if (dateDeparture.isAfter(dateArrival)) {
            throw new BusinessException(Constants.CROSSED_DATE_ERROR_MESSAGE);
        }
    }
}
