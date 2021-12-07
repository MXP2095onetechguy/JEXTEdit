package MXPSQL.JEXTEdit;

/*
JEXTEdit, text editor with web browser
Copyright (C) 2021 MXPSQL

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import java.io.*;
import java.nio.*;
import java.nio.file.*;

import org.apache.commons.codec.binary.Hex;

public class BOMUtils {
	  public static void writeBomFile(Path path, String content) {
	      // Java 8 default UTF-8
	      try (BufferedWriter bw = Files.newBufferedWriter(path)) {
	          bw.write("\ufeff");
	          bw.write(content);
	          bw.newLine();
	          bw.write(content);
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }

	  public static boolean isContainBOM(Path path) throws IOException {

	      if (Files.notExists(path)) {
	          throw new IllegalArgumentException("Path: " + path + " does not exists!");
	      }

	      boolean result = false;

	      byte[] bom = new byte[3];
	      try (InputStream is = new FileInputStream(path.toFile())) {

	          // read 3 bytes of a file.
	          is.read(bom);

	          // BOM encoded as ef bb bf
	          String content = new String(Hex.encodeHex(bom));
	          if ("efbbbf".equalsIgnoreCase(content)) {
	              result = true;
	          }

	      }

	      return result;
	  }

	  public static void removeBom(Path path) throws IOException {

	      if (isContainBOM(path)) {

	          byte[] bytes = Files.readAllBytes(path);

	          ByteBuffer bb = ByteBuffer.wrap(bytes);

	          System.out.println("Found BOM!");

	          byte[] bom = new byte[3];
	          // get the first 3 bytes
	          bb.get(bom, 0, bom.length);

	          // remaining
	          byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
	          bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);

	          System.out.println("Remove the first 3 bytes, and overwrite the file!");

	          // override the same path
	          Files.write(path, contentAfterFirst3Bytes);

	      }

	  }
}
