package config;

import org.hamcrest.Matcher;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rvukolov.moneytransferbackend.model.Card;
import ru.rvukolov.moneytransferbackend.repository.CardsRepository;
import ru.rvukolov.moneytransferbackend.repository.OperationsRepository;
import ru.rvukolov.moneytransferbackend.service.CardsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class TestContextConfiguration {

    @Bean
    CardsRepository cardsRepository() {
        Card card = new Card("1111111111111111", "05/24", "123", "Ivan", "Ivanov");
        CardsRepository cardsRepository = mock(CardsRepository.class);
        when(cardsRepository.getCardById(any())).thenReturn(card);
        when(cardsRepository.hasCard(any())).thenReturn(true);
        return cardsRepository;
    }

    @Bean
    OperationsRepository operationsRepository() {
        return mock(OperationsRepository.class);
    }

    @Bean
    CardsService cardsService(OperationsRepository operationsRepository, CardsRepository cardsRepository) {
        return new CardsService(operationsRepository, cardsRepository);
    }
}
