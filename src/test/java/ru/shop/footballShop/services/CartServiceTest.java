package ru.shop.footballShop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.shop.footballShop.entites.Customer;
import ru.shop.footballShop.entites.Product;
import ru.shop.footballShop.repositories.CustomerRepository;
import ru.shop.footballShop.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindCartByPerson() {
        // Создание фейковых данных
        Customer customer = new Customer();
        customer.setId(1L);
        List<Product> cart = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        cart.add(product1);
        cart.add(product2);
        customer.setCart(cart);

        // Мокирование репозитория
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Создание объекта CartService
        CartService cartService = new CartService(customerRepository, null);

        // Вызов метода и проверка результата
        List<Product> result = cartService.findCartByPerson(1L);
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
    }

    @Test
    public void testAddItemToCart() {
        // Создание тестовых данных
        long customerId = 1L;
        long productId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        Product product = new Product();
        product.setId(productId);

        // Мокирование поведения репозиториев
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Вызов метода
        cartService.addItemToCart(customerId, productId);

        // Проверка изменений
        assertNotNull(customer.getCart());
        assertEquals(1, customer.getCart().size());
        assertEquals(product, customer.getCart().get(0));
        assertNotNull(product.getCustomerList());
        assertEquals(1, product.getCustomerList().size());
        assertEquals(customer, product.getCustomerList().get(0));
    }

}
