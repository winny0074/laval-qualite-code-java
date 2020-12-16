package ca.ulaval.glo4002.reservation.context;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RestaurantContext extends AbstractBinder {
    private final PersistenceType persistenceType;

    public RestaurantContext(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
    }

    @Override
    protected void configure() {
        install(new ApplicationContext());
        switch (persistenceType) {
            case MEMORY:
                System.out.println("Using IN-MEMORY for persistence");
                install(new InMemoryPersistenceContext());
                break;
        }
    }

}
