/*
 * Copyright (c) 2009-2019, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package gov.hhs.fha.nhinc.docquery.deferredresults.adapter.proxy;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerException;
import gov.hhs.fha.nhinc.exchangemgr.ExchangeManagerException;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;

public abstract class BaseAdapterDocQueryDeferredProxy implements AdapterDocQueryDeferredProxy {


    private WebServiceProxyHelper oProxyHelper = new WebServiceProxyHelper();

    public WebServiceProxyHelper getWebServiceProxyHelper() {
        return oProxyHelper;
    }

    public void setWebServiceProxyHelper(WebServiceProxyHelper helper) {
        oProxyHelper = helper;
    }

    /**
     * This method returns the URL endpoint based on the ImplementsSpecVersion
     *
     * @param assertion Assertion received.
     * @param serviceName
     * @return The endpoint URL.
     * @throws ConnectionManagerException A ConnectionManagerException if one occurs.
     */
    public String getEndPointFromConnectionManagerByAdapterAPILevel(AssertionType assertion, String serviceName) throws
        ExchangeManagerException {
        String url = null;
        //get the Implements Spec version from the assertion
        if (assertion != null && assertion.getImplementsSpecVersion() != null) {
            //We only support 3.0 for now for Deferred Option Response
            if (assertion.getImplementsSpecVersion().equals(NhincConstants.UDDI_SPEC_VERSION.SPEC_3_0.toString())) {
                //look for ADAPTER_API_LEVEL a1 if not found then look for a0
                url = oProxyHelper.getEndPointFromConnectionManagerByAdapterAPILevel(serviceName,
                    NhincConstants.ADAPTER_API_LEVEL.LEVEL_a1);
                if (NullChecker.isNullish(url)) {
                    url = oProxyHelper.getEndPointFromConnectionManagerByAdapterAPILevel(serviceName,
                        NhincConstants.ADAPTER_API_LEVEL.LEVEL_a0);
                }
            }
        }

        //If the preferred API level is not configured, then return which ever one is available
        if (url == null)
        {
            url = oProxyHelper.getAdapterEndPointFromConnectionManager(serviceName);
        }
        return url;
    }
}