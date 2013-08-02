package org.jiucai.appframework.common.util;

/**
 * downloaded from http://stackoverflow.com/questions/5030971/how-to-know-the-original-size-width-and-height-of-a-swf-file-with-java
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * This class will read just enough of a SWF file's header to glean the
 * essential meta-data about the animation.
 * 
 * This is based on <a href="http://www.adobe.com/devnet/swf.html"> SWF File
 * Format Specification (version 10)</a>.
 * 
 * @author Resnbl Software
 * @since Mar 22, 2011
 */
public class SwfUtil extends BaseUtil {

	// Instantiate through getInfo() methods
	private SwfUtil() {
	}

	/**
	 * Get the header info for a (potential) SWF file specified by a file path String.
	 * @param path containing path to file.
	 * @return SWFinfo object or null if file not found or not SWF.
	 * @throws FileNotFoundException
	 */
	public static SwfInfo getInfo(String path) throws FileNotFoundException {

		return getInfo(new File(path));
	}

	private static SwfInfo getSwfUtilInternal(byte[] hdr) {
		if (hdr == null) {
			return null;
		}

		SwfInfo info = new SwfInfo();

		if ('C' == hdr[0]) {
			info.setCompressed(true);
		} else {
			info.setCompressed(false);
		}

		info.setVersion(hdr[3]);

		info.setSize(hdr[4] & 0xFF | (hdr[5] & 0xFF) << 8
				| (hdr[6] & 0xFF) << 16 | hdr[7] << 24);

		BitReader rdr = new BitReader(hdr, SwfInfo.UNCOMP_HDR_LEN);

		int[] dims = decodeRect(rdr);

		info.setWidth((dims[1] - dims[0]) / 20); // convert twips to pixels
		info.setHeight((dims[3] - dims[2]) / 20);

		info.setFps((float) rdr.uI16() / 256f); // 8.8 fixed-point format
		info.setFrameCount(rdr.uI16());

		return info;
	}

	/*
	 * Read just enough of the file for our purposes
	 */
	private static byte[] getByteInternal(InputStream fis) throws IOException {

		byte[] bytes = new byte[128]; // should be enough...

		if (fis.read(bytes) < bytes.length) {
			bytes = null; // too few bytes to be a SWF
		} else if (bytes[0] == 'C' && bytes[1] == 'W' && bytes[2] == 'S') {
			bytes = expand(bytes, SwfInfo.UNCOMP_HDR_LEN); // compressed SWF
		} else if (bytes[0] != 'F' || bytes[1] != 'W' || bytes[2] != 'S') {
			bytes = null; // not a SWF
		} else {
			// else uncompressed SWF
		}

		return bytes;

	}

	/**
	 * Get the header info for a (potential) SWF file specified by a File object.
	 * 
	 * @param file File pointing to the desired SWF file.
	 * 
	 * @return SWFinfo object or null if file not found or not SWF.
	 * @throws FileNotFoundException
	 */
	public static SwfInfo getInfo(File file) throws FileNotFoundException {
		SwfInfo info = null;
		try {
			info = getInfo(new FileInputStream(file));
		} catch (IOException e) {
			log.error("IOException: " + ExceptionUtils.getFullStackTrace(e));
		}

		return info;
	}

	/**
	 * 获取文件输入流
	 * 
	 * @param fis
	 * @return SwfInfo
	 * @throws IOException
	 */
	public static SwfInfo getInfo(InputStream fis) throws IOException {
		SwfInfo info = null;
		byte[] hdr = getByteInternal(fis);
		info = getSwfUtilInternal(hdr);

		return info;
	}

	/*
	 * All of the file past the initial {@link UNCOMP_HDR_LEN} bytes are
	 * compressed. Decompress as much as is in the buffer already read and
	 * return them, overlaying the original uncompressed data.
	 * 
	 * Fortunately, the compression algorithm used by Flash is the ZLIB
	 * standard, i.e., the same algorithms used to compress .jar files
	 */
	private static byte[] expand(byte[] bytes, int skip) {
		byte[] newBytes = new byte[bytes.length - skip];
		Inflater inflater = new Inflater();

		inflater.setInput(bytes, skip, newBytes.length);
		try {
			int outCount = inflater.inflate(newBytes);
			System.arraycopy(newBytes, 0, bytes, skip, outCount);
			Arrays.fill(bytes, skip + outCount, bytes.length, (byte) 0);
			return bytes;
		} catch (DataFormatException e) {
		}

		return null;
	}

	/**
	 * Return Stage frame rectangle as 4 <code>int</code>s: LRTB
	 * 
	 * Note the values are in TWIPS (= 1/20th of a pixel)
	 * 
	 * I do this to avoid a loading the <code>Rect</code> class which is an
	 * <code>android.graphics</code> class, and not available if you want to
	 * test this with desktop Java.
	 * 
	 * @param rdr
	 * @return
	 */
	private static int[] decodeRect(BitReader rdr) {
		int[] dims = new int[4];
		int nBits = rdr.uBits(5);

		dims[0] = rdr.sBits(nBits); // X min = left always 0
		dims[1] = rdr.sBits(nBits); // X max = right
		dims[2] = rdr.sBits(nBits); // Y min = top always 0
		dims[3] = rdr.sBits(nBits); // Y max = bottom

		return dims;
	}

	// commented out to prevent Eclipse from thinkg this is a standard Java app
	// when used for Android!
	// public static void main(String[] args)
	// {
	// if (args.length == 0)
	// throw new IllegalArgumentException("No swf_file parameter given");
	//
	// File file = new File(args[0]);
	// SWFInfo info = SWFInfo.getInfo(file);
	//
	// if (info != null)
	// {
	// System.out.println("File: " + file);
	// System.out.println("Flash ver: " + info.version + " FPS: " + info.fps +
	// " Frames: " + info.frameCount);
	// System.out.println("File size: " + file.length() + " Compressed: " +
	// info.isCompressed + " Uncompressed size: " + info.size);
	// System.out.println("Dimensions: " + info.width + "x" + info.height);
	// }
	// else
	// System.out.println("File not a .SWF: " + file);
	// }

	/**
	 * Read an arbitrary number of bits from a byte[].
	 * 
	 * This should be turned into a full-featured independant class
	 * (someday...).
	 */
	static class BitReader {
		private byte[] bytes;
		private int byteIdx;
		private int bitIdx = 0;

		/**
		 * Start reading from the beginning of the supplied array.
		 * 
		 * @param bytes
		 *            byte[] to process
		 */
		public BitReader(byte[] bytes) {
			this(bytes, 0);
		}

		/**
		 * Start reading from an arbitrary index into the array.
		 * 
		 * @param bytes
		 *            byte[] to process
		 * @param startIndex
		 *            byte # to start at
		 */
		public BitReader(byte[] bytes, int startIndex) {
			this.bytes = bytes;
			byteIdx = startIndex;
		}

		/**
		 * Fetch the next <code>bitCount</code> bits as an unsigned int.
		 * 
		 * @param bitCount
		 *            # bits to read
		 * @return int
		 */
		public int uBits(int bitCount) {
			int value = 0;

			while (--bitCount >= 0)
				value = value << 1 | getBit();
			return value;
		}

		/**
		 * Fetch the next <code>bitCount</code> bits as a <em>signed</em> int.
		 * 
		 * @param bitCount
		 *            # bits to read
		 * @return int
		 */
		public int sBits(int bitCount) {
			// First bit is the "sign" bit
			int value = getBit() == 0 ? 0 : -1;
			--bitCount;

			while (--bitCount >= 0)
				value = value << 1 | getBit();
			return value;
		}

		// Get the next bit in the array
		private int getBit() {
			int value = (bytes[byteIdx] >> (7 - bitIdx)) & 0x01;

			if (++bitIdx == 8) {
				bitIdx = 0;
				++byteIdx;
			}

			return value;
		}

		/**
		 * Fetch the next 2 "whole" bytes as an unsigned int (little-endian).
		 * 
		 * @return int
		 */
		public int uI16() {
			sync(); // back to "byte-aligned" mode
			return (bytes[byteIdx++] & 0xff) | (bytes[byteIdx++] & 0xff) << 8;
		}

		/**
		 * Bump indexes to the next byte boundary.
		 */
		public void sync() {
			if (bitIdx > 0) {
				++byteIdx;
				bitIdx = 0;
			}
		}
	}

}