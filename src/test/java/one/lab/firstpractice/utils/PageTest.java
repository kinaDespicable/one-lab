package one.lab.firstpractice.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageTest {

    @Test
    void testPrivateConstructor() {
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<Page> constructor = Page.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        }, "Expected private constructor to throw an exception");

        Throwable exception = assertThrows(InvocationTargetException.class, () -> {
            Constructor<Page> constructor = Page.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });

        assertTrue(exception.getCause() instanceof InvocationTargetException);
    }

    @Test
    void testGetPageable() {

        // Arrange
        Pageable pageable = Page.getPageable();

        // Assert
        assertThat(pageable).isNotNull();
        assertThat(pageable.getPageNumber()).isEqualTo(Page.DEFAULT_PAGE);
        assertThat(pageable.getPageSize()).isEqualTo(Page.MAX_ELEMENTS_PER_PAGE);
        assertThat(pageable.getSort()).isNotNull();

        Sort.Order order = pageable.getSort().getOrderFor(Page.DEFAULT_SORTING_ATTRIBUTE);
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    void testGetPageable_DefaultValues() {
        // Arrange
        Pageable pageable = Page.getPageable(Optional.empty(), Optional.empty(), Optional.empty());

        // Assert
        assertThat(pageable).isNotNull();
        assertThat(pageable.getPageNumber()).isEqualTo(Page.DEFAULT_PAGE);
        assertThat(pageable.getPageSize()).isEqualTo(Page.MAX_ELEMENTS_PER_PAGE);
        assertThat(pageable.getSort()).isNotNull();

        Sort.Order order = pageable.getSort().getOrderFor(Page.DEFAULT_SORTING_ATTRIBUTE);
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

    @Test
    void testGetPageable_CustomValues() {
        // Arrange
        Integer customPage = 1;
        Integer customSize = 10;
        String customSort = "title";

        Pageable pageable = Page.getPageable(
                Optional.of(customPage),
                Optional.of(customSize),
                Optional.of(customSort)
        );

        // Assert
        assertThat(pageable).isNotNull();
        assertThat(pageable.getPageNumber()).isEqualTo(customPage);
        assertThat(pageable.getPageSize()).isEqualTo(customSize);
        assertThat(pageable.getSort()).isNotNull();

        Sort.Order order = pageable.getSort().getOrderFor(customSort);
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.ASC);
    }

}

