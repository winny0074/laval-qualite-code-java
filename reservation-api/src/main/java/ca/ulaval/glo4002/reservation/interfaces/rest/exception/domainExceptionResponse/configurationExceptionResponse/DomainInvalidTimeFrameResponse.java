package ca.ulaval.glo4002.reservation.interfaces.rest.exception.domainExceptionResponse.configurationExceptionResponse;

import org.eclipse.jetty.http.HttpStatus;

public class DomainInvalidTimeFrameResponse {
  public static final String ERROR = "INVALID_TIME_FRAMES";
  public static final String DESCRIPTION =
    "Invalid time frames, please use better ones.";
  public static final int STATUS_CODE = HttpStatus.BAD_REQUEST_400;
}
