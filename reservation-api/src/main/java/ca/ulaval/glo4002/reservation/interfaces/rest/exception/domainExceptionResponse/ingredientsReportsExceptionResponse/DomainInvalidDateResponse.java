package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.ingredientsReportsExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class DomainInvalidDateResponse {
  public static final String ERROR = "INVALID_DATE";
  public static final String DESCRIPTION =
          "Dates should be between %s and %s and must be specified.";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;
}
