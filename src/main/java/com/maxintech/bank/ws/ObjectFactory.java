//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.21 at 11:28:48 AM ART 
//


package com.maxintech.bank.ws;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.maxintech.bank.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.maxintech.bank.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAccountResponse }
     * 
     */
    public CreateAccountResponse createCreateAccountResponse() {
        return new CreateAccountResponse();
    }

    /**
     * Create an instance of {@link OperationStatus }
     * 
     */
    public OperationStatus createOperationStatus() {
        return new OperationStatus();
    }

    /**
     * Create an instance of {@link TransferRequest }
     * 
     */
    public TransferRequest createTransferRequest() {
        return new TransferRequest();
    }

    /**
     * Create an instance of {@link CreateAccountRequest }
     * 
     */
    public CreateAccountRequest createCreateAccountRequest() {
        return new CreateAccountRequest();
    }

    /**
     * Create an instance of {@link TransferResponse }
     * 
     */
    public TransferResponse createTransferResponse() {
        return new TransferResponse();
    }

}
