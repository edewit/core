package org.example;

import javax.inject.Inject;

import org.example.simple.SimpleService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
public class SingleAddonTest
{
   @Deployment
   public static ForgeArchive getDeployment()
   {
      ForgeArchive archive = ShrinkWrap.create(ForgeArchive.class)
               .addClasses(SimpleService.class)
               .addAsManifestResource(new StringAsset(""), ArchivePaths.create("beans.xml"))
               .setAsForgeXML(new StringAsset("<addon/>"));

      return archive;
   }

   @Inject
   private SimpleService simple;

   @Test
   public void testContainerInjection()
   {
      Assert.assertNotNull(simple);
   }

   @Test
   public void testLifecycle() throws Exception
   {
      Assert.assertTrue(simple.isStartupObserved());
      Assert.assertTrue(simple.isPostStartupObserved());
      Assert.assertFalse(simple.isPreShutdownObserved());
      Assert.assertFalse(simple.isShutdownObserved());
   }

}