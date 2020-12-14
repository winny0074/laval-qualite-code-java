package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.ingredientsReportsExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class DomainInvalidTypeResponse {
  public static final String ERROR = "INVALID_TYPE";
  public static final String DESCRIPTION =
          "Type must be either total or unit and must be specified.";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;
}
