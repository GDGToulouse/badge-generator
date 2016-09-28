package gdg.toulouse.command.finder;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassPathFinderTest {

    @Test
    public void shouldBeAbleToRetrieveAResourceInTheRoot() throws Exception {
        final String regex = "test\\.1";
        final ClassPathFinder classPathFinder = givenAClassPathFinder(regex);

        assertThat(classPathFinder.getResources().size()).isEqualTo(1);
    }

    @Test
    public void shouldBeAbleToRetrieveAResourceInSubDirectory() throws Exception {
        final String regex = "test\\.2";
        final ClassPathFinder classPathFinder = givenAClassPathFinder(regex);

        assertThat(classPathFinder.getResources().size()).isEqualTo(1);
    }

    @Test
    public void shouldBeAbleToRetrieveAllResources() throws Exception {
        final String regex = "test\\.[12]";
        final ClassPathFinder classPathFinder = givenAClassPathFinder(regex);

        assertThat(classPathFinder.getResources().size()).isEqualTo(2);
    }

    private ClassPathFinder givenAClassPathFinder(String regex) {
        return new ClassPathFinder(Pattern.compile(regex));
    }
}