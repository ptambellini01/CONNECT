/**
 * @author achidamb
 *
 */
/**
 * 
 */
package gov.hhs.fha.nhinc.docsubmission.adapter.deferred.response.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.hhs.fha.nhinc.aspect.AdapterDelegationEvent;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.docsubmission.aspect.DocSubmissionArgTransformerBuilder;
import gov.hhs.fha.nhinc.docsubmission.aspect.DocSubmissionBaseEventDescriptionBuilder;

import java.lang.reflect.Method;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

import org.junit.Test;

/**
 * @author achidamb
 *
 */
public class AdapterDocSubmissionDeferredResponseProxyJavaImplTest {
    @Test
    public void hasAdapterDelegationEvent() throws Exception {
        Class<AdapterDocSubmissionDeferredResponseProxyJavaImpl> clazz = 
                AdapterDocSubmissionDeferredResponseProxyJavaImpl.class;
        Method method = clazz.getMethod("provideAndRegisterDocumentSetBResponse", RegistryResponseType.class,
                AssertionType.class);
        AdapterDelegationEvent annotation = method.getAnnotation(AdapterDelegationEvent.class);
        assertNotNull(annotation);
        assertEquals(DocSubmissionBaseEventDescriptionBuilder.class, annotation.beforeBuilder());
        assertEquals(DocSubmissionArgTransformerBuilder.class, annotation.afterReturningBuilder());
        assertEquals("Document Submission Deferred Response", annotation.serviceType());
        assertEquals("", annotation.version());
    }
}
