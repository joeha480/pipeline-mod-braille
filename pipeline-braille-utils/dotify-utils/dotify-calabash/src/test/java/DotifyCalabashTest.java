import java.io.File;

import javax.inject.Inject;

import org.daisy.dotify.api.text.Integer2TextFactoryMakerService;

import org.daisy.maven.xproc.xprocspec.XProcSpecRunner;

import static org.daisy.pipeline.pax.exam.Options.brailleModule;
import static org.daisy.pipeline.pax.exam.Options.calabashConfigFile;
import static org.daisy.pipeline.pax.exam.Options.domTraversalPackage;
import static org.daisy.pipeline.pax.exam.Options.felixDeclarativeServices;
import static org.daisy.pipeline.pax.exam.Options.logbackClassic;
import static org.daisy.pipeline.pax.exam.Options.logbackConfigFile;
import static org.daisy.pipeline.pax.exam.Options.mavenBundle;
import static org.daisy.pipeline.pax.exam.Options.mavenBundlesWithDependencies;
import static org.daisy.pipeline.pax.exam.Options.thisBundle;
import static org.daisy.pipeline.pax.exam.Options.xprocspec;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.util.PathUtils;

import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class DotifyCalabashTest {
	
	@Inject
	private XProcSpecRunner xprocspecRunner;
	
	@Test
	public void runXProcSpec() throws Exception {
		File baseDir = new File(PathUtils.getBaseDir());
		boolean success = xprocspecRunner.run(new File(baseDir, "src/test/xprocspec"),
		                                      new File(baseDir, "target/xprocspec-reports"),
		                                      new File(baseDir, "target/surefire-reports"),
		                                      new File(baseDir, "target/xprocspec"),
		                                      new XProcSpecRunner.Reporter.DefaultReporter());
		assertTrue("XProcSpec tests should run with success", success);
	}
	
	@Inject
	private Integer2TextFactoryMakerService int2textFactory;
	
	@Test
	public void testInt2textFactory() throws Exception {
		assertEquals("tolv", int2textFactory.newInteger2Text("sv-SE").intToText(12));
	}
	
	@Configuration
	public Option[] config() {
		return options(
			logbackConfigFile(),
			calabashConfigFile(),
			domTraversalPackage(),
			felixDeclarativeServices(),
			thisBundle(),
			junitBundles(),
			mavenBundlesWithDependencies(
				// tasks
				mavenBundle("org.daisy.dotify:dotify.api:?"),
				mavenBundle("org.daisy.dotify:dotify.task-api:?"),
				mavenBundle("org.daisy.dotify:dotify.task-runner:?"),
				mavenBundle("org.daisy.dotify:dotify.common:?"),
				mavenBundle("org.daisy.dotify:dotify.task.impl:?"),
				// formatter
				mavenBundle("org.daisy.dotify:dotify.formatter.impl:?"),
				mavenBundle("org.daisy.dotify:dotify.text.impl:?"),
				mavenBundle("org.daisy.dotify:dotify.hyphenator.impl:?"),
				mavenBundle("org.daisy.dotify:dotify.translator.impl:?"),
				// for the query syntax
				brailleModule("css-core"),
				// other
				brailleModule("common-utils"),
				brailleModule("obfl-utils"),
				brailleModule("pef-utils"),
				// logging
				logbackClassic(),
				mavenBundle("org.slf4j:jul-to-slf4j:?"),
				mavenBundle("org.daisy.pipeline:logging-activator:?"),
				// xprocspec
				xprocspec(),
				mavenBundle("org.daisy.maven:xproc-engine-daisy-pipeline:?"))
		);
	}
}
