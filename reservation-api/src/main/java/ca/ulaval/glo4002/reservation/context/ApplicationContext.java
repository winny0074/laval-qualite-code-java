package ca.ulaval.glo4002.reservation.context;

import ca.ulaval.glo4002.reservation.domain.Utils.IdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationIdentifierGenerator;
import ca.ulaval.glo4002.reservation.domain.restaurant.Restaurant;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.IngredientsReportsParametersValidator;
import ca.ulaval.glo4002.reservation.interfaces.rest.validators.ReservationBodyValidator;
import ca.ulaval.glo4002.reservation.services.*;
import ca.ulaval.glo4002.reservation.services.assemblers.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class ApplicationContext extends AbstractBinder {

    @Override protected void configure() {
        configureReservation();
        configureRestaurant();
        configureChef();
        configureConfiguration();
        configureIngredient();
        configureFinancial();
    }

    private void configureFinancial() {
        bindAsContract(FinancialReportService.class);
    }

    private void configureIngredient() {
        bindAsContract(IngredientsService.class);
        bindAsContract(OrderInUnitFormatDtoAssembler.class);
        bindAsContract(OrderInTotalFormatDtoAssembler.class);
        bindAsContract(IngredientsReportsParametersValidator.class);
    }

    private void configureConfiguration() {
        bindAsContract(ConfigurationService.class);
    }

    private void configureChef() {
        bind(ReservationIdentifierGenerator.class).to(IdentifierGenerator.class).in(Singleton.class);
        bindAsContract(GlobalChefsReportAssembler.class);
        bindAsContract(ChefService.class);
    }

    private void configureRestaurant() {
//        bind(IdentifierGenerator.class).to(ReservationIdentifierGenerator.class).in(Singleton.class);
        bindAsContract(Restaurant.class);

    }

    private void configureReservation() {
        bindAsContract(ReservationService.class);
        bindAsContract(ReservationRequestAssembler.class);
        bindAsContract(ReservationDtoAssembler.class);
        bindAsContract(ReservationBodyValidator.class);
    }
}
