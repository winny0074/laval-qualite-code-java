package ca.ulaval.glo4002.reservation.infrastructure;

import ca.ulaval.glo4002.reservation.domain.chef.Chef;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChefPersistenceInMemoryTest {
    private static long MEMORY_SIZE = 9L;

    @Test
    public void whenFinAllChiefsInMemory_thenReturnedSizeIsTrue(){

        ChefPersistenceInMemory chefPersistenceInMemory =  new ChefPersistenceInMemory();

        List<Chef> chefs = chefPersistenceInMemory.findAll();

        assertEquals(chefs.size(), MEMORY_SIZE);
    }
}
