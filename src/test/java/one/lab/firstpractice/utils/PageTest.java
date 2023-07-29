package one.lab.firstpractice.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.Optional;

import static one.lab.firstpractice.utils.Page.*;
import static org.junit.jupiter.api.Assertions.*;

class PageTest {


    @Test
    void testUtilityClassConstructor() {

        try {
            Constructor<Page> constructor = Page.class.getDeclaredConstructor();
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
            constructor.setAccessible(true);

            assertThrows(InvocationTargetException.class, constructor::newInstance,
                    "Utility class should not be instantiated.");
        } catch (NoSuchMethodException e) {
            fail("Expected private constructor not found.");
        }
    }


    @Test
    void testGetPageable_WithAllParameters() {
        Optional<Integer> pageOptional = Optional.of(2);
        Optional<Integer> sizeOptional = Optional.of(20);
        Optional<String> sortOptional = Optional.of("title");

        Pageable pageable = getPageable(pageOptional, sizeOptional, sortOptional);

        assertNotNull(pageable);
        assertEquals(2, pageable.getPageNumber());
        assertEquals(20, pageable.getPageSize());
        assertTrue(pageable.getSort().isSorted());
        assertEquals("title", Objects.requireNonNull(pageable.getSort().getOrderFor("title")).getProperty());
        assertEquals(Sort.Direction.ASC, Objects.requireNonNull(pageable.getSort().getOrderFor("title")).getDirection());
    }

    @Test
    void testGetPageable_WithOnlyPageParameter() {
        Optional<Integer> pageOptional = Optional.of(1);
        Optional<Integer> sizeOptional = Optional.empty();
        Optional<String> sortOptional = Optional.empty();

        Pageable pageable = getPageable(pageOptional, sizeOptional, sortOptional);

        assertNotNull(pageable);
        assertEquals(1, pageable.getPageNumber());
        assertEquals(MAX_ELEMENTS_PER_PAGE, pageable.getPageSize());
        assertNull(pageable.getSort().getOrderFor("someField"));
    }

    @Test
    void testGetPageable_WithNoParameters() {
        Optional<Integer> pageOptional = Optional.empty();
        Optional<Integer> sizeOptional = Optional.empty();
        Optional<String> sortOptional = Optional.empty();

        Pageable pageable = getPageable(pageOptional, sizeOptional, sortOptional);

        assertNotNull(pageable);
        assertEquals(DEFAULT_PAGE, pageable.getPageNumber());
        assertEquals(MAX_ELEMENTS_PER_PAGE, pageable.getPageSize());
        assertNull(pageable.getSort().getOrderFor("someField"));
    }
}

