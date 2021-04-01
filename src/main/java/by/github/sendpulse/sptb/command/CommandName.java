package by.github.sendpulse.sptb.command;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),

    START_QUIZ("/start_quiz"),
    STOP_QUIZ("/stop_quiz"),
    SHOW_USER_STATISTICS("/my_statistics"),

    NO("No such command");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
