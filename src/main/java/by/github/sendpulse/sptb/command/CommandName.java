package by.github.sendpulse.sptb.command;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),

    ADD_GROUP_SUB("/add_group_sub"),
    LIST_GROUP_SUB("/list_group_sub"),
    DELETE_GROUP_FROM_SUBS("/delete_group"),
    MY_SUBS("/my_subs"),

    QUIZ("/quiz"),
    START_QUIZ("/start_quiz"),
    STOP_QUIZ("/stop_quiz"),
    NEXT_QUESTION("/next_question"),
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
