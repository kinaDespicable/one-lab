package one.lab.firstpractice.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class Page {

    private Page() throws InvocationTargetException {
        throw new InvocationTargetException(new IllegalAccessException("Utility class should not be instantiated."));
    }

    public static final int DEFAULT_PAGE = 0;
    public static final int MAX_ELEMENTS_PER_PAGE = 5;
    public static final String DEFAULT_SORTING_ATTRIBUTE = "id";

    public static Pageable getPageable() {
        return PageRequest.of(DEFAULT_PAGE, MAX_ELEMENTS_PER_PAGE, Sort.by(Sort.Direction.ASC, DEFAULT_SORTING_ATTRIBUTE));
    }

    public static Pageable getPageable(Optional<Integer> pageOptional,
                                       Optional<Integer> sizeOptional,
                                       Optional<String> sortOptional) {
        Integer page = pageOptional.orElse(DEFAULT_PAGE);
        Integer size = sizeOptional.orElse(MAX_ELEMENTS_PER_PAGE);
        String sort = sortOptional.orElse(DEFAULT_SORTING_ATTRIBUTE);

        return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));

    }

}
