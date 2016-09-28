package gdg.toulouse.command.command;

import gdg.toulouse.data.Try;

public interface Command<T> {

    Try<T> create(String parameter);

}
