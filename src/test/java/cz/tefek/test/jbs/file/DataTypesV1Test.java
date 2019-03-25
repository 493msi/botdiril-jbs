package cz.tefek.test.jbs.file;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import cz.tefek.jbs.file.JBSDataTypesVersion1;

public class DataTypesV1Test
{
    private final String pathName = "dummy.jbs";
    RandomAccessFile dummyFile;

    @Before
    public void setUp() throws Exception
    {
        this.dummyFile = new RandomAccessFile(this.pathName, "rw");
    }

    @After
    public void tearDown() throws Exception
    {
        this.dummyFile.close();
        new File(this.pathName).delete();
    }

    @Test
    public void testByte() throws IOException
    {
        JBSDataTypesVersion1.byte8.write(this.dummyFile, (byte) 1);
        JBSDataTypesVersion1.byte8.write(this.dummyFile, (byte) 3);
        JBSDataTypesVersion1.byte8.write(this.dummyFile, (byte) 130);

        this.dummyFile.seek(0);

        assertEquals((byte) JBSDataTypesVersion1.byte8.read(this.dummyFile), (byte) 1);
        assertEquals((byte) JBSDataTypesVersion1.byte8.read(this.dummyFile), (byte) 3);
        assertEquals((byte) JBSDataTypesVersion1.byte8.read(this.dummyFile), (byte) 130);
    }

    @Test
    public void testByteArray() throws IOException
    {
        byte[] data = { 0, 4, 9, 15, 22, 29 };

        JBSDataTypesVersion1.bytesV256.write(this.dummyFile, data);

        this.dummyFile.seek(0);

        assertArrayEquals(JBSDataTypesVersion1.bytesV256.read(this.dummyFile), data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testByteArrayTooLong() throws IOException
    {
        byte[] data = new byte[300];

        JBSDataTypesVersion1.bytesV256.write(this.dummyFile, data);
    }

    @Test
    public void testDoubles() throws IOException
    {
        JBSDataTypesVersion1.double64.write(this.dummyFile, -1.5151);
        JBSDataTypesVersion1.double64.write(this.dummyFile, 6.451152);
        JBSDataTypesVersion1.double64.write(this.dummyFile, -451455.0);

        this.dummyFile.seek(0);

        assertEquals(JBSDataTypesVersion1.double64.read(this.dummyFile), -1.5151, 0.0);
        assertEquals(JBSDataTypesVersion1.double64.read(this.dummyFile), 6.451152, 0.0);
        assertEquals(JBSDataTypesVersion1.double64.read(this.dummyFile), -451455.0, 0.0);
    }

    @Test
    public void testFloats() throws IOException
    {
        JBSDataTypesVersion1.float32.write(this.dummyFile, -1.5151f);
        JBSDataTypesVersion1.float32.write(this.dummyFile, 6.451152f);
        JBSDataTypesVersion1.float32.write(this.dummyFile, -451455f);

        this.dummyFile.seek(0);

        assertEquals(JBSDataTypesVersion1.float32.read(this.dummyFile), -1.5151f, 0.0f);
        assertEquals(JBSDataTypesVersion1.float32.read(this.dummyFile), 6.451152f, 0.0f);
        assertEquals(JBSDataTypesVersion1.float32.read(this.dummyFile), -451455f, 0.0f);
    }

    @Test
    public void testIntegers() throws IOException
    {
        JBSDataTypesVersion1.int32.write(this.dummyFile, 0x10203040);
        JBSDataTypesVersion1.int32.write(this.dummyFile, 3);
        JBSDataTypesVersion1.int32.write(this.dummyFile, -451455);

        this.dummyFile.seek(0);

        assertEquals((int) JBSDataTypesVersion1.int32.read(this.dummyFile), 0x10203040);
        assertEquals((int) JBSDataTypesVersion1.int32.read(this.dummyFile), 3);
        assertEquals((int) JBSDataTypesVersion1.int32.read(this.dummyFile), -451455);
    }

    @Test
    public void testLongs() throws IOException
    {
        JBSDataTypesVersion1.long64.write(this.dummyFile, 210L);
        JBSDataTypesVersion1.long64.write(this.dummyFile, -53L);
        JBSDataTypesVersion1.long64.write(this.dummyFile, 15515151515511L);

        this.dummyFile.seek(0);

        assertEquals((long) JBSDataTypesVersion1.long64.read(this.dummyFile), 210L);
        assertEquals((long) JBSDataTypesVersion1.long64.read(this.dummyFile), -53L);
        assertEquals((long) JBSDataTypesVersion1.long64.read(this.dummyFile), 15515151515511L);
    }

    @Test
    public void testShorts() throws IOException
    {
        JBSDataTypesVersion1.short16.write(this.dummyFile, (short) 210);
        JBSDataTypesVersion1.short16.write(this.dummyFile, (short) -53);
        JBSDataTypesVersion1.short16.write(this.dummyFile, (short) 10);

        this.dummyFile.seek(0);

        assertEquals((short) JBSDataTypesVersion1.short16.read(this.dummyFile), (short) 210);
        assertEquals((short) JBSDataTypesVersion1.short16.read(this.dummyFile), (short) -53);
        assertEquals((short) JBSDataTypesVersion1.short16.read(this.dummyFile), (short) 10);
    }

    @Test
    public void testStrings() throws IOException
    {
        JBSDataTypesVersion1.utfStringV.write(this.dummyFile, "");
        JBSDataTypesVersion1.utfStringV.write(this.dummyFile, "Non Empty String");
        JBSDataTypesVersion1.utfStringV.write(this.dummyFile, "TEST");

        this.dummyFile.seek(0);

        assertEquals(JBSDataTypesVersion1.utfStringV.read(this.dummyFile), "");
        assertEquals(JBSDataTypesVersion1.utfStringV.read(this.dummyFile), "Non Empty String");
        assertEquals(JBSDataTypesVersion1.utfStringV.read(this.dummyFile), "TEST");
    }
}
