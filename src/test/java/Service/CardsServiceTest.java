package Service;

import config.TestContextConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.rvukolov.moneytransferbackend.exceptions.AApplicationException;
import ru.rvukolov.moneytransferbackend.model.Card;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.repository.CardsRepository;
import ru.rvukolov.moneytransferbackend.service.CardsService;

@WebMvcTest
@ContextConfiguration(classes = {TestContextConfiguration.class})
public class CardsServiceTest {

    @Autowired
    private CardsRepository cardsRepository;
    @Autowired
    private CardsService cardsService;

    @Test
    public void addCardTestAlreadyRegistered() {
        Card card = new Card("1111111111111111", "05/24", "123", "Ivan", "Ivanov");
        Assertions.assertThatThrownBy(() -> cardsService.addCard(card)).isInstanceOf(AApplicationException.class);
    }

    @Test
    public void getCardByIdTest() {
        Operation operation = cardsService.getCardById("1111111111111111");
        Assertions.assertThat(operation.getCard().getCardId()).isEqualTo("1111111111111111");
        Mockito.verify(cardsRepository, Mockito.times(1)).getCardById("1111111111111111");
        Mockito.verify(cardsRepository, Mockito.times(1)).hasCard("1111111111111111");
    }
}
