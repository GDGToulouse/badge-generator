package gdg.toulouse.command.finder;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.VFS;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ClassPathFinder {

    private static final String PATH_SEPARATOR = "path.separator";
    private static final String JAVA_CLASS_PATH = "java.class.path";
    private static final String DEFAULT_PATH = ".";

    private final Pattern pattern;

    /**
     * Constructor
     *
     * @param pattern
     */
    public ClassPathFinder(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Method called when a resources must be retrieved using a a regular expression
     *
     * @return the resources found
     */
    public Collection<FileObject> getResources() {
        return getClassPathElements().stream().
                flatMap(element -> getResources(element).stream()).
                collect(toList());
    }

    /**
     * Method called to retrieve all path entry
     *
     * @return a list of path entry
     */
    private List<String> getClassPathElements() {
        final String classPath = System.getProperty(JAVA_CLASS_PATH, DEFAULT_PATH);
        return asList(classPath.split(System.getProperty(PATH_SEPARATOR)));
    }

    /**
     * Method used to retrieved all filtered from an element
     *
     * @param element The starting point for the search
     * @return a collection of filtered file object
     */
    private Collection<FileObject> getResources(final String element) {
        try {
            return getResources(VFS.getManager().resolveFile(getElementToScan(element))).collect(toList());
        } catch (FileSystemException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Method use to create a
     *
     * @param element
     * @return
     */
    private String getElementToScan(String element) {
        if (element.endsWith(".jar")) {
            return "jar:" + element;
        } else {
            return "file:" + element;
        }
    }

    /**
     * Method used to retrieved all filtered from a file object
     *
     * @param fileObject The starting point for the search
     * @return a stream of filtered file object
     */
    private Stream<FileObject> getResources(final FileObject fileObject) {
        try {
            if (fileObject.isFile() && isFilteredByPattern(fileObject)) {
                return asList(fileObject).stream();
            }

            if (fileObject.isFolder()) {
                return asList(fileObject.getChildren()).stream().flatMap(this::getResources).collect(toList()).stream();
            }

            return Collections.<FileObject>emptyList().stream();
        } catch (FileSystemException e) {
            return Collections.<FileObject>emptyList().stream();
        }
    }

    private boolean isFilteredByPattern(FileObject fileObject) {
        return pattern.matcher(fileObject.getName().getBaseName()).matches();
    }
}