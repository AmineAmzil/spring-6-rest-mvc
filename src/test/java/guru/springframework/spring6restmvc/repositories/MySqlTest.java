package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jt, Spring Framework Guru.
 */
@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine");
    @Autowired
    DataSource dataSource;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers() {
        beerRepository.saveAllAndFlush(
                List.of(
                        Beer.builder()
                                .beerName("Milk")
                                .quantityOnHand(10)
                                .upc("Milk")
                                .beerStyle(BeerStyle.GOSE)
                                .price(new BigDecimal("19.99"))
                                .version(1)
                                .build(),
                        Beer.builder()
                                .beerName("Test 02")
                                .quantityOnHand(3)
                                .upc("Test 02")
                                .beerStyle(BeerStyle.LAGER)
                                .price(new BigDecimal("9.99"))
                                .version(1)
                                .build()
                )
        );

        List<Beer> beers = beerRepository.findAll();

        assertThat(beers.size()).isGreaterThan(2);
    }
}
