package cz.tefek.jbs.file;

import java.util.Arrays;

import java.io.IOException;
import java.io.RandomAccessFile;

public class JBSHeaderVersion1
{
    private static final byte[] MAGIC_NUMBER = MagicConstants.MAGIC_NUMBER;
    private static final byte MAJOR_VERSION = 1;
    private static final int ENDIANNESS_CHECK = MagicConstants.ENDIANNES_CHECK;

    public static boolean matchHeader(RandomAccessFile raf) throws IOException
    {
        byte[] magicNumber = new byte[3];
        raf.read(magicNumber);

        if (!Arrays.equals(magicNumber, MAGIC_NUMBER))
        {
            return false;
        }

        byte majorVersion = raf.readByte();

        if (majorVersion != MAJOR_VERSION)
        {
            return false;
        }

        int endianness = raf.readInt();

        if (endianness != ENDIANNESS_CHECK)
        {
            throw new IOException("Mismatched endianness flag.");
        }

        return true;
    }

    public static void writeHeader(RandomAccessFile raf) throws IOException
    {
        raf.write(MAGIC_NUMBER);
        raf.write(MAJOR_VERSION);
        raf.writeInt(ENDIANNESS_CHECK);
    }
}
