package jsf.test;

import org.junit.Before;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class ShitTest {

    @Before
    public void prepare() {
        setScriptingEnabled(false);
        setBaseUrl("http://localhost:8080/");
    }

    @Test
    public void testWelcomePage() {
        beginAt("welcome.xhtml");
        assertElementPresent("head");
        assertElementPresent("main");
        assertElementPresent("footer");
        assertTitleEquals("Web Lab 3 Home");
        assertButtonPresent("mainForm:index");
        assertElementPresent("normClock");
        assertElementPresent("time-container");
    }

    @Test
    public void testIndexPage() {
        beginAt("index.xhtml");
        assertElementPresent("head");
        assertElementPresent("user");
        assertElementPresent("graphFrame");
        assertElementPresent("resFrame");
        assertElementPresent("footer");
        assertTitleEquals("Web Lab 3 Main");
        assertButtonPresent("calcForm:send");
        assertButtonPresent("calcForm:reset");
        assertButtonPresent("calcForm:back");
        assertTablePresent("calcForm:x");
        for (int i = -4; i <= 4; i++) {
            assertLabelMatches(i + "");
        }
        assertElementPresent("calcForm:y_input");
        setTextField("calcForm:y_input", "0.0");
        assertTextInElement("calcForm:y_input", "0.0");
        assertElementPresent("calcForm:r_input");
        setTextField("calcForm:r_input", "0.85");
        assertTextInElement("calcForm:r_input", "0.85");
        assertElementPresent("calcForm:Rslider");

        assertElementPresent("graph");
        assertElementPresent("area");
        assertElementPresent("resTable");
    }

    @Test
    public void testNavi() {
        beginAt("welcome.xhtml");
        assertTitleEquals("Web Lab 3 Home");
        clickButton("mainForm:index");
        assertTitleEquals("Web Lab 3 Main");
    }

    @Test
    public void testForm() {
        beginAt("index.xhtml");
        setTextField("calcForm:x", "1");
        setTextField("calcForm:y_hinput", "-1");
        clickButton("calcForm:send");
    }
}
