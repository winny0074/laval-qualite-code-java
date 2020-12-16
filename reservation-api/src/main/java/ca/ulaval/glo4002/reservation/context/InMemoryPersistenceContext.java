package ca.ulaval.glo4002.reservation.context;

import ca.ulaval.glo4002.reservation.domain.chef.ChefReportRepository;
import ca.ulaval.glo4002.reservation.domain.chef.ChefRepository;
import ca.ulaval.glo4002.reservation.domain.ingredient.IngredientRepository;
import ca.ulaval.glo4002.reservation.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.reservation.domain.restaurant.RestaurantContextRepository;
import ca.ulaval.glo4002.reservation.infrastructure.ChefPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.ChefReportPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.ReservationPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.RestaurantContextPersistenceInMemory;
import ca.ulaval.glo4002.reservation.infrastructure.supplier.ExternalIngredientClient;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class InMemoryPersistenceContext extends AbstractBinder {
    @Override protected void configure() {
        bind(RestaurantContextPersistenceInMemory.class).to(RestaurantContextRepository.class).in(Singleton.class);
        bind(ExternalIngredientClient.class).to(IngredientRepository.class);
        bind(ReservationPersistenceInMemory.class).to(ReservationRepository.class).in(Singleton.class);
        bind(ChefReportPersistenceInMemory.class).to(ChefReportRepository.class).in(Singleton.class);
        bind(ChefPersistenceInMemory.class).to(ChefRepository.class);
    }
}
