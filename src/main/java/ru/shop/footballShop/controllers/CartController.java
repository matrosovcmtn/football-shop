package ru.shop.footballShop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.footballShop.dto.ProductDTO;
import ru.shop.footballShop.entites.Customer;
import ru.shop.footballShop.errors.PersonNotFoundException;
import ru.shop.footballShop.mappers.ProductMapper;
import ru.shop.footballShop.security.AuthenticatedCustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final AuthenticatedCustomerService authenticatedCustomerService;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;



    @GetMapping
    public List<ProductDTO> getCart() throws PersonNotFoundException {
        Customer person = authenticatedCustomerService.getAuthenticatedCustomer();
        return cartService.findCartByPerson(person.getId()).stream()
                .map(productMapper::convertProductDTOToProduct).collect(Collectors.toList());
    }

}
