package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project1Test {
    @Test
    void readmeCanBeReadAsResource() throws IOException {
        try(
                InputStream readme = Project1.class.getResourceAsStream("README.txt")
        ) {
            assertThat(readme, not(nullValue()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            assertThat(line, containsString("Class:   CS410P - Advanced Programming in Java"));
        }
    }

    @Test
    void validDateTimeFormatReturnsTrue() {
        assertThat(Project1.isValidDateTime("01/01/2022 00:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("1/01/2022 00:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("01/1/2022 00:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("01/01/2022 0:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("1/1/2022 00:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("1/01/2022 0:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("1/1/2022 0:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("13/01/2022 01:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("01/32/2022 01:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("01/01/2022 25:00"), equalTo(true));
        assertThat(Project1.isValidDateTime("01/01/2022 01:60"), equalTo(true));
    }

    @Test
    void invalidDateTimeFormatReturnsFalse() {
        assertThat(Project1.isValidDateTime("01/01/22 01:00"), equalTo(false));
        assertThat(Project1.isValidDateTime("AA/BB/CCCC DD:EE"), equalTo(false));
    }

    @Test
    void validPhoneNumberFormatReturnsTrue() {
        assertThat(Project1.isValidPhoneNumber("555-555-5555"), equalTo(true));
    }

    @Test
    void invalidPhoneNumberFormatReturnsFalse() {
        assertThat(Project1.isValidPhoneNumber("55-555-5555"), equalTo(false));
        assertThat(Project1.isValidPhoneNumber("555-55-5555"), equalTo(false));
        assertThat(Project1.isValidPhoneNumber("555-555-555"), equalTo(false));
        assertThat(Project1.isValidPhoneNumber("(555) 555-5555"), equalTo(false));
        assertThat(Project1.isValidPhoneNumber("+1 (555) 555-5555"), equalTo(false));
        assertThat(Project1.isValidPhoneNumber("+15555555555"), equalTo(false));
        assertThat(Project1.isValidPhoneNumber("+1-555-555-555"), equalTo(false));
    }
}
