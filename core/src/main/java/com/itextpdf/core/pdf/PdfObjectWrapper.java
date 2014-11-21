package com.itextpdf.core.pdf;

import com.itextpdf.basics.PdfException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PdfObjectWrapper<T extends PdfObject> {

    protected T pdfObject = null;

    public PdfObjectWrapper(T pdfObject) {
        this.pdfObject = pdfObject;
    }

    public PdfObjectWrapper(T pdfObject, PdfDocument pdfDocument) {
        this(pdfObject);
        pdfObject.makeIndirect(pdfDocument);
    }

    public T getPdfObject() {
        return pdfObject;
    }

    public void flush() throws PdfException {
        pdfObject.flush();
    }

    public boolean isFlushed() {
        return pdfObject.isFlushed();
    }

    public PdfDocument getDocument() {
        return pdfObject.getDocument();
    }

    public <T extends PdfObjectWrapper> T copy(PdfDocument document) throws PdfException {
        throw new NotImplementedException();
    }

    public <T extends PdfObjectWrapper> T copy() throws PdfException {
        return copy(getDocument());
    }

}
