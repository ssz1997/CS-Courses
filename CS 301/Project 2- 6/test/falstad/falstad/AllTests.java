package falstad;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BasicRobotTest.class, ExplorerTest.class, PledgeTest.class, WallFollowerTest.class, WizardTest.class })
public class AllTests {

}
