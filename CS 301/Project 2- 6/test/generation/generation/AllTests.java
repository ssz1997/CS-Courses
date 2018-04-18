package generation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AllCellsTests.class, CellsTest.class, CellsTestIterator.class, MazeBuilderEllerTest.class,
		MazeFactoryTest.class })
public class AllTests {

}
