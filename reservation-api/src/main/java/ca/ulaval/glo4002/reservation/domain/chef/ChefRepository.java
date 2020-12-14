package ca.ulaval.glo4002.reservation.domain.chef;

import java.util.List;

public interface ChefRepository {

  List<Chef> findAll();
}
