package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.rvukolov.moneytransferbackend.model.Card;
import ru.rvukolov.moneytransferbackend.model.Operation;
import ru.rvukolov.moneytransferbackend.model.OperationStatuses;
import ru.rvukolov.moneytransferbackend.model.OperationTypes;
import ru.rvukolov.moneytransferbackend.service.CardsService;

@WebMvcTest
//@ContextConfiguration(classes = {CardsService.class})
public class CardsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private MockHttpServletRequestBuilder requestBuilder;
    @MockBean
    private CardsService cardsService;

    public void cardRegisterTest() {
        Card card = new Card("1111111111111111", "05/24", "123", "Ivan", "Ivanov");
        Mockito.when(cardsService.addCard(card)).thenReturn(new Operation(OperationTypes.ADD_CARD, OperationStatuses.SUCCESS));


    }
}
