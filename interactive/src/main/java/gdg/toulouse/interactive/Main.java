package gdg.toulouse.interactive;

import gdg.toulouse.data.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Main {

    public static void main(String[] args) {

        final Map<String, Action> actions = new HashMap<>();
        actions.put("badge", new Badges());

        if (args.length < 1) {
            System.exit(1);
        }

        Optional.ofNullable(actions.get(args[0]))
                .map(action -> action.execute(args))
                .orElseGet(() -> {
                    System.err.println("usage: attendee");
                    actions.entrySet().stream().
                            map(entry -> " " + entry.getKey() + " " + entry.getValue().description()).
                            forEach(System.err::println);
                    return Unit.UNIT;
                });
    }
}
