import org.junit.Assert;
import org.junit.Test;
import spb.edu.DMat;
import spb.edu.SMat;

import java.io.IOException;
import java.io.InterruptedIOException;

import static org.junit.Assert.*;

/**
 * Created by Игорь on 29.12.2016.
 */
public class MatrixTestTest {
    DMat d1;
    DMat d2;
    DMat dd;
    SMat s1;
    SMat s2;
    SMat ss;
    SMat sd;
    SMat ds;
    public MatrixTestTest() {
        try {
            d1 = new DMat("1.txt");
            d2 = new DMat("3.txt");
            s1 = new SMat("31.txt");
            s2 = new SMat("34.txt");
            dd = new DMat("mulDDResult.txt");
            ss = new SMat("mulSSResult.txt");
            sd = new SMat("mulSDResult.txt");
            ds = new SMat("mulDSResult.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void DDmulTest() throws IOException {
        DMat d3 = (DMat)d1.mul(d2);
        Assert.assertEquals(dd, d3);
    }

    @Test
    public void SSmulTest() throws IOException {
        SMat s3 = (SMat)s1.mul(s2);
        Assert.assertEquals(ss, s3);
    }

    @Test
    public void SDmulTest() throws IOException {
        SMat d3 = (SMat)s1.mul(d1);
        Assert.assertEquals(sd, d3);
    }

    @Test
    public void DSmulTest() throws IOException {
        SMat d3 = (SMat)d1.mul(s1);
        Assert.assertEquals(ds, d3);
    }

    @Test
    public void DDpmulTest() throws IOException, InterruptedException{
        DMat d3 = (DMat)d1.pmul(d2);
        Assert.assertEquals(dd, d3);
    }

    @Test
    public void SSpmulTest() throws IOException, InterruptedException{
        SMat s3 = s1.pmulSS(s2);
        Assert.assertEquals(ss, s3);
    }


}