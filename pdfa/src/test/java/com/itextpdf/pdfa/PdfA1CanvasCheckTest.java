package com.itextpdf.pdfa;

import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import com.itextpdf.kernel.xmp.XMPException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

@Category(IntegrationTest.class)
public class PdfA1CanvasCheckTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/pdfa/";
    public static final String cmpFolder = sourceFolder + "cmp/PdfA1CanvasCheckTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/pdfa/PdfA1CanvasCheckTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Test
    public void canvasCheckTest1() throws IOException, XMPException {
        junitExpectedException.expect(PdfAConformanceException.class);
        junitExpectedException.expectMessage(PdfAConformanceException.GraphicStateStackDepthIsGreaterThan28);

        PdfWriter writer = new PdfWriter(new ByteArrayOutputStream());
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");
        PdfOutputIntent outputIntent = new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is);
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_1B, outputIntent);
        pdfDocument.addNewPage();
        PdfCanvas canvas = new PdfCanvas(pdfDocument.getLastPage());

        for (int i = 0; i < 29; i++) {
            canvas.saveState();
        }

        for (int i = 0; i < 28; i++) {
            canvas.restoreState();
        }

        pdfDocument.close();
    }

    @Test
    public void canvasCheckTest2() throws IOException, XMPException, InterruptedException {
        String outPdf = destinationFolder + "pdfA1b_canvasCheckTest2.pdf";
        String cmpPdf = cmpFolder + "cmp_pdfA1b_canvasCheckTest2.pdf";

        PdfWriter writer = new PdfWriter(outPdf);
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");
        PdfOutputIntent outputIntent = new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is);
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_1B, outputIntent);
        pdfDocument.addNewPage();
        PdfCanvas canvas = new PdfCanvas(pdfDocument.getLastPage());

        for (int i = 0; i < 28; i++) {
            canvas.saveState();
        }

        for (int i = 0; i < 28; i++) {
            canvas.restoreState();
        }

        pdfDocument.close();

        String result = new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff_");
        if (result != null) {
            fail(result);
        }
    }

    @Test
    public void canvasCheckTest3() throws IOException, XMPException {
        junitExpectedException.expect(PdfAConformanceException.class);
        junitExpectedException.expectMessage(PdfAConformanceException.IfSpecifiedRenderingShallBeOneOfTheFollowingRelativecolorimetricAbsolutecolorimetricPerceptualOrSaturation);

        PdfWriter writer = new PdfWriter(new ByteArrayOutputStream());
        InputStream is = new FileInputStream(sourceFolder + "sRGB Color Space Profile.icm");
        PdfOutputIntent outputIntent = new PdfOutputIntent("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", is);
        PdfADocument pdfDocument = new PdfADocument(writer, PdfAConformanceLevel.PDF_A_1B, outputIntent);
        pdfDocument.addNewPage();
        PdfCanvas canvas = new PdfCanvas(pdfDocument.getLastPage());

        canvas.setRenderingIntent(new PdfName("Test"));

        pdfDocument.close();
    }
}
