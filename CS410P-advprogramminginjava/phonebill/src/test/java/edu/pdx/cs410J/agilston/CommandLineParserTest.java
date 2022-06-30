package edu.pdx.cs410J.agilston;

import edu.pdx.cs410J.agilston.commandline.CommandLineParser;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link CommandLineParser} class.
 */
public class CommandLineParserTest {
    private CommandLineParser createParser() {
        CommandLineParser parser = new CommandLineParser("phonebill-2022.0.0.jar");
        parser.addArgument("positional1", "Positional argument 1");
        parser.addArgument("positional2", "Positional argument 2", "2");
        parser.addArgument("positional3", "Positional argument 3", "3",
                new String[] { "1", "2", "3" });
        parser.addFlag("--test1", "Test flag without option", false, "-t");
        parser.addFlag("--test2", "Test flag with option", true, "-e");
        parser.addFlag("--test3", "Test flag with choices", "a", new String[] { "a", "b", "c" }, "-s");
        return parser;
    }

    @Test
    void parseWithValidArguments() {
        CommandLineParser parser = createParser();
        parser.parse(new String[] {
                "1",
                "b",
                "2",
                "--test1",
                "--test2=test",
                "--test3",
                "c"
        });

        assertThat(parser.getValueOrDefault("positional1"), equalTo("1"));
        assertThat(parser.getValueOrDefault("positional2"), equalTo("b"));
        assertThat(parser.getValueOrDefault("positional3"), equalTo("2"));
        assertThat(parser.hasArgument("--test1"), equalTo(true));
        assertThat(parser.getValueOrDefault("--test2"), equalTo("test"));
        assertThat(parser.getValueOrDefault("--test3"), equalTo("c"));
    }

    @Test
    void parseWithFlagAliases() {
        CommandLineParser parser = createParser();
        parser.parse(new String[] {
                "1",
                "b",
                "2",
                "-t",
                "-e=test",
                "-s",
                "c"
        });

        assertThat(parser.getValueOrDefault("positional1"), equalTo("1"));
        assertThat(parser.getValueOrDefault("positional2"), equalTo("b"));
        assertThat(parser.getValueOrDefault("positional3"), equalTo("2"));
        assertThat(parser.hasArgument("--test1"), equalTo(true));
        assertThat(parser.hasArgument("-t"), equalTo(true));
        assertThat(parser.getValueOrDefault("--test2"), equalTo("test"));
        assertThat(parser.getValueOrDefault("--test3"), equalTo("c"));
    }

    @Test
    void parseWithCombinedFlagAliases() {
        CommandLineParser parser = createParser();
        parser.parse(new String[] {
                "1",
                "b",
                "2",
                "-tes",
                "test",
                "c"
        });

        assertThat(parser.getValueOrDefault("positional1"), equalTo("1"));
        assertThat(parser.getValueOrDefault("positional2"), equalTo("b"));
        assertThat(parser.getValueOrDefault("positional3"), equalTo("2"));
        assertThat(parser.hasArgument("--test1"), equalTo(true));
        assertThat(parser.hasArgument("-t"), equalTo(true));
        assertThat(parser.getValueOrDefault("--test2"), equalTo("test"));
        assertThat(parser.getValueOrDefault("--test3"), equalTo("c"));
    }

    @Test
    void parseWithMissingPositionalThatHasDefaultValue() {
        CommandLineParser parser = createParser();
        parser.parse(new String[] {
                "1",
                "--test1",
                "--test2=test",
                "--test3",
                "c"
        });

        assertThat(parser.getValueOrDefault("positional1"), equalTo("1"));
        assertThat(parser.hasArgument("positional2"), equalTo(false));
        assertThat(parser.getValueOrDefault("positional2"), equalTo("2"));
        assertThat(parser.hasArgument("positional3"), equalTo(false));
        assertThat(parser.getValueOrDefault("positional3"), equalTo("3"));
        assertThat(parser.hasArgument("--test1"), equalTo(true));
        assertThat(parser.getValueOrDefault("--test2"), equalTo("test"));
        assertThat(parser.getValueOrDefault("--test3"), equalTo("c"));
    }

    @Test
    void parseWithMissingPositionalArguments() {
        CommandLineParser parser = createParser();
        parser.parse(new String[] {
                "--test1",
                "--test2=test",
                "--test3",
                "c"
        });

        assertThat(parser.hasArgument("positional1"), equalTo(false));
        assertThat(parser.hasArgument("positional2"), equalTo(false));
        assertThat(parser.getValueOrDefault("positional2"), equalTo("2"));
        assertThat(parser.hasArgument("positional3"), equalTo(false));
        assertThat(parser.getValueOrDefault("positional3"), equalTo("3"));
        assertThat(parser.hasArgument("--test1"), equalTo(true));
        assertThat(parser.getValueOrDefault("--test2"), equalTo("test"));
        assertThat(parser.getValueOrDefault("--test3"), equalTo("c"));
    }

    @Test
    void parseWithNoArguments() {
        CommandLineParser parser = createParser();
        parser.parse(new String[0]);

        assertThat(parser.hasArgument("positional1"), equalTo(false));
        assertThat(parser.hasArgument("positional2"), equalTo(false));
        assertThat(parser.getValueOrDefault("positional2"), equalTo("2"));
        assertThat(parser.hasArgument("positional3"), equalTo(false));
        assertThat(parser.getValueOrDefault("positional3"), equalTo("3"));
        assertThat(parser.hasArgument("--test1"), equalTo(false));
        assertThat(parser.getValueOrDefault("--test2"), equalTo(""));
        assertThat(parser.getValueOrDefault("--test3"), equalTo("a"));
    }

    @Test
    void parseWithIncorrectOptions() {
        CommandLineParser parser = createParser();
        parser.parse(new String[] {
                "1",
                "b",
                "4",
                "--test1",
                "--test2=test",
                "--test3",
                "e"
        });

        assertThat(parser.getValueOrDefault("positional1"), equalTo("1"));
        assertThat(parser.getValueOrDefault("positional2"), equalTo("b"));
        assertThat(parser.getValueOrDefault("positional3"), equalTo("3"));
        assertThat(parser.hasArgument("--test1"), equalTo(true));
        assertThat(parser.getValueOrDefault("--test1"), equalTo(""));
        assertThat(parser.getValueOrDefault("--test2"), equalTo("test"));
        assertThat(parser.getValueOrDefault("--test3"), equalTo("a"));
    }

    @Test
    void parseWithTooManyPositionalArguments() {
        CommandLineParser parser = createParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(new String[] {
                        "1",
                        "b",
                        "3",
                        "4",
                        "--test1",
                        "--test2=test",
                        "--test3",
                        "c"
                }));

        assertThat(e.getMessage(), equalTo("Invalid argument: Too many positional arguments given"));
    }

    @Test
    void parseWithNonexistentFlag() {
        CommandLineParser parser = createParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(new String[] {
                        "1",
                        "2",
                        "3",
                        "--test1",
                        "--test2=test",
                        "--test3",
                        "c",
                        "--test4"
                }));

        assertThat(e.getMessage(), equalTo("Invalid argument: --test4"));
    }

    @Test
    void addInvalidFlagArgument() {
        CommandLineParser parser = createParser();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> parser.addFlag("test", "Test argument", false));

        assertThat(e.getMessage(), equalTo("Invalid argument: Flag name must start with \"-\""));
    }

    @Test
    void formatStringWithEmptyTextReturnsEmptyString() {
        assertThat(CommandLineParser.formatString("", 80), equalTo(""));
    }

    @Test
    void formatStringWithCommandLineParser() {
        String text = "This is a formatted string that breaks on column 20, where each line has a prefix."
                + System.lineSeparator() + "This is on a new line.";

        assertThat(CommandLineParser.formatString(text, 20, "%", true), equalTo(
                "%This is a \r\n" +
                        "%formatted string \r\n" +
                        "%that breaks on \r\n" +
                        "%column 20, where \r\n" +
                        "%each line has a \r\n" +
                        "%prefix. \r\n" +
                        "%This is on a new \r\n" +
                        "%line. "
        ));
    }

    @Test
    void printUsageInformationWithPrologue() {
        CommandLineParser parser = createParser();
        String prologue = "This is a prologue.";

        parser.setPrologue(prologue);
        assertThat(parser.getUsageInformation(), containsString(prologue));
    }

    @Test
    void printUsageInformationWithEpilogue() {
        CommandLineParser parser = createParser();
        String epilogue = "This is an epilogue.";

        parser.setEpilogue(epilogue);
        assertThat(parser.getUsageInformation(), containsString(epilogue));
    }
}
