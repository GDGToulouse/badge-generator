package gdg.toulouse.attendee.domain;

import gdg.toulouse.data.Try;
import gdg.toulouse.data.Unit;
import gdg.toulouse.design.annotations.ApplicationInterface;

import java.io.OutputStream;

@ApplicationInterface
public interface AttendeeBadge {

    // May be IO<...> monad can be a better solution [TODO]

    Try<Unit> generate(OutputStream stream);

}
