package ru.otus.hw.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
public class ConsoleCommands {

    @ShellMethod(value = "Start console", key = "console")
    public void openConsole() throws SQLException {
        org.h2.tools.Console.main();
    }
}
