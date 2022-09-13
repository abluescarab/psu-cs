package edu.pdx.cs410J.agilston;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project3</code> class.  This is different
 * from <code>Project3IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project3Test {
    @Test
    void readmeCanBeReadAsResource() throws IOException {
        try(
                InputStream readme = Project3.class.getResourceAsStream("README.txt")
        ) {
            assertThat(readme, not(nullValue()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            assertThat(line, containsString("Class:   CS410P - Advanced Programming in Java"));
        }
    }

    @Test
    void validDateTimeFormatReturnsTrue() {
        assertThat(Project3.isValidDateTime("01/01/2022 12:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("1/01/2022 12:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("01/1/2022 12:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("01/01/2022 1:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("1/1/2022 12:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("1/01/2022 1:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("1/1/2022 1:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("13/01/2022 01:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("01/32/2022 01:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("01/01/2022 25:00 am"), equalTo(true));
        assertThat(Project3.isValidDateTime("01/01/2022 01:60 am"), equalTo(true));
    }

    @Test
    void invalidDateTimeFormatReturnsFalse() {
        assertThat(Project3.isValidDateTime("01/01/22 01:00 am"), equalTo(false));
        assertThat(Project3.isValidDateTime("AA/BB/CCCC DD:EE"), equalTo(false));
    }

    @Test
    void validPhoneNumberFormatReturnsTrue() {
        assertThat(Project3.isValidPhoneNumber("555-555-5555"), equalTo(true));
    }

    @Test
    void invalidPhoneNumberFormatReturnsFalse() {
        assertThat(Project3.isValidPhoneNumber("55-555-5555"), equalTo(false));
        assertThat(Project3.isValidPhoneNumber("555-55-5555"), equalTo(false));
        assertThat(Project3.isValidPhoneNumber("555-555-555"), equalTo(false));
        assertThat(Project3.isValidPhoneNumber("(555) 555-5555"), equalTo(false));
        assertThat(Project3.isValidPhoneNumber("+1 (555) 555-5555"), equalTo(false));
        assertThat(Project3.isValidPhoneNumber("+15555555555"), equalTo(false));
        assertThat(Project3.isValidPhoneNumber("+1-555-555-555"), equalTo(false));
    }
}
