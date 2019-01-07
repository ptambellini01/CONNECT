/*
 * Copyright (c) 2009-2019, United States Government, as represented by the Secretary of Health and Human Services.
 *  All rights reserved.
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
package gov.hhs.fha.nhinc.patientlocationquery.entity;

import gov.hhs.fha.nhinc.orchestration.Orchestratable;
import gov.hhs.fha.nhinc.orchestration.OutboundDelegate;
import gov.hhs.fha.nhinc.orchestration.OutboundOrchestratable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundPatientLocationQueryDelegate implements OutboundDelegate {

    private static final Logger LOG = LoggerFactory.getLogger(OutboundPatientLocationQueryDelegate.class);

    @Override
    public Orchestratable process(Orchestratable message) {
        return process(message);
    }

    @Override
    public OutboundOrchestratable process(OutboundOrchestratable message) {
        if (message instanceof OutboundPatientLocationQueryOrchestratable) {
            LOG.debug("processing PLQ orchestratable ");
            OutboundPatientLocationQueryOrchestratable msg = (OutboundPatientLocationQueryOrchestratable) message;

            return (OutboundOrchestratable) new OutboundPatientLocationQueryOrchestrationContextBuilder()
                .withAssertionType(msg.getAssertion())
                .withNhinDelegate(msg.getDelegate())
                .withRequest(msg.getRequest())
                .withTarget(msg.getTarget())
                .build()
                .execute();

        } else {
            LOG.error("message is not an instance of OutboundPatientLocationQueryOrchestratable!");
            throw new IllegalArgumentException("Message is not a Patient Location Query Orchestratable instance");
        }
    }

    @Override
    public void createErrorResponse(OutboundOrchestratable message, String error) {
        // PLQ does not have any sort of element for error responses and therefore this method cannot be implemented
        // We will be pushing the error on the console instead. (Or just simply not use this method)

        if (message == null) {
            LOG.debug("OutboundOrchestratable was null");
            return;
        }
        LOG.error("Processing Outbound Patient Location failed. Reason: {}", error);
    }
}
