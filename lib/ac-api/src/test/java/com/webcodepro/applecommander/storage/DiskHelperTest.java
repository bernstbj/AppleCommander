/*
 * AppleCommander - An Apple ][ image utility.
 * Copyright (C) 2002-2022 by Robert Greene
 * robgreene at users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation; either version 2 of the License, or (at your 
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.webcodepro.applecommander.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.webcodepro.applecommander.storage.FormattedDisk.DiskUsage;
import com.webcodepro.applecommander.storage.filters.ApplesoftFileFilter;
import com.webcodepro.applecommander.storage.filters.BinaryFileFilter;
import com.webcodepro.applecommander.storage.filters.DisassemblyFileFilter;
import com.webcodepro.applecommander.storage.filters.GraphicsFileFilter;
import com.webcodepro.applecommander.storage.filters.IntegerBasicFileFilter;
import com.webcodepro.applecommander.storage.filters.TextFileFilter;
import com.webcodepro.applecommander.testconfig.TestConfig;

/**
 * Test Disk and FormattedDisk for read.
 * <p>
 * Date created: Oct 3, 2002 11:35:26 PM
 * @author Rob Greene
 */
public class DiskHelperTest {
	private TestConfig config = TestConfig.getInstance();

	@Test
	public void testLoadDos33() throws IOException, DiskException {
		FormattedDisk[] disks = showDirectory(config.getDiskDir() +
				"/DOS 3.3.po"); //$NON-NLS-1$
		assertApplesoftFile(disks[0], "HELLO"); //$NON-NLS-1$
		assertIntegerFile(disks[0], "ANIMALS"); //$NON-NLS-1$
		assertTextFile(disks[0], "APPLE PROMS"); //$NON-NLS-1$
		assertBinaryFile(disks[0], "BOOT13"); //$NON-NLS-1$
	}

	@Test
	public void testLoadMaster() throws IOException, DiskException {
		showDirectory(config.getDiskDir() + "/MASTER.DSK"); //$NON-NLS-1$
	}
	
	@Test
	public void testLoadGalacticAttack1() throws IOException, DiskException {
		showDirectory(config.getDiskDir() + "/galatt.dsk"); //$NON-NLS-1$
	}
	
	@Test
	public void testLoadProdos() throws IOException, DiskException {
		FormattedDisk[] disks = showDirectory(config.getDiskDir() + "/Prodos.dsk"); //$NON-NLS-1$
		assertApplesoftFile(disks[0], "COPY.ME"); //$NON-NLS-1$
		assertBinaryFile(disks[0], "SETTINGS"); //$NON-NLS-1$
		assertDisassemblyFile(disks[0], "PRODOS"); //$NON-NLS-1$
	}
	
	@Test
	public void testLoadMarbleMadness() throws IOException, DiskException {
		showDirectory(config.getDiskDir() 
				+ "/Marble Madness (1985)(Electronic Arts).2mg"); //$NON-NLS-1$
	}
	
	@Test
	public void testRdosBoot() throws IOException, DiskException {
		showDirectory(config.getDiskDir() + "/RDOSboot.dsk"); //$NON-NLS-1$
	}

	@Test
	public void testSsiSave() throws IOException, DiskException {
		showDirectory(config.getDiskDir() + "/SSIsave.dsk"); //$NON-NLS-1$
	}

	@Test
	public void testPhan2d1() throws IOException, DiskException {
		FormattedDisk[] disks = showDirectory(config.getDiskDir() 
				+ "/phan2d1.dsk"); //$NON-NLS-1$
		assertApplesoftFile(disks[0], "PHANTASIE II"); //$NON-NLS-1$
		assertBinaryFile(disks[0], "TWN21"); //$NON-NLS-1$
		assertTextFile(disks[0], "ITEM"); //$NON-NLS-1$
		assertGraphicsFile(disks[0], "ICE DRAGON"); //$NON-NLS-1$
	}

	@Test
	public void testPhan2d2() throws IOException, DiskException {
		showDirectory(config.getDiskDir() + "/phan2d2.dsk"); //$NON-NLS-1$
	}

	@Test
	public void testPhantasie1() throws IOException, DiskException {
		showDirectory(config.getDiskDir() + "/Phantasie1.dsk"); //$NON-NLS-1$
	}

	@Test
	public void testPhantasie2() throws IOException, DiskException {
		showDirectory(config.getDiskDir() + "/Phantasie2.dsk"); //$NON-NLS-1$
	}

	@Test
	public void testCavernsOfFreitag() throws IOException, DiskException {
		FormattedDisk[] disks = showDirectory(config.getDiskDir() 
				+ "/CavernsOfFreitag.dsk"); //$NON-NLS-1$
		assertGraphicsFile(disks[0], "TITLE.PIC"); //$NON-NLS-1$
	}
	
	@Test
	public void testUniDosD3110() throws IOException, DiskException {
		showDirectory(config.getDiskDir() 
				+ "/D3110.dsk"); //$NON-NLS-1$
	}

	@Test
	public void testUniDosD3151() throws IOException, DiskException {
		showDirectory(config.getDiskDir() 
				+ "/D3151.dsk"); //$NON-NLS-1$
	}
	
	protected FormattedDisk[] showDirectory(String imageName) throws IOException, DiskException {
		Disk disk = new Disk(imageName);
		FormattedDisk[] formattedDisks = disk.getFormattedDisks();
		for (int i=0; i<formattedDisks.length; i++) {
			FormattedDisk formattedDisk = formattedDisks[i];
			System.out.println();
			System.out.println(formattedDisk.getDiskName());
			List<FileEntry> files = formattedDisk.getFiles();
			if (files != null) {
				showFiles(files, ""); //$NON-NLS-1$
			}
			System.out.println(formattedDisk.getFreeSpace() + " bytes free."); //$NON-NLS-1$
			System.out.println(formattedDisk.getUsedSpace() + " bytes used."); //$NON-NLS-1$
			System.out.println("This disk " + (formattedDisk.canHaveDirectories() ? "does" : "does not") + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				" support directories."); //$NON-NLS-1$
			System.out.println("This disk is formatted in the " + formattedDisk.getFormat() + " format."); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println();
			
			showDiskUsage(formattedDisk);
		}
		return formattedDisks;
	}
	
	protected void showFiles(List<FileEntry> files, String indent) throws DiskException {
		for (int i=0; i<files.size(); i++) {
			FileEntry entry = files.get(i);
			if (!entry.isDeleted()) {
				List<String> data = entry.getFileColumnData(FormattedDisk.FILE_DISPLAY_NATIVE);
				System.out.print(indent);
				for (int d=0; d<data.size(); d++) {
					System.out.print(data.get(d));
					System.out.print(" "); //$NON-NLS-1$
				}
				System.out.println();
			}
			if (entry.isDirectory()) {
				showFiles(((DirectoryEntry)entry).getFiles(), indent + "  "); //$NON-NLS-1$
			}
		}
	}
	
	protected void showDiskUsage(FormattedDisk disk) {
		int[] dimensions = disk.getBitmapDimensions();
		DiskUsage usage = disk.getDiskUsage();
		if (usage == null) {
			System.out.println("A bitmap is not available."); //$NON-NLS-1$
			return;
		}
		if (dimensions == null) {
			int i=0;
			while (usage.hasNext()) {
				if (i > 0 && i % 80 == 0) System.out.println();
				usage.next();
				System.out.print(usage.isFree() ? "." : "U"); //$NON-NLS-1$ //$NON-NLS-2$
				i++;
			}
			System.out.println();
		} else {
			for (int y=dimensions[0]-1; y>=0; y--) {
				for (int x=0; x<dimensions[1]; x++) {
					usage.next();
					System.out.print(usage.isFree() ? "." : "U"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				System.out.println();
			}
		}
		System.out.println("U = used, . = free"); //$NON-NLS-1$
	}
	
	protected void assertApplesoftFile(FormattedDisk disk, String filename) throws DiskException {
		assertNotNull(filename + " test: Disk should not be null", disk); //$NON-NLS-1$
		FileEntry fileEntry = disk.getFile(filename);
		assertNotNull(filename + " test: File not found", disk); //$NON-NLS-1$
		assertTrue("ApplesoftFileFilter was not chosen",  //$NON-NLS-1$
			fileEntry.getSuggestedFilter() instanceof ApplesoftFileFilter);
	}

	protected void assertIntegerFile(FormattedDisk disk, String filename) throws DiskException {
		assertNotNull(filename + " test: Disk should not be null", disk); //$NON-NLS-1$
		FileEntry fileEntry = disk.getFile(filename);
		assertNotNull(filename + " test: File not found", disk); //$NON-NLS-1$
		assertTrue("IntegerBasicFileFilter was not chosen",  //$NON-NLS-1$
			fileEntry.getSuggestedFilter() instanceof IntegerBasicFileFilter);
	}
	
	protected void assertTextFile(FormattedDisk disk, String filename) throws DiskException {
		assertNotNull(filename + " test: Disk should not be null", disk); //$NON-NLS-1$
		FileEntry fileEntry = disk.getFile(filename);
		assertNotNull(filename + " test: File not found", disk); //$NON-NLS-1$
		assertTrue("TextFileFilter was not chosen",  //$NON-NLS-1$
			fileEntry.getSuggestedFilter() instanceof TextFileFilter);
	}
	
	protected void assertBinaryFile(FormattedDisk disk, String filename) throws DiskException {
		assertNotNull(filename + " test: Disk should not be null", disk); //$NON-NLS-1$
		FileEntry fileEntry = disk.getFile(filename);
		assertNotNull(filename + " test: File not found", disk); //$NON-NLS-1$
		assertTrue("BinaryFileFilter was not chosen",  //$NON-NLS-1$
			fileEntry.getSuggestedFilter() instanceof BinaryFileFilter);
	}
	
	protected void assertDisassemblyFile(FormattedDisk disk, String filename) throws DiskException {
        assertNotNull(filename + " test: Disk should not be null", disk);
        FileEntry fileEntry = disk.getFile(filename);
        assertNotNull(filename + " test: File not found", disk);
        assertTrue("DisassemblyFileFilter was not chosen",
            fileEntry.getSuggestedFilter() instanceof DisassemblyFileFilter);
	}
	
	protected void assertGraphicsFile(FormattedDisk disk, String filename) throws DiskException {
		assertNotNull(filename + " test: Disk should not be null", disk); //$NON-NLS-1$
		FileEntry fileEntry = disk.getFile(filename);
		assertNotNull(filename + " test: File not found", disk); //$NON-NLS-1$
		assertTrue("GraphicsFileFilter was not chosen",  //$NON-NLS-1$
			fileEntry.getSuggestedFilter() instanceof GraphicsFileFilter);
	}
}
