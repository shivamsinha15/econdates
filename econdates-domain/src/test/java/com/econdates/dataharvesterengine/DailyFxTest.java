package com.econdates.dataharvesterengine;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/*TestContext Framework provides annotation driven configuration using @ContextConfiguration to specify which configuration files to load the applications context.
 *By default The application context is loaded using GenericXmlContextLoader and loads the context for the XML configuration class allowing you to use @Autowired, @Resources and @Inject.
 *Spring 3.0 introduced support for Java-based configuration via @Configuration classes, but the TestsContext framework did not supply an appropriate ContextLoader to support @ContextLoader until now.
 *Spring 3.1 M2 introduces a new AnnotationConfigContextLoader for this purpose,@ContextConfiguration annotation now supports @Configuration classes via the classes attribute
 *
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataHarvesterConfig.class, loader = AnnotationConfigContextLoader.class)
public class DailyFxTest {

	@Autowired
	private HarvestLocation dailyFx;

}


