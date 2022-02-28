package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner cucumber-jvm project
 * This class that will be running by system
 */


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = {"steps"},
    plugin = {"pretty", "json:target/cucumber-reports/cucumber.json",
        "html:target/cucumber-reports/index.html"},
    monochrome = true
)
public class Runner {
}
