package cz.tefek.jbs.file;

import java.io.IOException;
import java.io.RandomAccessFile;

public class JBSDataTypesVersion1
{
    public static abstract class JBSDataType<T>
    {
        private final byte id;

        protected JBSDataType(byte id)
        {
            this.id = id;

            final int maskedID = id & 0xff;

            if (dataTypeMapper[maskedID] != null)
            {
                throw new IllegalStateException(String.format("Duplicate DataType ID! Signature byte 0x%02X is already taken!", maskedID));
            }

            dataTypeMapper[maskedID] = this;
        }

        public byte getID()
        {
            return this.id;
        }

        public abstract T read(RandomAccessFile raf) throws IOException;

        public abstract void write(RandomAccessFile raf, T data) throws IOException;
    }

    private static final JBSDataType<?>[] dataTypeMapper = new JBSDataType[256];

    public static final JBSDataType<byte[]> bytesV256 = new JBSDataType<byte[]>((byte) 0xB0) {
        @Override
        public byte[] read(RandomAccessFile raf) throws IOException
        {
            int length = raf.readByte() & 0xff;
            byte[] data = new byte[length];

            if (raf.read(data) != length)
            {
                throw new IOException("Byte VArray size mismatch!");
            }

            return data;
        }

        @Override
        public void write(RandomAccessFile raf, byte[] data) throws IOException
        {
            if (data.length >= 256)
            {
                throw new IllegalArgumentException("Passed byte[] is too big (>=256).");
            }

            raf.write(data.length);
            raf.write(data);
        }
    };

    public static final JBSDataType<String> utfStringV = new JBSDataType<String>((byte) 0xA1) {
        @Override
        public String read(RandomAccessFile raf) throws IOException
        {
            return raf.readUTF();
        }

        @Override
        public void write(RandomAccessFile raf, String data) throws IOException
        {
            raf.writeUTF(data);
        }
    };

    public static final JBSDataType<Byte> byte8 = new JBSDataType<Byte>((byte) 0xB0) {
        @Override
        public Byte read(RandomAccessFile raf) throws IOException
        {
            return raf.readByte();
        }

        @Override
        public void write(RandomAccessFile raf, Byte data) throws IOException
        {
            raf.write(data & 0xff);
        }
    };

    public static final JBSDataType<Character> char16 = new JBSDataType<Character>((byte) 0xC0) {
        @Override
        public Character read(RandomAccessFile raf) throws IOException
        {
            return raf.readChar();
        }

        @Override
        public void write(RandomAccessFile raf, Character data) throws IOException
        {
            raf.writeChar(data);
        }
    };

    public static final JBSDataType<Short> short16 = new JBSDataType<Short>((byte) 0x12) {
        @Override
        public Short read(RandomAccessFile raf) throws IOException
        {
            return raf.readShort();
        }

        @Override
        public void write(RandomAccessFile raf, Short data) throws IOException
        {
            raf.writeShort(data);
        }
    };

    public static final JBSDataType<Integer> int32 = new JBSDataType<Integer>((byte) 0x14) {
        @Override
        public Integer read(RandomAccessFile raf) throws IOException
        {
            return raf.readInt();
        }

        @Override
        public void write(RandomAccessFile raf, Integer data) throws IOException
        {
            raf.writeInt(data);
        }
    };

    public static final JBSDataType<Long> long64 = new JBSDataType<Long>((byte) 0x18) {
        @Override
        public Long read(RandomAccessFile raf) throws IOException
        {
            return raf.readLong();
        }

        @Override
        public void write(RandomAccessFile raf, Long data) throws IOException
        {
            raf.writeLong(data);
        }
    };

    public static final JBSDataType<Float> float32 = new JBSDataType<Float>((byte) 0xF4) {
        @Override
        public Float read(RandomAccessFile raf) throws IOException
        {
            return raf.readFloat();
        }

        @Override
        public void write(RandomAccessFile raf, Float data) throws IOException
        {
            raf.writeFloat(data);
        }
    };

    public static final JBSDataType<Double> double64 = new JBSDataType<Double>((byte) 0xF8) {
        @Override
        public Double read(RandomAccessFile raf) throws IOException
        {
            return raf.readDouble();
        }

        @Override
        public void write(RandomAccessFile raf, Double data) throws IOException
        {
            raf.writeDouble(data);
        }
    };
}
