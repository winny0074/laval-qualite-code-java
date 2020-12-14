package ca.ulaval.glo4002.reservation.interfaces.rest.exception;

import ca.ulaval.glo4002.reservation.domain.exception.DomainException;
import ca.ulaval.glo4002.reservation.domain.exception.TooPickyException;
import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidDate;
import ca.ulaval.glo4002.reservation.domain.exception.ingredientsReportsException.InvalidType;
import ca.ulaval.glo4002.reservation.domain.exception.reservationException.*;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.ingredientsReportsExceptionResponse.DomainInvalidDateResponse;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.ingredientsReportsExceptionResponse.DomainInvalidTypeResponse;
import ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.reservationExceptionResponse.*;
import org.eclipse.jetty.http.HttpStatus;

public class DomainExceptionResponseFactory {
  public DomainExceptionResponseFactory() {}

  public ExceptionResponse create(DomainException domainException) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(null, null, HttpStatus.BAD_REQUEST_400);

    if(domainException instanceof InvalidDate) {
      exceptionResponse = new ExceptionResponse(DomainInvalidDateResponse.ERROR, DomainInvalidDateResponse.DESCRIPTION, DomainInvalidDateResponse.STATUS_CODE);
    }
    else if(domainException instanceof InvalidType) {
      exceptionResponse = new ExceptionResponse(DomainInvalidTypeResponse.ERROR, DomainInvalidTypeResponse.DESCRIPTION, DomainInvalidTypeResponse.STATUS_CODE);
    }
    else if(domainException instanceof InvalidDinnerDate) {
      exceptionResponse = new ExceptionResponse(InvalidDinnerDateResponse.ERROR, InvalidDinnerDateResponse.DESCRIPTION, InvalidDinnerDateResponse.STATUS_CODE);
    }
    else if(domainException instanceof InvalidFormat) {
      exceptionResponse = new ExceptionResponse(DomainInvalidFormatResponse.ERROR, DomainInvalidFormatResponse.DESCRIPTION, DomainInvalidFormatResponse.STATUS_CODE);
    }
    else if(domainException instanceof InvalidPeopleAmount) {
      exceptionResponse = new ExceptionResponse(InvalidPeopleAmountResponse.ERROR, InvalidPeopleAmountResponse.DESCRIPTION, InvalidPeopleAmountResponse.STATUS_CODE);
    }
    else if(domainException instanceof InvalidReservationDate) {
      exceptionResponse = new ExceptionResponse(InvalidReservationDateResponse.ERROR, InvalidReservationDateResponse.DESCRIPTION, InvalidReservationDateResponse.STATUS_CODE);
    }
    else if(domainException instanceof TooPickyException) {
      exceptionResponse = new ExceptionResponse(TooPickyExceptionResponse.ERROR, TooPickyExceptionResponse.DESCRIPTION, TooPickyExceptionResponse.STATUS_CODE);
    }
    else if(domainException instanceof ReservationNotFound) {
      exceptionResponse = new ExceptionResponse(ReservationNotFoundResponse.ERROR, String.format(ReservationNotFoundResponse.DESCRIPTION, ((ReservationNotFound) domainException).getReservationId()), ReservationNotFoundResponse.STATUS_CODE);
    }

    return exceptionResponse;
  }
}
