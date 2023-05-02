package com.quotation;

import com.quotation.dto.CustomerRequestDto;
import com.quotation.dto.CustomerResponseDto;
import com.quotation.entity.CustomerEntity;
import com.quotation.exception.CustomerNotFoundException;
import com.quotation.repository.CustomerRepository;
import com.quotation.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTests {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testShouldCreateCustomer() {
        CustomerRequestDto customerRequest = new CustomerRequestDto("Burak", "Ugar", "Werner", "burak.ugar77@gmail.com", "+42077380900", LocalDate.of(1998, 1, 1));
        CustomerResponseDto expectedCustomerResponseDto = new CustomerResponseDto(1L, "Burak", "Ugar", "Werner", "burak.ugar77@gmail.com", "+42077380900", LocalDate.of(1998, 1, 1));
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save(any(CustomerEntity.class)))
                .thenAnswer(invocationOnMock -> {
                    CustomerEntity savedCustomer = invocationOnMock.getArgument(0);
                    savedCustomer.setId(1L);
                    return savedCustomer;
                });
        ModelMapper modelMapper = new ModelMapper();
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, modelMapper);
        CustomerResponseDto customerResponseDto = customerService.createCustomer(customerRequest);
        verify(customerRepository).save(argThat(customerEntity -> customerEntity.getFirstName().equals(customerRequest.getFirstName()) &&
                customerEntity.getLastName().equals(customerRequest.getLastName()) &&
                customerEntity.getMiddleName().equals(customerRequest.getMiddleName()) &&
                customerEntity.getEmail().equals(customerRequest.getEmail()) &&
                customerEntity.getPhoneNumber().equals(customerRequest.getPhoneNumber()) &&
                customerEntity.getBirthDate().equals(customerRequest.getBirthDate())));

        assertThat(customerResponseDto)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedCustomerResponseDto);
        assertNotNull(customerResponseDto.getId());
        verifyNoMoreInteractions(customerRepository);
    }


    @Test
    void testShouldUpdateCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("Burak");
        customer.setLastName("Ugar");
        customer.setMiddleName("Werner");
        customer.setEmail("burak.ugar77@gmasil.com");
        customer.setPhoneNumber("+420773703123");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        CustomerRequestDto customerRequest = new CustomerRequestDto();
        customerRequest.setFirstName("Burak");
        customerRequest.setLastName("Ugar");
        customerRequest.setMiddleName("Werner");
        customerRequest.setEmail("burak.ugar77@gmasil.com");
        customerRequest.setPhoneNumber("+420773703123");
        customerRequest.setBirthDate(LocalDate.of(1995, 1, 1));
        ModelMapper modelMapper = new ModelMapper();// create a CustomerService object with the mocked customerRepository and modelMapper
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, modelMapper);
        CustomerResponseDto customerResponseDto = customerService.updateCustomer(1L, customerRequest);
        verify(customerRepository).findById(1L);
        verify(customerRepository).save(customer);
        CustomerResponseDto expectedCustomerResponseDto = new CustomerResponseDto();
        expectedCustomerResponseDto.setId(1L);
        expectedCustomerResponseDto.setFirstName("Burak");
        expectedCustomerResponseDto.setLastName("Ugar");
        expectedCustomerResponseDto.setMiddleName("Werner");
        expectedCustomerResponseDto.setEmail("burak.ugar77@gmasil.com");
        expectedCustomerResponseDto.setPhoneNumber("+420773703123");
        expectedCustomerResponseDto.setBirthDate(LocalDate.of(1995, 1, 1));
        assertThat(customerResponseDto).isEqualTo(expectedCustomerResponseDto);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void testShouldGetCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("Burak");
        customer.setLastName("Ugar");
        customer.setMiddleName("Nothing");
        customer.setEmail("burak.ugar77@gmasil.com");
        customer.setPhoneNumber("+420773703123");
        customer.setBirthDate(LocalDate.of(1995, 1, 1));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        ModelMapper modelMapper = new ModelMapper();
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, modelMapper);
        CustomerResponseDto customerResponseDto = customerService.getCustomer(1L);
        verify(customerRepository).findById(1L);
        CustomerResponseDto expectedCustomerResponseDto = new CustomerResponseDto();
        expectedCustomerResponseDto.setId(1L);
        expectedCustomerResponseDto.setFirstName("Burak");
        expectedCustomerResponseDto.setLastName("Ugar");
        expectedCustomerResponseDto.setMiddleName("Nothing");
        expectedCustomerResponseDto.setEmail("burak.ugar77@gmasil.com");
        expectedCustomerResponseDto.setPhoneNumber("+420773703123");
        expectedCustomerResponseDto.setBirthDate(LocalDate.of(1995, 1, 1));
        assertThat(customerResponseDto).isEqualTo(expectedCustomerResponseDto);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    public void testDeleteCustomerWithExistingCustomer() {
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository, modelMapper);
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("Burak");
        customer.setLastName("Ugar");
        customer.setMiddleName("Nothing");
        customer.setEmail("burak.ugar77@gmail.com");
        customer.setPhoneNumber("+420773703123");
        customer.setBirthDate(LocalDate.of(1995, 1, 1));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).deleteById(1L);
        assertDoesNotThrow(() -> customerService.deleteCustomer(1L));
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    public void testDeleteCustomerWithNonExistingCustomer() {
        long nonExistingCustomerId = 123L;
        when(customerRepository.findById(eq(nonExistingCustomerId))).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(nonExistingCustomerId));
        verifyNoMoreInteractions(customerRepository);
    }

}

